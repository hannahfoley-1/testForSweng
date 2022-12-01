package com.sweng_group_three_assignment_two.calculator;

import java.util.Stack;

public class Calculator {
    private String input;
    private String result;

    //constructor for this class
    public Calculator() {
        setInput("");
        setResult("");
    }

    public void calculate(String uncheckedInput) {
        if(validate(uncheckedInput))
        {
            Stack<String> postfixInput = toPostfixStringStack(uncheckedInput);
            Float result = calculateThisStringStack(postfixInput);
            if(!isInteger(result)) {
                String resultAsString = String.format("%.3f", result);
                setResult(resultAsString);
            }
            else { setResult(String.valueOf(result.intValue()));}
        }
    }

    /*
        Param: String with expression
        Returns: Boolean - True if expression is valid, false if not
     */
    public boolean validate(String input)
    {
        if(bracketsBalance(input))
        {
            if(numbersBalance(input))
            {
                return true;
            }
        }
        return false;
    }

    public String getInput() {return input;}

    public void setInput(String input) {this.input = input;}

    public String getResult() {return result;}

    public void  setResult(String result) {this.result = result;}

    public static int addInteger (int a, int b){
        return a + b;
    }

    public static int subtractInteger (int a, int b){
        return a - b;
    }

    public static int multiplyInteger (int a, int b){
        return a * b;
    }

    public static int divideInteger (int a, int b){
        if (b == 0)
        {
            return 0;
        }
        return a / b;
    }

    public static int powerInt (int a) {
        return a * a;
    }

    public static double addDouble (double a, double b){
        return a + b;
    }

    public static double subtractDouble (double a, double b){
        return a - b;
    }

    public static double multiplyDouble (double a, double b){
        return a * b;
    }

    public static double divideDouble (double a, double b){
        if (b == 0)
        {
            return 0;
        }
        return a / b;
    }
     
     
     /*
        Param: 2 floats
        Returns: float - two floats added together
    */
    public static float add (float a, float b){
        return a + b;
    }

    /*
        Param: 2 floats
        Returns: float - one float subtracted from the other
    */
    public static float subtract (float a, float b){
        return a - b;
    }

    /*
         Param: 2 floats
         Returns: float - two floats multiplied
     */
    public static float multiply (float a, float b){
        return a * b;
    }

    /*
        Param: 2 floats
        Returns: float - division of two floats
    */
    public static float divide (float a, float b){
        if (b == 0)
        {
            return 0;
        }
        return a / b;
    }

    public static double powerDouble (double a) {
        return a * a;
    }

    /*
        Param: 2 floats
        Returns: float - a to the power of b
    */
    public static float power(float a, float b) {
        return (float) Math.pow(a, b);
    }

    /*
        Param: 1 float
        Returns: log of a
    */
    public static float log(float a) { return Math.log(a); }

    /*
        Param: float
        Returns: e to the power of a
    */
    public static float exp(float a) { return Math.exp(a); }

    public static double logDouble (double a) {
        return Math.log(a);
    }

    public static double logExponential (double a) {
        return Math.exp(a);
    }

    /*
        Param: String with expression
        Returns: Boolean - True if numbers and operators in expression balance
        Builds from Assignment 1 validate
    */
    public boolean numbersBalance(String input)
    {
        boolean lastWasNumber = false;
        if (input.equals(null))
        {
            setResult("Error: Invalid characters in input");
            return false;
        }

        char[] theChars = input.toCharArray();

        Stack<String> localNos = new Stack<String>();
        int count = 1;

        for (int i = 0; i < theChars.length; i++)
        {
            switch (theChars[i])
            {
                case('+'):
                case('*'):
                case('-'):
                case('/'):
                case('^'):
                    if (count == 1 || i == (theChars.length - 1))
                    {
                        setResult("Error: Duplicated operators");
                        return false;
                    }

                    //localOps.push(theChars[i]);
                    String currentNumber = "";

                    while (count > 1)
                    {
                        currentNumber = localNos.pop() + currentNumber;
                        count--;
                    }
                    lastWasNumber = true;
                    localNos.push(currentNumber);
                    break;

                case('0'):                  // might be a more efficient way of doing this with ASCII?
                case('1'):
                case('2'):
                case('3'):
                case('4'):
                case('5'):
                case('6'):
                case('7'):
                case('8'):
                case('9'):
                case('.'):
                    count++;
                    localNos.push(Character.toString(theChars[i]));
                    break;
                case(')'):
                case('('):
                    break;
                //brackets were already checked and balanced
                case(' '):
                    if(!lastWasNumber)
                    {
                        setResult("Error: Duplicated operands");
                        return false;
                    }
                    break;
                //ignores whitespace?
                default:
                    setResult("Error: Invalid characters in input");
                    return false;        // input involves some character that's neither a number nor character
            }
        }
        String current = "";

        while (count > 1)
        {
            current = localNos.pop() + current;
            count--;
        }

        localNos.push(current);

        return true;
    }


    /*
       Param: String with expression
       Returns: Boolean - True if brackets in expression balance
   */
    public boolean bracketsBalance(String input)
    {
        int opening = 0;
        int closing = 0;

        for(int i = 0; i < input.length(); i++){
            char c = input.charAt(i);

            if(Character.valueOf(c).equals('(')){
                opening++;
            }
            else if (Character.valueOf(c).equals(')')){
                closing++;
            }
        }

        if(opening-closing == 0)
        {
            return true;
        }
        else
        {
            setResult("Error: Unbalanced brackets");
            return false;
        }
    }


    /*
     Param: Character with operator
     Returns: Int: Precedence of this operator in BOMDAS
 */
    public static int precedence(char operator) {
        switch(operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }



    /*
      Param: String with expression
      Returns: Stack of Strings of PostFix order of expression
  */
    public static Stack<String> toPostfixStringStack(String input){
        Stack<String> result = new Stack <String>();
        Stack<Character> stack = new Stack<Character>();

        for(int i = 0; i < input.length(); i++){
            char c = input.charAt(i);

            if(Character.valueOf(c).equals('+') || Character.valueOf(c).equals('-') || Character.valueOf(c).equals('*')
                    || Character.valueOf(c).equals('/') || Character.valueOf(c).equals('^'))
            {
                while(!stack.isEmpty() && !Character.valueOf(stack.peek()).equals('(')){
                    if(precedence(stack.peek()) >= precedence(c))
                    {
                        result.push(String.valueOf(stack.pop()));
                    }
                    else{
                        break;
                    }
                }
                stack.push(c);
            }
            else if (Character.valueOf(c).equals('('))
            {
                stack.push(c);
            }
            else if (Character.valueOf(c).equals(')'))
            {
                while (!stack.isEmpty() && stack.peek() != '(')
                {
                    result.push(String.valueOf(stack.pop()));
                }
                if (!stack.isEmpty())
                {
                    stack.pop();
                }
            }
            else if(Character.isDigit(c) || Character.valueOf(c).equals('.'))
            {
                String number = String.valueOf(c);
                while(i+1<input.length() && (Character.isDigit(input.charAt(i+1)) || input.charAt(i+1) == '.'))
                {
                    i++;
                    c = input.charAt(i);
                    number += c;
                }
                result.push(number);
            }
        }
        while(!stack.empty())
        {
            result.push(String.valueOf(stack.pop()));
        }
        Stack<String> incorrectOrder = new Stack<String>();
        while(!result.isEmpty())
        {
            incorrectOrder.push(result.pop());
        }
        return incorrectOrder;
    }



    /*
        Param: Postfix expression stack
        Returns: Float - Result of expression
    */
    public static Float calculateThisStringStack(Stack<String> postfixInput){
        float result = 0;
        Stack<Float> resultStack = new Stack<Float>();

        while(!postfixInput.isEmpty()) {
            if(isNumber(postfixInput.peek()))
            {
                resultStack.push(Float.valueOf(postfixInput.pop()));
            }
            else
            {
                float value1 = resultStack.pop();
                float value2 = resultStack.pop();

                switch (postfixInput.pop())
                {
                    case ("+"):
                        resultStack.push(add(value2, value1));
                        break;
                    case ("-"):
                        resultStack.push(subtract(value2, value1));
                        break;
                    case ("*"):
                        resultStack.push(multiply(value2, value1));
                        break;
                    case ("/"):
                        resultStack.push(divide(value2, value1));
                        break;
                    case ("^"):
                        resultStack.push(power(value1, value2));
                        break;
                }
            }
        }
        if(!resultStack.empty()){
            return resultStack.pop();
        } else {
            return result;
        }
    }

    /*
        Param: String
        Returns: Boolean - True if the input is a number, false otherwisse
    */
    public static boolean isNumber(String input)
    {
        switch(input.charAt(0)){
            case('1'):
            case('2'):
            case('3'):
            case('4'):
            case('5'):
            case('6'):
            case('7'):
            case('8'):
            case('9'):
            case('0'):
                return true;
            default:
                return false;
        }
    }

    /*
        Param: Float
        Returns: Boolean, true if the float is an integer, false otherwise
        Source: https://www.geeksforgeeks.org/check-if-a-float-value-is-equivalent-to-an-integer-value/
     */
    public static boolean isInteger(Float result){
        // Convert float value
        // of N to integer
        int X = result.intValue();
        double temp2 = result - X;

        // If N is not equivalent
        // to any integer
        if (temp2 > 0)
        {
            return false;
        }
        return true;
    }
}
