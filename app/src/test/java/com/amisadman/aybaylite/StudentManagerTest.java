package com.amisadman.aybaylite;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentManagerTest {
    private StudentManager studentManager;

    @BeforeAll
    void initAll(){
        System.out.print("Before All Test");
    }
    @AfterAll
    void tearDowmAll(){
        System.out.println("After All Tests");
    }
    @BeforeEach
    void init(){
        studentManager = new StudentManager();
        System.out.println("Before Each Test");

    }

    @AfterEach
    void tearDown(){
        studentManager = null;
        System.out.println("Deleted the obj after test");
    }
    @Test
    void nullTest(){
        // NUll test
        assertNull(studentManager.getStudentName(999));
        assertNotNull(studentManager.getStudentName(1));
    }
    @Test
    void additionTest(){
        //addition
        assertEquals(80,studentManager.addMarks(30,50));
        assertNotEquals(10,studentManager.subtractMarks(50,35));

    }
    @Test
    void testExceptionHandaling(){
        assertThrows(IllegalArgumentException.class, () ->studentManager.riskyOperation(-1) );
    }
    @Test
    void lineMatch(){
        List<String> expectedLines = List.of("Report Line 1", "Report Line 2", "Report Line 3");
        List<String>actual = List.of(studentManager.getMultilineReport().split("\n"));
        assertLinesMatch(expectedLines,actual);

    }
    @Test
    void testTimeout(){
        assertTimeout(Duration.ofMillis(100), () -> studentManager.addMarks(10,20));
    }
    @Test
    void testFailIfNot(){
        try{
            studentManager.riskyOperation(-1);
            fail();
        }catch (IllegalArgumentException ignored){}
    }




}