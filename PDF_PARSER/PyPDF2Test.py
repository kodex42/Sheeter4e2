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
	HIT = 10
	END = 11

class Power:
	def __init__(self, name, level,desc,freq,key,type,pRange,target,atkstat,hit):
		self.name = name
		self.level = level
		self.desc = desc
		self.freq = freq
		self.key = key
		self.type = type
		self.pRange = pRange
		self.target = target
		self.atkstat = atkstat
		self.hit = hit
	
	def toString(self):
		print("--- Power --- ")
		print("Name: " + self.name)
		print("Level: " + str(self.level))
		print("Description: " + self.desc)
		print("Frequency: " + self.freq)
		print("Keywords: " + self.key)
		print("Action Type: " + self.type)
		print("Range: " + self.pRange)
		print("Target: " + self.target)
		print("Attack Stat: " + self.atkstat)
		print("Hit: " + self.hit)
		print("\n\n")


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
		
		
		powName = ""
		powLevel = 0
		powDesc = ""
		powType = ""
		powKey = ""
		powAction = ""
		powRange = ""
		powTarget = ""
		powAtkStat = ""
		powHit = ""
		
		weirdDesc = False
		
		
		nField = Field.START 
		
		iterator = iter(text.splitlines())
		
		for line in iterator:

			if (badRegex.match(line) != None):
				continue
			
			# Skip First Instance of Name 
			if (nField == Field.START):
				nField = Field.NAME
				
				s = line.split(' ')
				
				if s[len(s) - 1].isdigit():
					powLevel = s[len(s)-1] 
					
					powerLen = int((len(s) - 3) / 2)
					
					for i in range(0,powerLen):
						powName += s[i] + ' '
				
					weirdDesc = True
					nField = Field.DESCRIPTION
					
				else:	
					continue
			
			print(line)
			
			
			# GET HIT TEXT
			if nField == Field.HIT:
				
				if (line.startswith("Hit:")):
					powHit += line[5:]
				else:
					powHit += line
					if line[-1:] == '.':
						nField = Field.END
						print("Hit: " + powHit)
			
			
			if nField == Field.END:
				# CREATE POWER
				power = Power(powName,powLevel,powDesc,powType,powKey,powAction,powRange,powTarget,powAtkStat,powHit)
				
				power.toString()
				
				powName = ""
				powLevel = 0
				powDesc = ""
				powType = ""
				powKey = ""
				powAction = ""
				powRange = ""
				powTarget = ""
				powAtkStat = ""
				powHit = ""
				
				
				nField = Field.START

			# GET ATTACK_STAT
			if nField == Field.ATTACK_STAT:
				s = line.strip()
				powAtkStat = s[7:]
				nField = Field.HIT
				print("Attack Stat:" + powAtkStat)
				
			
			# GET TARGET
			if nField == Field.TARGET:
				s = line.strip()	
				if powRange == "Melee Weapon":
					if line == "Target:":
						continue
					powTarget = s
						
				else:
					powTarget = s

				print("Target: " + powTarget)
				nField = Field.ATTACK_STAT
			
			# GET RANGE
			if nField == Field.RANGE:
					s = line.strip()
					
					if (s.startswith("Melee")):
						powRange = "Melee Weapon"
					elif(s.startswith("Ranged")):
						powRange = "Ranged "
						nRange = s[7:9]
						powRange += nRange
					elif(s.startswith("Blast")):
						p = 1
					else: # Burst
						p = 1
					
					print("Range: " + powRange)
					nField = Field.TARGET
			
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
				if weirdDesc:
					weirdDesc = False
					continue
					
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
						

path = 'p1.pdf'
get_info(path)
