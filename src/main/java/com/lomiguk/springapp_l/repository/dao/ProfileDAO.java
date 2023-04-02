package com.lomiguk.springapp_l.repository.dao;

import com.google.common.hash.Hashing;
import com.lomiguk.springapp_l.model.Profile;
import com.lomiguk.springapp_l.repository.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class ProfileDAO {
    private final String CHECK_AUTHORISED_QUERY
            = "SELECT id, login, isadmin FROM profile WHERE login = ? AND password = ?;";
    private final String ADD_PROFILE =
            "INSERT INTO profile (login, password, isadmin) VALUES (?, ?, false);";
    private final String CHANGE_PASSWORD = "UPDATE profile SET password = ? WHERE id = ?;";
    private final String GET_PROFILES_BY_LOGIN_PART = "SELECT id, login, isadmin FROM profile WHERE login LIKE ?;";
    private final String GET_ADMIN_PERMISSION_BY_ID = "UPDATE profile SET isadmin = NOT isadmin WHERE id = ?;";
    private final String DELETE_BY_ID = "DELETE FROM profile WHERE id = ?;";
    private ConnectionFactory connectionFactory;

    @Autowired
    public ProfileDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void register(Profile profile) throws NamingException, SQLException {
        try (Connection connection = connectionFactory.getContextConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_PROFILE);
            statement.setString(1, profile.getLogin());
            statement.setLong(2, passwordHash(profile.getPassword()));
            statement.execute();
        }
    }

    private long passwordHash(String password) {
        return Hashing
                .sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .padToLong();
    }

    private Profile resultSetToProfile(ResultSet resultSet) throws SQLException {
        return Profile.builder()
                .id(resultSet.getLong("id"))
                .login(resultSet.getString("login"))
                .isAdmin(resultSet.getBoolean("isadmin"))
                .build();
    }

    public Profile getByLoginPassword(String login, String actualPassword) throws NamingException, SQLException {
        try (Connection connection = connectionFactory.getContextConnection()) {
            PreparedStatement statement = connection.prepareStatement(CHECK_AUTHORISED_QUERY);
            statement.setString(1, login);
            statement.setLong(2, passwordHash(actualPassword));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSetToProfile(resultSet);
            }
            return null;
        }
    }

    public void changePassword(long id, String newPassword) throws NamingException, SQLException {
        try(Connection connection = connectionFactory.getContextConnection()){
            PreparedStatement statement = connection.prepareStatement(CHANGE_PASSWORD);
            statement.setLong(1, passwordHash(newPassword));
            statement.setLong(2, id);
            statement.execute();
        }
    }

    public Set<Profile> getProfileByLoginFragment(String login) throws NamingException, SQLException {
        try(Connection connection = connectionFactory.getContextConnection()){
            PreparedStatement statement = connection.prepareStatement(GET_PROFILES_BY_LOGIN_PART);
            login = "%"+login+"%";
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            Set<Profile> profileSet = new HashSet<>();
            while (resultSet.next()){
                profileSet.add(resultSetToProfile(resultSet));
            }
            return profileSet;
        }
    }

    public void getAdminPermission(Long id) throws NamingException, SQLException {
        try(Connection connection = connectionFactory.getContextConnection()){
            PreparedStatement statement = connection.prepareStatement(GET_ADMIN_PERMISSION_BY_ID);
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public void delete(Long id) throws NamingException, SQLException {
        try (Connection connection = connectionFactory.getContextConnection()){
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            statement.execute();
        }
    }
}
