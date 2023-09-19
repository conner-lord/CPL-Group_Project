from token import *
import json
import sys

# Group members (Patrick Mahon, Conner Lord, Jonathan Lin)

def remove_items(test_list, item):
    #using list comprehension to perform the task
    res - [i for i in test_list if i != item]

    return res

def filter_file(File_name):
    try:
        file = open(File_name, 'r')
    except:
        print("no such file or directory: ", File_name)
        exit(2)
    # Filtering off
    # Block comments
    # Empty Lines
    # Empty Spaces
    # Line Comments

    Comment = False
    lineList = []

    for line in file:
        lineTokens = []

        #create string statement tokens using "
        if "" in line:
            splitLocation = line.find("")
            beforeStr = line[:splitLocation]
            afterStr = line[splitLocation:]
            secondSplitLocation = splitLocation + afterStr[1:].find("") + 1
            strStatement = line[splitLocation:secondSplitLocation + 1]
            afterStr = line[secondSplitLocation + 1:]

            beforeStatementTokens = beforeStr.split(' ')
            for token in beforeStatementTokens:
                lineTokens.append(token)

            lineTokens.append(strStatement)

            if afterStr != '\n':
                afterStatementTokens = afterStr.split(' ')
                for token in afterStatementTokens:
                    lineTokens.append(token)

            lineList.append(lineTokens)
            continue

        #TODO: turn this into a callable function
        # Split statement tokens using ^

        if '^' in line:
            splitLocation = line.find('^')
            beforeStr = line[:splitLocation]
            afterStr = line[splitLocation:]
            secondSplitLocation = splitLocation + afterStr[1:].find('^') + 1
            strStatement = line[splitLocation:secondSplitLocation + 1]
            afterStr = line[secondSplitLocation + 1:]

            beforeStatementTokens =  beforeStr.split(' ')
            for token in beforeStatementTokens:
                lineTokens.append(token)

            lineTokens.append(strStatement)

            afterStatementTokens = afterStr.split(' ')
            for token in afterStatementTokens:
                lineTokens.append(token)

            lineList.append(lineTokens)
            continue

        #split lines into line tokens using ' '
        lineTokens = line.split(' ')

        #filters block comments (Proof of concept, need to implement temp for changes)
        commentStart = "/*"
        commentEnd = "*/"

        if commentStart in line:
            Comment = True

        if not Comment:
            lineList.append(lineTokens)

        if commentEnd in line:
            Comment = False
        #End of block comment filter

    #Empty space token filter
    loopCount = 0
    for line in lineList:
        lineList[loopCount] = remove_items(line, '')
        loopCount += 1

    # \n filter
    loopCount = 0
    for line in lineList:
        if '\n' in line[len(line) - 1]:
            modifiedStr = line[len(line) - 1]
            modifiedStr = modifiedStr[:-1]
            line[len(line) - 1] = modifiedStr
            lineList[loopCount] = line
        loopCount += 1

    # line comment filter
    loopCount = 0
    for line in lineList:
        if '//' in line:
            line = line[:line.index('//')]
            lineList[loopCount] = line
        loopCount += 1

    currentLineNum = 0
    while currentLineNum < len(lineList):
        if lineList[currentLineNum[0]] == '':
            lineList.remove(lineList[currentLineNum])
        else:
            currentLineNum += 1

    lineList = list(filter(None, lineList))

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

# adds two dictionary together
def merge_dictionaries(dict1, dict2):
    merge_dict = dict1.copy()
    merge_dict.update(dict2)
    return merged_dict


# main scanner method 
if __name__ == '__main__':
    sysArgv = sys.argv # scan file from sys argvs

    ItemList = filter_file(sysArgv[1]) # create token list from file

    finalTokenList = []
    megaDict = {}

    # turn all tokens in list to appropriate token objs
    # print said tokens to console
    for Items in ItemList:
        for TokenItem in Items:
            if TokenItem in tokenList["keywords"]:
                newToken = Token("keywords", tokenList["keywords"][TokenItem], TokenItem)
            elif TokenItem in tokenList["identifiers"]:
                newToken = Token("identifiers", tokenList["identifiers"][TokenItem], TokenItem)
            elif TokenItem in tokenList["operators"]:
                newToken = Token("operators", tokenList["operators"][TokenItem], TokenItem)
            elif TokenItem in tokenList["specialSymbols"]:
                newToken = Token("specialSymbnols", tokenList["specialsymbols"][TokenItem], TokenItem)
            elif TokenItem[0] == '"' and TokenItem[len(TokenItem) - 1] == '"':
                newToken = Token('literals', 600, TokenItem)
            elif isfloat(TokenItem):
                newToken = Token('literals', 600, TokenItem)
            else:
                newToken = Token('UNKNOWN', 1200, TokenItem) #IN case no token is recognized

            finalTokenList.append(newToken)
            print("New Token created: ", newToken.getData())

        newToken = Token('EndOfStatement', 1000, 'EOS')
        finalTokenList.append(newToken)
        print("New Token created: ", newToken.getData())

    # Create json file by converting token obj into a dictionary
    jsonFile = open("OutputTokens.json", "w")

    loopCounter = 0
    for Token in finalTokenList:
        tokenStr = "Token_" + loopCounter.__str__()
        megaDict.update({tokenStr: {}})
        loopCounter += 1

    loopCounter = 0
    for Token in finalTokenList:
        tokenData = Token.getData()
        lst = ['Type', tokenData[0], 'id', tokenData[1], 'value', tokenData[2]]
        newList = Convert(lst)

        tokenStr = "Token_" + loopCounter.__str__()

    json_object = json.dumps(megaDict, indent=4)
    jsonFile.write(json_object)


