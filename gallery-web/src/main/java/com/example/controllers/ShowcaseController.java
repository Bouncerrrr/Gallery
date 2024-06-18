package com.example.controllers;

import com.example.angularDto.ShowcaseAngularDto;
import com.example.service.AngularService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/showcase")
public class ShowcaseController {

    private final AngularService angularService;

    @GetMapping("/page")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Page<ShowcaseAngularDto>> Showcase(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<ShowcaseAngularDto> imagesPage = angularService.showcasePage(page, 8);
        return ResponseEntity.ok(imagesPage);
    }

}
