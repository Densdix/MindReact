package com.bigdecimalit.mindreact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Calculation {
    private static final Random rn = new Random();

    public static ArrayList<Integer> calc(){

        int firstNumber = 0;
        int secondNumber = 0;
        int operator = 0;

        firstNumber = rn.nextInt(10)+1;
        secondNumber = rn.nextInt(10)+1;
        operator = rn.nextInt(2)+1;

        return new ArrayList<Integer>(Arrays.asList(firstNumber,secondNumber,operator));

    }

}
