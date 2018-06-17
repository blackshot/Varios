from time import sleep
from os import system
from colorama import init, Fore, Back, Style
import random

init(autoreset=True)
tablero = [[0 for i in range(10)] for i in range(20)]
bichito = [0 ,0]
saltamontes = [0, 0]
vivo = True # ¿el saltamontes está vivo?
vidas = [0, 0] # Botiquines de los 2

# constantes 
esquinas = [[0 ,0], [0, 9], [19, 0], [19, 9]]
arriba, der, abajo, izq = (0, 1, 2, 3)
bomba = 1
guarida = 2
botiquin = 3
sector = 0

# BOMBAS
cont = 0
while (cont < 10):
	coord = [random.randint(0, 19), random.randint(0, 9)]
	# print(coord)
	if(coord not in esquinas and tablero[coord[0]][coord[1]] == 0):
		tablero[coord[0]][coord[1]] = 1
		cont+=1
#GUARIDAS
for coord in esquinas:
	tablero[coord[0]][coord[1]] = 2

cont = 0
while (cont < 5):
	coord = [random.randint(0, 19), random.randint(0, 9)]
	# print(coord)
	if(coord not in esquinas and tablero[coord[0]][coord[1]] == 0):
		tablero[coord[0]][coord[1]] = 2
		cont+=1
# BOTIQUINES
cont = 0
while (cont < 5):
	coord = [random.randint(0, 19), random.randint(0, 9)]
	# print(coord)
	if(coord not in esquinas and tablero[coord[0]][coord[1]] == 0):
		tablero[coord[0]][coord[1]] = 3
		cont+=1


# INGRESO DE COORDENADAS DEL BICHITO
check = True
while (check):
	while(check):
		tempcord = int(input("Ingrese la coordenada X inicial del bichito: "))
		tempcord -= 1
		if(tempcord < 0) or (tempcord > 9):
			system("cls")
			print("Error, ingrese un rango valido.")
		else:
			bichito[1] = tempcord
			check = False
	check = True
	while(check):
		tempcord = int(input("Ingrese la coordenada Y inicial del bichito: "))
		tempcord -= 1
		if(tempcord < 0) or (tempcord > 19):
			system("cls")
			print("Error, ingrese un rango valido.")
		else:
			bichito[0] = tempcord
			check = False
	check = True
	if(bichito in esquinas):
		system("cls")
		print("No puedes poner el bichito en una esquina.")
	elif(tablero[bichito[0]][bichito[1]] != 0):
		system("cls")
		print("Ese no es un casillero vacío.")
	else:
		check = False

termino = False
tiempo = 1
#chequeo inicial del sector de bicho
if(bichito[0] < 10) and (bichito[1] < 5):
	sector = 1
elif(bichito[0] < 10) and (bichito[1] >= 5):
	sector = 2
elif(bichito[0] >= 10) and (bichito[1] < 5):
	sector = 3
else:
	sector = 4
while(not termino):

	system("cls")
	# Salto del depredador
	if(vivo):
		saltamontes = [0, 0] # prevencion para que el saltamonte no pueda caer en una guarida
		while(tablero[saltamontes[0]][saltamontes[1]] == 2):
			if(sector == 1):
				saltamontes = [random.randint(0, 9), random.randint(0, 4)]
			elif(sector == 2):
				saltamontes = [random.randint(0, 9), random.randint(5, 9)]
			elif(sector == 3):
				saltamontes = [random.randint(10, 19), random.randint(0, 4)]
			else:
				saltamontes = [random.randint(10, 19), random.randint(5, 9)]

		if(saltamontes == bichito):
			print(Fore.RED + "EL SALTAMONTES SE COMIO AL BICHITO :(")
			if(vivo):
				print("Saltamones: (", saltamontes[1]+1, ", ", saltamontes[0]+1, ") | Botiquines Robados: ", vidas[1])
			else:
				print(Fore.RED + "EL SALTAMONTES EXPLOTO")
			print("Bichito: (", bichito[1]+1, ", ", bichito[0]+1, ") | Tiempo: ", tiempo, " | Botiquines: ", vidas[0])
			print("Sector: ", sector)
			for y in range(20):
			 	for x in range(10):
			 		if([y, x] == saltamontes and vivo):
			 			print(Style.BRIGHT + Fore.RED + "S", end=" ")
			 		elif([y, x] == saltamontes and not vivo):
			 			print(Style.BRIGHT + Fore.RED + "X", end=" ")
			 		elif([y, x] == bichito):
			 			print(Style.BRIGHT + Fore.CYAN + "B", end=" ")
			 		elif(tablero[y][x] == 0):
			 			print(Fore.CYAN + ".", end=" ")
			 		elif(tablero[y][x] == 1):
			 			print(Style.DIM + Fore.RED + str(tablero[y][x]), end=" ")
			 		elif(tablero[y][x] == 2):
			 			print(Style.BRIGHT + Fore.GREEN + str(tablero[y][x]), end=" ")
			 		elif(tablero[y][x] == 3):
			 			print(Style.BRIGHT + Fore.MAGENTA + str(tablero[y][x]), end=" ")
			 	print()
			termino = True
		elif(tablero[saltamontes[0]][saltamontes[1]] == bomba):
			if(vidas[1] > 0):
				vidas[1] -=1
				tablero[saltamontes[0]][saltamontes[1]] = 0
			else:
				#print(Fore.RED + "EL SALTAMONES EXPLOTO")
				vivo = False
		elif (tablero[saltamontes[0]][saltamontes[1]] == botiquin):
				vidas[1]+=1
				tablero[saltamontes[0]][saltamontes[1]] = 0
				#print(Fore.RED + Style.BRIGHT + "EL SALTAMONTES SE ROBO UN BOTIQUIN")

	else:
		print(Fore.RED + "EL SALTAMONES EXPLOTO")

	# MOVIMIENTO DEL BICHITO
	direccion = random.randint(0, 3)
	for i in range(3):
		if (not termino):
			system("cls")

			if(direccion == arriba) and (bichito[0]-1 >= 0):
				bichito[0] -= 1
			if(direccion == der) and (bichito[1]+1 < 10):
				bichito[1] += 1
			if(direccion == abajo) and (bichito[0]+1 < 20):
				bichito[0] += 1
			if(direccion == izq) and (bichito[1]-1 >= 0):
				bichito[1] -= 1
			# Chequeo si paso por algun botiquin/guarida/bomba
			if (tablero[bichito[0]][bichito[1]] == botiquin):
				vidas[0]+=1
				tablero[bichito[0]][bichito[1]] = 0
			elif (tablero[bichito[0]][bichito[1]] == guarida):
				print(Fore.GREEN + "EL BICHITO ESCAPO")
				termino = True
			elif(tablero[bichito[0]][bichito[1]] == bomba):
				if(vidas[0] > 0):
					vidas[0] -=1
					tablero[bichito[0]][bichito[1]] = 0
				else:
					print(Fore.RED + "EL BICHITO EXPLOTO")
					termino = True
			elif (bichito == saltamontes):
				print(Fore.RED + "EL SALTAMONTES SE COMIO AL BICHITO :(")
				termino = True
			#chequeo del sector de bicho
			if(bichito[0] < 10) and (bichito[1] < 5):
				sector = 1
			elif(bichito[0] < 10) and (bichito[1] >= 5):
				sector = 2
			elif(bichito[0] >= 10) and (bichito[1] < 5):
				sector = 3
			else:
				sector = 4

			#datos del bicho
			if(vivo):
				print("Saltamones: (", saltamontes[1]+1, ", ", saltamontes[0]+1, ") | Botiquines Robados: ", vidas[1])
			else:
				print(Fore.RED + "EL SALTAMONTES EXPLOTO")
			print("Bichito: (", bichito[1]+1, ", ", bichito[0]+1, ") | Tiempo: ", tiempo, " | Botiquines: ", vidas[0])
			print("Sector: ", sector)
			# Muestra el Mapa
			for y in range(20):
			 	for x in range(10):
			 		if([y, x] == saltamontes and vivo):
			 			print(Style.BRIGHT + Fore.RED + "S", end=" ")
			 		elif([y, x] == saltamontes and not vivo):
			 			print(Style.BRIGHT + Fore.RED + "X", end=" ")
			 		elif([y, x] == bichito):
			 			print(Style.BRIGHT + Fore.CYAN + "B", end=" ")
			 		elif(tablero[y][x] == 0):
			 			print(Fore.CYAN + ".", end=" ")
			 		elif(tablero[y][x] == 1):
			 			print(Style.DIM + Fore.RED + str(tablero[y][x]), end=" ")
			 		elif(tablero[y][x] == 2):
			 			print(Style.BRIGHT + Fore.GREEN + str(tablero[y][x]), end=" ")
			 		elif(tablero[y][x] == 3):
			 			print(Style.BRIGHT + Fore.MAGENTA + str(tablero[y][x]), end=" ")
			 	print()
			sleep(0.24)
	sleep(0.25)
	tiempo += 1
# input("Persiona ENTER para salir...")
