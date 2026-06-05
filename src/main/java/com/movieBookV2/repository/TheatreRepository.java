package com.movieBookV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movieBookV2.model.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long>{

}
