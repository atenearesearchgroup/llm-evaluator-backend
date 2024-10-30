package me.loopbreak.hermesanalyzer.hooks.grader;

import ca.mcgill.sel.classdiagram.ClassDiagram;
import ca.mcgill.sel.classdiagram.uml2cdm.Uml2CdmConverter;
import ca.mcgill.sel.commons.emf.util.ResourceManager;
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
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static ca.mcgill.sel.grading.classdiagram.ClassdiagramGrader.initializeSolutionMarks;

public class EvaluatorConnectorImpl implements EvaluatorConnector {

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

    @Override
    public EvaluationResult evaluate(@Nullable InputStream text, Path solutionFile) {
        MarksModel model = null;
        try {
            Path path = generateTempFile(text);
            model = generateMarks(path != null ? path.toAbsolutePath().toString() : null,
                    solutionFile.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double score = MarksCalculator.of(model).calculateMarks();
        ClassDiagram solution = classDiagramCache.getUnchecked(solutionFile);
        List<CategoryError> errors = ErrorClassifier.of(model).solution(solution).classify();

        return new EvaluationResult(score, model.getMaxPoints(), errors, null, null);
    }

    @Nullable
    private Path generateTempFile(InputStream text) throws IOException {
        if (text == null) return null;

        String id = UUID.randomUUID().toString();
        Path tempFile = Files.createTempFile(id, ".cdm");
        Files.write(tempFile, text.readAllBytes());
        return tempFile;
    }

    @NotNull
    private static MarksModel gradeClassdiagram(Path solutionFile, @Nullable Path attemptFile) {
        // Load the class diagrams
        ClassDiagram solution = classDiagramCache.getUnchecked(solutionFile);
        MarksModel solutionMarks = emptyMarksModelCache.getUnchecked(solution);

        if (attemptFile == null) {
            double maxScore = MarksCalculator.of(solutionMarks).calculateMarks();
            solutionMarks.setMaxPoints(maxScore);
            return solutionMarks;
        }

        ClassDiagram attempt = classDiagramCache.getUnchecked(attemptFile);

        MarksModel result = ClassdiagramGraderAlgorithm.gradeClassdiagram(solution, solutionMarks, attempt);
        return result;
    }

    @NotNull
    private static MarksModel generateMarks(@Nullable String attemptFilePath, String solutionFilePath) {
        Path solutionPath = Paths.get(solutionFilePath);
        if (!Files.exists(solutionPath))
            throw new RuntimeException("Cannot find solution file: " + solutionFilePath + DOT);

        Path attemptPath = null;
        if (attemptFilePath != null) {
            attemptPath = Paths.get(attemptFilePath);
            if (!Files.exists(attemptPath))
                throw new RuntimeException("Cannot find attempt file: " + attemptFilePath + DOT);
        }
        initialize();

        return gradeClassdiagram(solutionPath, attemptPath);
    }

    private static boolean isInitialized = false;

    private static void initialize() {
        if (isInitialized) {
            return;
        }

//        ClassdiagramGrader.initializeEMF();
//        ClassdiagramGrader.initializeCdm();
//        ClassdiagramGrader.initializeMarks();
        isInitialized = true;
    }

    private static MarksModel getLocalModel(Path file) {
        MarksModel solutionMarks = (MarksModel) ResourceManager.loadModel(file.toFile());
        return solutionMarks;
    }


}
