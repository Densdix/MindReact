package com.bigdecimalit.mindreact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Calculation {
    private static final Random rn = new Random();
    private static int hard = 0;

    public static ArrayList<Integer> calc(){

        int firstNumber;
        int secondNumber;
        int operator;

        firstNumber = rn.nextInt(10 + hard)+1;
        secondNumber = rn.nextInt(10 + hard)+1;
        operator = rn.nextInt(3)+1;

        return new ArrayList<>(Arrays.asList(firstNumber, secondNumber, operator));

    }

    public static void setHard(int hard) {
        Calculation.hard = hard;
    }
}
