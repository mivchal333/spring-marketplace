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
import pl.edu.pb.springmarketplace.model.repository.AuctionRepository;
import pl.edu.pb.springmarketplace.model.repository.CategoryRepository;

import java.util.Optional;


@RequiredArgsConstructor
@Controller
@RequestMapping("/auction")
public class AuctionController {
    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String findAll(Model model) {
        Iterable<Auction> auctions = auctionRepository.findAll();
        model.addAttribute("auctions", auctions);
        return "/auction/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("auction", new Auction());
        model.addAttribute("categories", categories);
        return "/auction/form";
    }

    @PostMapping
    public String postAuction(Auction auction) {
        Auction saved = auctionRepository.save(auction);
        return "redirect:/auction/" + saved.getId();
    }

    @GetMapping(value = "/{id}/edit")
    public String editAuction(@PathVariable Long id, Model model) {
        Optional<Auction> foundOpt = auctionRepository.findById(id);

        foundOpt.ifPresent(auction -> model.addAttribute("auction", auction));
        return "/auction/form";
    }

    @GetMapping("/{id}")
    public String findAuction(@PathVariable Long id, Model model) {
        Optional<Auction> auctionOpt = auctionRepository.findById(id);
        auctionOpt.ifPresent(auction -> model.addAttribute("auction", auction));
        return "/auction/details";
    }

    @RequestMapping("/{id}/delete")
    public String deleteAirport(@PathVariable Long id) {
        auctionRepository.deleteById(id);
        return "redirect:/auction";
    }

}
