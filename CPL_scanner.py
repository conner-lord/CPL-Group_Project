from Token import *
import json
import sys
import re

# Group members (Patrick Mahon, Conner Lord, Jonathan Lin)

def remove_items(test_list, item):
    # Using list comprehension to perform the task
    res = [i for i in test_list if i != item]
    return res

def tokenize_line(line):
    tokens = []
    current_token = ""
    in_string = False

    # Define regular expressions for different token types
    keyword_regex = r'\b(import|implementations|function|main|is|variables|define|of|begin|display|set|input|if|then|else|endif|not|greater|or|equal|return)\b'
    string_literal_regex = r'("[^"]*"|\'[^\']*\')'
    identifier_regex = r'[a-zA-Z_]\w*'
    numeric_literal_regex = r'\b\d+(\.\d+)?\b'
    
    # Combine all the regular expressions
    regex = f'({keyword_regex})|({string_literal_regex})|({identifier_regex})|({numeric_literal_regex})|(\S)'

    matches = re.finditer(regex, line)

    for match in matches:
        token = match.group(0)
        
        if token.strip() == "":
            continue  # Ignore whitespace tokens
        
        if token.startswith('"') or token.startswith("'"):
            if in_string:
                in_string = False
                current_token += token
                tokens.append(current_token)
                current_token = ""
            else:
                in_string = True
                current_token = token
        else:
            if in_string:
                current_token += token
            else:
                tokens.append(token)

    return tokens



def filter_file(File_name):
    try:
        file = open(File_name, 'r')
    except:
        print("no such file or directory: ", File_name)
        exit(2)

    lineList = []

    for line in file:
        if line.startswith("/*"):
            continue  # Skip the comment line

        lineTokens = tokenize_line(line)
        
        # Remove empty tokens or tokens containing only whitespace
        lineTokens = [token for token in lineTokens if token.strip() != '']
        
        if lineTokens:
            lineList.append(lineTokens)

    return lineList

# checks if a string is a float
def isfloat(num):
    try:
        float(num)
        return True
    except ValueError:
        return False
    
# converts list to dictionary
def Convert(a):
    it = iter(a)
    res_dct = dict(zip(it, it))
    return res_dct

# adds two dictionaries together
def merge_dictionaries(dict1, dict2):
    merge_dict = dict1.copy()
    merge_dict.update(dict2)
    return merge_dict

# Function to categorize tokens
def categorize_token(token):
    if token.isspace():
        return {"Type": "Whitespace", "id": 999, "value": token}
    elif token == "EOS":
        return {"Type": "EndOfStatement", "id": 1000, "value": token}
    elif token in ["/*", "*/"]:
        return {"Type": "Comment", "id": 1001, "value": token}
    elif token in ["import", "implementations", "function", "main", "is", "variables", "define", "of", "begin", "display", "set", "input", "if", "then", "else", "endif", "not", "greater", "or", "equal", "return"]:
        return {"Type": "Keyword", "id": 2000, "value": token}
    elif re.match(r'^[a-zA-Z_]\w*$', token):
        return {"Type": "Identifier", "id": 3000, "value": token}
    elif re.match(r'^[0-9]+(\.[0-9]+)?$', token):
        return {"Type": "NumericLiteral", "id": 4000, "value": token}
    elif token.startswith('"') or token.startswith("'"):
        return {"Type": "StringLiteral", "id": 5000, "value": token}
    elif token in [":", ".", ":", ",", "/", "=", ">", "*", ")"]:
        if token in [":", ",", "=", ")"]:
            return {"Type": "Operator", "id": 6000, "value": token}
        elif token in [".", "/", "*", ">"]:
            return {"Type": "Operator", "id": 6001, "value": token}
        elif token == ":":
            return {"Type": "VariableDeclaration", "id": 6002, "value": token}
    # Add more conditions for other types of tokens

    # If none of the above conditions match, treat it as an "UNKNOWN" token
    return {"Type": "UNKNOWN", "id": 1200, "value": token}

if __name__ == '__main__':
    sysArgv = sys.argv

    ItemList = filter_file(sysArgv[1])

    finalTokenList = []

    megaDict = {}

    for lineTokens in ItemList:
        for token in lineTokens:
            categorized_token = categorize_token(token)
            newToken = Token(categorized_token["Type"], categorized_token["id"], categorized_token["value"])
            finalTokenList.append(newToken)
            print("New Token created: ", newToken.getData())

        newToken = Token('EndOfStatement', 1000, 'EOS')
        finalTokenList.append(newToken)
        print("New Token created: ", newToken.getData())

    # Converting token objects into a dictionary

    loopCounter = 0
    for Token in finalTokenList:
        tokenStr = "Token_" + str(loopCounter)
    
        # Create a dictionary for the token with relevant information
        tokenData = Token.getData()
        tokenDict = {
            "Type": tokenData[0],
            "id": tokenData[1],
            "value": tokenData[2]
        }
    
        # Add the token dictionary to the megaDict
        megaDict[tokenStr] = tokenDict
    
        loopCounter += 1

    loopCounter = 0
    for Token in finalTokenList:
        tokenData = Token.getData()
        lst = ['Type', tokenData[0], 'id', tokenData[1], 'value', tokenData[2]]
        newList = Convert(lst)

        tokenStr = "Token_" + str(loopCounter)
        
    #Creating JSON object of the mega dictionary and writing to new output JSON file
    json_object = json.dumps(megaDict, indent=4)
    with open('OutputTokens.json', 'w') as f:
        f.write(json_object)


