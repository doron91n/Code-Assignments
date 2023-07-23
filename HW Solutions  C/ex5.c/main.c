/******************************************
*Exercise name: ex5
******************************************/
#include <stdio.h>
#include <string.h>
#include "Mission_one_To_Four.h"
#include "Mission_Six.h"
#include "Mission_Five.h"
/***************************** function declaration **************************/
void menu();
/**************************************************************************************
* function name:  Menu()
* input:void
* output: void
* operation; main menu,give user options (Matrix Calculator\PigLatin Translator\quit)
* and preforms based on the user input(either end program or send to the chosen program
* *************************************************************************************/
void menu()
{//start of void Menu()
int letter=0,end=0,input=0;
char dummy;
	do
    {//start of do
		printf("Please enter your input:\n");
		end=0;
        scanf("%d",&input);
		scanf("%c", &dummy);
        switch (input)
        {//start of switch
/******************************* case 1 **************************************/
        case 1:
            main1();
        break;
/******************************* case 2 **************************************/
        case 2:
            main2();
        break;
/******************************* case 3 **************************************/
        case 3:
            main3();
        break;
/******************************* case 4 **************************************/
        case 4:
             main4();
        break;
/******************************* case 5 **************************************/
        case 5:
            main5();
        break;
/******************************* case 6 **************************************/
        case 6:
            main6();
        break;
/******************************* case 0 **************************************/
        case 0:
            end = 1;
        break;
/******************************* default *************************************/
        default:
            printf(" Error: Illegal input\n");
            break;
        }//end of switch
    }//end of do
    while(end==0);
}//end of void Menu()
/*****************************************************************************/
int main()
{
menu();//sends to the menu function
return 0;
}
