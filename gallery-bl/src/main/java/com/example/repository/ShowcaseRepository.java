package com.example.repository;

import com.example.database.Image;
import com.example.database.Mood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowcaseRepository extends PagingAndSortingRepository<Image, Long> {
    @Query("SELECT i.id, i.thumbnail, i.name FROM Image i")
    Page<Object[]> findAllBy(Pageable pageable);

}


