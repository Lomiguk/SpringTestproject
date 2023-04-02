package com.lomiguk.springapp_l.service;

import com.lomiguk.springapp_l.model.Person;
import com.lomiguk.springapp_l.repository.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Collection;

@Service
public class PersonService {
    private final PersonDAO personDAO;

    @Autowired
    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public Collection<Person> getAll() throws SQLException, NamingException {
        return personDAO.getAll();
    }

    public Person getById(Long id) throws SQLException, NamingException {
        return personDAO.getById(id);
    }

    public void add(Person person) throws SQLException, NamingException {
        personDAO.add(person);
    }

    public void update(Long id, Person person) throws SQLException, NamingException {
        personDAO.update(id, person);
    }

    public void delete(Long id) throws SQLException, NamingException {
        personDAO.delete(id);
    }
}
