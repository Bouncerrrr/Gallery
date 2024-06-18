package com.example.controllers;

import com.example.angularDto.ImageAngularDto;
import com.example.database.Mood;
import com.example.dto.TagDTO;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.AngularService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.converter.TagParser.parseTags;
import static com.example.converter.ThumbnailCompressor.compressImage;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gallery")
public class UploadController {
    private final AngularService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("content") MultipartFile file,
                                              @RequestParam("name") String name,
                                              @RequestParam("description") String description,
                                              @RequestParam("mood") Mood mood,
                                              @RequestParam("tags") String tags) throws IOException {
        ImageAngularDto dto = new ImageAngularDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setMood(mood);
        dto.setContent(Base64.getEncoder().encodeToString(file.getBytes()));
        byte [] thumbnailBytes = Base64.getDecoder().decode(dto.getContent());
        dto.setThumbnail(Base64.getEncoder().encodeToString(compressImage(thumbnailBytes, 250)));
        Set<TagDTO> tagSet = parseTags(tags);
        dto.setTags(tagSet);
        try {
            imageService.storeOrUpdateImage(dto);
            return ResponseEntity.ok().body("Image uploaded successfully.");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }

}
