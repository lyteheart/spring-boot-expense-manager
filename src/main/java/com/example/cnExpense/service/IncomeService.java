package com.example.cnExpense.service;

import com.example.cnExpense.dal.IncomeDal;
import com.example.cnExpense.entity.Income;
import com.example.cnExpense.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class IncomeService {

    @Autowired
    private IncomeDal incomeDal;

    @Autowired
    private UserService userService;

    @Transactional
    public Income getIncomeById(Long incomeId) {
        Income income = incomeDal.getIncomeById(incomeId);

        if (income == null) {
            throw new RuntimeException(
                    "Income not found with id: " + incomeId);
        }

        return income;
    }

    @Transactional
    public Income saveIncome(User user, Income income) {
        if (income == null) {
            throw new RuntimeException("Income cannot be null.");
        }

        if (user == null || user.getId() == null) {
            throw new RuntimeException("Valid user is required.");
        }

        User existingUser = userService.getUserById(user.getId());

        return incomeDal.saveIncome(existingUser, income);
    }

    @Transactional
    public Income editIncome(Long incomeId, Income income) {
        if (income == null) {
            throw new RuntimeException("Income cannot be null.");
        }

        Income existingIncome = incomeDal.getIncomeById(incomeId);

        if (existingIncome == null) {
            throw new RuntimeException(
                    "Income not found with id: " + incomeId);
        }

        Income updatedIncome = incomeDal.updateIncome(incomeId, income);

        if (updatedIncome == null) {
            throw new RuntimeException(
                    "Unable to update income with id: " + incomeId);
        }

        return updatedIncome;
    }
}