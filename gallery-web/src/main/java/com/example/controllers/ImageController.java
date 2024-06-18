package com.example.controllers;

import com.example.angularDto.ImageAngularDto;
import com.example.service.AngularService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gallery")
public class ImageController {

    private final AngularService angularService;
    @GetMapping("/image/{id}")
    public ResponseEntity<ImageAngularDto> showImage(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(angularService.findImageById(id));
    }

}
