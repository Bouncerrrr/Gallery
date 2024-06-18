package com.example.converter;

import com.example.dto.TagDTO;

import java.util.*;
import java.util.stream.Collectors;

public class TagParser {

    public static Set<TagDTO> parseTags(String tags) {
        HashSet<TagDTO> result = new HashSet<>();
        for(String name : tags.split("\\s+"))
        {
            TagDTO tagDTO = new TagDTO();
            tagDTO.setTagName(name);
            result.add(tagDTO);
            System.out.println("creating a tag " + name);
        }
        return result;
    }

}
