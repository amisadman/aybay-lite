package com.amisadman.aybaylite.Controllers;

import android.content.Context;
import android.util.Log;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowExpenseHelper {
    private DatabaseHelper dbHelper;
    public ShowExpenseHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public ShowExpenseHelper(Context context) {
        this(new DatabaseHelper(context));
    }
    public ArrayList<HashMap<String, String>> loadExpense(){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        arrayList.clear(); // Clear previous data
        arrayList = dbHelper.getAllExpenses();
        return arrayList;
    }
    public boolean deleteData(String id) {
        try {
            int rowsDeleted = dbHelper.deleteExpense(id);
            return rowsDeleted > 0;
        } catch (Exception e) {
            Log.e("ShowExpenseHelper", "Delete failed for ID: " + id, e);
            return false;
        }
    }

}
