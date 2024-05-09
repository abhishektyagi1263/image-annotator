// ImageController.java
package com.Image.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.Image.entity.ImageEntity;
import com.Image.service.ImageService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageId = imageService.saveImage(file);
            return ResponseEntity.ok("Image uploaded successfully with ID: " + imageId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @GetMapping("/annotation-options")
    public ResponseEntity<List<String>> getAnnotationOptions() {
        List<String> annotationOptions = imageService.getAnnotationOptions();
        return ResponseEntity.ok(annotationOptions);
    }

    @PostMapping("/{imageId}/annotate")
    public ResponseEntity<String> annotateImage(@PathVariable Long imageId, @RequestParam String annotation) {
        boolean saved = imageService.saveAnnotation(imageId, annotation);
        if (saved) {
            return ResponseEntity.ok("Annotation saved successfully for image with ID: " + imageId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/annotated")
    public ResponseEntity<List<ImageEntity>> getAllAnnotatedImages() {
        List<ImageEntity> annotatedImages = imageService.getAllAnnotatedImages();
        return ResponseEntity.ok(annotatedImages);
    }
    @GetMapping("")
public ResponseEntity<List<ImageEntity>> getAllImages() {
    List<ImageEntity> images = imageService.getAllImages();
    return ResponseEntity.ok(images);
}

}

