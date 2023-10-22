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
        self.token_key_list = list(self.tokens.keys())
        self.repeated_token_keys = []
        self.current_token = -1 #Use this to keep track of where we are in the token list
    def loadJsonTokens(self, filename):
        with open(filename, 'r') as file:
            tokens = json.load(file)
        return tokens
    
    #Gets the next Token in the tokens using a list of keys to add an index of 1 and return that key
    def getNextToken(self):
        next_token = None
        try:
            next_token = self.token_key_list[self.current_token+1]
        except (ValueError, IndexError):
            next_token = None
        return next_token
    

    #Obtain current id at the current token index, increments through the list of identifiers by id, value to ensure it looks into the sublist(s)
    #Then if the current id is equal to any of the Identifier id's in the Identifiers list its key is added to the repeated token keys list and loop is broken to prevent repeated additions of the key
    def identifierExists(self, id, value, token_type):
        for token_id, token_value in self.token_lists[token_type]:
            if id == token_id and value == token_value:
            # This ID and value already exist for this token type, don't add it to the list
                return True
        return False

    #TODO: Add begin() and start() functions based on project deliverable guidelines
    def begin(self):
        print("Initializing Parser...")
        parser.start()
    
    def start(self):
        parser.parse()

    def parse(self):
        for token_key, token_data in self.tokens.items():
            token_type = token_data["Type"]
            token_id = token_data["id"]
            token_value = token_data["value"]

            #print(f"{token_key}, Type: {token_type}, ID: {token_id}, Value: {token_value}")
            self.current_token += 1

            #Test call for next token
            next_token = self.getNextToken()
            print(f" Next Token: {next_token}")

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

            #Test call for identifier Exists,
            
        print("\n")
        self.printTokenLists()


    def parseSL(self, token_data): #Just prints value of parsed SL
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed String Literal: Value: {value}, ID: {id}")
        if(self.identifierExists(id, value, "StringLiterals") == True):
            return
        else:
            self.token_lists["StringLiterals"].append((id, value)) #stores token in list to be printed

        
    def parseNL(self, token_data): #Same as above
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Numeric Literal: Value: {value}, ID: {id}")
        if(self.identifierExists(id, value, "NumericLiterals") == True):
            return
        else:
            self.token_lists["NumericLiterals"].append((id, value)) #stores token in list to be printed
    
    def parseSS(self, token_data): #Same as above
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Special Symbol: Value: {value} ID: {id}")
        if(self.identifierExists(id, value, "SpecialSymbols") == True):
            return
        else:
            self.token_lists["SpecialSymbols"].append((id, value)) #stores token in list to be printed
    
    def parseIdent(self, token_data): #Same as above
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Identifier: Value: {value}, ID: {id}")
        if(self.identifierExists(id, value, "Identifiers") == True):
            return
        else:
            self.token_lists["Identifiers"].append((id, value)) #stores token in list to be printed

    def parseKey(self, token_data): #Same as above
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Keyword: Value: {value}, ID: {id}")
        if(self.identifierExists(id, value, "Keywords") == True):
            return
        else:
            self.token_lists["Keywords"].append((id, value)) #stores token in list to be printed

    def parseOps(self, token_data):
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed Operator: Value: {value}, ID: {id}")
        if(self.identifierExists(id, value, "Operators") == True):
            return
        else:
            self.token_lists["Operators"].append((id, value)) #stores token in list to be printed

    def parseEOS(self, token_data):
        value = token_data["value"]
        id = token_data["id"]
        print(f"Parsed End of Statement: Value: {value}, ID: {id}")
        if(self.identifierExists(id, value, "EOS") == True):
            return
        else:
            self.token_lists["EOS"].append((id, value)) #stores token in list to be printed

    def printTokenLists(self): # Sort and print the token lists based on their precedence (ID in this case)
        for token_type, token_list in self.token_lists.items():
            sorted_tokens = sorted(token_list, key=lambda x: x[0])
            print(f"{token_type} tokens:")
            for id, value in sorted_tokens:
                print(f"ID: {id}, Value: {value}")
            print("\n")
    

if __name__ == '__main__':
    parser = Parser('OutputTokens.json')
    parser.begin()
