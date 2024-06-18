package com.example.mapper;

import com.example.database.Tag;
import com.example.dto.TagDTO;
import com.example.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TagMapper {
    private final TagRepository tagRepository;
    public TagDTO entityToDTO(Tag entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setTagName(entity.getName());
        return dto;
    }

    Tag dtoToEntity(TagDTO dto) {
        Tag entity = new Tag();
        entity.setName(dto.getTagName());
        return entity;
    }

    public Tag createTag(TagDTO dto) {
        Tag entity = tagRepository.findByName(dto.getTagName());

        if (entity == null) {
            entity = dtoToEntity(dto);
            entity = tagRepository.save(entity);
        }
        return entity;
    }

}
