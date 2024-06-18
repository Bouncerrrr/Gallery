package com.example.mapper;

import com.example.angularDto.ImageAngularDto;
import com.example.database.Image;
import com.example.dto.ImageDTO;
import com.example.repository.ImageRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.stream.Collectors;

@Component
@Transactional
@AllArgsConstructor
public class ImageAngularMapper {
    private final ImageRepository imageRepository;
    private final TagMapper tagMapper;

    public Image dtoToEntity(ImageAngularDto dto) throws NotFoundException {
        Image entity;
        Long id = dto.getId();
        if(id != null){
            entity = imageRepository.findById(id).orElseThrow(()
                    -> new NotFoundException("Image not found"));
        } else {
            entity = new Image();
        }
        byte[] contentBytes = Base64.getDecoder().decode(dto.getContent());
        byte[] thumbnailBytes = Base64.getDecoder().decode(dto.getThumbnail());

        entity.setContent(contentBytes);
        entity.setThumbnail(thumbnailBytes);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setMood(dto.getMood());
        entity.setTags(dto.getTags().stream()
                .map(tagMapper::createTag)
                .collect(Collectors.toSet()));
        return entity;
    }
}
