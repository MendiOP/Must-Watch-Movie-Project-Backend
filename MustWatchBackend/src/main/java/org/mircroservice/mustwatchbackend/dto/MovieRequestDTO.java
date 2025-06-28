package org.mircroservice.mustwatchbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDTO {
    private String title;
    private String director;
    private String imgUrl;
    private int releaseYear;
    private int rating;
    private String genre;
}
