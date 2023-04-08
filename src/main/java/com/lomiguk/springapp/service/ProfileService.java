package com.lomiguk.springapp.service;

import com.lomiguk.springapp.model.Profile;
import com.lomiguk.springapp.repository.dao.ProfileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Collection;

@Service
public class ProfileService {
    private ProfileDAO profileDAO;

    @Autowired
    public ProfileService(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }
    public void register(Profile profile) throws SQLException, NamingException {
        profileDAO.register(profile);
    }

    public Profile getByLoginPassword(String login, String actualPassword) throws SQLException, NamingException {
        return profileDAO.getByLoginPassword(login, actualPassword);
    }

    public void changePassword(long id, String newPassword) throws SQLException, NamingException {
        profileDAO.changePassword(id, newPassword);
    }

    public Collection<Profile> getProfilesByLoginFragment(String login) throws SQLException, NamingException {
        return profileDAO.getProfileByLoginFragment(login);
    }

    public void getAdminPermission(Long id) throws SQLException, NamingException {
        profileDAO.getAdminPermission(id);
    }

    public void delete(Long id) throws SQLException, NamingException {
        profileDAO.delete(id);
    }
}
