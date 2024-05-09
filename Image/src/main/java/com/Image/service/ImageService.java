package com.Image.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Image.entity.ImageEntity;
import com.Image.repo.ImageRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;
    public List<ImageEntity> getAllImages() {
        return imageRepository.findAll();
    }

    public String saveImage(MultipartFile file) throws IOException {
        byte[] imageData = file.getBytes();

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageData(imageData);
        ImageEntity savedImage = imageRepository.save(imageEntity);

        return savedImage.getId().toString();
    }

    public List<String> getAnnotationOptions() {
        // Return predefined annotation options
        return Arrays.asList("Dog", "Car", "Tree");
    }

    public boolean saveAnnotation(Long imageId, String annotation) {
        Optional<ImageEntity> optionalImage = imageRepository.findById(imageId);
        if (optionalImage.isPresent()) {
            ImageEntity image = optionalImage.get();
            image.setAnnotation(annotation);
            imageRepository.save(image);
            return true;
        } else {
            return false;
        }
    }

    public List<ImageEntity> getAllAnnotatedImages() {
        return imageRepository.findAllByAnnotationIsNotNull();
    }
}
