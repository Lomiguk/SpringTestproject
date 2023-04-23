package com.lomiguk.springapp.controller.profile;

import com.lomiguk.springapp.model.priofile.Profile;
import com.lomiguk.springapp.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Arrays;
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
        return ((boolean)isAuthorised) ? "/profile/profile" : "/profile/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/profile/register";
    }

    @GetMapping("/password")
    public String password() {
        return "/profile/passwordChange";
    }

    @PostMapping("/password")
    public String passwordChange(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("userName");
        if (login == null || login.isEmpty()) {
            return "redirect: ../login";
        }
        String actualPassword = request.getParameter("oldPassword");
        try {
            Profile profile = profileService.getByLoginPassword(login, actualPassword);
            if (profile == null) {
                return "redirect: ../password";
            }
            String newPassword = request.getParameter("newPassword");
            profileService.changePassword(profile.getId(), newPassword);
        } catch (SQLException | NamingException e) {
            LOGGER.log(Level.WARNING, "password change exception", e);
            // надо как-то уведомлять об ошибке
            return "redirect: ../password";
        }
        return "redirect: ../profile";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request) {
        char[] password = request.getParameter("password_1").toCharArray();
        if (!Arrays.equals(password,
                           request.getParameter("password_2").toCharArray())) {
            return "/profile/register";
        }
        Profile profile = Profile.builder()
                                 .login(request.getParameter("login"))
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
    public String login(HttpServletRequest request) {
        try {
            Profile authorisedProfile = profileService
                        .getByLoginPassword(request.getParameter("login"),
                                            request.getParameter("password"));
            if (authorisedProfile == null) {
                LOGGER.log(Level.WARNING, "wrong profile data");
                return "profile/login";
            }
            HttpSession session = request.getSession();
            session.setAttribute("isAuthorised", true);
            session.setAttribute("userName", authorisedProfile.getLogin().trim());
            session.setAttribute("isAdmin", authorisedProfile.isAdmin());
        } catch (SQLException | NamingException e) {
            LOGGER.log(Level.WARNING, "login error", e);
            // надо как-то уведомлять об ошибке
            return "profile/login";
        }
        return "redirect: ../person";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("isAuthorised", false);
        session.setAttribute("userName", null);
        session.setAttribute("isAdmin", false);
        return "redirect: ../";
    }
}
