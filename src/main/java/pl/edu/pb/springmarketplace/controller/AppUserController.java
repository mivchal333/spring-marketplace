package pl.edu.pb.springmarketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pb.springmarketplace.appuser.AppUser;
import pl.edu.pb.springmarketplace.appuser.AppUserRepository;
import pl.edu.pb.springmarketplace.appuser.AppUserService;
import pl.edu.pb.springmarketplace.appuser.AppUserServiceImpl;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/appuser")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public String findAll(Model model) {
        Iterable<AppUser> appUsers = appUserService.findAll();
        model.addAttribute("appusers", appUsers);
        return "/appuser/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("appuser", new AppUser());
        return "/appuser/form";
    }

    @PostMapping
    public String postUser(AppUser appUser) {
        AppUser saved = appUserService.buildAndSaveUser(appUser);

        return "redirect:/appuser/" + saved.getId();
    }

    @GetMapping(value = "/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<AppUser> foundOpt = appUserService.findById(id);

        foundOpt.ifPresent(appuser -> model.addAttribute("appuser", appuser));
        return "/appuser/form";
    }

    @GetMapping("/{id}")
    public String findUser(@PathVariable Long id, Model model) {
        Optional<AppUser> auctionOpt = appUserService.findById(id);
        auctionOpt.ifPresent(appuser -> model.addAttribute("appuser", appuser));
        return "/appuser/details";
    }

    @RequestMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        appUserService.deleteById(id);
        return "redirect:/appuser";
    }
}
