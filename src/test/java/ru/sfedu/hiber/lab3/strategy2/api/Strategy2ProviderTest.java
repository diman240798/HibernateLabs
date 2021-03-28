package ru.sfedu.hiber.lab3.strategy2.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ru.sfedu.hiber.lab3.strategy2.model.CreditAccount1;
import ru.sfedu.hiber.lab3.strategy2.model.DebitAccount1;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class Strategy2ProviderTest {

    private final static Logger LOG = LogManager.getLogger(Strategy2ProviderTest.class);

    @Test
    public void getByAccountsSuccess() throws IOException{
        IProvider instance =  new Strategy2Provider();
        List obj = instance.getByAccounts().get();
        LOG.info(obj);
        assertNotNull(obj);
    }

    @Test
    public void saveSuccess() throws IOException {
        List<Long> ids = saveGetIds();
        LOG.info(ids);
        assertNotNull(ids);
    }

    private List<Long> saveGetIds() throws IOException {
        IProvider instance =  new Strategy2Provider();
        CreditAccount1 creditAccount = new CreditAccount1();
        creditAccount.setOwner("Alex");
        creditAccount.setCreditLimit(new BigDecimal("10"));
        creditAccount.setBalance(new BigDecimal("105"));
        creditAccount.setInterestRate(new BigDecimal("123"));

        DebitAccount1 debitAccount =  new DebitAccount1();
        debitAccount.setBalance(new BigDecimal("1090"));
        debitAccount.setInterestRate(new BigDecimal("1245"));
        debitAccount.setOwner("Ura");
        debitAccount.setOverdraftFee(new BigDecimal("10"));

        return instance.save(creditAccount, debitAccount);
    }

    @Test
    public void saveFail() throws IOException{
        IProvider instance =  new Strategy2Provider();
        CreditAccount1 creditAccount = new CreditAccount1();
        DebitAccount1 debitAccount =  new DebitAccount1();
        creditAccount.setOwner("Name10");
        creditAccount.setCreditLimit(new BigDecimal("10"));
        creditAccount.setBalance(new BigDecimal("105"));
        creditAccount.setInterestRate(new BigDecimal("123"));

        debitAccount.setBalance(new BigDecimal("1090"));
        debitAccount.setInterestRate(new BigDecimal("1245"));
        debitAccount.setOwner("Ura");
        debitAccount.setOverdraftFee(new BigDecimal("10"));

        List<Long> ids = instance.save(creditAccount, debitAccount);
        LOG.info(ids);
        assertNull(ids);
    }

    @Test
    public void updateTypeAccountSuccess() throws IOException{
        IProvider instance = new Strategy2Provider();
        List<Long> ids = saveGetIds();
        CreditAccount1 creditAccount = (CreditAccount1) instance.updateTypeAccount(CreditAccount1.class, ids.get(0), "Don").get();
        assertNotNull(creditAccount);
    }

    @Test
    public void updateTypeAccountFail() throws IOException{
        IProvider instance =  new Strategy2Provider();
        CreditAccount1 creditAccount = null;
        try{
            creditAccount = (CreditAccount1) instance.updateTypeAccount(CreditAccount1.class, 390L, "Son").get();
        }catch (Exception e){
            LOG.error(e);
        }
        assertNull(creditAccount);
    }

    @Test
    public void getByTypeAccountSuccess() throws IOException {
        IProvider instance =  new Strategy2Provider();
        List<Long> ids = saveGetIds();
        CreditAccount1 creditAccount = (CreditAccount1) instance.getByTypeAccount(CreditAccount1.class, ids.get(0)).get();
        assertNotNull(creditAccount);
    }

    @Test
    public void getByTypeAccountFail() throws IOException{
        IProvider instance =  new Strategy2Provider();
        CreditAccount1 creditAccount = null;
        try {
            creditAccount = (CreditAccount1) instance.getByTypeAccount(CreditAccount1.class, 12345L).get();
        }catch (NullPointerException e){
            LOG.error(e);
        }
        assertNull(creditAccount);
    }

    @Test
    public void deleteTypeAccountSuccess()throws IOException{
        IProvider instance =  new Strategy2Provider();
        List<Long> ids = saveGetIds();
        boolean flag = instance.deleteTypeAccount(CreditAccount1.class, ids.get(0));
        assertTrue(flag);
    }

    @Test
    public void deleteTypeAccountFail()throws IOException{
        IProvider instance =  new Strategy2Provider();
        boolean flag = instance.deleteTypeAccount(CreditAccount1.class, 555L);
        assertFalse(flag);
    }

}