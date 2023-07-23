/******************************************
*Exercise name: ex4
******************************************/#ifndef MATRIX_H
#define MATRIX_H
#include <stdio.h>
#define MAX 15
/***************************** function declaration **************************/
void FirstMatrixInput();
void MatrixOperation();
void TempMatrix(int tempmatrix[MAX][MAX],int tempRow ,int tempColumn);
void PrintMatrix(int Matrix[MAX][MAX]);
void SecondMatrixMultiplyInput();
void SumMatrixMultiplyInput(int secondMatrixMultiply[MAX][MAX], int column2);
void SecondMatrixPlusMinusInput(char Operation);
void SumPlusMatrixInput(int secondMatrixPlusMinus[MAX][MAX]);
void SumMinusMatrixInput(int secondMatrixPlusMinus[MAX][MAX]);
void TransposeMatrixInput();
/*****************************************************************************/
#endif
