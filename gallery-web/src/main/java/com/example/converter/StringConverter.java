package com.example.converter;

import com.example.dto.TagDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class StringConverter {
    public static String convertSetToString(Set<TagDTO> tags) {
        if (tags.isEmpty()) {
            return "";
        }

        return tags.stream()
                .map(TagDTO::getTagName)
                .collect(Collectors.joining(", "));
    }

    public static String convertSetToStringSpace(Set<TagDTO> tags) {
        if (tags.isEmpty()) {
            return "";
        }

        return tags.stream()
                .map(TagDTO::getTagName)
                .collect(Collectors.joining(" "));
    }
}
