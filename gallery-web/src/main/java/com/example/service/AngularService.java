package com.example.service;

import com.example.angularDto.ImageAngularDto;
import com.example.angularDto.ShowcaseAngularDto;
import com.example.database.Image;
import com.example.database.Mood;
import com.example.dto.ImageDTO;
import com.example.mapper.ImageAngularMapper;
import com.example.mapper.TagMapper;
import com.example.repository.ImageRepository;
import com.example.repository.SearchRepository;
import com.example.repository.ShowcaseRepository;
import com.example.specification.SearchSpecification;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AngularService {

    private final ImageRepository imageRepository;
    private final ShowcaseRepository showcaseRepository;
    private final SearchRepository searchRepository;
    private final TagMapper tagMapper;
    private final ImageAngularMapper imageAngularMapper;

    public Page<ShowcaseAngularDto> showcasePage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> imagesDataPage = showcaseRepository.findAllBy(pageable);

        List<ShowcaseAngularDto> showcaseDTOList = new ArrayList<>();

        for (Object[] data : imagesDataPage.getContent()) {
            Long id = (Long) data[0];
            byte[] thumbnailBytes = (byte[]) data[1];
            String thumbnail = Base64.getEncoder().encodeToString(thumbnailBytes);
            String name = (String) data[2];

            ShowcaseAngularDto showcaseDTO = new ShowcaseAngularDto(id, name, thumbnail);
            showcaseDTOList.add(showcaseDTO);
        }

        return new PageImpl<>(showcaseDTOList, pageable, imagesDataPage.getTotalElements());
    }

    public ImageAngularDto findImageById(Long id) throws NotFoundException {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            return ImageAngularDto.fromEntity(image, tagMapper);
        } else {
            throw new NotFoundException("Image not found with id: " + id);
        }
    }

    public void storeOrUpdateImage(ImageAngularDto dto) throws NotFoundException {
        Image entity = imageAngularMapper.dtoToEntity(dto);
        imageRepository.save(entity);
    }

    public Page<ImageAngularDto> searchByKeywordAndMood(String keyword, Mood mood, int page, int size) {
        Specification<Image> combinedSpec = SearchSpecification.searchWithKeywordAndMood(keyword, mood);

        Pageable pageable = PageRequest.of(page, size);
        Page<Tuple> imageSearchPage = searchRepository.searchImageBy(combinedSpec, pageable);

        // Log pagination information
        System.out.println("Page requested: " + pageable.getPageNumber());
        System.out.println("Page size: " + pageable.getPageSize());
        System.out.println("Total elements: " + imageSearchPage.getTotalElements());
        System.out.println("Number of elements in current page: " + imageSearchPage.getNumberOfElements());

        List<ImageAngularDto> imageDisplayDtoList = imageSearchPage.stream()
                .map(ImageAngularDto::fromTuple)
                .collect(Collectors.toList());

        return new PageImpl<>(imageDisplayDtoList, pageable, imageSearchPage.getTotalElements());
    }
}
