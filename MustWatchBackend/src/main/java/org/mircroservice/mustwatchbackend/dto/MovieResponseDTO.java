package org.mircroservice.mustwatchbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO {
    private String id;
    private String title;
    private String director;
    private String imgUrl;
    private int releaseYear;
    private int rating;
    private String genre;

    private LocalDateTime createdAt;
}
