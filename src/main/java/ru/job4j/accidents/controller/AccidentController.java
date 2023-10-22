package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/accidents")
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = new ArrayList<>();
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        model.addAttribute("types", types);
        return "/accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidents.save(accident);
        return "redirect:/index";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(Model model, @RequestParam int id) {
        var accident = accidents.findById(id);
        if (accident.isEmpty()) {
            model.addAttribute("message", "Accident not found");
            return "/errors/404";
        }
        model.addAttribute("accident", accident.get());
        return "/accidents/editAccident";
    }

    @PostMapping("/editAccident")
    public String update(@ModelAttribute Accident accident, Model model) {
        boolean rsl = accidents.update(accident);
        if (!rsl) {
            model.addAttribute("message", "Ошибка редактирования");
            return "/errors/404";
        }
        return "redirect:/index";
    }

}