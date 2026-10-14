package com.example.cnExpense.dal;

import com.example.cnExpense.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserDalImpl implements UserDal {

    @Autowired
    EntityManager entityManager;

    @Override
    public User getUserById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class, id);
    }

    @Override
    public User saveUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.save(user);
        return user;
    }

    @Override
    public User updateUser(Long userId, User user) {
        Session session = entityManager.unwrap(Session.class);

        User existingUser = session.get(User.class, userId);

        if (existingUser == null) {
            return null;
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setNickname(user.getNickname());
        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());

        return existingUser;
    }

    @Override
    public User setBudget(Long userId, double budget) {
        Session session = entityManager.unwrap(Session.class);

        User user = session.get(User.class, userId);

        if (user == null) {
            return null;
        }

        user.setBudget(budget);
        user.setIsbudgetSet(true);

        return user;
    }

    @Override
    public double getTotalExpense(Long userId) {
        Session session = entityManager.unwrap(Session.class);

        Double total = session.createQuery(
                        "SELECT COALESCE(SUM(e.amount), 0.0) " +
                                "FROM Expense e WHERE e.user.id = :userId",
                        Double.class)
                .setParameter("userId", userId)
                .getSingleResult();

        return total;
    }

    @Override
    public double getQuotation(Long userId) {
        Session session = entityManager.unwrap(Session.class);

        Double totalIncome = session.createQuery(
                        "SELECT COALESCE(SUM(i.amount), 0.0) " +
                                "FROM Income i WHERE i.user.id = :userId",
                        Double.class)
                .setParameter("userId", userId)
                .getSingleResult();

        Double totalExpense = session.createQuery(
                        "SELECT COALESCE(SUM(e.amount), 0.0) " +
                                "FROM Expense e WHERE e.user.id = :userId",
                        Double.class)
                .setParameter("userId", userId)
                .getSingleResult();

        return totalIncome - totalExpense;
    }

    @Override
    public double getAvgExpenseData(Long userId) {
        Session session = entityManager.unwrap(Session.class);

        Double average = session.createQuery(
                        "SELECT AVG(e.amount) " +
                                "FROM Expense e WHERE e.user.id = :userId",
                        Double.class)
                .setParameter("userId", userId)
                .getSingleResult();

        return average == null ? 0.0 : average;
    }
}