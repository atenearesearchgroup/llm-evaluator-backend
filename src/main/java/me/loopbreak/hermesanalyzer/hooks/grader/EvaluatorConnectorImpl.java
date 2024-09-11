package me.loopbreak.hermesanalyzer.hooks.grader;

import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.classdiagram.uml2cdm.Uml2CdmConverter;
import ca.mcgill.sel.commons.emf.util.ResourceManager;
import ca.mcgill.sel.grading.classdiagram.ClassdiagramGrader;
import ca.mcgill.sel.grading.classdiagram.ClassdiagramGraderAlgorithm;
import ca.mcgill.sel.grading.marks.MarksModel;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.loopbreak.hermesanalyzer.objects.grader.CategoryError;
import me.loopbreak.hermesanalyzer.objects.grader.EvaluationResult;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.MarksCalculator;
import me.loopbreak.hermesanalyzer.objects.grader.helpers.error.ErrorClassifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static ca.mcgill.sel.grading.classdiagram.ClassdiagramGrader.initializeSolutionMarks;

public class EvaluatorConnectorImpl implements EvaluatorConnector {

    private static final EvaluationResult DEFAULT_RESULT = new EvaluationResult(0.0, 0.0, new ArrayList<>(), null);
    private static final String DOT = ".";
    private static final String UML_SUFFIX = ".uml";

    private static LoadingCache<Path, ClassDiagram> classDiagramCache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .softValues()
            .build(new CacheLoader<>() {
                @Override
                public @NotNull ClassDiagram load(@NotNull Path key) throws Exception {
                    ClassDiagram solution = null;
                    File solutionFile = key.toFile();
                    if (key.getFileName().endsWith(UML_SUFFIX)) {
                        solution = Uml2CdmConverter.convertUMLtoCdm(solutionFile);
                    } else {
                        solution = (ClassDiagram) ResourceManager.loadModel(solutionFile);
                    }
                    return solution;
                }
            });
    private static LoadingCache<ClassDiagram, MarksModel> emptyMarksModelCache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .softValues()
            .build(new CacheLoader<>() {
                @Override
                public @NotNull MarksModel load(@NotNull ClassDiagram key) throws Exception {
                    return initializeSolutionMarks(key);
                }
            });

//    private final RestClient client = getClient();

    //    TODO: Implement evaluation logic
    @Override

    public EvaluationResult evaluate(InputStream text, Path solutionFile) {
        MarksModel model = null;
        try {
            model = generateMarks(generateTempFile(text).toAbsolutePath().toString(),
                    solutionFile.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (model == null) {
            System.out.println("Model is null");
            return DEFAULT_RESULT;
        }

        double score = MarksCalculator.of(model).calculateMarks();
        List<CategoryError> errors = ErrorClassifier.of(model).classify();

        return new EvaluationResult(score, model.getMaxPoints(), errors, null);
    }

    private Path generateTempFile(InputStream text) throws IOException {
        String id = UUID.randomUUID().toString();
        Path tempFile = Files.createTempFile(id, ".cdm");
        Files.write(tempFile, text.readAllBytes());
        return tempFile;
    }

    private static MarksModel gradeClassdiagram(Path solutionFile, Path marksModelFile, Path attemptFile) {
        // Load the class diagrams
        ClassDiagram solution = classDiagramCache.getUnchecked(solutionFile);
//        if (solutionFile.getName().endsWith(UML_SUFFIX)) {
//            solution = Uml2CdmConverter.convertUMLtoCdm(solutionFile);
//        } else {
//            solution = (ClassDiagram) ResourceManager.loadModel(solutionFile);
//        }
        ClassDiagram attempt = classDiagramCache.getUnchecked(attemptFile);

//        if (attemptFile.getName().endsWith(UML_SUFFIX)) {
//            attempt = Uml2CdmConverter.convertUMLtoCdm(attemptFile);
//        } else {
//            attempt = (ClassDiagram) ResourceManager.loadModel(attemptFile);
//        }
        MarksModel solutionMarks = emptyMarksModelCache.getUnchecked(solution);

//        if (marksModelFile == null) {
//            solutionMarks = initializeSolutionMarks(solution);
//        }

        return ClassdiagramGraderAlgorithm.gradeClassdiagram(solution, solutionMarks, attempt);
    }

    @Nullable
    private static MarksModel generateMarks(String attemptFilePath, String solutionFilePath) {
        Path solutionPath = Paths.get(solutionFilePath);
        File solutionFile = null;
        if (!Files.exists(solutionPath)) {
            System.out.println("Cannot find solution file: " + solutionFile + DOT);
            return null;
        } else {
            solutionFile = solutionPath.toFile();
        }
        Path attemptPath = Paths.get(attemptFilePath);
        File attemptFile = null;
        if (!Files.exists(attemptPath)) {
            System.out.println("Cannot find attempt file: " + attemptFilePath + DOT);
            return null;
        } else {
            attemptFile = attemptPath.toFile();
        }

        initialize();

        return gradeClassdiagram(solutionPath, null, attemptPath);
    }

    private static boolean IS_INITIALIZED = false;

    private static void initialize() {
        if (IS_INITIALIZED) {
            return;
        }

        ClassdiagramGrader.initializeEMF();
        ClassdiagramGrader.initializeCdm();
        ClassdiagramGrader.initializeMarks();
        IS_INITIALIZED = true;
    }


}
