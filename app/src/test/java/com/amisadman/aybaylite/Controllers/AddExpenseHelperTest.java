package com.amisadman.aybaylite.Controllers;

import static org.mockito.Mockito.verify;

import android.content.Context;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class AddExpenseHelperTest
{
    @Mock
    private DatabaseHelper mockDbHelper;
    @Mock
    private Context mockContext;
    @InjectMocks
    private AddExpenseHelper addExpenseHelper;

    @BeforeEach
    void setUp() { addExpenseHelper = new AddExpenseHelper(mockDbHelper); }

    @AfterEach
    void tearDown() { }

    @Test
    void testConstructorWithContext() { new AddExpenseHelper(mockContext); }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 1.00, 1000.00, 999999.99})
    void testAddExpanseDiverseValues(double amount)
    {
        addExpenseHelper.addData(amount, "Test reason");
        verify(mockDbHelper).addExpense(amount, "Test reason");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "A", "Long reason with spaces and special chars: !@#$%^&*()"})
    void testAddDataWithVariousReasons(String reason)
    {
        addExpenseHelper.addData(100.00, reason);
        verify(mockDbHelper).addIncome(100.00, reason);
    }

    @ParameterizedTest
    @CsvSource({
            "1200.00, Rent payment",
            "89.99, Utility bill",
            "45.50, Coffee with client"
    })
    void testAddExpense(double amount, String reason)
    {
        addExpenseHelper.addData(amount, reason);
        verify(mockDbHelper).addExpense(amount, reason);
    }

    @ParameterizedTest
    @CsvSource({
            "1200.00, Rent payment",
            "89.99, Utility bill",
            "45.50, Coffee with client"
    })
    void testUpdateExpense(double amount, String reason)
    {
        addExpenseHelper.addData(amount, reason);
        verify(mockDbHelper).addExpense(amount, reason);
    }

    @ParameterizedTest
    @MethodSource("provideAddExpenseTestCases")
    void testAddExpenseWithExtraParameter(double amount, String reason)
    {
        addExpenseHelper.addData(amount, reason);
        verify(mockDbHelper).addExpense(amount, reason);
    }
    private static Stream<Arguments> provideAddExpenseTestCases()
    {
        return Stream.of(
                // Normal cases
                Arguments.of(950.00, "House rent", "Recurring monthly payment"),
                Arguments.of(150.75, "Electricity bill", "Utility expense verification"),

                // Edge cases
                Arguments.of(0.01, "Rounding fix", "Lowest boundary test"),
                Arguments.of(1_000_000.00, "Big purchase", "Stress test for large expense"),
                Arguments.of(300.00, "🍔 Lunch", "Emoji inclusion test"),
                Arguments.of(55.55, "E", "Shortest non-empty reason"),
                Arguments.of(275.00, "Refund for faulty product from online vendor", "Typical case of return processing"),
                Arguments.of(69.42, "Misc", "Precision and formatting check")
        );
    }

    @ParameterizedTest
    @MethodSource("provideUpdateExpenseTestCases")
    void testUpdateExpenseDataWithVariousCases(String id, double amount, String reason)
    {
        addExpenseHelper.updateData(id, amount, reason);
        verify(mockDbHelper).updateExpense(id, amount, reason);
    }
    private static Stream<Arguments> provideUpdateExpenseTestCases()
    {
        return Stream.of(
                // Normal cases
                Arguments.of("exp_1", 200.75, "Grocery shopping"),
                Arguments.of("exp_2", 99.99, "Bus fare"),

                // Edge cases
                Arguments.of("id-dash-case", 0.01, "Tiny purchase"),
                Arguments.of("EXP_ID_999", 999999.99, "Medical emergency expense"),
                Arguments.of("weird$id#chars", 500.00, "ID with special chars"),
                Arguments.of("no_reason", 45.00, ""),
                Arguments.of("ridiculously_long_id_x1234567890", 1200.00, "Extremely verbose reason text to test overflow and resilience under verbose user input")
        );
    }
}