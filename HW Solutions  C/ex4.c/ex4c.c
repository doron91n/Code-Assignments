/******************************************
*Exercise name: ex4
******************************************/
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "PigLatinTranslator.h"
#include "MatrixCalculator.h"
/***************************** function declaration **************************/
void Menu();
/**************************************************************************************
* function name:  Menu()
* input:void
* output: void
* operation; main menu,give user options (Matrix Calculator\PigLatin Translator\quit)
* and preforms based on the user input(either end program or send to the chosen program
* *************************************************************************************/
void Menu()
{//start of void Menu()
char input[15];
char input1[15]="translator";
char input2[15]="calculator";
char input3[15]="quit";
int letter=0,end=0;
char dummy;
	do
    {//start of do
		printf("Main menu, choose between the options:\nMatrix Calculator\nPigLatin Translator\n");
		end=0;
        scanf("%s",&input);
		scanf("%c", &dummy);
        //transforms all letters in input to lowercase
        for(letter=0;input[letter];letter++)
        {//start of for 1
            input[letter]=tolower(input[letter]);
        }//end of for 1
        //checks if input is "translator" and if so goes to piglatintranslator
        if (strcmp(input1, input)==0)
        {//start of if 1
            SentenceScan();
        }//end of if 1
        //checks if input is "calculator" and if so goes to matrixcalculator
        else if (strcmp(input2, input)==0)
        {//start of if 2
            printf("Welcome to the Matrix calculator\n");
            MatrixOperation();
        }//end of if 2
        //checks if input is "quit" and if so exits program
        else if (strcmp(input3, input)==0)
        {//start of if 3
            end=1;
        }//end of if 3
        else
            printf("This was not an option.\n");
    }//end of do
    while(end==0);
}//end of void Menu()
/*****************************************************************************/
int main()
{
Menu();//sends to the menu function
return 0;
}
