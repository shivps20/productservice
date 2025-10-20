package org.example.evaluations.productservice.repositories;

import org.example.evaluations.productservice.models.Category;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepositoryImplementation<Category,Long> {
    Optional<Category> findByNameIgnoreCase(String name);
}
