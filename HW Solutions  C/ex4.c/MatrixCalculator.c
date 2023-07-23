/******************************************
*Exercise name: ex4
******************************************/
#include "MatrixCalculator.h"
/************************** Global variables declaration *********************/
char dummy;
int firstMatrix[MAX][MAX];
int row=0,column=0;
/**************************************************************************************
* function name: TempMatrix
* input:int tempmatrix[MAX][MAX],int tempRow ,int tempColumn
* output: void
* operation function:gets a matrix,row,column
 from all other functions and enters it to the first matrix(the main one)
* *************************************************************************************/
void TempMatrix(int tempmatrix[MAX][MAX],int tempRow ,int tempColumn)
{//start of TempMatrix()
/*********** variables declaration **************/
int m=0,n=0;
/************************************************/
row=tempRow;
column=tempColumn;
    for (m = 0; m < tempRow; m++)
    {//start of for 1
        for (n = 0; n < tempColumn; n++)
            firstMatrix[m][n]=tempmatrix[m][n];
    }//end of for 1
return;
}//end of TempMatrix()
/**************************************************************************************
* function name: FirstMatrixInput()
* input:int void
* output: void
* operation function:gets the first matrix(the main one) matrix,row,column
* *************************************************************************************/
void FirstMatrixInput()
{//start of void FirstMatrixInput()
/*********** variables declaration **************/
int m=0,n=0;
/************************************************/
    do
    {//start of do(makes sure the dimensions are valid)
        printf("Please enter first matrix dimensions (row col):\n");
        scanf("%d %d", &row, &column);
        //dummy recevies the ‘\n’ char
        scanf("%c", &dummy);
        //checks the matrix row and column are valid(bigger then 0)
        if((row<1)||(column<1))
        {//start of if 1
            printf("Error\n");
        }//end of if 1
    }//end of do(makes sure the dimensions are valid)
    while((row<1)||(column<1));
    //user enters the first matrix
    printf("Please enter matrix elements(%dx%d):\n",row,column);
    for (m = 0; m < row; m++)
        {
        for (n = 0; n < column; n++)
            scanf("%d", &firstMatrix[m][n]);
            //dummy recevies the ‘\n’ char
            scanf("%c", &dummy);
        }
return;
}//end of void FirstMatrixInput()
/**************************************************************************************
* function name: MatrixOperation()
* input: void
* output: void
* operation function:gets the user desired operation and preforms it by sending
to the right functions
* *************************************************************************************/
void MatrixOperation()
{//start of MatrixOperation
/*********** variables declaration **************/
int end=0;
char Operation;
/************************************************/
 do{//start of do 1 (gets the firstmatrix and the operation)
 FirstMatrixInput();
 end=0;
    do{//start of do 2(gets the operation)
        printf("Please enter operation:(+,-,*,t,c,q)\n");
        scanf(" %c",&Operation);
        //dummy gets the "/n"
        scanf("%c", &dummy);
        switch (Operation)
        {//start of switch (Operation)
        /*************************** case '+' ***************************/
            case '+':
                SecondMatrixPlusMinusInput(Operation);
            break;
        /*************************** case '-' ***************************/
            case '-':
                SecondMatrixPlusMinusInput(Operation);
            break;
        /*************************** case 'q' ***************************/
            case 'q':
                end=2;
            break;
        /*************************** case 'c' ***************************/
            case 'c':
                end=1;
            break;
        /*************************** case 't' ***************************/
            case 't':
                TransposeMatrixInput();
            break;
        /*************************** case '*' ***************************/
            case '*':
                SecondMatrixMultiplyInput();
            break;
        /*************************** default  ***************************/
            default:
                printf("This operation does not exist!\n");
            break;
        /****************************************************************/
        }//end of switch (Operation)
    }//end of do 2 (gets the operation)
    while(end==0);
 }//end of do 1(gets the firstmatrix and the operation)
 while (end==1);
return;
}//end of MatrixOperation()
/**************************************************************************************
* function name: PrintMatrix()
* input:int Matrix[MAX][MAX]
* output: void
* operation function:gets a matrix and prints it
* *************************************************************************************/
void PrintMatrix(int Matrix[MAX][MAX])
{//start of PrintMatrix()
/*********** variables declaration **************/
int m=0,n=0;
/************************************************/
 for(m = 0; m < row; m++)
    {//start of for 4
        for(n = 0 ; n < column; n++)
            printf("%4d", Matrix[m][n]);
            printf("\n");
    }//end of for 4
return;
}//end of PrintMatrix()
/**************************************************************************************
* function name: SecondMatrixMultiplyInput()
* input:void
* output: void
* operation: function gets a second matrix and its column from user and sends it to
* a function that calculates the sum of the first and second matrix with (*) operation
* *************************************************************************************/
void SecondMatrixMultiplyInput()
{//start of SecondMatrixMultiplyInput()
/*********** variables declaration **************/
int secondMatrixMultiply[MAX][MAX];
int column2=0,m2=0, n2=0, row2;
/************************************************/
    //the second matrix row needs to be the same as the first matrix column in order to  multiply them
    row2=column;
    do
    {//start of do (makes sure the column of matrix 2 is valid(bigger then 0))
        printf("Please enter column dimension of second matrix:\n");
        scanf("%d",&column2);
        //dummy recevies the ‘\n’ char
        scanf("%c", &dummy);
        if (column2<1)
            {//start of if
            printf("Error\n");
            }//end of if
    }//end of do (makes sure the column of matrix 2 is valid)
    while(column2<1);
    //user enters the second matrix
    printf("Please enter matrix elements(%dx%d):\n",row2,column2);
    for(m2 = 0; m2 < row2; m2++)
    {//start of for 1
        for(n2 = 0 ; n2 < column2; n2++)
        {//start of for 2
            scanf(" %d", &secondMatrixMultiply[m2][n2]);
            //dummy recevies the ‘\n’ char
            scanf("%c", &dummy);
        }//end of for 2
    }//end of for 1
    SumMatrixMultiplyInput(secondMatrixMultiply, column2);
return ;
}//end of SecondMatrixMultiplyInput()
/**************************************************************************************
* function name: SumMatrixMultiplyInput()
* input:int secondMatrixMultiply[MAX][MAX], int column2
* output: void
* operation: function gets a second matrix and its column from SecondMatrixMultiplyInput()
* calculates the sum of the first and second matrix with (*) operation and prints the result matrix
* *************************************************************************************/
void SumMatrixMultiplyInput(int secondMatrixMultiply[MAX][MAX], int column2)
{// start of void SumMatrixMultiplyInput()
/*********** variables declaration **************/
int matrixMultiply[MAX][MAX];
int sumMatrixMult=0,m3=0,n3=0,i=0;
/************************************************/
    //calculates the sum of the 2 matrix with (*)
    for(m3 = 0; m3 < row; m3++)//size of row is from the first matrix
    {//start of for 1
        for(n3 = 0 ; n3 < column2; n3++)//size of column2 is from the second matrix
        {//start of for 2
            for(i=0;i<column;i++)//size of column is from the first matrix
                {//start of for 3
                sumMatrixMult+=(firstMatrix[m3][i]*secondMatrixMultiply[i][n3]);
                }//end of for 3
            matrixMultiply[m3][n3]=sumMatrixMult;
            sumMatrixMult=0;
        }//end of for 2
    }//end of for 1
    //prints the matrixMultiply after multiplying
    printf("The answer is:\n");
    TempMatrix(matrixMultiply,row,column2);
    PrintMatrix(firstMatrix);
return ;
}//end of void SumMatrixMultiplyInput()
/**************************************************************************************
* function name: TransposeMatrixInput()
* input: void
* output: void
* operation: function gets the  matrix and preforms a transpose on it
*(switches between row and column places) and prints the result matrix
* *************************************************************************************/
void TransposeMatrixInput()
{//start of TransposeMatrixInput()
/*********** variables declaration **************/
int m=0,n=0;
int transposeMatrix[MAX][MAX];
/************************************************/
    for(m = 0; m < column; m++)
    {//start of for 1
        for(n = 0 ; n < row; n++)
        {//start of for 2
        transposeMatrix[m][n]=firstMatrix[n][m];
        }//end of for 2
    }//end of for 1
    TempMatrix(transposeMatrix,column,row);
    printf("The answer is:\n");
    PrintMatrix(firstMatrix);
return;
}//end of TransposeMatrixInput()
/**************************************************************************************
* function name: SecondMatrixPlusMinusInput()
* input: char Operation
* output: void
* operation: function gets the second matrix from user and the operation (+/-)
* and sends the second matrix for a sum matrix based on the operation
*(+ to add the first and second matrix)&(- to subtract the second  from the first matrix)
* *************************************************************************************/
void SecondMatrixPlusMinusInput(char Operation)
{//start of SecondMatrixPlusMinusInput()
/*********** variables declaration **************/
int secondMatrixPlusMinus[MAX][MAX];
int m2=0,n2=0;
/************************************************/
    printf("Please enter matrix elements(%dx%d):\n",row,column);
    for (m2 = 0; m2 < row; m2++)
        {//start of for 1
        for (n2 = 0; n2 < column; n2++)
            scanf("%d", &secondMatrixPlusMinus[m2][n2]);
            //dummy recevies the ‘\n’ char
            scanf("%c", &dummy);
        }//end of for 1
       if(Operation=='+')
        SumPlusMatrixInput(secondMatrixPlusMinus);
        else
        SumMinusMatrixInput(secondMatrixPlusMinus);
return;
}//end of SecondMatrixPlusMinusInput()
/**************************************************************************************
* function name: SumMinusMatrixInput()
* input: int secondMatrixPlusMinus[MAX][MAX]
* output: void
* operation: function gets the second matrix and subtract the second matrix from the first matrix
*  and prints the result matrix
* *************************************************************************************/
void SumMinusMatrixInput(int secondMatrixPlusMinus[MAX][MAX])
{//start of SumMinusMatrix()
/*********** variables declaration **************/
int matrixSumMinus[MAX][MAX];
int m=0, n=0;
/************************************************/
    //calculates and prints the sum of the 2 matrix with (-)
    for(m = 0; m < row; m++)
    {//start of for 1
        for(n = 0 ; n < column; n++)
        {//start of for 2
            matrixSumMinus[m][n]=firstMatrix[m][n]-secondMatrixPlusMinus[m][n];
        }//end of for 2
    }//end of for 1
    printf("The answer is:\n");
    TempMatrix(matrixSumMinus,row,column);
    PrintMatrix(firstMatrix);
return;
}//end of SumMinusMatrix()
/**************************************************************************************
* function name: SumPlusMatrixInput()
* input: int secondMatrixPlusMinus[MAX][MAX]
* output: void
* operation: function gets the second matrix and add the second matrix to the first matrix
*  and prints the result matrix
* *************************************************************************************/
void SumPlusMatrixInput(int secondMatrixPlusMinus[MAX][MAX])
{// start of SumPlusMatrixInput()
/*********** variables declaration **************/
int matrixSumPlus[MAX][MAX];
int m=0, n=0;
/************************************************/
    //calculates and prints the sum of the 2 matrix with (+)
    for(m = 0; m < row; m++)
    {//start of for 1
        for(n = 0 ; n < column; n++)
        {//start of for 2
            matrixSumPlus[m][n]=firstMatrix[m][n]+secondMatrixPlusMinus[m][n];
        }//end of for 2
    }//end of for 1
    printf("The answer is:\n");
    TempMatrix(matrixSumPlus,row,column);
    PrintMatrix(firstMatrix);
return;
}//end of SumPlusMatrixInput()
/*****************************************************************************/

