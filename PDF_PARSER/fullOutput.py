from PyPDF2 import PdfFileReader
from enum import Enum
import re

# Get rid of all non desired lines. eg. Page Numbers, Level Headers
badRegex = re.compile('^(Character|$|Level|[0-9])')	

def get_info(path):
	with open(path, 'rb') as f:
		pdf = PdfFileReader(f)
		info = pdf.getDocumentInfo()
		number_of_pages = pdf.getNumPages()
		page = pdf.getPage(64)
		#print(page.extractText())
		text = page.extractText()
		
		i = -1
		iterator = iter(text.splitlines())
		lines = text.splitlines()
		
		for line in iterator:
			i+= 1
			print(str(i) + ":" + line)
			
			if (badRegex.match(line) != None):
				continue
			
			
			
path = 'p1.pdf'
get_info(path)