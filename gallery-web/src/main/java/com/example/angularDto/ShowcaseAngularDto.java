package com.example.angularDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowcaseAngularDto {
    private Long id;
    private String name;
    private String thumbnail;
}
