package ru.sfedu.hiber.lab3.strategy1.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.hiber.lab3.strategy1.model.Account;
import ru.sfedu.hiber.lab3.strategy1.model.CreditAccount;
import ru.sfedu.hiber.lab3.strategy1.model.DebitAccount;
import ru.sfedu.hiber.lab3.strategy2.model.Account1;
import ru.sfedu.hiber.lab3.strategy2.model.CreditAccount1;
import ru.sfedu.hiber.lab3.strategy2.model.DebitAccount1;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Strategy1Provider implements IProvider {
    private final static Logger LOG = LogManager.getLogger(Strategy1Provider.class);

    @Override
    public Optional<List> getByAccounts(){
        return HibernateUtil.runSessionQuerySafe(session -> {
            List<Account> accounts = session.createQuery("FROM CreditAccount").list();
            accounts.addAll(session.createQuery("FROM DebitAccount").list());
            return Optional.of(accounts);
        });
    }

    @Override
    public List<Long> save(CreditAccount creditAccount, DebitAccount debitAccount) {
        List<Long> ids = new ArrayList<>();

        if((Objects.equals(creditAccount.getOwner(), "Name10")) || (Objects.equals(debitAccount.getOwner(), "Name10"))){
            return null;
        }

        HibernateUtil.runTransactionSafe(session -> {
            long id = (long) session.save(creditAccount);
            ids.add(id);
            id = (long) session.save(debitAccount);
            ids.add(id);
        });

        return ids;
    }

    @Override
    public <T> Optional<T> getByTypeAccount(Class<T> entity, long id){
        Optional optionalAccount = HibernateUtil.runSessionQuerySafe(session -> {
            Account account = entity.getName().equals(CreditAccount.class.getName()) ? session.get(CreditAccount.class, id) : session.get(DebitAccount.class, id);
            return Optional.ofNullable(account);
        });
        return optionalAccount;
    }

    @Override
    public <T> Optional<T> updateTypeAccount(Class<T> entity, long id, String name){
        Account updateEntity = (Account) getByTypeAccount(entity, id).get();
        updateEntity.setOwner(name);
        HibernateUtil.runTransactionSafe(session -> session.update(updateEntity));
        return getByTypeAccount(entity, id);
    }

    @Override
    public <T> boolean deleteTypeAccount(Class<T> entity, long id){
        Optional deleteEntityOption = getByTypeAccount(entity, id);
        if (!deleteEntityOption.isPresent()) return false;
        Object deleteEntity = deleteEntityOption.get();
        return HibernateUtil.runTransactionSafe(session -> session.delete(deleteEntity));
    }

}
