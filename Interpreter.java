package com.cpl;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
    private Map<Integer, String> identifierList = new HashMap<>();
    private String currentIdentifier = "";
    private Map<String, Object> symbolTable = new HashMap<>();

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
                handleAssignment();
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
        identifier = currentToken.getValue();
        System.out.println(currentToken.getValue());
        int ident_id = currentToken.getId();
        System.out.println(ident_id);
        if (identifierList.isEmpty()) {
            identifierList.put(ident_id, identifier);
        }
        else if(!(identifierList.containsKey(ident_id))) {
            identifierList.put(ident_id, identifier);
        }
        
        System.out.println("Handling identifier: " + identifier + ", " + ident_id);
    }

    private <T> T handleLiteral(String literal) {
        switch(literal){
            case "StringLiteral":
                String stringLiteral = currentToken.getValue();
                System.out.println("Handling String Literal");
                return (T) stringLiteral;
            case "NumericLiteral":
                String numericLiteral = currentToken.getValue();
                System.out.println("Handling Numeric Literal: " + numericLiteral);
                    try {
                        int intTokenValue = Integer.parseInt(numericLiteral);
                        return (T) handleIntLiteral(intTokenValue);
                    } catch (NumberFormatException e1) {
                        try {
                            float floatTokenValue = Float.parseFloat(numericLiteral);
                            return (T) handleFloatLiteral(floatTokenValue);
                        } catch (NumberFormatException e2) {
                            try {
                                double doubleTokenValue = Double.parseDouble(numericLiteral);
                                return (T) handleDoubleLiteral(doubleTokenValue);
                            } catch (NumberFormatException e3) {
                                System.err.println("Invalid Numeric Literal format '" + numericLiteral + "'");
                            }
                    }
                }
            default:
                System.err.println("Unhandled literal type: " + literal);
                throw new IllegalArgumentException("Unhandled literal type: " + literal);
        }
    }
    private Integer handleIntLiteral(int tokenValue) {
        System.out.println("Handling Integer...");
        return tokenValue;
    }
    private Float handleFloatLiteral(float tokenValue) {
        System.out.println("Handling Float...");
        return tokenValue;
    }
    private Double handleDoubleLiteral(double tokenValue) {
        System.out.println("Handling Double...");
        return tokenValue;
    }

    private void handleAssignment() {

        String varName = currentIdentifier;
        getNextToken(); //Move past "="

        Object exprValue = handleExpression();

        symbolTable.put(varName, exprValue);
    }
    private <T> T handleExpression() {
        T exprValue = handleLiteral(currentToken.getValue());

        System.out.println("Expression value: " + exprValue);

        getNextToken();

        return exprValue;
    }


    private void handleFunctionDeclaration() throws ParseException {
        String functionName = getNextToken().getValue();  //Getting Function name
        System.out.println("Defining function: " + functionName);
    
        // Parse return type
        String returnType = getNextToken().getValue();
        while (!(currentToken.getValue().equals("type"))) {
            returnType = getNextToken().getValue();
            if (returnType.equals("type")) {
                returnType = getNextToken().getValue();
                break;
            }
        }
        
        System.out.println("Return type: " + returnType);
        
        //While function has not ended
        while (!currentToken.getValue().equals("endfun")) {
            //Moves past any end of statement tokens found prior to beginning the function body
            if (currentToken.getType().equals("EndOfStatement")) {
                getNextToken();
            }
            // Parse parameters (if any)
            if (currentToken.getValue().equals("parameters")) {
                System.out.println("Handling parameters.");
                getNextToken(); // Move past "parameters"
                Map<String, String> parameters = new HashMap<>();
            
                // Parameter declarations
                while (!(currentToken.getValue().equals("is"))) {
                    if (currentToken.getValue().equals(",") || currentToken.getType().equals("EndOfStatement")) {
                        getNextToken(); // Move past "," or EOS
                    } else {
                        String paramName = currentToken.getValue();
                        getNextToken();
                        boolean isArray = false;
                        String arrayType = null;
                        if(currentToken.getValue().equals("array")){
                            System.out.println("  Generating array for " + paramName);
                            isArray = true;
                            getNextToken(); //Move past "array"
                            getNextToken(); //Move past "["
                            getNextToken(); //Move past "]"
                            getNextToken(); //Move past "of"
                            getNextToken(); //Move past "type"
                            arrayType = currentToken.getValue();
                            System.out.println("   Array is of type " + arrayType);
                        }
                        getNextToken(); //Move past "of"
                        getNextToken(); //Move past "type"
                        String paramType = currentToken.getValue();
                        parameters.put(paramName, paramType);
                        getNextToken(); //Move past param type
                    }
                    getNextToken();
                }
                if (!(parameters.isEmpty())) {
                    for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    String paramName = entry.getKey();
                    String paramType = entry.getValue();
                    System.out.println(" Parameter: Name = " + paramName + ", Type = " + paramType);
                    }
                }
            
                // Skip past any "EndOfStatement" tokens
                if (currentToken.getType().equals("EndOfStatement")) {
                    getNextToken();
                }
            }
            if (currentToken.getValue().equals("variables")) {
                System.out.println("Handling Variables.");
                getNextToken(); //Move past "variables"
                getNextToken(); //Move past initial EOS
                Map<String, String> variables = new HashMap<>(); //Map containing variables

                while (!(currentToken.getValue().equals("begin"))) {
                    //Move past EOS tokens
                    if (currentToken.getType().equals("EndOfStatement")) {
                        getNextToken();
                    }
                    if (currentToken.getValue().equals("define")) {
                        getNextToken(); //Move past "define"
                        String varName = currentToken.getValue(); //get variable name
                        handleIdentifier(varName);
                        getNextToken(); //move past "x"
                        if (currentToken.getValue().equals("of")) { //Move past "of"
                            getNextToken();
                            if (currentToken.getValue().equals("type")) { //Move past "type"
                                getNextToken();
                                String varType = currentToken.getValue(); //Get variable type
                                variables.put(varName, varType);
                            }
                        }
                    }
                    if (currentToken.getValue().equals("begin")) {
                        break;
                    }
                    getNextToken();
                }
                //Printing out variables and types
                if (!(variables.isEmpty())) {
                    for (Map.Entry<String, String> entry : variables.entrySet()) {
                        String varName = entry.getKey();
                        String varType = entry.getValue();
                        System.out.println(" Variable: Name = " + varName + ", Type = " + varType);
                    }
                }
            }

            if (currentToken.getValue().equals("begin")) {
                
                while (!(currentToken.getValue().equals("exit"))) {
                    if (currentToken.getType().equals("EndOfStatement")) {
                        getNextToken();
                    }
                    
                    if (currentToken.getValue().equals("display")) {
                        System.out.println("Displaying");
                        getNextToken(); //Skip "display"
                        if (currentToken.getType().equals("StringLiteral")) {
                            String displayString = handleLiteral(currentToken.getType()); //Handle string
                            System.out.println(" " + displayString);
                            getNextToken();
                            if (currentToken.getValue().equals(",")) {
                                getNextToken();
                                String varName = currentToken.getValue();
                                if (symbolTable.containsKey(varName)) {
                                    Object varValue = symbolTable.get(varName);
                                    System.out.println("Displaying variable '" + varName + "' with value: " + varValue);
                                }
                                else {
                                    System.err.println("Error: Variable '" + varName + "' not found.");
                                }
                            }
                        }
                    }
                    if (currentToken.getValue().equals("set")) {
                        if (identifierList.containsKey(currentToken.getId())) {
                            System.out.println("Setting " + currentToken.getValue());
                            handleAssignment();
                        }
                    }
                    if (currentToken.getValue().equals("exit")) {
                        System.out.println("exit");
                        break;
                    }
                    getNextToken();
                }
            }
            if (currentToken.getValue().equals("endfun")) {
                System.out.println("end function");
                break;               
            }
            getNextToken();
        }
        
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
                    currentIdentifier = currentToken.getValue();
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

                default:
                    // Handle unrecognized token types
                    System.err.println("Error: Unrecognized token type - " + currentToken.getType() + ": '" + currentToken.getValue() + "'");
            }

            // Move to the next token
            getNextToken();
        }
    }

    public static void main(String[] args) throws ParseException {
        Interpreter interpreter = new Interpreter("C:\\Users\\joshl\\CPL_Proj3\\demo_cpl_proj\\src\\main\\java\\com\\cpl\\OutputTokens.json");
        interpreter.interpret();
    }
}
