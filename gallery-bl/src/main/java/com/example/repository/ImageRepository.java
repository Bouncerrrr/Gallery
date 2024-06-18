package com.example.repository;
import java.util.List;
import java.util.Optional;

import com.example.database.Image;
import com.example.database.Mood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>,
        JpaSpecificationExecutor<Image> {
    @Query("SELECT i.id, i.content, i.thumbnail, i.name, i.description, i.mood, t.name FROM Image i LEFT JOIN i.tags t")
    List<Object[]> findImages();

}
