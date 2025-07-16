package com.amisadman.aybaylite.Controllers;

import android.content.Context;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

public class AddExpenseHelper {
    private final DatabaseHelper dbHelper;
    public AddExpenseHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public AddExpenseHelper(Context context) {
        this(new DatabaseHelper(context));
    }
    public void updateData(String id, double amount, String reason){
        dbHelper.updateExpense(id, amount, reason);

    }
    public void addData(double amount,String reason){
        dbHelper.addExpense(amount, reason);

    }

}
