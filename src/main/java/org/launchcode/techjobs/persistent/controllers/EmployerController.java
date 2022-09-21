package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/index";

    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }

        employerRepository.save(newEmployer);

        return "redirect:";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        Optional optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }
    }

    @GetMapping("edit/{employerId}")
    public String displayEditEmployer(Model model, @PathVariable int employerId){

        Optional<Employer> optEmployer = employerRepository.findById(employerId);
        if(optEmployer.isPresent()){
            Employer employer = optEmployer.get();
            model.addAttribute("employer", employer);
            model.addAttribute("title", "Edit Employer ");
        }else{
            //do something
        }
        return "employers/edit";
    }

    @PostMapping("edit")
    public String processEditEmployer(@RequestParam int employerId,
                                      @RequestParam String name,
                                      @RequestParam String location){
        Optional<Employer> optionalEmployer = employerRepository.findById(employerId);
        if(optionalEmployer.isPresent()){
            Employer employer = optionalEmployer.get();
            employer.setName(name);
            employer.setLocation(location);
            employerRepository.save(employer);
        }else{
            //do something
        }
        return "redirect:";
    }
}
