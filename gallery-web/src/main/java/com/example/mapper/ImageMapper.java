package com.example.mapper;

import com.example.database.Image;
import com.example.database.Tag;
import com.example.dto.ImageDTO;
import com.example.dto.TagDTO;
import com.example.repository.ImageRepository;
import com.example.repository.TagRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
@AllArgsConstructor
public class ImageMapper {

    private final ImageRepository imageRepository;
    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    public ImageDTO imageToDto(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setName(image.getName());
        dto.setDescription(image.getDescription());
        dto.setMood(image.getMood());
        dto.setContent(image.getContent());
        dto.setThumbnail(image.getThumbnail());
        Set<TagDTO> tagDTOs = new HashSet<>();
        for (Tag tag : image.getTags()) {
            tagDTOs.add(tagMapper.entityToDTO(tag));
        }
        return dto;
    }

    public Image dtoToEntity(ImageDTO dto) throws NotFoundException {
        Image entity;
        Long id = dto.getId();
        if(id != null){
            entity = imageRepository.findById(id).orElseThrow(()
                    -> new NotFoundException("Image not found"));
        } else {
            entity = new Image();
        }
        entity.setContent(dto.getContent());
        entity.setThumbnail(dto.getThumbnail());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setMood(dto.getMood());
        entity.setTags(dto.getTags().stream()
                .map(tagMapper::createTag)
                .collect(Collectors.toSet()));
        return entity;
    }



}
