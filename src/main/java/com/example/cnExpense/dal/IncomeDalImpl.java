package com.example.cnExpense.dal;

import com.example.cnExpense.entity.Income;
import com.example.cnExpense.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class IncomeDalImpl implements IncomeDal {

    @Autowired
    EntityManager entityManager;

    @Override
    public Income getIncomeById(Long incomeId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Income.class, incomeId);
    }

    @Override
    public Income saveIncome(User user, Income newIncome) {
        Session session = entityManager.unwrap(Session.class);

        newIncome.setUser(user);
        session.save(newIncome);

        return newIncome;
    }

    @Override
    public Income updateIncome(Long incomeId, Income income) {
        Session session = entityManager.unwrap(Session.class);

        Income existingIncome = session.get(Income.class, incomeId);

        if (existingIncome == null) {
            return null;
        }

        existingIncome.setAmount(income.getAmount());
        existingIncome.setDate(income.getDate());
        existingIncome.setDescription(income.getDescription());
        existingIncome.setIncomeType(income.getIncomeType());

        return existingIncome;
    }
}