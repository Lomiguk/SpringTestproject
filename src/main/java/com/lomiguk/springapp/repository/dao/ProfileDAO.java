package com.lomiguk.springapp.repository.dao;

import com.lomiguk.springapp.model.Profile;
import com.lomiguk.springapp.repository.ConnectionFactory;
import com.lomiguk.springapp.tool.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ProfileDAO {
    private final String CHECK_AUTHORISED_QUERY
            = "SELECT id, login, \"isAdmin\" FROM profile WHERE login = ? AND password = ?;";
    private final String ADD_PROFILE =
            "INSERT INTO profile (login, password, \"isAdmin\") VALUES (?, ?, false);";
    private final String CHANGE_PASSWORD =
            "UPDATE profile SET password = ? WHERE id = ?;";
    private final String GET_PROFILES_BY_LOGIN_PART =
            "SELECT id, login, \"isAdmin\" FROM profile WHERE login LIKE ?;";
    private final String GET_ADMIN_PERMISSION_BY_ID =
            "UPDATE profile SET \"isAdmin\" = NOT \"isAdmin\" WHERE id = ?;";
    private final String DELETE_BY_ID =
            "DELETE FROM profile WHERE id = ?;";
    private ConnectionFactory connectionFactory;
    private final JdbcTemplate jdbcTemplate;

    /*public ProfileDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }*/
    @Autowired
    public ProfileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void register(Profile profile) {
        jdbcTemplate.update(ADD_PROFILE,
                            profile.getLogin(),
                            PasswordUtils.getHash(profile.getPassword()));
    }

    public Profile getByLoginPassword(String login, String actualPassword) {
        Profile profile = jdbcTemplate.query(CHECK_AUTHORISED_QUERY,
                                             new BeanPropertyRowMapper<>(Profile.class),
                                             login,
                                             PasswordUtils.getHash(actualPassword))
                                      .stream().findAny().orElse(null);
        return profile;
    }

    public void changePassword(long id, String newPassword) {
        jdbcTemplate.update(CHANGE_PASSWORD,
                            PasswordUtils.getHash(newPassword),
                            id);
    }

    public Collection<Profile> getProfileByLoginFragment(String login) {
        login = "%"+login+"%";
        return jdbcTemplate.query(GET_PROFILES_BY_LOGIN_PART,
                                  new BeanPropertyRowMapper<>(Profile.class), login);
    }

    public void getAdminPermission(Long id) {
        jdbcTemplate.update(GET_ADMIN_PERMISSION_BY_ID, id);
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }
}
