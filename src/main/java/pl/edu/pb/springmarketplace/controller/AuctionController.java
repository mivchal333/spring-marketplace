package pl.edu.pb.springmarketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pb.springmarketplace.model.Auction;
import pl.edu.pb.springmarketplace.model.Category;
import pl.edu.pb.springmarketplace.service.AuctionService;
import pl.edu.pb.springmarketplace.service.CategoryService;

import java.util.Optional;


@RequiredArgsConstructor
@Controller
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;
    private final CategoryService categoryService;

    @GetMapping
    public String findAll(Model model) {
        Iterable<Auction> auctions = auctionService.findPublished();
        model.addAttribute("auctions", auctions);
        return "/auction/list";
    }

    @GetMapping("/my")
    public String findAllMyAuctions(Model model) {
        Iterable<Auction> auctions = auctionService.findAllMyAuctions();
        model.addAttribute("auctions", auctions);
        return "/auction/my";
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
        Auction saved = auctionService.save(auction);
        return "redirect:/auction/" + saved.getId();
    }

    @GetMapping("/{id}/publish")
    public String publishAuction(@PathVariable Long id) {
        auctionService.changePublishState(id, true);
        return "redirect:/auction/my";
    }

    @GetMapping("/{id}/unpublish")
    public String unpublishAuction(@PathVariable Long id) {
        auctionService.changePublishState(id, false);
        return "redirect:/auction/my";
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
