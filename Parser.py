# Concepts of Programming Languages Section W02
# Patrick Mahon, Conner Lord, Jonathan Lin
# Description: Parser for the project deliverable that takes the SCL grammar and file to syntactically analyze the tokens

import json

class ParseError(Exception):
    def __init__(self, index, msg, *args):
        self.index = index
        self.msg = msg
        self.args = args

    def __str__(self):
        return "%s at position %s" % (self.msg % self.args % self.index)

class Parser:
    def __init__(self, filename):
        self.tokens = self.loadJsonTokens(filename)
        self.cache = {}
        self.token_lists = {  # We can use these lists to assign precedence
            "StringLiterals": [],
            "NumericLiterals": [],
            "Keywords": [],
            "Operators": [],
            "EOS": [],
            "SpecialSymbols": [],
            "Identifiers": []
        }
        self.current_token = -1 #Use this to keep track of where we are in the token list

    def loadJsonTokens(self, filename):
        with open(filename, 'r') as file:
            tokens = json.load(file)
        return tokens
       

    def parse(self):
        for token_key, token_data in self.tokens.items():
            token_type = token_data["Type"]
            token_id = token_data["id"]
            token_value = token_data["value"]

            #print(f"{token_key}, Type: {token_type}, ID: {token_id}, Value: {token_value}")

            if(token_type == "StringLiteral"):
                self.parseSL(token_data)
            elif(token_type == "NumericLiteral"):
                self.parseNL(token_data)
            elif(token_type == "specialSymbols"):
                self.parseSS(token_data)
            elif(token_type == "identifiers"):
                self.parseIdent(token_data)
            elif(token_type == "keywords"):
                self.parseKey(token_data)
            elif(token_type == "operators"):
                self.parseOps(token_data)
            elif(token_type == "EndOfStatement"):
                self.parseEOS(token_data)
        self.printTokenLists()


    def parseSL(self, token_data): #Just prints value of parsed SL
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed String Literal: Value: {value}, ID: {id}")
        self.token_lists["StringLiterals"].append((id, value)) #stores token in list to be printed

    def parseNL(self, token_data): #Same as above
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Numeric Literal: Value: {value}, ID: {id}")
        self.token_lists["NumericLiterals"].append((id, value))
    
    def parseSS(self, token_data): #Same as above
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Special Symbol: Value: {value} ID: {id}")
        self.token_lists["SpecialSymbols"].append((id, value))
    
    def parseIdent(self, token_data): #Same as above
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Identifier: Value: {value}, ID: {id}")
        self.token_lists["Identifiers"].append((id, value))

    def parseKey(self, token_data): #Same as above
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Keyword: Value: {value}, ID: {id}")
        self.token_lists["Keywords"].append((id, value))

    def parseOps(self, token_data):
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Operator: Value: {value}, ID: {id}")
        self.token_lists["Operators"].append((id, value))

    def parseEOS(self, token_data):
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Operator: Value: {value}, ID: {id}")
        self.token_lists["EOS"].append((id, value))

    def printTokenLists(self): # Sort and print the token lists based on their precedence (ID in this case)
        for token_type, token_list in self.token_lists.items():
            sorted_tokens = sorted(token_list, key=lambda x: x[0])
            print(f"{token_type} tokens:")
            for id, value in sorted_tokens:
                print(f"ID: {id}, Value: {value}")
    

if __name__ == '__main__':
    parser = Parser('OutputTokens.json')
    parser.parse()  



