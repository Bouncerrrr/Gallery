package com.example.dto;

import com.example.database.Image;
import com.example.database.Mood;
import com.example.mapper.TagMapper;
import lombok.*;

import javax.persistence.Tuple;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO implements Serializable {
    private Long id;
    private byte[] content;
    private byte[] thumbnail;
    private String name;
    private String description;
    private Mood mood;
    private Set<TagDTO> tags;

    public static ImageDTO fromEntity(Image entity, TagMapper tagMapper) {
        Set<TagDTO> tagDTOs = entity.getTags().stream()
                .map(tagMapper::entityToDTO)
                .collect(Collectors.toSet());

        return ImageDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .thumbnail(entity.getThumbnail())
                .name(entity.getName())
                .description(entity.getDescription())
                .mood(entity.getMood())
                .tags(tagDTOs)
                .build();
    }

    public static ImageDTO fromTuple(Tuple entity) {
        Long id = entity.get("id", Long.class);
        String name = entity.get("name", String.class);
        byte[] thumbnail = entity.get("thumbnail", byte[].class);

        return ImageDTO.builder()
                .id(id)
                .name(name)
                .thumbnail(thumbnail)
                .build();
    }
}
