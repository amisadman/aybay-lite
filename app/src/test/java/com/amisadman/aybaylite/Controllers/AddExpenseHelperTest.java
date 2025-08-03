package com.amisadman.aybaylite.Controllers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import android.content.Context;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
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
                Arguments.of(950.00, "House rent"),
                Arguments.of(150.75, "Electricity bill"),

                // Edge cases
                Arguments.of(0.01, "Rounding fix"),
                Arguments.of(1_000_000.00, "Big purchase"),
                Arguments.of(300.00, "🍔 Lunch"),
                Arguments.of(55.55, "E"),
                Arguments.of(275.00, "Refund for faulty product from online vendor"),
                Arguments.of(69.42, "Misc")
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
    //===============================================================================================

    //Boundary value testing

    @ParameterizedTest
    @MethodSource("provideAmountTestCases")
    void testAddData_BoundaryValues(double amount, boolean shouldSucceed) {
        String reason = "Test income";

        if (shouldSucceed) {
            // Test valid cases
            assertDoesNotThrow(() -> addExpenseHelper.addData(amount, reason));
            verify(mockDbHelper).addExpense(amount, reason);
        } else {
            // Test invalid cases
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> addExpenseHelper.addData(amount, reason));

            assertTrue(ex.getMessage().contains("Amount is outside valid range"));
            verify(mockDbHelper, never()).addExpense(anyDouble(), anyString());
        }
    }

    private static Stream<Arguments> provideAmountTestCases() {
        return Stream.of(
                // Boundary Test Cases (amount, shouldSucceed)

                // Lower Boundary
                Arguments.of(0.99, false),   // min- (1 cent below minimum)
                Arguments.of(1.00, true),    // exact minimum
                Arguments.of(1.01, true),     // min+ (1 cent above minimum)

                // Middle Range
                Arguments.of(500_000_000.00, true),  // nominal value
                Arguments.of(999_999_999.99, true),  // max- (1 cent below max)

                // Upper Boundary
                Arguments.of(1_000_000_000.00, true), // exact maximum
                Arguments.of(1_000_000_000.01, false), // max+ (1 cent above max)

                // Special Cases
                Arguments.of(Double.MIN_VALUE, false), // smallest possible double
                Arguments.of(Double.MAX_VALUE, false)  // largest possible double
        );
    }

    //===============================================================================================
    @ParameterizedTest
    @CsvFileSource(resources = "/combined_test_data.csv", numLinesToSkip = 1)
    void testAddData_WithCsv(double amount, String reason, String rangeType) {
        System.out.printf("Running test for amount: %.2f, reason: %s, rangeType: %s%n",
                amount, reason, rangeType);

        addExpenseHelper.addData(amount, reason);
        verify(mockDbHelper).addExpense(amount, reason);
    }




}