package com.example.dto;

import com.example.database.Image;
import com.example.database.Mood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ShowcaseDTO implements Serializable {
    private Long id;
    private byte[] content;
    private byte[] thumbnail;
    private String name;
    private String description;
    private Mood mood;
    private Set<TagDTO> tags;
}
