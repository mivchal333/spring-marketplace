package pl.edu.pb.springmarketplace.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.edu.pb.springmarketplace.model.repository.AuctionRepository;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AuctionRepositoryTest {
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void shouldSaveAuctionWithoutCategory() {
        Auction auction = new Auction();
        auction.setTitle("title");
        auction.setDescription("desc");
        auction.setPrice(BigDecimal.ONE);

        Auction save = auctionRepository.save(auction);

        Auction savedById = entityManager.find(Auction.class, save.getId());

        Assertions.assertNotNull(savedById);
    }

    @Test
    public void shouldSaveAuctionWithCategory() {
        Category category = insertCategory();

        Auction auction = new Auction();
        auction.setTitle("title");
        auction.setDescription("desc");
        auction.setPrice(BigDecimal.ONE);
        auction.setCategory(category);

        Auction save = auctionRepository.save(auction);

        Auction savedById = entityManager.find(Auction.class, save.getId());

        Assertions.assertNotNull(savedById);
        Assertions.assertEquals(category, savedById.getCategory());
    }


    private Category insertCategory() {
        Category c = new Category();
        c.setName("Cat name");
        c.setDescription("DESC");
        return entityManager.persistAndFlush(c);
    }
}
