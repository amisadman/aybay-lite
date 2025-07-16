package com.amisadman.aybaylite.Controllers;

import android.content.Context;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DashboardManagerTest {


    @Mock
    private DatabaseHelper mockDbHelper;

    private DashboardManager dashboardManager;

    @BeforeEach
    public void setUp() {
        dashboardManager = new DashboardManager(mockDbHelper);
    }

    @Test
    public void testLoadDataFromDatabase() {
        // Arrange
        ArrayList<HashMap<String, String>> expectedData = new ArrayList<>();
        HashMap<String, String> testItem = new HashMap<>();
        testItem.put("key", "value");
        expectedData.add(testItem);

        when(mockDbHelper.getStatement()).thenReturn(expectedData);

        // Act
        ArrayList<HashMap<String, String>> result = dashboardManager.loadDataFromDatabase();

        // Assert
        assertEquals(expectedData, result);
        verify(mockDbHelper).getStatement();
    }
    @ParameterizedTest
    @CsvSource({
            "key1,value1",
            "date,2023-01-01",
            "description,Café & Restaurant"
    })
    public void testLoadDataFromDatabaseWithCsv(String key, String value) {
        // Arrange
        ArrayList<HashMap<String, String>> expectedData = new ArrayList<>();
        HashMap<String, String> testItem = new HashMap<>();
        testItem.put(key, value);
        expectedData.add(testItem);

        when(mockDbHelper.getStatement()).thenReturn(expectedData);

        // Act and Assert
        assertEquals(expectedData, dashboardManager.loadDataFromDatabase());
        verify(mockDbHelper).getStatement();
    }

    @Test
    public void testGetTotalIncome() {
        // Arrange
        double expectedIncome = 1500.75;
        when(mockDbHelper.calculateTotalIncome()).thenReturn(expectedIncome);

        // Act
        double result = dashboardManager.getTotalIncome();

        // Assert
        assertEquals(expectedIncome, result, 0.001);
        verify(mockDbHelper).calculateTotalIncome();
    }

    @Test
    public void testGetTotalExpense() {
        // Arrange
        double expectedExpense = 850.50;
        when(mockDbHelper.calculateTotalExpense()).thenReturn(expectedExpense);

        // Act
        double result = dashboardManager.getTotalExpense();

        // Assert
        assertEquals(expectedExpense, result, 0.001);
        verify(mockDbHelper).calculateTotalExpense();
    }

}