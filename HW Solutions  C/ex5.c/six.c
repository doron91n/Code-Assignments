/******************************************
*Exercise name: ex5
******************************************/
#include "Mission_Six.h"
/****************************** function printMenu ****************************
 * function name: printMenu
 * input: void
 * output: void
 * operation function: prints menu options
 * ***************************************************************************/
void printMenu()
{//start of printMenu
printf("Please select your choice:\n");
printf("0.Exit\n");
printf("1.Add item to the queue\n");
printf("2.Remove item from the queue\n");
printf("3.Print queue\n");
printf("4.Print the maximum item in the queue\n");
printf("5.Print the minimum item in the queue\n");
printf("6.Print index of given item\n");
printf("7.Clear queue\n");
printf("8.Print the menu\n");
}//end of printMenu
/****************************** function freeArrayInt ****************************
 * function name: freeArrayInt
 * input: int **array1, int items
 * output: void
 * operation function: gets array1 and its size and frees all allocated space
 * ***************************************************************************/
void freeArrayInt(int **array1, int items)
{//start of freeArrayInt
/***************** variable declartion ******************/
int  i = 0;
/********************************************************/
for (i = items ; i >= 0; i--)
    free(array1[i]);
free(array1);
}//end of freeArrayInt
/****************************** function arrayIsEmpty ****************************
 * function name: arrayIsEmpty
 * input: int count1
 * output: return 1 if array has at least 1 item else print error and return 0
 * operation function: checks if array is  empty
 * ***************************************************************************/
int arrayIsEmpty(int count1)
{//start of
if (count1 >= 0)
{
    return 1;
}
else
{
    printf("Error: Queue is empty!\n");
    printf("Please select your next choice (select 8 for complete menu)\n");
    return 0;
}
}//end of arrayIsEmpty
/****************************** function deleteItem ****************************
 * function name: deleteItem
 * input: int **array,int count1
 * output: int count1(returns the new count1)
 * operation function: get array and its size deletes the first Item that was added
 * ***************************************************************************/
int deleteItem(int **array1,int count1)
{//start of deleteItem
/***************** variable declartion ******************/
 int returnVal=0;
/********************************************************/
returnVal = arrayIsEmpty(count1);
if(returnVal == 1)
    {//start of if (deletes item and free memmory)
    printf("Item %d was removed \n",*array1[count1]);
    free(array1[count1]);
    printf("Please select your next choice (select 8 for complete menu)\n");
    count1--;
    } //end of if (deletes item and free memmory)
    return count1;
}//end of deleteItem
/****************************** function addItem ****************************
 * function name: addItem
 * input: int **array,int number,int count1
 * output: int count1(returns the new count1)
 * operation function: get array and its size then gets a new number from user to
 * be added to the queue, adds a pointer to it that gets into the array
 * ***************************************************************************/
int addItem(int **array1,int count1)
{//start of addItem
/***************** variable declartion ******************/
 int *pNum = 0,*pNum2 = 0,i=0,size=2 ,number=0;
 char dummy;
/********************************************************/
printf("Enter item value to add\n");
scanf("%d",&number);
//dummy gets the \n
scanf("%c",&dummy);
pNum = ( int*)calloc(size,sizeof( int));
if (pNum  == 0)
{//start of if 1 (checks allocation success)
    size = size*2;
    pNum2 = (int*)realloc(pNum, size*sizeof(int));
    if (pNum2 != 0)
        pNum = pNum2;
    else
    {//start of else belongs to if 2
        free(pNum);
        printf("Error: Insufficient Memory\n");
    }//end of else belongs to if 2
}//end of if 1 (checks allocation success)
//assign a pointer to number
*pNum = number;
//if its the first item put in array1[0]
if(count1 == -1)
    array1[count1+1] = pNum;
//else put it at the head of the array and move the others
else
{//start of else
    for(i=count1;i>=0;i--)
    {//start of for
        array1[i+1] = array1[i];
        if(i==0)
            array1[i] = pNum;
    }//end of for
}//end of else
printf("Item %d added\n",number);
count1++;
printf("Please select your next choice (select 8 for complete menu)\n");
return count1;
}//end of int addItem
/****************************** function findMaxItem ****************************
 * function name: findMaxItem
 * input: int **array1,int count1
 * output: void
 * operation function: get array1 and its size  and prints the max number in it
 * ***************************************************************************/
void findMaxItem(int **array1,int count1)
{//start of findMaxItem
/***************** variable declartion ******************/
int returnVal=0,i=0,maxItem = -99999;
/********************************************************/
returnVal = arrayIsEmpty(count1);
    if(returnVal == 1)
    {//start of if 1
        maxItem = *array1[0];
        for(i=0;i<=count1;i++)
        {//start of for
             if(maxItem < *array1[i])
                    maxItem = *array1[i];
        }//end of for
        printf("Maximum item in queue is %d\n",maxItem);
        printf("Please select your next choice (select 8 for complete menu)\n");
    }//end of if 1
}//end of findMaxItem
/****************************** function findMinItem ****************************
 * function name: findMinItem
 * input: int **array1,int count1
 * output: void
 * operation function: get array1 and its size  and prints the min number in it
 * ***************************************************************************/
void findMinItem(int **array1,int count1)
{//start of findMinItem
/***************** variable declartion ******************/
int returnVal=0,i=0,minItem = 99999;
/********************************************************/
returnVal = arrayIsEmpty(count1);
    if(returnVal == 1)
    {//start of if 1
        minItem = *array1[0];
        for(i=0;i<=count1;i++)
        {//start of for
             if(minItem > *array1[i])
                minItem = *array1[i];
        }//end of for
        printf("Minimum item in queue is %d\n",minItem);
        printf("Please select your next choice (select 8 for complete menu)\n");
    }//end of if 1
}//end of findMinItem
/****************************** function findNum ****************************
 * function name: findNum
 * input: int **array1,int count1,int num
 * output: void
 * operation function: get array1 and its size  and a number from user
 * if the number is in the array1 prints it and its index
 * ***************************************************************************/
void findNum(int **array1,int count1)
{//start of findNum
/***************** variable declartion ******************/
int i=0,numFound = 0,number=0,returnVal=0;
char dummy;
/********************************************************/
returnVal = arrayIsEmpty(count1);
if(returnVal == 1)
{//start of if 1
    printf("Please enter the item you would like to know its index\n");
    scanf("%d",&number);
    //dummy gets the \n
    scanf("%c",&dummy);
    for (i = 0; i <= count1; i++)
    {//start of for 1
        if(number == *array1[i])
        {//start of if 2
            printf("Item %d index is %d\n",number,i+1);
            numFound++;
        }//end of if 2
    }//end of for 1
    if(numFound == 0)
        printf("Error: no such item!\n");
    printf("Please select your next choice (select 8 for complete menu)\n");
}//end of if 1
}//end of findNum
/****************************** function clearArray ****************************
 * function name: clearArray
 * input: int **array1,int count1,int delCount
 * output: int delCount
 * operation function: get array1 and its size and clear it (frees all allocated space)
 * returns delcount to keep track of all used spaces for the final free to avoid leaks
 * ***************************************************************************/
int clearArray(int **array1,int count1,int delCount)
{//start of clearArray
/***************** variable declartion ******************/
int i=0;
/********************************************************/
if(delCount < count1)
{//start of if 2
    delCount = count1;
    for (i = delCount ; i >= 0; i--)
        free(array1[i]);
}//end of if 2
else
{//start of else (belongs to if 2)
    for (i = count1 ; i >= 0; i--)
        free(array1[i]);
}//end of else (belongs to if 2)
printf("Queue is clear\n");
printf("Please select your next choice (select 8 for complete menu)\n");
return delCount;
}//end of clearArray
/****************************** function printIntArray ****************************
 * function name: printIntArray
 * input: int **array1,int count1
 * output: void
 * operation function: get array1 and its size and prints it
 * note: (there wasnt an example of printin anywhere so i hope its ok)
 * ***************************************************************************/
void printIntArray(int **array1,int count1)
{//start of printIntArray
/***************** variable declartion ******************/
int i=0,returnVal=0;
/********************************************************/
returnVal = arrayIsEmpty(count1);
if(returnVal == 1)
{//start of if 1
    if(count1 == 0)
        printf("Queue items are: %d\n",*array1[i]);
    else{
    printf("Queue items are: ");
    for (i = count1; i >= 0 ; i--)
    {//start of for (prints the array)
        printf("%d ",*array1[i]);
    }//end of for (prints the array)
    printf("\n");}
    printf("Please select your next choice (select 8 for complete menu)\n");
}//end of if 1
}//end of printIntArray
/****************************** function main6 ****************************
 * function name: main6
 * input: void
 * output: void
 * operation function: main function of mission6 gets prints menue
 * and dynamicly allocate sapace to build the queue based on user input of menu
 * options sends to needed func
 * ***************************************************************************/
int main6()
{//start of main 6
/***************** variable declartion ******************/
char dummy,end=0;
int choice=0,**myQueue = 0,**myQueue2 = 0;
int count1 =-1,size = 2,delCount=0,returnVal=0;
/********************************************************/
printMenu();
//allocate the needed space for myQueue
myQueue =(int**)calloc(size,sizeof(int*));
if (myQueue  == 0)
{//start of if  (checks allocation success)
    freeArrayInt(myQueue,size);
    printf("Error: Insufficient Memory\n");
    return 0;
}//end of if  (checks allocation success)
do
{//start of do
    scanf(" %d",&choice);
    //dummy gets the \n
    scanf("%c",&dummy);
    //reallocate if needed more space for myQueue
    if (count1 == size-1)
    {//start of if 1
        size=size*2;
        myQueue2 = (int**)realloc(myQueue, size*sizeof(int*));
        if (myQueue2 != 0)
            myQueue = myQueue2;
    else
    {//start of else belongs to if 1
        freeArrayInt(myQueue, size);
        printf("Error: Insufficient Memory\n");
    }//end of else belongs to if 1
    }//end of if 1
    switch(choice)
    {//start of switch
    /******************************* case 0 **************************************/
    case 0:
        end = 1;
    break;
    /******************************* case 1 **************************************/
    case 1:
        count1 = addItem(myQueue,count1);
    break;
    /******************************* case 2 **************************************/
    case 2:
        count1 = deleteItem(myQueue,count1);
    break;
    /******************************* case 3 **************************************/
    case 3:
        printIntArray(myQueue,count1);
    break;
    /******************************* case 4 **************************************/
    case 4:
        findMaxItem(myQueue,count1);
    break;
    /******************************* case 5 **************************************/
    case 5:
        findMinItem(myQueue,count1);
    break;
    /******************************* case 6 **************************************/
    case 6:
        findNum(myQueue,count1);
    break;
    /******************************* case 7 **************************************/
    case 7:
        returnVal = arrayIsEmpty(count1);
        if(returnVal == 1)
        {
        delCount=clearArray(myQueue,count1,delCount);
        count1 = -1;
        }
    break;
    /******************************* case 8 **************************************/
    case 8:
        printMenu();
    break;
    /******************************* default *************************************/
    default:
        printf("Error: Unrecognized choice\n");
        printf("Please select your next choice (select 8 for complete menu)\n");
    break;
    }//end of switch
}//end of do
while(end==0);
//check if queue is already clear
if(count1!=-1)
{//start of if(check if queue is already clear)
    //free based on the biggest count(to avoid leaving leaks)
    if (delCount > count1)
        freeArrayInt(myQueue,delCount);
    else
        freeArrayInt(myQueue,count1);
}//end of if (check if queue is already clear)
else
  free(myQueue);
return 0;
}//end of main 6
