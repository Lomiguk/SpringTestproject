package com.lomiguk.springapp_l.controller.profile;

import com.lomiguk.springapp_l.model.Profile;
import com.lomiguk.springapp_l.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.HashSet;
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
    public String profiling() {
        return "/profile/profile";
    }

    @GetMapping("/login")
    public String login() {
        return "/profile/login";
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
            // надо как-то уведомлять о ошибке
            return "redirect: ../password";
        }
        return "redirect: ../profile";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        try {
            Profile authorisedProfile = profileService.getByLoginPassword(request.getParameter("login"),
                    request.getParameter("password"));
            if (authorisedProfile == null) {
                LOGGER.log(Level.WARNING, "wrong profile data");
                return "/profile/login";
            }
            HttpSession session = request.getSession();
            session.setAttribute("isAuthorised", true);
            session.setAttribute("userName", authorisedProfile.getLogin());
            session.setAttribute("isAdmin", authorisedProfile.isAdmin());
        } catch (SQLException | NamingException e) {
            LOGGER.log(Level.WARNING, "login error", e);
            // надо как-то уведомлять о ошибке
            return "/profile/login";
        }
        return "redirect: ../person";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request) {
        String password = request.getParameter("password_1");
        String checkPassword = request.getParameter("password_2");
        if (!password.equals(checkPassword)) {
            return "/profile/register";
        }
        Profile profile = Profile.builder()
                .login(request.getParameter("login"))
                .password(password)
                .isAdmin(false)
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

    @GetMapping("/get_permission")
    public String adminPermission(Model model) {
        final String directPage = "/profile/giveAdmin";
        model.addAttribute("profiles", new HashSet<Profile>());
        return directPage;
    }

    @PostMapping("/get_profiles")
    public String getProfilesByLoginFragmentLogin(HttpServletRequest request,
                                             Model model) throws SQLException, NamingException {
        model.addAttribute("profiles",
                profileService.getProfilesByLoginFragment(request.getParameter("userLogin")));
        return "/profile/giveAdmin";
    }

    @PostMapping("/get_profiles_for_delete")
    public String getProfilesByLoginFragmentDelete(HttpServletRequest request,
                                             Model model) throws SQLException, NamingException {
        model.addAttribute("profiles",
                profileService.getProfilesByLoginFragment(request.getParameter("userLogin")));
        return "/profile/deleteUser";
    }

    @PostMapping("/to_admin")
    public String getPermission(HttpServletRequest request){
        Long id = Long.parseLong(request.getParameter("userId"));
        try {
            profileService.getAdminPermission(id);
        } catch (SQLException | NamingException e) {
            LOGGER.log(Level.WARNING, "giving admin permission Error", e);
            // надо как-то уведомлять о ошибке
            return "redirect: ../profile/get_permission";
        }
        return "redirect: ../profile/get_permission";
    }

    @GetMapping("/delete_user")
    public String deleteUser(Model model){
        final String directPage = "/profile/deleteUser";
        model.addAttribute("profiles", new HashSet<Profile>());
        return directPage;
    }

    @PostMapping("/delete")
    public String delete(HttpServletRequest request){
        try {
            profileService.delete(Long.valueOf(request.getParameter("userId")));
        } catch (SQLException | NamingException e) {
            LOGGER.log(Level.WARNING, "delete user by id exception", e);
            // надо как-то уведомлять о ошибке
            return "/profile/deleteUser";
        }
        return "/profile/deleteUser";
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
