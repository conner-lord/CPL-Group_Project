# Tokens
tokenList = {
    "keywords":{
        'import': 0,
        'implementations': 1,
        'function': 2,
        'main': 3,
        'return': 4,
        'type': 5,
        'integer': 6,
        'double': 7,
        'char': 8,
        'num': 9,
        'is': 10,
        'variables': 11,
        'define': 12,
        'of': 13,
        'begin': 14,
        'display': 15,
        'set': 16,
        'exit': 17,
        'endfun': 18,
        'symbol': 19,
        'end': 20,
        'input': 21,
        'structures': 22,
        'pointer': 23,
        'head': 24,
        'last': 25,
        'NULL': 26,
        'ChNode': 27,
        'using': 28,
        'reverse': 29,
        'while': 30,
        'endwhile': 31,
        'call': 32,
        'constants': 33,
        'float': 34,
        'array': 35,
        'for': 36,
        'to': 37,
        'do': 38,
        'endfor': 39,
        'if': 40,
        'NumericLiteral': 41,
        'StringLiteral': 42,
        'then': 43,
        'endif': 44,
        'else': 45

    },

    "identifiers": {
        'x': 200,
        'r': 201,
        'area': 202,
        'cir': 203,
        'pchar': 204,
        'j': 205,
        'N': 206,
        'sum': 207,
        'ave': 208,
        'svalue': 209,
        'num': 210,
        'varr': 211,
        't': 212,
        'pt': 213,
        'varr[j]': 212 #Fix for deliverable 2...
    },

    "operators": {
        '+': 400,
        '-': 401,
        '*': 402,
        '/': 403,
        '^': 404,
        '>': 405,
        '<': 406,
        '"': 407,
        '=': 408,
        'add': 409,
        'greater': 408,
        'or': 409,
        'equal': 410,
        'not': 411
        
        
    },

    "specialSymbols": {
        ',': 800,
        '.': 801,
        'PI': 802,
        'M_PI': 803,
        '[N]': 804
    }
}

class Token:
    def __init__(self, type, id, value):
        self.type = type
        self.id = id
        self.value = value

    def getData(self):
        return [self.type, self.id, self.value]
        
