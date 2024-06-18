package com.example.implementation;

import com.example.database.Image;
import com.example.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class searchImplementation implements SearchRepository {
    private final EntityManager entityManager;

    @Override
    public Page<Tuple> searchImageBy(Specification<Image> spec, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
        Root<Image> root = criteriaQuery.from(Image.class);

        criteriaQuery.select(criteriaBuilder.tuple(root));

        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, criteriaQuery, criteriaBuilder);
            if (predicate != null) {
                criteriaQuery.where(predicate);
            }
        }

        criteriaQuery.select(criteriaBuilder.tuple(root.get("id").alias("id"),
                root.get("name").alias("name"),
                root.get("thumbnail").alias("thumbnail")));

        TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Tuple> resultList = query.getResultList();
        long totalCount = countImageBy(spec);
        return new PageImpl<>(resultList, pageable, totalCount);
    }

    public long countImageBy(Specification<Image> spec) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Image> root = countQuery.from(Image.class);

        countQuery.select(criteriaBuilder.countDistinct(root.get("id")));

        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, countQuery, criteriaBuilder);
            if (predicate != null) {
                countQuery.where(predicate);
            }
        }

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
