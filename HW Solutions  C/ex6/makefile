a.out: GADT.o main.o 
	gcc GADT.o main.o
GADT.o : GADT.c GADT.h
	gcc -c GADT.c
main.o: main.c GADT.h
	gcc -c main.c