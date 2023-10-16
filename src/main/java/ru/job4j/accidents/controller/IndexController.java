package ru.job4j.accidents.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.service.AccidentService;

@Controller
@RequestMapping("/")
@Data
public class IndexController {

    private final AccidentService accidentService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }

}
