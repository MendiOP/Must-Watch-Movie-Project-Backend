package org.mircroservice.mustwatchbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "mustmovies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    private String id;
    private String title;
    private String director;
    private String imgUrl;
    private int releaseYear;
    private int rating;
    private String genre;
    private String userName;

    private LocalDateTime createdAt;

    public Movie(String title, String director, int releaseYear, String genre, int rating, String imgUrl) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = rating;
        this.imgUrl = imgUrl;
    }
}
