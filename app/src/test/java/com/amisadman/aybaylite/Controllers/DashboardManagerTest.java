package com.amisadman.aybaylite.Controllers;

import android.content.Context;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
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

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 1500.0, -100.0})
    public void testIncomePositivity(double income)
    {
        when(mockDbHelper.calculateTotalIncome()).thenReturn(income);
        double result = dashboardManager.getTotalIncome();
        if(income > 0) { assertTrue(result > 0); }
        else { assertFalse(result > 0); }
    }

    static Stream<Arguments> expenseThresholdProvider()
    {
        return Stream.of(
                Arguments.of(500.0, 0.0),
                Arguments.of(0.0, 500.0),
                Arguments.of(300.0, 300.0)
        );
    }
    @ParameterizedTest
    @MethodSource("expenseThresholdProvider")
    public void testExpenseNotEqual(double expense1, double expense2)
    {
        when(mockDbHelper.calculateTotalExpense()).thenReturn(expense1);
        if(expense1 != expense2) { assertNotEquals(expense2, dashboardManager.getTotalExpense(), 0.001); }
        else { assertEquals(expense2, dashboardManager.getTotalExpense(), 0.001); }
    }

    @Test
    public void testLoadDataReturnsNotNull()
    {
        when(mockDbHelper.getStatement()).thenReturn(new ArrayList<>());
        assertNotNull(dashboardManager.loadDataFromDatabase());
    }

    @Test
    public void testLoadDataReturnsNull()
    {
        when(mockDbHelper.getStatement()).thenReturn(null);
        assertNull(dashboardManager.loadDataFromDatabase());
    }

    @Test
    public void testObjectReferences()
    {
        ArrayList<HashMap<String, String>> data1 = new ArrayList<>();
        when(mockDbHelper.getStatement()).thenReturn(data1);

        ArrayList<HashMap<String, String>> result1 = dashboardManager.loadDataFromDatabase();
        ArrayList<HashMap<String, String>> result2 = dashboardManager.loadDataFromDatabase();

        assertSame(result1, result2);
        assertNotSame(new ArrayList<>(), result1);
    }

    @Test
    public void testExceptionHandling()
    {
        when(mockDbHelper.calculateTotalIncome()).thenThrow(new NullPointerException("DB Error"));
        assertThrows(NullPointerException.class, () -> dashboardManager.getTotalIncome());

        when(mockDbHelper.calculateTotalExpense()).thenReturn(100.0);
        assertDoesNotThrow(() -> dashboardManager.getTotalExpense());
    }

    @Test
    public void testTotalIncomeWithinTime()
    {
        when(mockDbHelper.calculateTotalIncome()).thenReturn(2000.0);
        assertTimeout(Duration.ofMillis(100), () -> dashboardManager.getTotalIncome());
    }

    @Test
    public void testIncomeUnderSimulatedLatency()
    {
        when(mockDbHelper.calculateTotalIncome()).thenAnswer(invocation ->
        {
            Thread.sleep(80);
            return 2000.0;
        });
        assertTimeout(Duration.ofMillis(100), () -> dashboardManager.getTotalIncome());
    }
}