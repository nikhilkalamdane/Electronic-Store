package com.nikhilkalamdane.electronic.store.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank(message = "Title is required !!!")
    @Size(min = 4, message = "Title must be of minimum 4 characters !!!")
    private String title;

    @NotBlank(message = "Description is required !!!")
    private String description;

    @NotBlank(message = "Cover Image is required !!!")
    private String coverImage;
}

