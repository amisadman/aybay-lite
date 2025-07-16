package com.amisadman.aybaylite;

public class StudentManager {
    public String getStudentName(int id){
        if(id == 1)return "Alice";
        if(id == 2)return "Bob";
        return null;
    }
    public int addMarks(int a,int b){return a+b;}

    public int subtractMarks(int a, int b) {
        return a - b;
    }

    public int[] getTop3Score(){
        return new int[]{95,98,100};
    }
    public String getMultilineReport(){
        return "Report Line 1\nReport Line 2\nReport Line 3";
    }
    public void riskyOperation(int x){
        if(x < 0) throw new IllegalArgumentException("Negative Not allowed");
    }
    public boolean isPassed(int marks){
        return marks >= 33;
    }


}

