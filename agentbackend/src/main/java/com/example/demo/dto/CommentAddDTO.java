package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddDTO {
    private Long fromUserId;
    private Long carId;
    private String descriptionComment;
}
