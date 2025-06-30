package org.mircroservice.mustwatchbackend.repository;

import org.mircroservice.mustwatchbackend.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByUserName(String userName);
}
