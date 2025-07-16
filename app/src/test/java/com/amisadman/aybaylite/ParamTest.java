package com.amisadman.aybaylite;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class ParamTest
{
    @ParameterizedTest
    @ValueSource(ints = {2,4,6,8,10})
    void isEven(int n){
        boolean result = n%2 == 0;
        assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 7, 10",
            "10, 15, 25",
            "0,0,0"
    })
    void testAddition(int a,int b, int expected){
        assertEquals(a+b,expected);
    }

    static Stream<Arguments>provider(){
        return Stream.of(
                Arguments.of("Dog",true),
                Arguments.of("Cat",true),
                Arguments.of("NaziaShakchunni",true),
                Arguments.of("oooooooo",false)
        );
    }
    @ParameterizedTest
    @MethodSource("provider")
    void isUpper(String words, boolean expected){
        char c = words.charAt(0);
        boolean result = false;
        if(c >= 'A' && c <= 'Z')result = true;

        assertEquals(expected,result);
    }

    static Stream<Arguments>provideTemp(){
        return Stream.of(
                Arguments.of(-5,true),
                Arguments.of(0,false),
                Arguments.of(5,false)
        );
    }
    @ParameterizedTest
    @MethodSource("provideTemp")
    void isFreezing(int temp,boolean expected){
        boolean result = false;
        if(temp < 0)result = true;

        assertEquals(expected,result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/concat.csv",numLinesToSkip = 0)
    void concatTest(String a,String b,String c){
        assertEquals(a+b,c);
    }



}

