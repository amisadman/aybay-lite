package com.amisadman.aybaylite.Controllers;

import static org.junit.jupiter.api.Assertions.*;

import android.provider.ContactsContract;

import com.amisadman.aybaylite.Repo.DatabaseHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.Context;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

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

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 1.00, 1000.00, 999999.99})
    void testAddExpanseDiverseValues(double amount)
    {
        addExpenseHelper.addData(amount, "Test reason");
        verify(mockDbHelper).addExpense(amount, "Test reason");
    }
}