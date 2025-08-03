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
    public void addData(double amount, String reason)
    {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (reason == null || reason.trim().isEmpty()) throw new IllegalArgumentException("Reason is required");
        dbHelper.addExpense(amount, reason);
    }

    public void updateData(String id, double amount, String reason)
    {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID is required");
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (reason == null || reason.trim().isEmpty()) throw new IllegalArgumentException("Reason is required");
        dbHelper.updateExpense(id, amount, reason);
    }
}
