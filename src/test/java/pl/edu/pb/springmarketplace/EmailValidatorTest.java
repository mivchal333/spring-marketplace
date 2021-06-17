package pl.edu.pb.springmarketplace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.pb.springmarketplace.registration.EmailValidator;
import pl.edu.pb.springmarketplace.service.AuctionServiceImpl;
import pl.edu.pb.springmarketplace.service.EmailServiceImpl;

public class EmailValidatorTest {

    @Autowired
    private EmailValidator emailValidator;

    @BeforeEach
    public void setUp() {
        emailValidator = new EmailValidator();

    }

    @Test
    public void validEmailTest(){
        String addr = "admin@admin.com";

        Assertions.assertTrue(emailValidator.test(addr));
    }

    @Test
    public void invalidEmailNoAtTest(){
        String addr = "admin#admin";

        Assertions.assertFalse(emailValidator.test(addr));
    }


    @Test
    public void invalidEmailDollarTest(){
        String addr = "$$@admin";

        Assertions.assertFalse(emailValidator.test(addr));
    }

    @Test
    public void invalidEmailNothingAfterDotTest(){
        String addr = "admin@admin.";

        Assertions.assertFalse(emailValidator.test(addr));
    }


    @Test
    public void invalidEmailDoubleDotTest(){
        String addr = "admin@admin..test";

        Assertions.assertFalse(emailValidator.test(addr));
    }
}
