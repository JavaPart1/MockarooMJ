package be.vdab.dao;

import be.vdab.model.Person;
import be.vdab.model.PersonException;

import java.sql.SQLException;

public interface PersonDao {
    public Person getPersonById(int id) throws PersonException;
    public void createPerson(Person nwPerson) throws SQLException;
    public void updatePerson(Person corPerson) throws SQLException;
    public void deletePerson(Person exPerson) throws SQLException;

}
