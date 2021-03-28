package ru.sfedu.hiber.lab3.strategy4.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ru.sfedu.hiber.lab3.strategy2.api.Strategy2Provider;
import ru.sfedu.hiber.lab3.strategy2.model.CreditAccount1;
import ru.sfedu.hiber.lab3.strategy3.api.Strategy3Provider;
import ru.sfedu.hiber.lab3.strategy3.model.CreditAccount2;
import ru.sfedu.hiber.lab3.strategy3.model.DebitAccount2;
import ru.sfedu.hiber.lab3.strategy4.model.CreditAccount3;
import ru.sfedu.hiber.lab3.strategy4.model.DebitAccount3;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class Strategy4ProviderTest {

    private final static Logger LOG = LogManager.getLogger(Strategy4ProviderTest.class);


    @Test
    public void getByAccountsSuccess() throws IOException{
        IProvider instance =  new Strategy4Provider();
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
        IProvider instance = new Strategy4Provider();
        CreditAccount3 creditAccount = new CreditAccount3();
        DebitAccount3 debitAccount =  new DebitAccount3();
        creditAccount.setOwner("Alex");
        creditAccount.setCreditLimit(new BigDecimal("10"));
        creditAccount.setBalance(new BigDecimal("105"));
        creditAccount.setInterestRate(new BigDecimal("123"));

        debitAccount.setBalance(new BigDecimal("1090"));
        debitAccount.setInterestRate(new BigDecimal("1245"));
        debitAccount.setOwner("Ura");
        debitAccount.setOverdraftFee(new BigDecimal("10"));

        return instance.save(creditAccount, debitAccount);
    }

    @Test
    public void saveFail() throws IOException{
        IProvider instance =  new Strategy4Provider();
        CreditAccount3 creditAccount = new CreditAccount3();
        DebitAccount3 debitAccount =  new DebitAccount3();
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
    public void getByTypeAccountSuccess() throws IOException {
        IProvider instance =  new Strategy4Provider();
        List<Long> ids = saveGetIds();
        CreditAccount3 creditAccount = (CreditAccount3) instance.getByTypeAccount(CreditAccount3.class, ids.get(0)).get();
        LOG.info(creditAccount);
        assertNotNull(creditAccount);
    }

    @Test
    public void getByTypeAccountFail() throws IOException{
        IProvider instance =  new Strategy4Provider();
        CreditAccount3 creditAccount = null;
        try {
            creditAccount = (CreditAccount3) instance.getByTypeAccount(CreditAccount3.class, 12345L).get();
        }catch (NullPointerException e){
            LOG.error(e);
        }
        assertNull(creditAccount);
    }

    @Test
    public void updateTypeAccountSuccess() throws IOException{
        IProvider instance =  new Strategy4Provider();
        List<Long> ids = saveGetIds();
        CreditAccount3 creditAccount = (CreditAccount3) instance.updateTypeAccount( CreditAccount3.class, ids.get(0), "Don1").get();
        assertNotNull(creditAccount);
    }

    @Test
    public void updateTypeAccountFail() throws IOException{
        IProvider instance =  new Strategy4Provider();
        CreditAccount3 creditAccount = null;
        try{
            creditAccount = (CreditAccount3) instance.updateTypeAccount(CreditAccount3.class, 390L, "Son").get();
        }catch (NullPointerException e){
            LOG.error(e);
        }
        assertNull(creditAccount);
    }

    @Test
    public void deleteTypeAccountSuccess()throws IOException{
        IProvider instance = new Strategy4Provider();
        List<Long> ids = saveGetIds();
        boolean flag = instance.deleteTypeAccount(DebitAccount3.class, ids.get(1));
        assertTrue(flag);
    }

    @Test
    public void deleteTypeAccountFail()throws IOException{
        IProvider instance =  new Strategy4Provider();
        boolean flag = instance.deleteTypeAccount(CreditAccount1.class, 555L);
        assertFalse(flag);
    }



}