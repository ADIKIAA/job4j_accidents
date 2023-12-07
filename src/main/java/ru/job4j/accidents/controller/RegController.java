package ru.job4j.accidents.controller;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;
import ru.job4j.accidents.service.UserService;

@Controller
@Data
public class RegController {

    private final PasswordEncoder encoder;

    private final UserRepository users;

    private final AuthorityRepository authorities;

    private final UserService userService;

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            users.save(user);
        } catch (Exception e) {
            model.addAttribute("message", "Пользователь уже зарегистрирован");
            return "/reg";
        }
        return "redirect:/login";
    }

}
