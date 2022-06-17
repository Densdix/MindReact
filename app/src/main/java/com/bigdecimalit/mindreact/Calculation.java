package com.bigdecimalit.mindreact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Calculation {
    private static final Random rn = new Random();
    private static int level = 0;

    public static ArrayList<Integer> calc(){

        int firstNumber;
        int secondNumber;
        int operator;
        //random.nextInt(max - min + 1) + min
        firstNumber = rn.nextInt((10 + level)-(1+ level)+1) + (1+ level);
        operator = rn.nextInt(3)+1;

        if(level < 10) {
            secondNumber = rn.nextInt(10)+1;
            if(operator == 3) secondNumber = rn.nextInt(5)+1;
        }
        else if(level < 20) {
            secondNumber = rn.nextInt((10 + level -5)-(1+ level -5)+1) + (1+ level-5);
            if(operator == 3) secondNumber = rn.nextInt(10)+1;
        }
        else {
            secondNumber = rn.nextInt(10 + level)+(1+ level);
            if(operator == 3) secondNumber = rn.nextInt(10 + level)+1;
        }

        return new ArrayList<>(Arrays.asList(firstNumber, secondNumber, operator));

    }

    public static void setLevel(int level) {
        Calculation.level = level;
    }

    public static int getLevel() {
        return level;
    }
}
