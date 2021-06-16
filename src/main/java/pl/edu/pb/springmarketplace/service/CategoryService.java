package pl.edu.pb.springmarketplace.service;

import pl.edu.pb.springmarketplace.model.Category;

import java.util.Optional;

public interface CategoryService {
    Iterable<Category> findAll();

    Category save(Category category);

    Optional<Category> findById(Long id);

    void deleteById(Long id);

}
