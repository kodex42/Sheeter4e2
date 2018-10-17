from PyPDF2 import PdfFileReader
from enum import Enum
import re

class Field(Enum):
	START = 0
	NAME = 1
	LEVEL = 2
	DESCRIPTION = 3
	FREQUENCY = 4
	KEYWORD = 5
	ACTION_TYPE = 6
	RANGE = 7
	TARGET = 8
	ATTACK_STAT = 9

class Power:
	def __init__(self, name, desc, level):
		self.name = name
		self.desc = desc
		self.level = level


# Get rid of all non desired lines. eg. Page Numbers, Level Headers
badRegex = re.compile('^(Character|$|Level|[0-9])')	

# Check if action line
actionRegex = re.compile('^(Standard|Minor|Move|Immediate|Interupt)')

 
 
def get_info(path):
	with open(path, 'rb') as f:
		pdf = PdfFileReader(f)
		info = pdf.getDocumentInfo()
		number_of_pages = pdf.getNumPages()
		page = pdf.getPage(64)
		#print(page.extractText())
		text = page.extractText()
		
		
		begPow = True
		inDesc = False
		
		powName = ""
		powLevel = 0
		powDesc = ""
		powType = ""
		powKey = ""
		powAction = ""
		powRange = ""
		
		
		nField = Field.START 
		
		iterator = iter(text.splitlines())
		
		for line in iterator:

			if (badRegex.match(line) != None):
				continue
			
			# Skip First Instance of Name 
			if (begPow): 
				begPow = False
				nField = Field.NAME
				continue
			
			# GET RANGE
			if nField == Field.RANGE:
				
			
			
			# GET KEYWORDS
			if nField == Field.KEYWORD:
				if actionRegex.match(line) == None:
					powKey += line
				else:
					print ("Keywords: " + powKey)
					nField = Field.ACTION_TYPE
				
			# GET ACTION_TYPE
			if nField == Field.ACTION_TYPE:
				powAction = line
				print("Action Type: " + powAction)
				nField = Field.RANGE
			
			# GET FREQUENCY
			if nField == Field.FREQUENCY:
				powType = line
				print("Frequency: " + powType)
				nField = Field.KEYWORD
			
			# GET DESCRIPTION
			if nField == Field.DESCRIPTION:
				powDesc += line
				if line[-1:] == '.':
					nField = Field.FREQUENCY
					print("Description: " + powDesc + '\n')
			
			# Get level of power
			if nField == Field.LEVEL:
				powLevel = int(line[-1:])
				print("Level: " + str(powLevel))
				nField = Field.DESCRIPTION
			
			# Get name of power
			if nField == Field.NAME:
				powName = line
				print("Name: " + powName)
				nField = Field.LEVEL
						
		
			print(line)

path = 'p1.pdf'
get_info(path)
