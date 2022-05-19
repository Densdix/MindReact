package com.bigdecimalit.mindreact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Calculation {
    private static final Random rn = new Random();

    public static ArrayList<Integer> calc(){

        int firstNumber;
        int secondNumber;
        int operator;

        firstNumber = rn.nextInt(10)+1;
        secondNumber = rn.nextInt(10)+1;
        operator = rn.nextInt(3)+1;

        return new ArrayList<>(Arrays.asList(firstNumber, secondNumber, operator));

    }

}
