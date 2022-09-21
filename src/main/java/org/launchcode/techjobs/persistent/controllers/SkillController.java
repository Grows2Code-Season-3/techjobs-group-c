package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("skills", skillRepository.findAll());
        return "skills/index";
    }

    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "skills/add";
        }

        skillRepository.save(newSkill);

        return "redirect:";
    }

    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable int skillId) {

        Optional<Skill> optSkill = skillRepository.findById(skillId);
        if (optSkill.isPresent()) {
            Skill skill = optSkill.get();
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:../";
        }
    }

    @GetMapping("edit/{skillId}")
    public String displayEditSkill(Model model, @PathVariable int skillId){

        Optional<Skill> optSkill = skillRepository.findById(skillId);
        if(optSkill.isPresent()){
            Skill skill = optSkill.get();
            model.addAttribute("skill", skill);
            model.addAttribute("title", "Edit Skill ");
        }else{
            //do something
        }
        return "skills/edit";
    }

    @PostMapping("edit")
    public String processEditSkill(@RequestParam int skillId,
                                      @RequestParam String name, @RequestParam String description){
        Optional<Skill> optionalSkill = skillRepository.findById(skillId);
        if(optionalSkill.isPresent()){
            Skill skill = optionalSkill.get();
            skill.setName(name);
            skill.setDescription(description);
            skillRepository.save(skill);
        }else{
            //do something
        }
        return "redirect:";
    }





}
