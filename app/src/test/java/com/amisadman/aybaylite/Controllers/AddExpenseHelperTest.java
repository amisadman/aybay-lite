package com.amisadman.aybaylite.Controllers;

import static org.mockito.Mockito.verify;

import android.content.Context;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}