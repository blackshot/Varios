from os import listdir, remove
from shutil import rmtree
import re
import sys

try:
	borrar = sys.argv[1] == "borrar"
	glob = sys.argv[1] == "glob"
	try:
		borrar2 = sys.argv[2] == "borrar"
	except Exception as e:
		borrar2 = False
except Exception as e:
	borrar = False
	glob = False

user = "labinf10"
ruta = "C:\\Users\\" + user + "\\"
disco = "D:\\"
limpiar = ["Desktop\\", "Downloads\\", "Pictures\\", "Videos\\", "Favorites\\"
, "Music\\"]
ignorar = ["payara5", "Vms", "VMS", "script.py", "NetBeans", "css", "System Volume Information", "$RECYCLE.BIN", "Camera Roll", "Saved Pictures", "Links", "Dell", "Bing.url", "Software_II Semestre 2018"]
if(glob == False):
	for path in limpiar:
		archivos = [archivo for archivo in listdir(ruta + path)]
		print("========================================== " + path)
		for f in archivos:
			if(not re.match(r'.*\.(exe|lnk|ini)', f) and f not in ignorar):
				if borrar:
					try:
						remove(ruta + path + f)
						print("E: " + ruta + path + f)
					except Exception as e:
						rmtree(ruta + path + f)	
						print("E Carp: " + ruta + path + f)
				else:
					print(ruta + path + f)
	print("Limpiando el disco D:\\  ...")
	try:
		for f in listdir("D:\\"):
			if(not re.match(r'.*\.(exe|lnk|ini)', f) and f not in ignorar):
				if borrar:
					try:
						remove("D:\\" + f)
						print("E: " + "D:\\" + f)
					except Exception as e:
						rmtree(ruta + path + f)	
						print("E Carp: " + "D:\\" + f)
				else:
					print(ruta + path + f)
	except Exception as e:
		print("Disco D:\\ no encontrado.")
		raise e
else:
	import glob
	for f in glob.iglob(ruta + "**\\*.java", recursive=True):
		if not any(ign in f for ign in ignorar):
			if borrar2:
				remove(f)
				print("E:" + f)
			else:
				print(f)

	print("Limpiando el disco D:\\  ...")
	try:
		for f in glob.iglob("D:\\**\\*.java", recursive=True):
			if not any(ign in f for ign in ignorar):
				if borrar2:
					remove(f)
					print("E: " + f)
				else:
					print(f)
	except Exception as e:
		print("Disco D:\\ no encontrado.")
		raise e


print("Finalizado")
input("Presiona cualquier tecla para terminar")