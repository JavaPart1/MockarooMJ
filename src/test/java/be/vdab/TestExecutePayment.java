package be.vdab;

import be.vdab.dao.BitAccDao;
import be.vdab.dao.BitAccDaoImpl;
import be.vdab.dao.PersonDao;
import be.vdab.dao.PersonDaoImpl;
import be.vdab.model.BitcoinAccount;
import be.vdab.model.NotEnoughBalanceException;
import be.vdab.model.Person;
import be.vdab.model.PersonException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static be.vdab.dao.JdbcPass.*;

public class TestExecutePayment {

    @ParameterizedTest
    @MethodSource("range")
    void testExecutePayment(int parm) throws PersonException, SQLException, NotEnoughBalanceException {
        int person1Id = 5;
        int person2Id = 12;
        double paymentAmount = (double)parm;
        //double balanceBefore;
        //double balanceAfter;
        // Person and Dao
        try {
            PersonDao personDao = new PersonDaoImpl(JDBCURL,JDBCUSER,PASSW);
            Person person1 = personDao.getPersonById(person1Id);
            Person person2 = personDao.getPersonById(person2Id);
            // bitcoin account and dao
            ArrayList<BitcoinAccount> accList = new ArrayList<>();
            BitAccDao accDao = new BitAccDaoImpl(JDBCURL,JDBCUSER,PASSW);
            accList = accDao.getBitAccByOwner(person1.getId());
            BitcoinAccount personBitAcc1 = accList.get(0);// first account found
            accList = accDao.getBitAccByOwner(person2.getId());
            BitcoinAccount personBitAcc2 = accList.get(0);// first account found
            //balanceBefore = personBitAcc.getSaldo();

            // Execute payment with different amounts
            accDao.executePaymentTxn(personBitAcc1,personBitAcc2,paymentAmount);

            // compare with DB
            System.out.println("payment amount: "+paymentAmount);

        } catch (PersonException e) {
            Assertions.fail();
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NotEnoughBalanceException e) {
            e.printStackTrace();
        }

    }

    static IntStream range(){
        return IntStream.range(999990,1000005);
    }
}
