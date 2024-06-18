package com.example.service;

import com.example.database.Image;
import com.example.database.Mood;
import com.example.dto.ImageDTO;
import com.example.dto.ShowcaseDTO;
import com.example.mapper.ImageMapper;
import com.example.mapper.TagMapper;
import com.example.repository.ImageRepository;
import com.example.repository.SearchRepository;
import com.example.repository.ShowcaseRepository;
import com.example.repository.TagRepository;

import com.example.specification.SearchSpecification;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ShowcaseRepository showcaseRepository;
    private final SearchRepository searchRepository;
    private final ImageMapper imageMapper;
    private final TagMapper tagMapper;


    public void storeOrUpdateImage(ImageDTO dto) throws NotFoundException {
        Image entity = imageMapper.dtoToEntity(dto);
        imageRepository.save(entity);
    }

    public ImageDTO findImageById(Long id) throws NotFoundException {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            return ImageDTO.fromEntity(image, tagMapper);
        } else {
            throw new NotFoundException("Image not found with id: " + id);
        }
    }

    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        imageRepository.delete(image);
    }

    public Page<ShowcaseDTO> showcasePage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> imagesDataPage = showcaseRepository.findAllBy(pageable);

        List<ShowcaseDTO> showcaseDTOList = new ArrayList<>();

        for (Object[] data : imagesDataPage.getContent()) {
            Long id = (Long) data[0];
            byte[] thumbnail = (byte[]) data[1];
            String name = (String) data[2];

            ShowcaseDTO showcaseDTO = new ShowcaseDTO(id, null, thumbnail, name, null, null, new HashSet<>());
            showcaseDTOList.add(showcaseDTO);
        }

        return new PageImpl<>(showcaseDTOList, pageable, imagesDataPage.getTotalElements());
    }
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
    public Page<ImageDTO> searchByKeywordAndMood(String keyword, Mood mood, int page, int size){
        Specification<Image> combinedSpec = SearchSpecification.searchWithKeywordAndMood(keyword, mood);

        Pageable pageable = PageRequest.of(page, size);
        Page<Tuple> imageSearchPage = searchRepository.searchImageBy(combinedSpec, pageable);
        List<ImageDTO> imageDisplayDtoList = imageSearchPage.stream()
                .map(ImageDTO::fromTuple)
                .collect(Collectors.toList());

        long totalElements = imageSearchPage.getTotalElements();
        System.out.println("Found " + totalElements + " images matching keyword '" + keyword + "' and mood '" + mood + "'");
        return new PageImpl<>(imageDisplayDtoList, pageable, imageSearchPage.getTotalElements());

    }

}
