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
import pl.edu.pb.springmarketplace.model.repository.CategoryRepository;

import java.math.BigDecimal;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String findAll(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "/category/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categories);
        return "/category/form";
    }

    @PostMapping
    public String postCategory(Category category) {
        Auction auction = new Auction();
        auction.setTitle("title");
        auction.setDescription("desc");
        auction.setPrice(BigDecimal.ONE);
        auction.setCategory(category);
        category.getAuctions().add(auction);


        Category saved = categoryRepository.save(category);
        return "redirect:/category/" + saved.getId();
    }

    @GetMapping(value = "/{id}/edit")
    public String editCategory(@PathVariable Long id, Model model) {
        Optional<Category> foundOpt = categoryRepository.findById(id);

        foundOpt.ifPresent(category -> model.addAttribute("category", category));
        return "/category/form";
    }

    @GetMapping("/{id}")
    public String findCategory(@PathVariable Long id, Model model) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        categoryOpt.ifPresent(category -> model.addAttribute("category", category));
        return "/category/details";
    }

    @RequestMapping("/{id}/delete")
    public String deleteAirport(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/category";
    }

}
