package pl.edu.pb.springmarketplace.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.edu.pb.springmarketplace.appuser.AppUser;
import pl.edu.pb.springmarketplace.appuser.AppUserRepository;
import pl.edu.pb.springmarketplace.appuser.AppUserRole;
import pl.edu.pb.springmarketplace.model.repository.AuctionRepository;

import java.math.BigDecimal;
import java.util.Collection;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AppUserTest {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void CorrectGetAuthoritiesTest(){
        AppUser appUser = new AppUser();
        appUser.setUsername("Test");
        appUser.setEmail("test@test.pl");
        appUser.setAppUserRole(AppUserRole.ADMIN);

        Collection<? extends GrantedAuthority> authority = appUser.getAuthorities();

       Assertions.assertTrue(authority.toString().equals("[ADMIN]"));
    }

    @Test
    public void AppUserSaveTest(){
        AppUser appUser = new AppUser();
        appUser.setUsername("Test");
        appUser.setEmail("test@test.pl");
        appUser.setPassword("Test");
        appUser.setAppUserRole(AppUserRole.ADMIN);

        AppUser saved = appUserRepository.save(appUser);

        Assertions.assertNotNull(saved);

    }

    @Test
    public void AppUserSameMailShouldFailTest(){
        AppUser appUser = new AppUser();
        appUser.setUsername("Test");
        appUser.setEmail("test@test.pl");
        appUser.setPassword("Test");
        appUser.setAppUserRole(AppUserRole.ADMIN);

        AppUser saved = appUserRepository.save(appUser);

        AppUser appUser2 = new AppUser();
        appUser2.setUsername("Test2");
        appUser2.setEmail("test@test.pl");
        appUser2.setPassword("Test2");
        appUser2.setAppUserRole(AppUserRole.ADMIN);

                Assertions.assertThrows(
                        DataIntegrityViolationException.class,
                        () -> {appUserRepository.save(appUser2);});


    }
}
