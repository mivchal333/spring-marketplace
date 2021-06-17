package pl.edu.pb.springmarketplace.appuser;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pb.springmarketplace.appuser.exceptions.InvalidEmailAddressException;
import pl.edu.pb.springmarketplace.model.Auction;
import pl.edu.pb.springmarketplace.registration.EmailValidator;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImpl implements UserDetailsService, AppUserService {

    private final static String USER_NOT_FOUND = "User with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailValidator emailValidator;

    @Value("${spring.security.user.name}")
    private String adminUserName;

    @Value("${spring.security.user.password}")
    private String adminPassword;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if(email.equals("admin@admin.com") && !appUserRepository.findByEmail(email).isPresent()){
            appUserRepository.save(new AppUser(adminUserName,
                    "admin@admin.com",
                    bCryptPasswordEncoder.encode(adminPassword),
                    AppUserRole.ADMIN));
            log.info("Created admin user.");
        }

        Optional<AppUser> existingUser = appUserRepository.findByEmail(email);
        if (existingUser.isPresent()){
            log.info("Loaded user with email {}", email);
            return existingUser.get();
        }else{
            log.error(USER_NOT_FOUND, email);
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND,email));
        }

    }

    //Create via admin panel
    @Override
    public AppUser buildAndSaveUser(AppUser appUser) {

        Optional<AppUser> existingUserWithSameMail = appUserRepository.findByEmail(appUser.getEmail());

        if (existingUserWithSameMail.isPresent()){
            if (existingUserWithSameMail.get().getId() != appUser.getId()){
                log.error("Email {} already taken by different user.", appUser.getEmail());
                throw new IllegalStateException("Email already used.");
            }
            //if new password = encode again!
            if (!existingUserWithSameMail.get().getPassword().equals(appUser.getPassword())) {
                String encodedPass = bCryptPasswordEncoder.encode(appUser.getPassword());
                appUser.setPassword(encodedPass);
            }

        }
        else{
            String encodedPass = bCryptPasswordEncoder.encode(appUser.getPassword());
            appUser.setPassword(encodedPass);
        }


        if (!emailValidator.test(appUser.getEmail())){
            log.error(String.format("Error while creating user Email address %s is invalid", appUser.getEmail()));
            throw new InvalidEmailAddressException(String.format("Email address %s is invalid", appUser.getEmail()));
        }

        appUser.setAppUserRole(appUser.getAppUserRole());
        log.info(String.format("Registered new user (via admin panel): %s %s", appUser.getUsername(),
                appUser.getEmail()));
        appUser.getAuthorities();
        appUserRepository.save(appUser);

        return appUser;
    }

    //Register via register form
    @Override
    public AppUser registerNewUser(AppUser appUser) {

        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (userExists){
            throw new IllegalStateException("Email already used.");
        }
        if (!emailValidator.test(appUser.getEmail())){
            log.error(String.format("Error while creating user Email address %s is invalid", appUser.getEmail()));
            throw new InvalidEmailAddressException(String.format("Email address %s is invalid", appUser.getEmail()));
        }
        String encodedPass = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPass);



        appUser.setAppUserRole(AppUserRole.USER);
        log.info(String.format("Registered new user (via register form): %s %s", appUser.getUsername(),
                appUser.getEmail()));
        appUser.getAuthorities();
        appUserRepository.save(appUser);

        return appUser;
    }

    @Override
    public Iterable<AppUser> findAll() {
        log.info("Listing all app users.");
        return appUserRepository.findAll();
    }


    @Override
    public Optional<AppUser> findById(Long id) {
        log.info("Found user by id={}", id);
        return appUserRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        try {
            appUserRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("User with id={} not found!", id);
        }
    }
}
