#Concepts of Programming Languages Section W02
#Patrick Mahon, Conner Lord, Jonathan Lin
#Description
    #Parser for the project deliverable that takes the SCL grammar and file to syntatically analyze the tokens


from Token import *
   


class ParseError(Exception):
    def __init__(self, index, msg, *args):
        self.index = index
        self.msg = msg
        self.args = args
    def __str__(self):
        return "%s at position %s" % (self.msg % self.args % self.index) 

    
class Parser:
    def __init__(self):
        self.cache = {}

    def parse(self, text):
        self.text = text
        self.index = -1
        self.len = len(text)-1
        parsing = self.start_parse()
        self.end_parse()

    def end_parse(self):
        if self.index < self.len:
            raise ParseError(self.index+1, 'Expected end of string but got %s', self.text[self.index+1])
    def start_parse(self):
        
    

#imports : 
#        | imports import_file
#        ;
#import_file : IMPORT header_file_name 
#            | USE header_file_name    
#            ;
#header_file_name : LANGB fname RANGB          
#                 | QUOTES fname QUOTES        
#				 ;
#fname : IDENTIFIER                    
#      | fname shlash IDENTIFIER       
#      ;
#shlash : FSHASH
#       | BSLASH
#       ;	


    #SCL Grammar Rule Functions
    def shlash(self):
        token = self.current_token
    
    def fname(self):
        token = self.current_token



if __name__ == '__main__':
    print("Hello!")
