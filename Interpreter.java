package com.cpl;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
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
        Map<String, Token> tokenMap = new LinkedHashMap<>();
    
        JSONParser parser = new JSONParser();
    
        try (FileReader reader = new FileReader(filename)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
    
            for (int i = 0; i < jsonObject.size(); i++) {
                String tokenKey = "Token_" + i;
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
    // Get the values of the tokens map
    Collection<Token> tokenValues = tokens.values();

    // Find the index of the current token
    int currentIndex = new ArrayList<>(tokenValues).indexOf(currentToken);

    // If the current token is found and there is a next token, move to it and return
    if (currentIndex != -1 && currentIndex + 1 < tokenValues.size()) {
        currentToken = new ArrayList<>(tokenValues).get(currentIndex + 1);
        return currentToken;
    }

    // If there are no more tokens, set currentToken to null and return null
    currentToken = null;
    return null;
}

    

    private void handleKeyword(String keyword) throws ParseException {
        switch (keyword) {
            case "import":
                String module = getNextToken().getValue();
                System.out.println("Importing module: " + module);
                // Implement logic for handling import statement
                break;
            case "implementations":
                System.out.println("Handling implementations statement");
                // Implement logic for handling implementations statement
                break;
            case "function":
                System.out.println("Handling function statement");
                handleFunctionDeclaration();
                break;
            case "main":
                System.out.println("Handling main function");
                // Implement logic for handling main function
                break;
            
            case "variables":
                System.out.println("Preparing to initialize variables: ");
                // Implement logic for handling variable declarations
                break;
            
            case "endfun":
                System.out.println("Handling end of function");
                // Implement logic for handling end of function
                break;

            case "set":
                System.out.println("Handling set function");
                break;
            
            case "then":
                System.out.println("Handling then function");
                break;

            case "begin":
                System.out.println("Handling begin function");
                break;

            case "if":
                System.out.println("Handling if function");
                break;

            case "display":
                String display = getNextToken().getValue();
                System.out.println("Preparing to display: " + display);
                break;

            case "input":
                String input = getNextToken().getValue();
                System.out.println("Gathering input for: " + input);
                break;
            
            case "return":
                System.out.println("Handling return function");
                break;

            case "endif":
                System.out.println("Handling endif function");
                break;

            case "is":
                System.out.println("Handling is statement");
                break;

            case "define":
                String define = getNextToken().getValue();
                System.out.println("Preparing to define: " + define);
                break;

            case "of":
                System.out.println("Handling of statement");
                break;
                
            case "type":
                System.out.println("Gathering type for previous identifier");
                break;

            case "double":
                System.out.println("Initialized type as double");
                break;

            case "pointer":
                System.out.println("Initializing pointer variable");
                break;

            case "else":
                System.out.println("Handling else statement");
                break;

            default:
                // Handle unrecognized keywords
                System.err.println("Error: Unrecognized keyword - '" + keyword + "'");
        }
    }

    private void handleOperator(String operator) {
        switch (operator) {
            case ">":
                System.out.println("Handling > operator");
                break;
            case "=":
                System.out.println("Handling = operator");
                break;

            case "not":
                System.out.println("Handling not operator");
                break;

            case "greater":
                System.out.println("Handling greater operator");
                break;

            case "or":
                System.out.println("Handling or operator");
                break;

            case "equal":
                System.out.println("Handling equal operator");
                break;
            
            case ",":
                System.out.println("Handling comma");
                break;

            default:
                // Handle unrecognized operators
                System.err.println("Error: Unrecognized operator - '" + operator + "'");
        }
    }

    private void handleIdentifier(String identifier) {
        identifier = currentToken.getType();
        System.out.println("Handling identifier: " + identifier);
    }

    private Object handleLiteral(String literal) {
        switch(literal){
            case "StringLiteral":
                String stringLiteral = currentToken.getValue();
                System.out.println("Handling string literal: " + stringLiteral);
                break;
            case "NumericLiteral":
                String numericLiteral = currentToken.getValue();
                System.out.println("Handling Numeric Literal: " + numericLiteral);
                    try {
                        System.out.println("Handling Integer...");
                        int intTokenValue = Integer.parseInt(numericLiteral);
                        return intTokenValue;
                    } catch (NumberFormatException e1) {
                        try {
                            System.out.println("Handling Float...");
                            float floatTokenValue = Float.parseFloat(numericLiteral);
                            return floatTokenValue;
                        } catch (NumberFormatException e2) {
                            try {
                                System.out.println("Handling Double...");
                                double doubleTokenValue = Double.parseDouble(numericLiteral);
                                return doubleTokenValue;
                            } catch (NumberFormatException e3) {
                                System.err.println("Invalid Numeric Literal format '" + numericLiteral + "'");
                            }
                        }
                    }
                    finally {
                    System.out.println("Assigning numeric literal to x");
                    }
        }
        return null;
    }

    private void handleFunctionDeclaration() throws ParseException {
        String functionName = getNextToken().getValue(); // Assuming function name immediately follows "function"
        System.out.println("Defining function: " + functionName);
    
        // Parse return type (if any)
        String returnType = getNextToken().getValue();
        while (!(currentToken.getValue().equals("type"))) {
            returnType = getNextToken().getValue();
            if (returnType.equals("type")) {
                returnType = getNextToken().getValue();
                break;
            }
        }
        
        System.out.println("Return type: " + returnType);
    
        // Parse "parameters" keyword
        if (!(getNextToken().getValue().equals("is"))) {
            System.out.println("Error: Expcected 'is' but got " + currentToken.getValue());
            throw new ParseException(0);
        }
        getNextToken();

        /////////////////////////////////////////////////////////////
        /*Conner currently working on below for Function definition*/
        /////////////////////////////////////////////////////////////

        while (!(currentToken.getValue().equals("begin"))) {
            if (currentToken.getValue().equals("parameters")) {
                ArrayList<String> parameters = new ArrayList<>();
                while (!(currentToken.getType().equals("EndOfStatement"))) {

                    if (!currentToken.getValue().equals("of") && !currentToken.getValue().equals("type")) {
                        parameters.add(currentToken.getValue());
                    }
                    getNextToken();
                    System.out.println("Function parameters: " + parameters);
                }
            }
        }
    
        // Parse function specifications
        while (!currentToken.getValue().equals("begin")) {
            getNextToken(); // Move past "begin"
        }
    
        // Parse function body (assuming "{" and "}" delimiters)
        while (!currentToken.getValue().equals("endfun")) {
            // Handle statements inside the function body
            // You may need to recursively call other methods to interpret statements
            getNextToken();
        }
    
        System.out.println("End of function: " + functionName);
    }


    


    private void handleSpecialSymbol(String symbol) {
        switch (symbol) {
            case ",":
                System.out.println("Handling , symbol");
                break;
            case ":":
                System.out.println("Handling : symbol");
                break;
            case ";":
                System.out.println("Handling ; symbol");
                break;
            case "{":
                System.out.println("Handling { symbol");
                break;
            case "}":
                System.out.println("Handling } symbol");
                break;
            default:
                // Handle unrecognized special symbols
                System.err.println("Error: Unrecognized special symbol - '" + symbol + "'");
        }
    }

    public void interpret() throws ParseException {
        while (currentToken != null) {
            switch (currentToken.getType()) {
                case "Keyword":
                    handleKeyword(currentToken.getValue());
                    break;
                case "Operator":
                    handleOperator(currentToken.getValue());
                    break;
                case "Identifier":
                    handleIdentifier(currentToken.getValue());
                    break;
                case "NumericLiteral":
                    handleLiteral(currentToken.getType());
                case "StringLiteral":
                    handleLiteral(currentToken.getType());
                    break;
                case "specialSymbols":
                    handleSpecialSymbol(currentToken.getValue());
                    break;

                case "EndOfStatement":
                    System.out.println(" Handled End of Statement");
                    break;

                // Add cases for other token types...
                default:
                    // Handle unrecognized token types
                    System.err.println("Error: Unrecognized token type - " + currentToken.getType() + ": '" + currentToken.getValue() + "'");
            }

            // Move to the next token
            getNextToken();
        }
    }

    public static void main(String[] args) throws ParseException {
        Interpreter interpreter = new Interpreter("C:\\Users\\joshl\\CPL_Proj3\\OutputTokens.json");
        interpreter.interpret();
    }
}