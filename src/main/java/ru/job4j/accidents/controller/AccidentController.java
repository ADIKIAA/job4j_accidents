package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.Set;

@Controller
@RequestMapping("/accidents")
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;

    private final AccidentTypeService accidentTypeService;

    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "/accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam(required = false) Set<String> rIds) {
        accidents.save(accident);
        return "redirect:/index";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(Model model, @RequestParam int id) {
        var accident = accidents.findById(id);
        model.addAttribute("types", accidentTypeService.findAll());
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