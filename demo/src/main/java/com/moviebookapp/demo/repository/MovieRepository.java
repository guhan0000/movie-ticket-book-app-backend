package com.moviebookapp.demo.repository;

import com.moviebookapp.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findAllByOrderByMovieNameAsc();
//    select * from movie where language='tamil' order by ticketPrice desc;
    List<Movie> findAllByLanguageOrderByTicketPriceDesc(String language);
}
