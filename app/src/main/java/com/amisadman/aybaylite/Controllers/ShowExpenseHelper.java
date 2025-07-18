package com.amisadman.aybaylite.Controllers;

import android.content.Context;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowExpenseHelper {
    private final DatabaseHelper dbHelper;
    public ShowExpenseHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public ShowExpenseHelper(Context context) {
        this(new DatabaseHelper(context));
    }
    public ArrayList<HashMap<String, String>> loadIncome(){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        arrayList.clear(); // Clear previous data
        arrayList = dbHelper.getAllExpenses();
        return arrayList;
    }
    public void deleteData(String id){
        dbHelper.deleteExpense(id);
    }

}
