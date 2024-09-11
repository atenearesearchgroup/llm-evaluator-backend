package me.loopbreak.hermesanalyzer.controllers;

import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@CrossOrigin
@RequestMapping(value = "/file")
public class FileController {

    public static final Path UPLOADS_DIR = Path.of("uploads");

    @PostMapping("/upload/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void uploadFile(@RequestBody MultipartFile file, @PathVariable String name) {
        System.out.println("File uploaded: " + name + " " + file.getOriginalFilename());

        saveFile(file, name);
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String name, @RequestParam String fileName) {
        System.out.println("File downloaded: " + name);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(headers -> headers.setContentDispositionFormData("attachment", fileName))
                .header("name", fileName)
                .body(loadFile(name, fileName));
    }

    @Nullable
    private Resource loadFile(String name, String fileName) {
        Path identifierDir = UPLOADS_DIR.resolve(name);
        Path filePath = identifierDir.resolve(fileName);

        if (!Files.exists(filePath))
            return null;

        System.out.println("\"x00\" = " + "x00");
        System.out.println("filePath = " + filePath);
        System.out.println("filePath2 = " + filePath.toUri());

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveFile(MultipartFile file, String identifier) {
        if (!Files.exists(UPLOADS_DIR))
            try {
                Files.createDirectory(UPLOADS_DIR);
            } catch (Exception e) {
                e.printStackTrace();
            }

        Path identifierDir = UPLOADS_DIR.resolve(identifier);

        if (!Files.exists(identifierDir))
            try {
                Files.createDirectory(identifierDir);
            } catch (Exception e) {
                e.printStackTrace();
            }

        Path filePath = identifierDir.resolve(file.getOriginalFilename());

        try {
            System.out.println("file.getSize() = " + file.getSize());
            System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
            System.out.println("filePath = " + filePath);
            Files.write(filePath, file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
