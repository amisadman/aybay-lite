package com.amisadman.aybaylite.Controllers;

import android.content.Context;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

public class AddIncomeHelper {
    private final DatabaseHelper dbHelper;
    public AddIncomeHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public AddIncomeHelper(Context context) {
        this(new DatabaseHelper(context));
    }
    public void updateData(String id, double amount, String reason){
        dbHelper.updateExpense(id, amount, reason);

    }
    public void addData(double amount,String reason){
        dbHelper.addIncome(amount, reason);

    }

}
