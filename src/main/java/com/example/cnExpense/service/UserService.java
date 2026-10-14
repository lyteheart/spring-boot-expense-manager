package com.example.cnExpense.service;

import com.example.cnExpense.dal.UserDal;
import com.example.cnExpense.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDal userDal;

    @Transactional
    public User getUserById(Long id) {
        User user = userDal.getUserById(id);

        if (user == null) {
            throw new RuntimeException("User not found with id: " + id);
        }

        return user;
    }

    @Transactional
    public User saveUser(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null.");
        }

        return userDal.saveUser(user);
    }

    @Transactional
    public User editUser(Long userId, User user) {
        User existingUser = userDal.getUserById(userId);

        if (existingUser == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        User updatedUser = userDal.updateUser(userId, user);

        if (updatedUser == null) {
            throw new RuntimeException("Unable to update user with id: " + userId);
        }

        return updatedUser;
    }

    @Transactional
    public User setBudget(Long userId, double budget) {
        if (budget < 0) {
            throw new RuntimeException("Budget cannot be negative.");
        }

        User user = userDal.setBudget(userId, budget);

        if (user == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return user;
    }

    @Transactional
    public double getTotalExpense(Long userId) {
        getUserById(userId);
        return userDal.getTotalExpense(userId);
    }

    @Transactional
    public double getQuotation(Long userId) {
        getUserById(userId);
        return userDal.getQuotation(userId);
    }

    @Transactional
    public double getAvgExpenseData(Long userId) {
        getUserById(userId);
        return userDal.getAvgExpenseData(userId);
    }
}