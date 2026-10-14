package com.example.cnExpense.dal;

import com.example.cnExpense.entity.Expense;
import com.example.cnExpense.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ExpenseDalImpl implements ExpenseDal {

    @Autowired
    EntityManager entityManager;

    @Override
    public Expense saveExpense(Long userId, Expense expense) {
        Session session = entityManager.unwrap(Session.class);

        User user = session.get(User.class, userId);

        if (user == null) {
            return null;
        }

        expense.setUser(user);
        session.save(expense);

        return expense;
    }

    @Override
    public Expense getExpenseById(Long expenseId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Expense.class, expenseId);
    }
}