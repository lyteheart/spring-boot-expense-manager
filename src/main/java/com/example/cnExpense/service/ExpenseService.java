package com.example.cnExpense.service;

import com.example.cnExpense.dal.ExpenseDal;
import com.example.cnExpense.dal.UserDal;
import com.example.cnExpense.entity.Expense;
import com.example.cnExpense.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseDal expenseDal;

    @Autowired
    private UserDal userDal;

    @Transactional
    public Expense saveExpense(Long userId, Expense expense) {

        User user = userDal.getUserById(userId);

        if (user == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        if (expense == null) {
            throw new RuntimeException("Expense cannot be null.");
        }

        if (expense.getAmount() <= 0) {
            throw new RuntimeException("Expense amount must be greater than zero.");
        }

        if (user.isIsbudgetSet()) {
            double totalExpense = userDal.getTotalExpense(userId);

            if (totalExpense + expense.getAmount() > user.getBudget()) {
                throw new RuntimeException("Expense exceeds the set budget.");
            }
        }

        return expenseDal.saveExpense(userId, expense);
    }

    @Transactional
    public Expense getExpenseById(Long expenseId) {
        Expense expense = expenseDal.getExpenseById(expenseId);

        if (expense == null) {
            throw new RuntimeException("Expense not found with id: " + expenseId);
        }

        return expense;
    }
}