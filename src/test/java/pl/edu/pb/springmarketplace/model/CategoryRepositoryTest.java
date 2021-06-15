package pl.edu.pb.springmarketplace.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.edu.pb.springmarketplace.model.repository.CategoryRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void shouldSaveCategory() {
        Category category = new Category();
        category.setName("name");
        category.setDescription("desc");

        Category saved = categoryRepository.save(category);

        Category savedById = entityManager.find(Category.class, saved.getId());

        Assertions.assertNotNull(savedById);
    }


    private Category insertCategory() {
        Category c = new Category();
        c.setName("Cat name");
        c.setDescription("DESC");
        return entityManager.persistAndFlush(c);
    }
}
