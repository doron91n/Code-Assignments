/******************************************
*Exercise name: ex5
******************************************/
#include "Mission_Five.h"
/****************************** function duplicateRemove ****************************
* function name: duplicateRemove
* input: char **array4,int len
* output: int
* operation function:gets an array of pointers and its size and remove all duplicate
* items,returns the new size of the array.
* note:taken from http://www.studytonight.com/c/remove-duplicate-element-program.php
* ***************************************************************************/
int duplicateRemove(char **array4,int len)
{//start of duplicateRemove
/***************** variable declartion ******************/
int i=0,j=0,x=0,k=0;
/********************************************************/
for(i=0; i < len; i++)
{//start of for 1
    for(j= i+1; j < len; )
    {//start of for 2
    //comapre all items and remove duplicates from array.
    x=strcmp(array4[j] ,array4[i]);
    if(x == 0)
    {//start of if 1
        for(k=j; k < len;k++)
        {//start of for 3
            array4[k] = array4[k+1];
        }//end of for 3
        len--;
    }//end of if 1
    else
        j++;
    }//end of for 2
}//end of for 1
return len;
}//end of duplicateRemove
/****************************** function freeArray ****************************
* function name: freeArray
* input: char **array1,int items
* output: void
* operation function:gets an array of pointers and its size and free allocated
* memmory for each pointer in the array and then the array itself
* ***************************************************************************/
void freeArray(char **array1, int items)
{//start of freeArray
/***************** variable declartion ******************/
int  i = 0;
/********************************************************/
for (i = items - 1; i >= 0; i--)
    free(array1[i]);
free(array1);
}//end of freeArray
 /****************************** function arraySort ****************************
 * function name: arraySort
 * input: char **array2, int words2
 * output: void
 * operation function:gets array of values (and its size) and sorts them
 * ***************************************************************************/
void arraySort(char **array2, int words2)
{//start of arraySort
 /***************** variable declartion ******************/
int i = 0, j = 0, compare = 0;
char *temp=0;
/********************************************************/
for (i = 0; i < words2 - 1; i++)
{//start of for 1
    for (j = i + 1; j < words2; j++)
    {//start of for 2
        compare = strcmp(array2[i], array2[j]);
        if (compare > 0)
        {
            temp = array2[i];
            array2[i] = array2[j];
            array2[j] = temp;
        }
    }//end of for 2
}//end of for 1
}//end of arraySort
 /****************************** function printArray ****************************
 * function name: printArray
 * input: char **array3, int words3
 * output: void
 * operation function:gets array of values (and its size) sends it to sort and
 * print the array
 * ***************************************************************************/
void printArray(char **array3, int words3)
{//start of printArray
 /***************** variable declartion ******************/
int i = 0,newLen=0, compare = 0;
/********************************************************/
if (words3 > 1)
{// start of if 1 (as long as there is more then 1 word sort array)
    //send array to remove all duplicate items
    newLen = duplicateRemove(array3,words3);
    //send array to soting
    arraySort(array3, newLen);
    for (i = 0; i < newLen; i++)
    {//start of for (prints the sorted array)
            printf("%s\n", array3[i]);
    }//end of for (prints the sorted array)
}// end of if 1 (as long as there is more then 1 word sort array)
else
    printf("%s\n", array3[i]);
}//end of printArray
/****************************** function main5 ****************************
 * function name: main5
 * input: void
 * output: void
 * operation function:main function of mission5 gets user input(list of names)
 * and dynamicly allocate sapace(until user input is QUIT) then sends the list of
 * names to sorinting func and prints the sorted list
 * ***************************************************************************/
int main5()
{//start of main5
/***************** variable declartion ******************/
int  c, quit = 1, size1 = 1, i = 0, j = 0, size2 = 1;
char *name = 0, *name2 = 0, **namesArray = 0, **namesArray2 = 0;
/********************************************************/
printf("Please enter list of names:\n");
//allocate space for namesArray
namesArray = (char**)calloc(size2, sizeof(char*));
if (namesArray == 0)
{//start of if (checks allocation success)
    printf("Error: Cannot allocate Memory\n");
    free(namesArray);
    return 1;
}//end of if (checks allocation success)
while (quit != 0)
{//start of while 1
    size1 = 1;
    i = 0;
    //gets the name
    name = (char*)calloc(size1, sizeof(char));
    while ((c = getchar()) != '\n')
    {//start of while 2(gets name)
        name[i++] = (char)c;
        //if i reached allocated size then realloc double size1
        if (i == size1)
        {//start of if 1 (realloc for name)
            size1 = size1 * 2;
            name2 = (char*)realloc(name, size1*sizeof(char));
            if (name2 != 0)
                name = name2;
            else
            {//start of if 2 (checks allocation success)
                free(name);
                printf("Error: Cannot allocate Memory\n");
            }//end of if 2 (checks allocation success)
        }//end of if 1 (realloc for name)
    }//end of while 2(gets name)
    //add \0 at end of the word
    name[i] = '\0';
    quit = strcmp(name, "QUIT");
    if (quit == 0)
    {
        printf("There are %d names:\n", j);
        printArray(namesArray, j);
        break;
    }//end of if 3 (quits program)
    else
    {//start of else 1 (belongs to if 3)
        namesArray[j] = name;
        j++;
        if (j == size2)
        {//start of if 4 (realloc for namesArray)
            size2 = size2 * 2;
            namesArray2 = (char**)realloc(namesArray, size2*sizeof(char*));
            if (namesArray2 != 0)
                namesArray = namesArray2;
            else
            {
                freeArray(namesArray, j);
                printf("Error: Cannot allocate Memory\n");
            }
        }//end of if 4 (realloc for namesArray)
    }//end of else 1 (belongs to if 3)
}//end of while 1
free(name);
freeArray(namesArray, j);
}//end of main5




