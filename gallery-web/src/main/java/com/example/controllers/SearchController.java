package com.example.controllers;

import com.example.angularDto.ImageAngularDto;
import com.example.angularDto.ShowcaseAngularDto;
import com.example.database.Mood;
import com.example.service.AngularService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    private final AngularService angularService;

    @GetMapping("/page")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Page<ImageAngularDto>> Search(@RequestParam(name = "page",
                                                                         defaultValue = "0") int page,
                                                           @RequestParam("keyword") String keyword,
                                                           @RequestParam("mood")Mood mood) {
        Page<ImageAngularDto> imagesPage = angularService.searchByKeywordAndMood(keyword, mood, page, 8);
        return ResponseEntity.ok(imagesPage);
    }

}
