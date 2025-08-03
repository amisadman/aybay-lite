package com.amisadman.aybaylite.Controllers;

import android.content.Context;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

public class AddExpenseHelper {
    private final DatabaseHelper dbHelper;
    private  final int minimum = 1;
    private final int maximum = (int) 1e9;
    public AddExpenseHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public AddExpenseHelper(Context context) {
        this(new DatabaseHelper(context));
    }
    public void updateData(String id, double amount, String reason){
        if(amount >= minimum && amount <= maximum)dbHelper.updateExpense(id, amount, reason);
        else{
            throw new IllegalArgumentException(
                    String.format("Amount %.2f is outside valid range [%.2f, %.2f]",
                            amount, minimum, maximum)
            );
        }
    }
    public void addData(double amount,String reason){
        if(amount >= minimum && amount <= maximum)dbHelper.addExpense(amount, reason);
        else{
            throw new IllegalArgumentException(
                    String.format("Amount %.2f is outside valid range [%.2f, %.2f]",
                            amount, minimum, maximum)
            );
        }

    }

}
