package com.lomiguk.springapp_l.controller.person;

import com.lomiguk.springapp_l.model.Person;
import com.lomiguk.springapp_l.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("person", new Person());
        return "person/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("person") @Valid Person person,
                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "person/add";
        }
        try {
            personService.add(person);
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
        return "redirect: ../person";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id,
                       Model model,
                       HttpServletRequest request) {
        Boolean isAdmin = (Boolean)request.getSession().getAttribute("isAdmin");
        if (isAdmin != null && isAdmin) {
            try {
                model.addAttribute("person", personService.getById(id));
            } catch (SQLException | NamingException e) {
                throw new RuntimeException(e);
            }
            return "person/edit";
        }
        return "redirect: ../"+id;
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id,
                         @ModelAttribute @Valid Person person,
                         BindingResult bindingResult,
                         HttpServletRequest request) {
        if (bindingResult.hasErrors())
            return "person/edit";
        Boolean isAdmin = (Boolean)request.getSession().getAttribute("isAdmin");
        if (isAdmin != null && isAdmin) {
            try {
                personService.update(id, person);
            } catch (SQLException | NamingException e) {
                throw new RuntimeException(e);
            }

            return "redirect: ../person";
        }
        return "redirect: ../"+id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id,
                         HttpServletRequest request) {
        Boolean isAdmin = (Boolean)request.getSession().getAttribute("isAdmin");
        if (isAdmin != null && isAdmin) {
            try {
                personService.delete(id);
            } catch (SQLException | NamingException e) {
                throw new RuntimeException(e);
            }
            return "redirect: ../person";
        }
        return "redirect: ../"+id;
    }
}
