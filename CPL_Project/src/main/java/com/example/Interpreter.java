package com.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class Token {
    private String type;
    private int id;
    private String value;

    public Token(String type, int id, String value) {
        this.type = type;
        this.id = id;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}

public class Interpreter {
    private Map<String, Token> tokens;
    private Token currentToken;

    public Interpreter(String filename) {
        tokens = loadTokensFromFile(filename);
        currentToken = tokens.values().iterator().next();
    }

    private Map<String, Token> loadTokensFromFile(String filename) {
        Map<String, Token> tokenMap = new HashMap<>();

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            for (Object key : jsonObject.keySet()) {
                String tokenKey = (String) key;
                JSONObject tokenData = (JSONObject) jsonObject.get(tokenKey);

                String type = (String) tokenData.get("Type");
                int id = Integer.parseInt(tokenData.get("id").toString());
                String value = (String) tokenData.get("value");

                Token token = new Token(type, id, value);
                tokenMap.put(tokenKey, token);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return tokenMap;
    }

    private Token getNextToken() {
        if (currentToken != null) {
            currentToken = tokens.values().iterator().next();
            return currentToken;
        } else {
            return null;
        }
    }

    private void handleKeyword(String keyword) {
        switch (keyword) {
            case "import":
                // Logic for handling import statement
                break;
            case "implementations":
                // Logic for handling implementations statement
                break;
            case "function":
                // Logic for handling function declaration
                break;
            case "main":
                // Logic for handling main function
                break;
            case "variables":
                // Logic for handling variable declarations
                break;
            case "endfun":
                // Logic for handling end of function
                break;
            default:
                // Handle unrecognized keywords
                System.err.println("Error: Unrecognized keyword - " + keyword);
        }
    }

    private void handleOperator(String operator) {
        switch (operator) {
            case ">":
                // Logic for handling greater than operator
                break;
            case "=":
                // Logic for handling assignment operator
                break;
            default:
                // Handle unrecognized operators
                System.err.println("Error: Unrecognized operator - " + operator);
        }
    }

    private void handleIdentifier(String identifier) {
        // Logic for handling identifiers
        // You might want to track variable values or perform other actions based on identifiers
    }

    private void handleLiteral(String literal) {
        // Logic for handling literals
        // This could involve assigning values, displaying, or using literals in expressions
    }

    private void handleVariableDeclaration(String variableName, String variableType, String assignedValue) {
        // Logic for handling variable declarations
        System.out.println("Variable Declaration: Name=" + variableName + ", Type=" + variableType + ", Value=" + assignedValue);
    }

    private void handleIfStatement(String condition) {
        // Logic for handling if statements
        System.out.println("Conditional Statement: Condition=" + condition);
    }


    private void handleSpecialSymbol(String symbol) {
        switch (symbol) {
            case ",":
                // Logic for handling comma
                break;
            case ":":
                // Logic for handling colon
                break;
            case ";":
                // Logic for handling semicolon
                break;
            case "{":
                // Logic for handling opening curly brace
                break;
            case "}":
                // Logic for handling closing curly brace
                break;
            default:
                // Handle unrecognized special symbols
                System.err.println("Error: Unrecognized special symbol - " + symbol);
        }
    }

    public void interpret() {
        while (currentToken != null) {
            switch (currentToken.getType()) {
                case "keywords":
                    handleKeyword(currentToken.getValue());
                    break;
                case "operators":
                    handleOperator(currentToken.getValue());
                    break;
                case "identifiers":
                    handleIdentifier(currentToken.getValue());
                    break;
                case "NumericLiteral":
                case "StringLiteral":
                    handleLiteral(currentToken.getValue());
                    break;
                case "specialSymbols":
                    handleSpecialSymbol(currentToken.getValue());
                    break;
                
                // Add cases for other token types...
                default:
                    // Handle unrecognized token types
                    System.err.println("Error: Unrecognized token type - " + currentToken.getType());
            }

            // Move to the next token
            getNextToken();
        }
    }

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter("OutputTokens.json");
        interpreter.interpret();
    }
}



