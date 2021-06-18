package pl.edu.pb.springmarketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.springmarketplace.appuser.AppUser;
import pl.edu.pb.springmarketplace.model.Auction;
import pl.edu.pb.springmarketplace.model.Category;
import pl.edu.pb.springmarketplace.service.AuctionService;
import pl.edu.pb.springmarketplace.service.AuthFacade;
import pl.edu.pb.springmarketplace.service.CategoryService;
import pl.edu.pb.springmarketplace.service.EmailService;

import java.util.Optional;


@RequiredArgsConstructor
@Controller
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;
    private final CategoryService categoryService;
    private final EmailService emailService;
    private final AuthFacade authFacade;

    @GetMapping
    public String findAll(@RequestParam(required = false) Long categoryId,  Model model) {
        Iterable<Auction> auctions = null;
        if (categoryId != null){
            auctions = auctionService.findPublishedByCategory(categoryId);
        }else{
            auctions = auctionService.findPublished();
        }
        model.addAttribute("auctions", auctions);
        model.addAttribute("categories", categoryService.findAll());
        return "/auction/list";
    }

    @GetMapping("/moderate")
    public String moderateAuctions(Model model) {
        Iterable<Auction> auctions = auctionService.findAll();
        model.addAttribute("auctions", auctions);
        return "/auction/moderate";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("auction", new Auction());
        model.addAttribute("categories", categories);
        return "/auction/form";
    }

    @PostMapping
    public String postAuction(Auction auction) {
        AppUser user = authFacade.getCurrentUser();
        auction.setCreator(user);
        Auction saved = auctionService.save(auction);
        return "redirect:/auction/" + saved.getId();
    }

    @GetMapping("/{id}/publish")
    public String publishAuction(@PathVariable Long id) {
        Auction saved = auctionService.changePublishState(id, true);
        emailService.sendAuctionPublishedMail(saved.getCreator(), saved);
        return "redirect:/auction/moderate";
    }

    @GetMapping("/{id}/unpublish")
    public String unpublishAuction(@PathVariable Long id) {
        Auction saved = auctionService.changePublishState(id, false);
        emailService.sendAuctionUnpublishedMail(saved.getCreator(), saved);
        return "redirect:/auction/moderate";
    }

    @GetMapping(value = "/{id}/edit")
    public String editAuction(@PathVariable Long id, Model model) {
        Optional<Auction> foundOpt = auctionService.findById(id);
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        foundOpt.ifPresent(auction -> model.addAttribute("auction", auction));
        return "/auction/form";
    }

    @GetMapping("/{id}")
    public String findAuction(@PathVariable Long id, Model model) {
        Optional<Auction> auctionOpt = auctionService.findById(id);
        auctionOpt.ifPresent(auction -> model.addAttribute("auction", auction));
        return "/auction/details";
    }

    @RequestMapping("/{id}/delete")
    public String deleteAirport(@PathVariable Long id) {
        auctionService.deleteById(id);
        return "redirect:/auction";
    }

}
