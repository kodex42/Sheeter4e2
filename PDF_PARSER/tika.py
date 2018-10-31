import os
import os.path

tika_dir=os.path.join(os.path.dirname(__file__),'tika-app-1.19.1.jar')

def extract_pdf(source_pdf:str,target_txt:str):
    os.system('java -jar '+tika_dir+' -t {} > {}'.format(source_pdf,target_txt))
	
extract_pdf('cleric.pdf','test.txt')