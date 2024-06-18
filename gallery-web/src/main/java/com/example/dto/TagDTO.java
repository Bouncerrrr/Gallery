package com.example.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDTO {
    private Long id;
    private String tagName;
    private List<Long> imageIds;
}
