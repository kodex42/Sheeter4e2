from enum import Enum
import re
import sqlite3

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
	INCREASE = 15
	SPECIAL = 16
	
class Power:
	def __init__(self, name, level,desc,freq,key,type,pRange,target,atkstat,hit,burstEffect,sustainAction,miss,increase11,increase21,special):
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
		self.increase11 = increase11
		self.increase21 = increase21
		self.special = special
	
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
badRegex = re.compile('^(C H A|4E|$|Character|Level|[0-9]|[A-Z] [A-Z]+|[A-Z]$|[A-Z][A-Z]+)')
targetRegex = re.compile('^Target:')
attackRegex = re.compile('^Attack:')
hitRegex = re.compile('^Hit:')
actionTypeRegex = re.compile('^(Standard|Minor|Free)')
frequencyRegex = re.compile('^(Daily|Encounter|At-Will)')
missRegex = re.compile("^Miss:")
effectRegex = re.compile("^Effect:")
increaseRegex = re.compile("^Increase")
sustainRegex = re.compile("^Sustain")
specialRegex = re.compile("^Special:")
	
powers = []
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
powIncrease11 = ""
powIncrease21 = ""
powSpecial = ""

nField = Field.START

def generateDatabase():
	connection = sqlite3.connect("powers.db")
	cursor = connection.cursor()
	
	sqlCommand = """
	CREATE TABLE powers (
	power_number INTEGER PRIMARY KEY,
	power_name VARCHAR(30),
	power_level INTEGER,
	power_desc VARCHAR(255),
	power_type VARCHAR(255),
	power_keywords VARCHAR (255),
	power_action_type VARCHAR (255),
	power_range VARCHAR (255),
	power_target VARCHAR (50),
	power_attack_stat VARCHAR (100),
	power_hit VARCHAR(1000),
	power_effect VARCHAR(1000),
	power_sustain VARCHAR (200),
	power_miss VARCHAR(255),
	power_increase_11 VARCHAR(100),
	power_increase_21 VARCHAR(100),
	power_special VARCHAR(255));"""
	
	cursor.execute(sqlCommand)
	i = 0
	for p in powers:
		sqlCommand = """INSERT INTO powers(power_number,power_name,power_level,power_desc,power_type,power_keywords,power_action_type,power_range,power_target,power_attack_stat,power_hit,power_effect,power_sustain,power_miss,power_increase_11,power_increase_21,power_special) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"""
		
		cursor.execute(sqlCommand, (i,p.name,p.level,p.desc,p.freq,p.key,p.type,p.pRange,p.target,p.atkstat,p.hit,p.burstEffect,p.sustainAction,p.miss,p.increase11,p.increase21,p.special))
		i += 1
	connection.commit()
	connection.close()
	
with open("test.txt", "r") as input:
	for line in input:
	
		line = line.rstrip('\n')
		if not(line.startswith("AC ")):
			if badRegex.match(line) != None :	
				continue
		
		if missRegex.match(line) != None:
			nField = Field.MISS
		elif hitRegex.match(line) != None:
			nField = Field.HIT
		elif targetRegex.match(line) != None:
			nField = Field.TARGET
		elif frequencyRegex.match(line) != None:
			nField = Field.FREQUENCY
		elif attackRegex.match(line) != None:
			nField = Field.ATTACK_STAT
		elif effectRegex.match(line) != None:
			nField = Field.BURST_BLAST_EFFECT
		elif actionTypeRegex.match(line) != None:
			nField = Field.ACTION_TYPE
		elif increaseRegex.match(line) != None:
			nField = Field.INCREASE
		elif sustainRegex.match(line) != None:
			nField = Field.SUSTAIN
		elif specialRegex.match(line) != None:
			nField = Field.SPECIAL
		
		if not(line[-1:].isdigit()) and nField == Field.END:	
			nField = Field.BURST_BLAST_EFFECT
		
		if nField == Field.END:
				# CREATE POWER
				power = Power(powName,powLevel,powDesc,powType,powKey,powAction,powRange,powTarget,powAtkStat,powHit,powEffect,powSustain,powMiss,powIncrease11,powIncrease21,powSpecial)
				powers.append(power)
				print("\n\n")
								
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
				powIncrease11 = "" 
				powIncrease21 = ""
				powSpecial = ""
					
				nField = Field.START
		
		if nField == Field.SPECIAL:
			if line.startswith("Special:"):
				powSpecial += line[9:]
			else:
				powSpecial += line
			
			if line[-1:] == ".":
				powSpecial.replace("-","")
				print("Special: " + powSpecial)
				nField = Field.END
		
		if nField == Field.INCREASE:
			atIndex = line.find("at")
			toIndex = line.find("to")
			myInc = line[toIndex+3:atIndex-1]
			
			
			if line.find("21st") != -1:
				powIncrease21 = myInc
			else:
				powIncrease11 = myInc
				
			print("Increase: " + myInc)
			nField = Field.END
			continue
		
		if nField == Field.MISS:
			if (line.startswith("Miss:")):
				powMiss += line[6:]
			else:
				powMiss += line
			
			if line[-1:] == '.':
				powMiss = powMiss.replace("-","")
				print("Miss: " + powMiss)
				nField = Field.END
				continue
				
		
		if nField == Field.START:
			s = line.split(' ')	
			powLevel = s[len(s)-1] 					
			powerLen = int((len(s) - 3) / 2)
			for i in range(0,powerLen):
				powName += s[i] + ' '
				
			print("Name: " + powName)
			print("Level: " + str(powLevel))
			nField = Field.DESCRIPTION
			continue
			
		if nField == Field.DESCRIPTION:
			powDesc += line
			if line[-1:] == '.':
				nField = Field.FREQUENCY
				print("Description: " + powDesc)
				continue
				
		if nField == Field.FREQUENCY:
			s = line.replace(" ","")
			s = s.split("?")
			
			powType = s[0]
			powKey = s[1]
			print("Frequency: " + powType)
			print("Keywords: " + powKey)
			nField = Field.ACTION_TYPE
			continue
			
		if nField == Field.ACTION_TYPE:
			s = line.split(" ")
			powAction = s[0] + " " + s[1]
			
			for i in range(2,len(s)):
				powRange += s[i] + " "
			powRange = powRange.rstrip()
			
			print("Action Type: " + powAction)
			print("Range: " + powRange)
			
			nField = Field.TARGET
			continue
			
		if nField == Field.TARGET:
			s = line.split(":")
			powTarget = s[1].lstrip()
			
			print("Target: " + powTarget)
			nField = Field.ATTACK_STAT
			continue
		
		if nField == Field.ATTACK_STAT:
			s = line.split(":")
			powAtkStat = s[1].lstrip()
			
			print("Attack Stat: " + powAtkStat)
			nField = Field.HIT
			continue
		
		if nField == Field.HIT:	
			if (line.startswith("Hit:")):
				powHit += line[5:]
			else:
				powHit += line
			
			if line[-1:] == '.':
				powHit = powHit.replace("-","")	
				nField = Field.END						
				print("Hit: " + powHit)
				continue
					
		if nField == Field.BURST_BLAST_EFFECT:
			
			if (line.startswith("Effect")):
				powEffect += line[8:]
			else:
				powEffect += line
				
			if line.endswith("."):
				nField = Field.END
				print("Effect: " + powEffect)
				continue
					
		if nField == Field.SUSTAIN:
			if line.startswith("Sustain"):
				powSustain += line[8:]
			else:
				powSustain += line
			
			if line[-1:] == '.':
				print("Sustain: " + powSustain)
				nField = Field.END
		
input.close()

generateDatabase()
