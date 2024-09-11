package me.loopbreak.hermesanalyzer.hooks.format;

import edu.atenearesearchgroup.PumlBuilderPlantumlVisitor;
import edu.atenearesearchgroup.PumlModelToCOREMapper;
import edu.atenearesearchgroup.errorhandling.PumlValidation;
import edu.atenearesearchgroup.errorhandling.SemanticErrorReporter;
import edu.atenearesearchgroup.model.PumlModel;
import edu.atenearesearchgroup.parser.PlantumlLexer;
import edu.atenearesearchgroup.parser.PlantumlParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class FormatConnectorImpl implements FormatConnector {

    public FormattedUml transform(@NotNull String plantUmlCode) {
//        TextToPumlModel textToPumlMapper = new TextToPumlModel(args[0]);
        try {
            UUID uuid = UUID.randomUUID();
            String filePath = "cache/%s.cdm".formatted(uuid);

            PumlModel model = map(plantUmlCode);
            PumlModelToCOREMapper modelMapper = new PumlModelToCOREMapper(model);
            modelMapper.map("TestClassDiagram", filePath);

            FileInputStream fstream = new FileInputStream(filePath);
            byte[] content = new byte[fstream.available()];
            fstream.read(content);
            fstream.close();

            Files.deleteIfExists(Path.of(filePath));
            return new FormattedUml(new ByteArrayInputStream(content), plantUmlCode);
//            InputStream inputStream = new FileInputStream("studentCORE.cdm");

//            return fstream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PumlModel map(String text) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));

        ANTLRInputStream input = new ANTLRInputStream(br);
        PlantumlLexer lexer = new PlantumlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PlantumlParser parser = new PlantumlParser(tokens);
        ParseTree tree = parser.uml();
        SemanticErrorReporter errorReporter = new SemanticErrorReporter();
        PumlValidation errorCheck = new PumlValidation(errorReporter);
        errorCheck.visit(tree);
        if (errorReporter.getErrors().size() <= 0) {
            PumlBuilderPlantumlVisitor eval = new PumlBuilderPlantumlVisitor();
            eval.visit(tree);
            return eval.getModel();
        } else {
            for (int i = 0; i < errorReporter.getErrors().size(); ++i) {
                System.out.printf("[%d] %s%n", i + 1, errorReporter.getErrors().get(i));
            }

            throw new IOException();
        }
    }


}
