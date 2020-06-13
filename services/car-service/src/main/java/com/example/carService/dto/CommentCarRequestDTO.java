package com.example.carService.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCarRequestDTO {
    private Long fromUserId;
    private Long carId;
    private String descriptionComment;
}
