package com.lomiguk.springapp.controller.profile;

import com.lomiguk.springapp.exception.QueryGetProfileException;
import com.lomiguk.springapp.model.priofile.Profile;
import com.lomiguk.springapp.model.priofile.dto.ProfileLoginDto;
import com.lomiguk.springapp.model.priofile.dto.ProfileRegisterDto;
import com.lomiguk.springapp.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping()
    public String profiling(HttpServletRequest servletRequest) {
        Object isAuthorised = servletRequest.getSession().getAttribute("isAuthorised");
        if (isAuthorised == null) return "/profile/login";
        return ((boolean) isAuthorised) ? "/profile/profile" : "/profile/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/profile/register";
    }

    @GetMapping("/password")
    public String password(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("passwordChangeErrors", new HashSet<String>());
        return "/profile/passwordChange";
    }

    @PostMapping("/password")
    public String passwordChange(HttpServletRequest request,
                                 @SessionAttribute("passwordChangeErrors") Set<String> errors) {
        HttpSession session = request.getSession();
        // Set<String> errors = (Set<String>) session.getAttribute("passwordChangeErrors");
        String login = (String) session.getAttribute("userName");
        if (login == null || login.isEmpty()) {
            errors.clear();
            errors.add("Empty login");
            session.setAttribute("passwordChangeErrors", errors);
            return "redirect: ../login";
        }
        String actualPassword = request.getParameter("oldPassword");
        try {
            Profile profile = profileService.getByLoginPassword(login, actualPassword);
            if (profile == null) {
                errors.clear();
                errors.add("Missing profile");
                session.setAttribute("passwordChangeErrors", errors);
                return "redirect: ../password";
            }
            String newPassword = request.getParameter("newPassword");
            profileService.changePassword(profile.getId(), newPassword);
        } catch (SQLException | NamingException e) {
            LOGGER.log(Level.WARNING, "password change exception", e);
            errors.clear();
            errors.add("password change exception");
            session.setAttribute("passwordChangeErrors", errors);
            return "redirect: ../password";
        }
        return "redirect: ../profile";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("profileRegister") @Valid ProfileRegisterDto profileRegister,
                           BindingResult bindingResult,
                           HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/profile/register";
        }
        char[] password = profileRegister.getPasswordF().toCharArray();
        if (!Arrays.equals(password, profileRegister.getPasswordS().toCharArray())) {
            bindingResult.rejectValue("passwordF",
                    "unsymmetrical",
                    "Passwords don't match");
            return "/profile/register";
        }
        Profile profile = Profile.builder()
                .login(profileRegister.getLogin())
                .password(password)
                .admin(false)
                .build();
        try {
            profileService.register(profile);
            HttpSession session = request.getSession();
            session.setAttribute("isAuthorised", true);
            session.setAttribute("isAdmin", profile.isAdmin());
            session.setAttribute("userName", profile.getLogin());
        } catch (SQLException | NamingException e) {
            bindingResult.rejectValue("login",
                    "register error",
                    "Register error");
            LOGGER.log(Level.WARNING, "register error", e);
            return "/profile/register";
        }
        return "redirect: ../person";
    }

    @GetMapping("/login")
    public String login() {
        return "profile/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("profileLogin") @Valid ProfileLoginDto profileLoginDto,
                        BindingResult bindingResult,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "profile/login";
        }
        try {
            Profile authorisedProfile = profileService.getByLoginPassword(
                    profileLoginDto.getLogin().trim(),
                    profileLoginDto.getPassword().trim());
            if (authorisedProfile == null) {
                throw new QueryGetProfileException("get null profile");
            }
            HttpSession session = request.getSession();
            session.setAttribute("isAuthorised", true);
            session.setAttribute("userName", authorisedProfile.getLogin().trim());
            session.setAttribute("isAdmin", authorisedProfile.isAdmin());
        } catch (SQLException | NamingException | QueryGetProfileException e) {
            bindingResult.rejectValue("login",
                    "Wrong login or password",
                    "Wrong login or password!");
            LOGGER.log(Level.WARNING, "login error", e);
            return "profile/login";
        }
        return "redirect: ../person";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("isAuthorised", false);
        session.setAttribute("userName", null);
        session.setAttribute("isAdmin", false);
        return "redirect: ../";
    }
}
