/******************************************

*Exercise name: úøâéì 2 ex2
******************************************/
#include <stdio.h>
#include <math.h>
int main()
{
unsigned int program=0,grade=0;
float num1=0,num2=0,num3=0,max=0,min=0,average=0;
char rank;
char dummy;
char input1,input2,input3;
float exam1=0,hw1=0,exam2=0,hw2=0,grade1=0,grade2=0;
char name2,name1;
//choose a program(case)to run
printf("Please enter your input:\n");
scanf("%d", &program);
// dummy recevies the ‘\n’ char
scanf("%c", &dummy);

switch(program)
    {
//*****************program number 0 *****************//
//if 0 is chosen then end of program
    case 0:
    return 0;
//*****************program number 1 *****************//
    case 1:
//user input test grade
    printf("Please enter your score:\n");
    scanf("%d",&grade);
// dummy recevies the ‘\n’ char
    scanf("%c", &dummy);
// error if grade is not between 0-100
    if (grade>100||grade<0)
    {
        printf("Error\n");
        return 0;
    }
    else
    {
//ranks the grade A-E
        if (grade>=90&&grade<=100)
        rank='A';
        if (grade>=80&&grade<=89)
        rank='B';
        if (grade>=70&&grade<=79)
        rank='C';
        if (grade>=60&&grade<=69)
        rank='D';
        if (grade<60)
        rank='E';
    }
// prints the grade and its rank
    printf("The rank for %d is: %c \n",grade,rank);
    break;
//*****************program number 2 *****************//
    case 2 :
//user input - 3 numbers
    printf("Please enter three numbers:\n");
    scanf("%f %f %f",&num1, &num2,&num3 );
//dummy recevies the ‘\n’ char
    scanf("%c", &dummy);
//calculate the average of the 3 num
    average = ((num1+num2+num3)/3);
//decides the max and min out of the 3 num
    if (num1>num2)
    {
        max=num1;
        min=num2;
    }

    else
    {
        max=num2;
        min=num1;
    }

    if (max<num3)
        max=num3;
    if (min>num3)
        {
        min=num3;
        }
//prints min max and average
    printf("The minimal value is: %.f\n",min);
    printf("The maximal value is: %.f\n",max);
    printf("The average is: %.2f\n",average);

    break;
//*****************program number 3 *****************//

    case 3:
//user input - 3 char
    printf("Please enter three chars:\n");
    scanf("%c %c %c",&input1, &input2,&input3 );
//dummy recevies the ‘\n’ char
    scanf("%c", &dummy);
//prints the inputs in different variations
    printf("%c\n",input1);
    printf("%c\n%c\n%c\n",input1,input2,input3);
    printf("%c@%c@%c\n",input1,input2,input3);
    printf("%c\n",input3);
    printf("%c#%c#%c\n",input3,input2,input1) ;

    break;
//*****************program number 4 *****************//
    case 4:
//user input - 2 grades and name char for each of the 2 students
    printf("Please enter two course details:\n");
    scanf("%f %f %c",&exam1,&hw1,&name1);
//dummy recevies the ‘\n’ char
    scanf("%c", &dummy);
    scanf("%f %f %c",&exam2,&hw2,&name2);


// error if hw or exam are not between 0-100
    if ((hw1>100||hw1<0)||(exam1>100||exam1<0)||(hw2>100||hw2<0)||(exam2>100||exam2<0))
    {
        printf("Error\n");
        return 0;
    }
    else
    {
//calculate final grade and prints it for each student
    grade1=((8*sqrtf(exam1))+(hw1*0.1));
    grade2=((8*sqrtf(exam2))+(hw2*0.1));

    printf("The final grade of %c is: %.2f\n",name1,grade1);
    printf("The final grade of %c is: %.2f\n",name2,grade2);
    }
    break;
    }

return 0;
}
