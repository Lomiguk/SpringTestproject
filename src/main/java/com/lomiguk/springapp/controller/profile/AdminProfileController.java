package com.lomiguk.springapp.controller.profile;

import com.lomiguk.springapp.model.priofile.Profile;
import com.lomiguk.springapp.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("*/admin/prof")
public class AdminProfileController {
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private final ProfileService profileService;
    @Autowired
    public AdminProfileController(ProfileService profileService) {
        this.profileService = profileService;
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
            // надо как-то уведомлять об ошибке
            return "redirect: ../prof/get_permission";
        }
        return "redirect: ../prof/get_permission";
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
            // надо как-то уведомлять об ошибке
            return "/profile/deleteUser";
        }
        return "/profile/deleteUser";
    }
}
