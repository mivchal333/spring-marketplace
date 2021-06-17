package pl.edu.pb.springmarketplace.model;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.edu.pb.springmarketplace.appuser.AppUserRepository;
import pl.edu.pb.springmarketplace.model.repository.AuctionRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AppUserTest {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private TestEntityManager entityManager;
}
