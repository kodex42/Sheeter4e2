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
	BURST_BLAST_EFFECT = 12
	SUSTAIN = 13
	MISS = 14

class Power:
	def __init__(self, name, level,desc,freq,key,type,pRange,target,atkstat,hit,burstEffect,sustainAction,miss):
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
		self.burstEffect = burstEffect
		self.sustainAction = sustainAction
		self.miss = miss
	
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
		print("Miss: " + self.miss)
		print("Effect: " + self.burstEffect)
		print("Sustain: " + self.sustainAction)
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
		powEffect = ""
		powSustain = ""
		powMiss = ""
		i=-1
		weirdDesc = False
		wasMiss = False
		
		nField = Field.START 
		
		lines = text.splitlines()
		iterator = iter(text.splitlines())
		
		for line in iterator:
			i += 1
			if (badRegex.match(line) != None):
				continue
			
			# Skip First Instance of Name 
			if (nField == Field.START):
				nField = Field.NAME
				
				s = line.split(' ')
				
				if s[len(s) - 1].isdigit():
					powLevel = s[len(s)-1] 
					
					powerLen = int((len(s) - 3) / 2)
					
					for x in range(0,powerLen):
						powName += s[x] + ' '
				
					weirdDesc = True
					nField = Field.DESCRIPTION
					
				else:	
					continue
			
			# GET MISS TEXT
			if nField == Field.MISS:
				words = line.split(".")
				wasMiss = True	
					
				if(len(words) > 1):
					powMiss += (words[0] + ".")
					print("Miss: " + powMiss)
					nField = Field.END
				else:
					powMiss += line
			
			# GET HIT TEXT
			if nField == Field.HIT:
				if (line.startswith("Hit:")):
					powHit += line[5:]
				else:
					powHit += line
					if line[-1:] == '.':
						print("\n\n\nNext Line: " + lines[i+1] + "\n\n\n")
						print(str(i+1))
						if lines[i+1].startswith("Miss:"):
							nField = Field.MISS
						else:
							nField = Field.END
						print("Hit: " + powHit)
			
			# GET SUSTAIN ACTION
			if nField == Field.SUSTAIN:
				powSustain += line[8:]
				print("Sustain: " + powSustain)
				nField = Field.END
			
			
			
			# END OF POWER
			if nField == Field.END:
				
				# CREATE POWER
				power = Power(powName,powLevel,powDesc,powType,powKey,powAction,powRange,powTarget,powAtkStat,powHit,powEffect,powSustain,powMiss)
				
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
				powEffect = ""					
				powSustain = ""
				powMiss = ""
					
				if wasMiss:
					nField = Field.NAME
					wasMiss = False
				else:
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
			
			
			# GET BURST/BLAST EFFECT
			if nField == Field.BURST_BLAST_EFFECT:
				powEffect += line
				
				if line.endswith("."):
					nField = Field.SUSTAIN
					print("Effect: " + powEffect)
			
			# GET RANGE
			if nField == Field.RANGE:
					s = line.strip()
					
					if (s.startswith("Melee")):
						powRange = "Melee Weapon"
						nField = Field.TARGET
					elif(s.startswith("Ranged")):
						powRange = "Ranged "
						nRange = s[7:9]
						powRange += nRange
						nField = Field.TARGET
					elif(s.startswith("Close Blast") or s.startswith("Blast")):
						p = 1
					else: # Burst
						nCharsToIgnore = 0
						if s.startswith("Close"):
							powRange = "Close Burst "
							nCharsToIgnore = 12
						else:
							powRange = "Burst "
							nCharsToIgnore = 6
						
						nRange = s[nCharsToIgnore:nCharsToIgnore+1]
						powRange += nRange
						nField = Field.BURST_BLAST_EFFECT
					print("Range: " + powRange)
					
			
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
				if line.startswith("Daily"):
					powType = "Daily"
					powKey = line[5:]
					
					print("Frequency: " + powType)
					print("Keywords: " + powKey)
					nField = Field.ACTION_TYPE
				else:
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
					print("Description: " + powDesc)
			
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
