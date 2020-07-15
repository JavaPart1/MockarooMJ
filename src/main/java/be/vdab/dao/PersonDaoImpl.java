package be.vdab.dao;

import be.vdab.model.Person;
import be.vdab.model.PersonException;

import java.sql.*;

public class PersonDaoImpl implements PersonDao{
    private String url;
    private String user;
    private String password;

    public PersonDaoImpl(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Person getPersonById(int id) throws PersonException {
        String query = "SELECT * FROM Person WHERE Id=?";
        try (
                Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(query)
        ) {
            System.out.println("Connection ok");
            stmt.setInt(1, id);
            try (
                    ResultSet rs = stmt.executeQuery()
            ) {
                if (rs.next()) {
                    Person person = new Person();
                    person.setId(id);
                    person.setFirstName(rs.getString("first_name"));
                    person.setLastName(rs.getString("last_name"));
                    person.setEmail(rs.getString("email"));
                    person.setGender(rs.getString("gender"));
                    person.setIpAddress(rs.getString("ip_address"));
                    return person;
                } else {
                    System.out.println("Person not found with Id : " + id);
                    throw new PersonException("P not found");
                    //return null;

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new PersonException("SQL err");
        }
    }

    @Override
    public void createPerson(Person nwPerson) throws SQLException {
        String insert = "INSERT INTO person (id, first_name, last_name, email, gender, ip_address) " +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(insert)
        ) {
            stmt.setInt(1, nwPerson.getId());
            stmt.setString(2, nwPerson.getFirstName());
            stmt.setString(3, nwPerson.getLastName());
            stmt.setString(4, nwPerson.getEmail());
            stmt.setString(5, nwPerson.getGender());
            stmt.setString(6, nwPerson.getIpAddress());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throw new SQLException(throwables);
        }

    }

    @Override
    public void updatePerson(Person corPerson) throws SQLException {
        String update = "UPDATE person SET first_name =?, last_name =?, " +
                "email=?, gender=?, ip_address=?" +
                ", WHERE id=?";
        try (
                Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(update)
        ) {
            stmt.setString(1, corPerson.getFirstName());
            stmt.setString(2, corPerson.getLastName());
            stmt.setString(3, corPerson.getEmail());
            stmt.setString(4, corPerson.getGender());
            stmt.setString(5, corPerson.getIpAddress());
            stmt.setInt(6, corPerson.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throw new SQLException(throwables);
        }

    }

    @Override
    public void deletePerson(Person exPerson) throws SQLException {
        String del = "DELETE person WHERE Id=?";
        try (
                Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(del)
        ) {
            stmt.setInt(1, exPerson.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throw new SQLException(throwables);
        }

    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
