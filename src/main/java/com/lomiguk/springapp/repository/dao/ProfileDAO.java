package com.lomiguk.springapp.repository.dao;

import com.lomiguk.springapp.model.priofile.Profile;
import com.lomiguk.springapp.repository.ConnectionFactory;
import com.lomiguk.springapp.repository.rowMapper.LoginProfileRowMapper;
import com.lomiguk.springapp.tool.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public ProfileDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void register(Profile profile) {
        jdbcTemplate.update(ADD_PROFILE,
                            profile.getLogin(),
                            PasswordUtils.getHash(new String(profile.getPassword())));
    }

    @Transactional
    public Profile getByLoginPassword(String login, String actualPassword) {
        try{
            return jdbcTemplate.queryForObject(CHECK_AUTHORISED_QUERY,
                                           new LoginProfileRowMapper(),
                                           login,
                                           PasswordUtils.getHash(actualPassword));
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Transactional
    public void changePassword(long id, String newPassword) {
        jdbcTemplate.update(CHANGE_PASSWORD,
                            PasswordUtils.getHash(newPassword),
                            id);
    }

    @Transactional
    public Collection<Profile> getProfileByLoginFragment(String login) {
        login = "%"+login+"%";
        return jdbcTemplate.query(GET_PROFILES_BY_LOGIN_PART,
                                  new LoginProfileRowMapper(), login);
    }

    @Transactional
    public void getAdminPermission(Long id) {
        jdbcTemplate.update(GET_ADMIN_PERMISSION_BY_ID, id);
    }

    @Transactional
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }
}
