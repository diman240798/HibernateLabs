package ru.sfedu.hiber.lab3.strategy1.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ru.sfedu.hiber.lab2.api.ITestEntityProvider;
import ru.sfedu.hiber.lab2.api.TestEntityProvider;
import ru.sfedu.hiber.lab3.strategy1.model.CreditAccount;
import ru.sfedu.hiber.lab3.strategy1.model.DebitAccount;
import ru.sfedu.hiber.lab3.strategy2.model.CreditAccount1;
import ru.sfedu.hiber.lab3.strategy2.model.DebitAccount1;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class Strategy1ProviderTest {

    private final static Logger LOG = LogManager.getLogger(Strategy1ProviderTest.class);

    @Test
    public void getByAccountsSuccess() throws IOException{
        IProvider instance =  new Strategy1Provider();
        List obj = instance.getByAccounts().get();
        LOG.info(obj);
        assertNotNull(obj);
    }


    @Test
    public void saveSuccess() throws IOException {
        IProvider instance = new Strategy1Provider();
        CreditAccount creditAccount = new CreditAccount();
        DebitAccount debitAccount =  new DebitAccount();
        creditAccount.setOwner("Alex");
        creditAccount.setCreditLimit(new BigDecimal("10"));
        creditAccount.setBalance(new BigDecimal("105"));
        creditAccount.setInterestRate(new BigDecimal("123"));

        debitAccount.setBalance(new BigDecimal("1090"));
        debitAccount.setInterestRate(new BigDecimal("1245"));
        debitAccount.setOwner("Ura");
        debitAccount.setOverdraftFee(new BigDecimal("10"));

        List<Long> ids = instance.save(creditAccount, debitAccount);
        LOG.info(ids);
        assertNotNull(ids);
    }

    @Test
    public void saveFail() throws IOException{
        IProvider instance = new Strategy1Provider();
        CreditAccount creditAccount = new CreditAccount();
        DebitAccount debitAccount =  new DebitAccount();
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
        IProvider instance =  new Strategy1Provider();
        List<Long> ids = instance.save(new CreditAccount(), new DebitAccount());
        CreditAccount creditAccount = (CreditAccount) instance.getByTypeAccount(CreditAccount.class, ids.get(0)).get();
        LOG.info(creditAccount);
        assertNotNull(creditAccount);
    }

    @Test
    public void getByTypeAccountFail() throws IOException{
        IProvider instance =  new Strategy1Provider();
        Optional<CreditAccount> creditAccount = Optional.empty();
        try {
            creditAccount = instance.getByTypeAccount(CreditAccount.class, 12345L);
        } catch (NullPointerException e){
            LOG.error(e);
        }
        assertFalse(creditAccount.isPresent());
    }

    @Test
    public void updateTypeAccountSuccess() throws IOException{
        IProvider instance =  new Strategy1Provider();
        List<Long> ids = instance.save(new CreditAccount(), new DebitAccount());
        CreditAccount creditAccount = instance.updateTypeAccount(CreditAccount.class, ids.get(0), "Alex2").get();
        assertNotNull(creditAccount);
    }

    @Test
    public void updateTypeAccountFail() throws IOException{
        IProvider instance =  new Strategy1Provider();
        Optional<CreditAccount> creditAccount = Optional.empty();
        try{
            creditAccount = instance.updateTypeAccount(CreditAccount.class, 309L, "Son");
        } catch (Exception e){
            LOG.error(e);
        }
        assertFalse(creditAccount.isPresent());
    }

    @Test
    public void deleteTypeAccountSuccess()throws IOException{
        IProvider instance =  new Strategy1Provider();
        List<Long> ids = instance.save(new CreditAccount(), new DebitAccount());
        boolean flag = instance.deleteTypeAccount(CreditAccount.class, ids.get(0));
        assertTrue(flag);
    }

    @Test
    public void deleteTypeAccountFail()throws IOException{
        IProvider instance =  new Strategy1Provider();
        boolean flag = instance.deleteTypeAccount(CreditAccount.class, 555L);
        assertFalse(flag);
    }

}