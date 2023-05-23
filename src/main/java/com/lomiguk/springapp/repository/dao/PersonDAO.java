package com.lomiguk.springapp.repository.dao;

import com.lomiguk.springapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
public class PersonDAO {
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

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Collection<Person> getAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY,
                                  new BeanPropertyRowMapper<>(Person.class));
    }

    @Transactional
    public Person getById(Long id) {
        return jdbcTemplate
                .query(SELECT_BY_ID_QUERY, new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny().orElse(null);
    }

    @Transactional
    public void add(Person person) {
        jdbcTemplate.update(INSERT_PERSON,
                            person.getName(),
                            person.getPhone(),
                            person.getAge(),
                            person.getEmail());
    }

    @Transactional
    public void update(long id, Person person) {
        jdbcTemplate.update(UPDATE_PERSON,
                            person.getName(),
                            person.getPhone(),
                            person.getAge(),
                            person.getEmail(),
                            id);
    }

    public void delete(long id) {
        jdbcTemplate.update(DELETE_PERSON, id);
    }
}
