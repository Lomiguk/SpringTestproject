package com.lomiguk.springapp.repository;

import com.lomiguk.springapp.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String AGE = "age";
    public static final String EMAIL = "email";

    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getLong(ID));
        person.setName(resultSet.getString(NAME));
        person.setPhone(resultSet.getString(PHONE));
        person.setAge(resultSet.getInt(AGE));
        person.setEmail(resultSet.getString(EMAIL));

        return person;
    }
}
