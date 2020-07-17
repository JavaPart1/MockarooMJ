package be.vdab.app;

import be.vdab.dao.*;
import be.vdab.model.BitcoinAccount;
import be.vdab.model.NotEnoughBalanceException;
import be.vdab.model.Person;
import be.vdab.model.PersonException;

import java.sql.SQLException;
import java.util.ArrayList;

import static be.vdab.dao.JdbcPass.*;

public class BitcoinApp {
    public static void main(String[] args) throws PersonException, SQLException {
        // Get person 1 & 2
        int person1Id = 5;
        PersonDao personDao = new PersonDaoImpl(JDBCURL,JDBCUSER,PASSW);
        Person person1 = personDao.getPersonById(person1Id);
        System.out.println("Paying person : " + person1.getFirstName() + " " + person1.getLastName());

        int person2Id = 15;
        Person person2 = personDao.getPersonById(person2Id);
        System.out.println("Receiving person : " + person2.getFirstName() + " " + person2.getLastName());

        // Get both persons bitcoinaccount(s)
        ArrayList<BitcoinAccount> accList = new ArrayList<>();
        BitAccDao accDao = new BitAccDaoImpl(JDBCURL,JDBCUSER,PASSW);
        // Account person 1
        accList = accDao.getBitAccByOwner(person1.getId());
        BitcoinAccount person1BitAcc = accList.get(0);// first account found
        // Account person 2
        accList = accDao.getBitAccByOwner(person2.getId());
        BitcoinAccount person2BitAcc = accList.get(0);// first account found

        // Pay 1000 € from person 1 to person 2
        double paymentAmount = 1000000;
        System.out.println("Payment amount : " + paymentAmount);
        System.out.println("BitcoinAccounts found");
        try {
            // Execute payment
            accDao.ExecutePaymentTxn(person1BitAcc,person2BitAcc,paymentAmount);
            System.out.println("Payment executed");
        } catch (NotEnoughBalanceException e) {
            System.out.println(e);
            e.printStackTrace();
            System.out.println("Payment failed");
        }

    }
}
