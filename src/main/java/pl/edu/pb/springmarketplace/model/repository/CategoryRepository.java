package pl.edu.pb.springmarketplace.model.repository;

import org.springframework.stereotype.Repository;
import pl.edu.pb.springmarketplace.model.Category;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
