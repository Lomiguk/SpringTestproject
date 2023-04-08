package com.lomiguk.springapp.repository.dao;

import com.lomiguk.springapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class PersonDAO {
    //private final ConnectionFactory connectionFactory;
    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_ID_QUERY =
            "SELECT id, name, phone, age, email FROM person WHERE id = ?;";
    private static final String SELECT_ALL_QUERY =
            "SELECT id, name, phone, age, email FROM person;";
    private static final String INSERT_PERSON =
            "INSERT INTO person (name, phone, age, email) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_PERSON =
            "UPDATE person SET name = ?, phone = ?, age = ?, email = ? WHERE id = ?;";
    private static final String DELETE_PERSON =
            "DELETE FROM person WHERE id = ?;";

    /*@Autowired
    /public PersonDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }*/

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Person> getAll() throws SQLException, NamingException {
        // (моё, второе спринговое) new PersonMapper() == new BeanPropertyRowMapper<>(Person.class)
        return jdbcTemplate.query(SELECT_ALL_QUERY,
                                  new BeanPropertyRowMapper<>(Person.class));
        /*try (Connection connection = connectionFactory.getContextConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            Set<Person> personSet = new HashSet<>();
            while (resultSet.next()) {
                personSet.add(resultSetToPerson(resultSet));
            }
            return personSet;
        }*/
    }

    public Person getById(Long id) throws SQLException, NamingException {
        return jdbcTemplate
                .query(SELECT_BY_ID_QUERY, new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny().orElse(null);
        /*try (Connection connection = connectionFactory.getContextConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToPerson(resultSet);
            }
            return null;
        }*/
    }

    public void add(Person person) throws SQLException, NamingException {
        jdbcTemplate.update(INSERT_PERSON,
                            person.getName(),
                            person.getPhone(),
                            person.getAge(),
                            person.getEmail());
        /*try (Connection connection = connectionFactory.getContextConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_PERSON);
            statement.setString(1, person.getName());
            statement.setString(2, person.getPhone());
            statement.setInt(3, person.getAge());
            statement.setString(4, person.getEmail());

            statement.execute();
        }*/
    }

    public void update(long id, Person person) throws SQLException, NamingException {
        jdbcTemplate.update(UPDATE_PERSON,
                            person.getName(),
                            person.getPhone(),
                            person.getAge(),
                            person.getEmail(),
                            id);
        /*try (Connection connection = connectionFactory.getContextConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PERSON);
            statement.setString(1, person.getName());
            statement.setString(2, person.getPhone());
            statement.setInt(3, person.getAge());
            statement.setString(4, person.getEmail());
            statement.setLong(5, id);

            statement.execute();
        }*/
    }

    public void delete(long id) throws SQLException, NamingException {
        jdbcTemplate.update(DELETE_PERSON, id);
        /*try (Connection connection = connectionFactory.getContextConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_PERSON);
            statement.setLong(1, id);

            statement.execute();
        }*/
    }

    /*private Person resultSetToPerson(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getLong("id"));
        person.setName(resultSet.getString("name"));
        person.setPhone(resultSet.getString("phone"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));

        return person;
    }*/
}
