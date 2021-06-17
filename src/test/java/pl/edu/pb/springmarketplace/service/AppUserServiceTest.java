package pl.edu.pb.springmarketplace.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.edu.pb.springmarketplace.appuser.*;
import pl.edu.pb.springmarketplace.model.Auction;
import pl.edu.pb.springmarketplace.model.repository.AuctionRepository;
import pl.edu.pb.springmarketplace.security.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Collections;


public class AppUserServiceTest {

    AppUserService appUserService;
    AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        appUserService = new AppUserServiceImpl(appUserRepository, bCryptPasswordEncoder );

    }

    @Test
    public void testFindAllEmpty() {
        Mockito.when(appUserRepository.findAll()).thenReturn(Collections.emptyList());
        Iterable<AppUser> all = appUserService.findAll();

        Assertions.assertFalse(all.iterator().hasNext());
    }

    @Test
    public void testFindAllFilled() {
        AppUser appUser = prepareUser();

        Mockito.when(appUserRepository.findAll()).thenReturn(Collections.singletonList(appUser));
        Iterable<AppUser> all = appUserService.findAll();

        Assertions.assertTrue(all.iterator().hasNext());
    }

    @Test
    public void testEncryptionAfterBuilding(){
        AppUser appUser = new AppUser("Test User","test@test.com","123123", AppUserRole.USER);
        String passBeforeEncryption = appUser.getPassword();
        String passAfterEncryption = appUserService.buildAndSaveUser(appUser).getPassword();

        Assertions.assertTrue(bCryptPasswordEncoder.matches(passBeforeEncryption, passAfterEncryption));

    }

    private AppUser prepareUser() {
        AppUser appuser = new AppUser("Test User","test@test.com","123123", AppUserRole.USER);
        return appuser;
    }
}
