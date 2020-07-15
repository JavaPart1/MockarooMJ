package be.vdab.dao;

import be.vdab.model.BitcoinAccount;
import be.vdab.model.NotEnoughBalanceException;
import be.vdab.model.Person;
import be.vdab.model.PersonException;

import java.sql.SQLException;

public interface BitAccDao {
    public BitcoinAccount getBitAccById(int id) throws SQLException;
    public void createBitAcc(BitcoinAccount nwBitAcc) throws SQLException;
    public void updateBitAcc(BitcoinAccount corBitAcc) throws SQLException;
    public void deleteBitAcc(BitcoinAccount exBitAcc) throws SQLException;
    public double getBalance(BitcoinAccount curBitAcc) throws SQLException;
    public void pay(BitcoinAccount corBitAcc) throws NotEnoughBalanceException;
    public void receivePayment(BitcoinAccount corBitAcc);

}
