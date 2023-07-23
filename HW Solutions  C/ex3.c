/******************************************
*Exercise name: ex3
******************************************/
#include <stdio.h>
//functions declarations
void ChooseAssignment();
void MultiplicationTable();
void ScanDivide();
int  Divide(int n,int div1,int div2);
void Fibonacci();
int  FibonacciCaculation(int fn);
void TwoSmallestPositiveNumbers();
void SumOfAllDigits();
//functions define
/**************************************************************************************
* function name: ChooseAssignment
* input:void
* user input: int assignment (valid value 0<=assignment<=5)
* output: void
* operation function: the main function- takes the user back to the assignment choosing screen( my main screen).
* Note: if the given assignment is not valid we will print an error msg and start over
* *************************************************************************************/
void ChooseAssignment()
{//start of ChooseAssignment()
int assignment=0;
char dummy;
    do
    {//start of do
    //user input selects which assignment to run
    printf("Please select the assignment:\n");
    scanf("%d",&assignment);
    //dummy recevies the ‘\n’ char
    scanf("%c", &dummy);
        switch (assignment)
        {//start of switch
/***************** assignment number 0 *****************/
            //if 0 is selected -> exist program
            case 0:
            break;
/***************** assignment number 1 *****************/
            case 1:
            MultiplicationTable();
            break;
/***************** assignment number 2 *****************/
            case 2:
            ScanDivide();
            break;
/***************** assignment number 3 *****************/
            case 3:
            Fibonacci();
            break;
/***************** assignment number 4 *****************/
            case 4:
            TwoSmallestPositiveNumbers();
            break;
/***************** assignment number 5 *****************/
            case 5:
            SumOfAllDigits();
            break;
/*****************      default        ****************/
            default:
                printf("NO SUCH ASSIGNMENT!\n");
                break;
        }//end of switch
    }//end of do
    while(assignment != 0);
    return;
}//end of ChooseAssignment()
/**************************************************************************************
* function name: MultiplicationTable
* input:void
* user input: int num1,num2
* output:  void
* operation function:gets 2 numbers and prints out the Multiplication Table of num1<= numbers <=num2  .
* Note:gets the same result without importance to order of user input.
* *************************************************************************************/
void MultiplicationTable()
{//start of MultiplicationTable()
signed int num1=0,num2=0,temp=0,column=0,row=0;
    printf("Please enter two numbers for the multiplication table:\n");
    scanf("%d %d",&num1,&num2);
    // if num1>num2 switch their places
    if(num1>num2)
    {//start of if
        temp=num1;
        num1=num2;
        num2=temp;
    }//end of if
        //creates and prints the Multiplication Table
        for (row=num1;row<=num2;row++)
        {//start of for number 1
            for (column=num1;column<=num2;column++)
            {//start of for number 2
                printf("%-4d",column*row);
            }//end of for number 2
        printf("\n");
        }//end of for number 1
return;
}//end of MultiplicationTable()
/**************************************************************************************
* function name: ScanDivide
* input:void
* user input: int n,div1,div2
* output:  void
* operation function:gets a number (n) and 2 dividers and forwards them to Divide .
* Note:after Divide is done returns to ChooseAssignment
* *************************************************************************************/
void ScanDivide()
{//start of ScanDivide()
char dummy;
int div1=0,div2=0,n=0;
    //user input-> n,2 dividers
    printf("Please enter n:\n");
    scanf("%d",&n);
    //dummy recevies the ‘\n’ char
    scanf("%c", &dummy);
    printf("Please enter two dividers:\n");
    scanf("%d %d",&div1,&div2);
    //dummy recevies the ‘\n’ char
    scanf("%c", &dummy);
    Divide(n,div1,div2);

return;
}//end of ScanDivide()
/**************************************************************************************
* function name: Divide
* input:int div1,div2,n; char key;
* output:returns 0 when done(stops the function)
* operation function: gets user input from ScanDivide
if key=(a|A) prints all numbers from 1 to n that can be divided by div1 and div 2.
if key=(o|O) prints all numbers from 1 to n that can be divided by div1 or div 2.
* Note:prints error if key is not valid(a|A|O|o) and return to ChooseAssignment
* *************************************************************************************/
int Divide(int n,int div1,int div2)
{//start of Divide()
char key,dummy;
int numbers=0;
    //user input-> key
    printf("Please enter the key:\n");
    scanf("%c",&key);
    //dummy recevies the ‘\n’ char
    scanf("%c", &dummy);
    //checks if key is valid
    if(key=='o'||key=='O'||key=='A'||key=='a')
    {//start of if number 1
        //if key is o or O then prints all 1<numbers<n that can be divided by div1 or div 2
        if(key=='o'||key=='O')
        {//start of if number 2
            for(numbers=1;numbers<=n;numbers++)
                if((numbers%div1==0)||(numbers%div2==0))
                    printf("%d ",numbers);
                    printf("\n");
        }//end of if number 2
        //if key is a or A then prints all 1<numbers<n that can be divided by div1 and div 2
        if(key=='A'||key=='a')
        {//start of if number 3
            for(numbers=1;numbers<=n;numbers++)
                if((numbers%div1==0)&&(numbers%div2==0))
                    printf("%d ",numbers);
                    printf("\n");
        }//end of if number 3
    }//end of if number 1
    else
    //if key is not valid (a|A|O|o) print error and go to ChooseAssignment
    {//start of else
    printf("ERROR IN KEY \n");
    }//end of else
return 0;
}//end of Divide()
/**************************************************************************************
* function name: Fibonacci
* input:void
* user input: gets user input (int n - valid is n>1) that decides how many places the series will have
* output:void
* operation function: user input- n ; if n<1 print error and go to ChooseAssignment()
*  else use FibonacciCaculation to creat the series and print it
* *************************************************************************************/
void Fibonacci()
{//start of Fibonacci()
int n=0,index=1,fn=1;
    //user input -> n the size of the fibonacci series
    printf("Please enter n:\n");
    scanf("%d",&n);
    // if n<1 print error and go to ChooseAssignment
    if(n<1)
        {//start of if
        printf("INPUT ERROR\n");
        return;
        }//end of if
    else
        {//start of else
        //uses FibonacciCaculation on fn until we get to fn in n place and prints the series
        for ( index=1 ; index<= n ; index++ )
            {
            printf("%d ", FibonacciCaculation(fn));
            fn++;
            }
        printf("\n");
        }//end of else
return;
}//end of Fibonacci()
/**************************************************************************************
* function name: FibonacciCaculation
* input: int fn
* output: int fn
* operation function: the function receives int fn from Fibonacci() and creates the fibonacci series up to n place.
* uses the formula: fn=(fn-1)+(fn-2)
* Note:returns the int fn after using the formula to calculate its value based on its place in the series
* *************************************************************************************/
int FibonacciCaculation(int fn)
{//start of FibonacciCaculation(int fn)
    //gets fn and return the new fn based on its old value
    if (fn == 0)
      return 0;
    else if (fn == 1)
      return 1;
    else
        {//start of else
      return (FibonacciCaculation(fn-1) + FibonacciCaculation(fn-2));
      return 0;
        }//end of else
}//end of FibonacciCaculation(int fn)
/**************************************************************************************
* function name: TwoSmallestPositiveNumbers
* input: void
* user input: long signed  int- input
* output: void
* operation function: the function receives a sequence of numbers (-1 marks the end of the sequence)
* the function checks all the numbers and prints the 2 lowest positive among them
* note:the value of positiveMin1 && positiveMin2 are set to the possible max value
* *************************************************************************************/
void TwoSmallestPositiveNumbers()
{//start of TwoSmallestPositiveNumbers()
int i=1,input = 0, positiveMin1 = 2147483647, positiveMin2 = 2147483647;
    printf("Please enter your sequence:\n");
    //-1 marks the end of sequence
    scanf("%d", &input);
    while(input != -1)
    {//start of while number 1
        //puts the lowest positive number in positiveMin1 and the second lowest at positiveMin2
        while ((input < positiveMin1)&&(input > 0))
    		{//start of while number 2
            positiveMin2 = positiveMin1;
            positiveMin1 = input;
    		}//end of while number 2
        //puts the second lowest positive number in positiveMin2 and makes sure that the first and second are not the same number
        while ((input < positiveMin2)&&(input > 0)&&(input != positiveMin1))
    		{//start of while number 3
            positiveMin2 = input;
    		}//end of while number 3
    scanf(" %d", &input);
    }//end of while number 1
printf("%d %d\n", positiveMin1, positiveMin2);
return;
}//end of TwoSmallestPositiveNumbers()
/**************************************************************************************
* function name: SumOfAllDigits
* input: void
* user input: long unsigned int-- num
* output: void
* operation function:function get a number and calculate and prints the sum of its digits
* *************************************************************************************/
void SumOfAllDigits()
{//start of SumOfAllDigits()
char dummy;
long unsigned int num=0,reminder=0,sum=0;
    printf("Please enter your number:\n");
    scanf("%u",&num);
    //dummy recevies the ‘\n’ char
    scanf("%c", &dummy);
    while(num != 0)
    {//start of while
    //num%10 gives only the first digit
    reminder=num%10;
    sum= sum+reminder;
    //num/10 gets rid of the first digit --*when there is only one digit the result is 0
    num=num/10;
    }//end of while
printf("%u \n",sum);
return;
}//end of SumOfAllDigits()
/**************************************************************************************/

int main()
{
ChooseAssignment();
return 0;
}





