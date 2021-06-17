package pl.edu.pb.springmarketplace.registration;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.springmarketplace.appuser.AppUser;
import pl.edu.pb.springmarketplace.appuser.AppUserRepository;
import pl.edu.pb.springmarketplace.appuser.AppUserRole;
import pl.edu.pb.springmarketplace.appuser.AppUserService;
import pl.edu.pb.springmarketplace.model.Auction;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private AppUserService appUserService;

    @GetMapping
    public String newForm(Model model){
        model.addAttribute("appUser", new AppUser());
        return "/registration";
    }


    @PostMapping
    public String register(AppUser appUser){

        appUserService.registerNewUser(appUser);
        return "/auction/list";
    }

}
