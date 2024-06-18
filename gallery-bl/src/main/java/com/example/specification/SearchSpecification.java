package com.example.specification;

import com.example.database.Image;
import com.example.database.Mood;
import com.example.database.Tag;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


public interface SearchSpecification{

    static Specification<Image> searchWithKeywordAndMood(String keyword, Mood mood) {
        return (root, query, criteriaBuilder) -> {

            Join<Image, Tag> tagJoin = root.join("tags", JoinType.LEFT);

            Predicate nameLike = criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
            Predicate descriptionLike = criteriaBuilder.like(root.get("description"), "%" + keyword + "%");
            Predicate tagLike = criteriaBuilder.like(tagJoin.get("name"), "%" + keyword + "%");

            Predicate keywordPredicate = criteriaBuilder.or(nameLike, descriptionLike, tagLike);

            Predicate moodPredicate = criteriaBuilder.equal(root.get("mood"), mood);

            Predicate finalPredicate = criteriaBuilder.and(keywordPredicate, moodPredicate);

            query.distinct(true);

            return finalPredicate;
        };
    }
}


