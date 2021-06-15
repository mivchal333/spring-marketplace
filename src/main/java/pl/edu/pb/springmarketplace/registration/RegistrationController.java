package pl.edu.pb.springmarketplace.registration;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.springmarketplace.appuser.AppUser;
import pl.edu.pb.springmarketplace.appuser.AppUserRepository;
import pl.edu.pb.springmarketplace.appuser.AppUserRole;
import pl.edu.pb.springmarketplace.model.Auction;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private AppUserRepository appUserRepository;
    private final static String USER_NOT_FOUND = "User with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public String newForm(Model model){
        model.addAttribute("appUser", new AppUser());
        return "/registration";
    }


    @PostMapping
    public String register(AppUser appUser){


        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (userExists){
            throw new IllegalStateException("Email already used.");
        }
        String encodedPass = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPass);

        appUser.setAppUserRole(AppUserRole.USER);
        appUserRepository.save(appUser);
        //TODO: Add success or fail message
        return "/auction/list";
    }

}
