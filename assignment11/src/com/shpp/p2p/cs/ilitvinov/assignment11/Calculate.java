package com.shpp.p2p.cs.ilitvinov.assignment11;

import com.shpp.p2p.cs.ilitvinov.assignment11.ast.Expression;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

/**Class to calculate expression*/
public class Calculate {
    private List<Expression> nodes;
    private LinkedHashMap<String, Double> variables;

    /**@param  nodes - List<Expression>
     * @param  variables - HashMap<String name, Double value>*/
    public Calculate(List<Expression> nodes, LinkedHashMap<String, Double> variables) {
        this.nodes = nodes;
        this.variables = variables;
    }

    /**@param  nodes - List<Expression>
     * @param  variables - HashMap<String name, Double value>
     * @param args - array of variables(type String); example: [a=2, b=8...]*/
    public Calculate(List<Expression> nodes, LinkedHashMap<String, Double> variables, String[] args) throws Exception {
        this.nodes = nodes;
        this.variables = variables;
        setVariablesFromArray(args);
    }

    /**To initiate variables with values in HashMap<> variables*/
    public void setVariables() {
        for (String key : variables.keySet()) {
            setVar(key, "Enter variable's value, pls\n%s: ");
        }
    }

    /**Set variable from HashMap<> variables with name and value.
     * @param name - key name of variable
     * @param title - question for user to set value*/
    private void setVar(String name, String title) {
        Scanner scanner = new Scanner(System.in);
        double value;
        System.out.printf(title, name);
        try {
            value = scanner.nextDouble();
            variables.put(name, value);
        } catch (java.lang.Exception e) {
            setVar(name, "Pls use decimals or integers only\n%s: ");
        }
    }

    /**Set variables from String[] args to HashMap variables
     * @param variables - array of variables; example: [a=0, b=2...]*/
    private void setVariablesFromArray(String[] variables) throws Exception {
        for (String variable : variables) {

            String arg = variable.replaceAll(" ", "");
            arg = arg.replaceAll(",", ".");

            String[] buffer = arg.split("=");
            if (buffer.length != 2) {
                throw new Exception("Unknown form of variable in arguments: " + variable);
            }
            ExpressionValidation.validateVariable(buffer[0]);
            ExpressionValidation.validateConstant(buffer[1]);

            if (this.variables.containsKey(buffer[0])) {
                this.variables.put(buffer[0], Double.parseDouble(buffer[1]));
            } else {
                throw new Exception("No such variable found in expression: " + buffer[0]);
            }

        }

        ExpressionValidation.validateInitializationVariables(this.variables);
        if (variables.length != this.variables.size()) {
            throw new Exception("Different amount of variables in expression and in arguments.");
        }
    }

    public void printOutResult(String expression) throws Exception {
        for (Expression node : nodes) {
            System.out.printf("expression: %s\n", expression);
            printOutVariables();
            System.out.println("\nresult = " + node.evaluate(variables));
        }
    }

    private void printOutVariables() {
        StringBuilder variablesToString = new StringBuilder();
        for (String key : variables.keySet()) {
            Double value = variables.get(key);
            if (value != null)
                variablesToString.append(key).append(" = ").append(value).append("; ");
        }
        if (variablesToString.length() > 0) {
            System.out.print("variables: ");
            System.out.println(variablesToString.toString());
        }
    }
}
