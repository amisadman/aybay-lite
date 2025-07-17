package com.amisadman.aybaylite.Controllers;

import android.content.Context;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowIncomeHelper {
    private final DatabaseHelper dbHelper;
    public ShowIncomeHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public ShowIncomeHelper(Context context) {
        this(new DatabaseHelper(context));
    }
    public ArrayList<HashMap<String, String>> loadIncome(){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        arrayList.clear(); // Clear previous data
        arrayList = dbHelper.getAllIncome();
        return arrayList;
    }
    public void deleteData(String id){
        dbHelper.deleteIncome(id);
    }

}
