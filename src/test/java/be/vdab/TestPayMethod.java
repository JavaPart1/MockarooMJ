package be.vdab;

import be.vdab.dao.*;
import be.vdab.model.BitcoinAccount;
import be.vdab.model.NotEnoughBalanceException;
import be.vdab.model.Person;
import be.vdab.model.PersonException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestPayMethod {

    @Test
    void testMeth() throws PersonException, SQLException, NotEnoughBalanceException {
        int person1Id = 5;
        double paymentAmount = 1000;
        double balanceBefore;
        double balanceAfter;
        // Person and Dao
        try {
            PersonDao personDao = new PersonDaoImpl(JdbcPass.getJDBCURL(),JdbcPass.getJDBCUSER(),JdbcPass.getPASSW());
            Person person1 = personDao.getPersonById(person1Id);
            // bitcoin account and dao
            ArrayList<BitcoinAccount> accList = new ArrayList<>();
            BitAccDao accDao = new BitAccDaoImpl(JdbcPass.getJDBCURL(),JdbcPass.getJDBCUSER(),JdbcPass.getPASSW());
            accList = accDao.getBitAccByOwner(person1.getId());
            BitcoinAccount personBitAcc = accList.get(0);// first account found
            balanceBefore = personBitAcc.getSaldo();

            // withdraw bitcoin account
            accDao.pay(personBitAcc,paymentAmount);
            balanceAfter = personBitAcc.getSaldo();

            // compare with DB
            System.out.println("balance before pay : " + balanceBefore);
            System.out.println("balance after pay : " + balanceAfter);
            Assertions.assertNotEquals(balanceBefore,balanceAfter,"Balances are equal");

        } catch (PersonException e) {
            Assertions.fail();
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NotEnoughBalanceException e) {
            e.printStackTrace();
        }

    }
}
