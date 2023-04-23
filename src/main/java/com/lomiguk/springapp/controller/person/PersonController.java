package com.lomiguk.springapp.controller.person;

import com.lomiguk.springapp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.NamingException;
import java.sql.SQLException;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model) {
        try {
            model.addAttribute("people", personService.getAll());
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
        return "person/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id,
                       Model model) {
        try {
            model.addAttribute("person", personService.getById(id));
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
        return "person/show";
    }
}
