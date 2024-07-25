package tn.enicarthage.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import tn.enicarthage.services.image.ImageServiceImp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

    private final ImageServiceImp imageService;
    
    @PostMapping("/image")
    public ResponseEntity<String> uploadFiles(
        @RequestParam(required = false) Long answerId, 
        @RequestParam(required = false) Long questionId, 
        @RequestParam("files") List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                imageService.storeFile(file, answerId, questionId);
            }
            return ResponseEntity.ok("Images stored successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
