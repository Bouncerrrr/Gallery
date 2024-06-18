package com.example.angularDto;

import com.example.database.Image;
import com.example.database.Mood;
import com.example.dto.TagDTO;
import com.example.mapper.TagMapper;
import lombok.*;

import javax.persistence.Tuple;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Base64;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageAngularDto implements Serializable {
    private Long id;
    private String content;
    private String thumbnail;
    private String name;
    private String description;
    private Mood mood;
    private Set<TagDTO> tags;

    public static ImageAngularDto fromEntity(Image entity, TagMapper tagMapper) {
        Set<TagDTO> tagDTOs = entity.getTags().stream()
                .map(tagMapper::entityToDTO)
                .collect(Collectors.toSet());

        String contentBase64 = Base64.getEncoder().encodeToString(entity.getContent());
        String thumbnailBase64 = Base64.getEncoder().encodeToString(entity.getThumbnail());


        return ImageAngularDto.builder()
                .id(entity.getId())
                .content(contentBase64)
                .thumbnail(thumbnailBase64)
                .name(entity.getName())
                .description(entity.getDescription())
                .mood(entity.getMood())
                .tags(tagDTOs)
                .build();
    }

    public static ImageAngularDto fromTuple(Tuple entity) {
        Long id = entity.get("id", Long.class);
        String name = entity.get("name", String.class);
        byte[] thumbnailBytes = entity.get("thumbnail", byte[].class);

        String thumbnail = Base64.getEncoder().encodeToString(thumbnailBytes);

        return ImageAngularDto.builder()
                .id(id)
                .name(name)
                .thumbnail(thumbnail)
                .build();
    }
}
