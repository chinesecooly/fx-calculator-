package com.chinesecooly.model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.Setter;

public class Calculator {

    private enum Operator {
        ADD {
            @Override
            Double action(Double operandA, Double operandB) {
                return operandB + operandA;
            }
        },
        SUB {
            @Override
            Double action(Double operandA, Double operandB) {
                return operandB - operandA;
            }
        },
        MUL {
            @Override
            Double action(Double operandA, Double operandB) {
                return operandB * operandA;
            }
        },
        DIV {
            @Override
            Double action(Double operandA, Double operandB) {
                return operandB / operandA;
            }
        };

        abstract Double action(Double operandA, Double operandB);

        public static Operator operatorMapping(String operator) {
            switch (operator) {
                case "+":
                    return ADD;
                case "-":
                    return SUB;
                case "*":
                    return MUL;
                case "/":
                    return DIV;
                default:
                    return null;
            }
        }

    }

    @Getter
    @Setter
    private String expression;
    final private EnumMap<Operator, Integer> priority;
    final private String regx = "\\d+|\\D";
    final private Deque<Double> operandStack;
    final private Deque<Operator> operatorStack;
    static private volatile Calculator instance;

    private Calculator() {
        priority = new EnumMap<>(Operator.class);
        operandStack = new LinkedList<>();
        operatorStack = new LinkedList<>();
        priority.put(Operator.ADD, 0);
        priority.put(Operator.SUB, 0);
        priority.put(Operator.MUL, 1);
        priority.put(Operator.DIV, 1);
    }

    static public Calculator getInstance() {
        if (instance == null) {
            synchronized (Calculator.class) {
                if (instance == null) {
                    instance = new Calculator();
                }
            }
        }
        return instance;
    }

    public Map<String, Object> calculate() {
        Pattern compile = Pattern.compile(regx);
        Matcher matcher = compile.matcher(expression);
        while (matcher.find()) {
            if (matcher.group().matches("\\d+")) {
                operandStack.push(Double.valueOf(matcher.group()));
            } else if (operatorStack.size() == 0) {
                operatorStack.push(Operator.operatorMapping(matcher.group()));
            } else {
                Operator top = operatorStack.pop();
                if (priority.get(Operator.operatorMapping(matcher.group())) >= priority.get(top)) {
                    operatorStack.push(top);
                } else {
                    Double operandA = operandStack.pop();
                    Double operandB = operandStack.pop();
                    operandStack.push(top.action(operandA, operandB));
                }
                operatorStack.push(Operator.operatorMapping(matcher.group()));
            }
        }
        while (!operatorStack.isEmpty()) {
            Double operandA = operandStack.pop();
            Double operandB = operandStack.pop();
            operandStack.push(operatorStack.pop().action(operandA, operandB));
        }
        String result = operandStack.pop() + "";
        HashMap<String, Object> results = new HashMap<>();
        results.put("calculateResult", result);
        return results;
    }
}
