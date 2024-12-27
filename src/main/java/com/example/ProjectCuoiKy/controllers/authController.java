package com.example.ProjectCuoiKy.controllers;

import com.example.ProjectCuoiKy.models.manga;
import com.example.ProjectCuoiKy.models.users;
import com.example.ProjectCuoiKy.services.usersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class authController {
    @Autowired
    private usersService userService;
    @Autowired
    private com.example.ProjectCuoiKy.repository.mangaRepository mangaRepository;
    @Autowired
    private com.example.ProjectCuoiKy.services.mangaService mangaService;

    public authController(usersService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //Register config
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") users user, BindingResult result, Model model) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Passwords do not match");
        }

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Registration failed. Please check your input.");
            return "register"; // Trả về trang đăng ký
        }

        if (userService.findByUsername(user.getUsername())) {
            model.addAttribute("errorMessage", "Username already exists. Please choose another.");
            return "register";
        }

        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("errorMessage", "Email already exists. Please use another.");
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/login";
    }


    //Home config
    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User: " + authentication.getName());
        return "index";
    }

    //Shop config
    @GetMapping("/shop")
    public String getMangaPage(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 9; // Show 9 products per page
        Page<manga> manga = mangaService.getPaginatedProducts(page, pageSize);

        model.addAttribute("manga", manga.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", manga.getTotalPages());
        return "shop";
    }

    //Admin config
    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<manga> manga = mangaService.getAllManga();
        model.addAttribute("manga", manga);
        return "adminsite";
    }

    @PostMapping("/admin/save")
    public String saveManga(@ModelAttribute manga manga) {
        mangaService.addManga(manga);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String editManga(@PathVariable Long id, Model model) {
        model.addAttribute("editManga", mangaService.findMangabyid(id));
        model.addAttribute("mangaList", mangaService.getAllManga());
        return "adminsite";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteManga(@PathVariable Long id) {
        mangaService.deleteManga(id);
        return "redirect:/admin";
    }

    @PutMapping("/admin/edit/{id}")
    public String updateManga(@PathVariable Long id, @ModelAttribute manga manga) {
        manga.setManga_id(id);
        mangaService.updateManga(manga);
        return "redirect:/admin";
    }
}
