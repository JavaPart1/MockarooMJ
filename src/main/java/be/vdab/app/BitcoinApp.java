package be.vdab.app;

import be.vdab.dao.*;
import be.vdab.model.BitcoinAccount;
import be.vdab.model.NotEnoughBalanceException;
import be.vdab.model.Person;
import be.vdab.model.PersonException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BitcoinApp {
    public static void main(String[] args) throws PersonException, SQLException {
        // Get person 1 & 2
        int person1Id = 5;
        PersonDao personDao = new PersonDaoImpl(JdbcPass.getJDBCURL(),JdbcPass.getJDBCUSER(),JdbcPass.getPASSW());
        Person person1 = personDao.getPersonById(person1Id);

        int person2Id = 15;
        Person person2 = personDao.getPersonById(person2Id);

        // Get both persons bitcoinaccount(s)
        ArrayList<BitcoinAccount> accList = new ArrayList<>();
        BitAccDao accDao = new BitAccDaoImpl(JdbcPass.getJDBCURL(),JdbcPass.getJDBCUSER(),JdbcPass.getPASSW());
        // Account person 1
        accList = accDao.getBitAccByOwner(person1.getId());
        BitcoinAccount person1BitAcc = accList.get(0);// first account found
        // Account person 2
        accList = accDao.getBitAccByOwner(person2.getId());
        BitcoinAccount person2BitAcc = accList.get(0);// first account found

        // Pay 1000 € from person 1 to person 2
        double paymentAmount = 1000;
        try {
            // withdraw first bitcoin account
            accDao.pay(person1BitAcc,paymentAmount);
            // deposit second bitcoin account
            accDao.receivePayment(person2BitAcc,paymentAmount);
        } catch (NotEnoughBalanceException e) {
            e.printStackTrace();
        }

    }
}
