import math
import random

import numpy as np
import copy
import decimal


class myMatrix:

    def __init__(self, matrix, num_of_bits, example_num, empty_bit):
        self.matrix = matrix
        self.num_of_bits = num_of_bits  # the number of 1 in the matrix
        self.example_num = example_num  # the number the matrix represent
        self.empty_bit = empty_bit  # 0 or -1
        if empty_bit == -1:
            convertZeroToMinus1(self.matrix)
        #self.weight_matrix = create_W(mat2vec(self.matrix))
        self.weight_matrix = (createWeightMatrix(self.matrix))

    def deepCopy(self):
        return copy.deepcopy(self)


# convert every 0 in given 10X10 matrix to -1
def convertZeroToMinus1(matrix):
    try:
        size = 10
        print(f"convertZeroToMinus1:: got Matrix:\n")
        printMatrix(matrix)
        for row in range(size):
            for column in range(size):
                if matrix[row][column] == 0:
                    matrix[row][column] = -1
        print(f"convertZeroToMinus1:: after swap 0 to -1 Matrix:\n")
        printMatrix(matrix)
    except TypeError as e:
        convertZeroToMinus1(matrix.matrix)


# read the Digits file and create 10 arrays , each array will hold 10 matrix examples for each number 0-9

def readDigitsFile(empty_bit):
    # each number from 0 - 9 have 10 different examples , each array will hold 10 matrix of size 10x10 representation of the number
    file_path = "Digits.txt"
    line_index = 0
    size = 10
    inner_matrix = np.zeros(shape=(size, size), dtype=int)
    array_ALL = np.full((size, size), None)  # array_ALL= [ [ matrix_0_num_1 ,matrix_0_num_2 ,.. ,matrix_0_num_10],
    #                                                   [ matrix_1_num_1 ,matrix_1_num_2 ,.. ,matrix_1_num_10],...
    #                                               ....[ matrix_9_num_1 ,matrix_9_num_2 ,.. ,matrix_9_num_10] ]

    print(f"readDigits:: inner_matrix=\n{inner_matrix}\n   array_ALL=\n{array_ALL} ")
    with open(file_path) as f:
        current_line_count = 0
        inner_matrix_index = 0
        bits_num = 0  # number of 1 in the matrix
        outer_num_index = 0  # all 10 examples for zero will be at  array_ALL[0][0~9 = inner_matrix_index]

        for line in f:
            # print(f"readDigits:: current line_index={line_index} , current_line_count ={current_line_count} , current line={line}\n ")
            current_char_count = 0

            for current_char in line:
                if current_char != "\n" and current_char != " ":
                    inner_matrix[current_line_count][current_char_count] = int(current_char)
                    current_char_count += 1
                    if current_char == "1":
                        bits_num += 1

            current_line_count += 1
            if current_line_count > size:  # new number matrix
                # print( f"readDigits:: current line_index={line_index} , current_line_count ={current_line_count}  is bigger then 10 created new matrix \n")
                # printMatrix(inner_matrix)
                if inner_matrix_index >= size:
                    # print(f"readDigits::inner_matrix_index:{inner_matrix_index} is bigger then 10 outer++ \n")
                    outer_num_index += 1
                    inner_matrix_index = 0
                    bits_num = 0
                # print(f"readDigits::adding new matrix at  array_ALL[outer_num_index][inner_matrix_index] =  array_ALL[{outer_num_index}][{inner_matrix_index}]   \n")
                array_ALL[outer_num_index][inner_matrix_index] = myMatrix(inner_matrix, bits_num, outer_num_index,
                                                                          empty_bit)
                inner_matrix_index += 1
                current_line_count = 0
                inner_matrix = np.zeros(shape=(size, size), dtype=int)

            line_index += 1

        # printAllMatrix(array_ALL)
        f.close()
        return array_ALL


# each cell in this 2D array is of type myMatrix
def printAllMatrix(array_all):
    row = 0
    column = 0
    for inner_num_array in array_all:
        for my_matrix in inner_num_array:
            print(
                f"printAllMatrix:: current number_example=row={row} example number[out of 10 examples](column)= {column}\n")
            print(
                f"printAllMatrix:: num_of_bits={my_matrix.num_of_bits} example number[out of 10 examples]= {my_matrix.example_num}\n")

            printMatrix(my_matrix.matrix)
            column += 1
        column = 0
        row += 1


def printMatrix(matrix):
    try:
        map_modified = np.vectorize(get_color_coded_str)(matrix)
        print("\n".join(["".join(["{}"] * 10)] * 10).format(*[x for y in map_modified.tolist() for x in y]))
    except TypeError as e:
        printMatrix(matrix.matrix)


def get_color_coded_str(i):
    return "\033[4{}m{}\033[0m".format(i + 1, i)


# return a number (rounded down) which is the given percent out of given number ,ex: 10% out of 100 will return 10
def calculatePercent(num, percent):
    return math.floor(num * percent / 100)


# will change percent_to_change of the bits to the other state in given matrix
def matrixRandomChange(myMatrix, percent_to_change):
    try:
        # calculate how many bits we need to change
        cal_percent_bits = calculatePercent(myMatrix.num_of_bits, percent_to_change)
        size = 9
        print(
            f"matrixRandomChange:: got percent_to_change={percent_to_change} matrix have num_of_bits={myMatrix.num_of_bits}   calculated cal_percent_bits={cal_percent_bits}  for matrix:\n")
        printMatrix(myMatrix)

        for cur_bit in range(cal_percent_bits):
            # choose random 0 cell and random 1 cell and swap them
            can_swap = False
            while not can_swap:
                row_0 = random.randint(0, size)
                column_0 = random.randint(0, size)
                row_1 = random.randint(0, size)
                column_1 = random.randint(0, size)
                if myMatrix.matrix[row_0][column_0] == myMatrix.empty_bit and myMatrix.matrix[row_1][column_1] == 1:
                    myMatrix.matrix[row_1][column_1] = myMatrix.empty_bit
                    myMatrix.matrix[row_0][column_0] = 1
                    print(
                        f"matrixRandomChange:: myMatrix.matrix[row_0][column_0]=myMatrix.matrix[{row_0}][{column_0}]={myMatrix.matrix[row_0][column_0]}")
                    print(
                        f"matrixRandomChange:: myMatrix.matrix[row_1][column_1]=myMatrix.matrix[{row_1}][{column_1}]={myMatrix.matrix[row_1][column_1]}")
                    can_swap = True
                    print(
                        f"matrixRandomChange:: after change 1 bit matrix:\n")
                    printMatrix(myMatrix)
        print(
            f"matrixRandomChange:: FINALmatrix:\n")
        printMatrix(myMatrix)
    except TypeError as e:
        matrixRandomChange(myMatrix.matrix, percent_to_change)


# compare source matrix a and dest matrix b, count the number of bits that are different  -- use to check if we are close to solution
def compareMatrix(a, b):
    try:
        diff_count = 0
        size = 10
        for row in range(size):
            for column in range(size):
                if a[row][column] != b[row][column]:
                    diff_count += 1
        print(f"compareMatrix:: a=\n")
        printMatrix(a)
        print(f"compareMatrix:: b=\n")
        printMatrix(b)
        print(f"compareMatrix:: diff_count={diff_count}\n")
        return diff_count

    except TypeError as e:
        return compareMatrix(a.matrix, b.matrix)



def calcRow2(src_weight_matrix,row_to_calc,empty_bit):
    size = len(row_to_calc)
    # calc new row based on original and w matrix
    corrected_row = np.zeros(size).astype(int)
    column_index_list=list(range(0, size))
    print(f"calcRow2:: bf column_index_list={column_index_list}  ")

    #for column in range(size):
    while  len(column_index_list):
        current_column_index = random.choice(range(len(column_index_list)))
        print(f"calcRow2:: before  current_column_index={current_column_index} out of column_index_list={column_index_list}   ")

        column_index_list.pop(current_column_index)
        print(f"calcRow2:: after  current_column_index={current_column_index} out of column_index_list={column_index_list}   ")
        total_sum =0
        src_weight_matrix_column = src_weight_matrix[:,current_column_index]
        print(f"calcRow2:: src_weight_matrix_column={src_weight_matrix_column}  ")

        for i in range(size):
            if i!=current_column_index:
                print(f"calcRow2:: i = {i} src_weight_matrix_column[i]={src_weight_matrix_column[i]}    row_to_calc[i] ={row_to_calc[i]} result={row_to_calc[i]*src_weight_matrix_column[i]}")

                total_sum+=src_weight_matrix_column[i]*row_to_calc[i]
        print(f"calcRow2::   total_sum ={total_sum}")
        if total_sum >= 0:
                total_sum = 1
        else:
            total_sum = empty_bit

        corrected_row[current_column_index]=total_sum
    print(f"calcRow2::   Final corrected_row ={corrected_row}")
    return corrected_row


def calcRow(src_weight_matrix,row_to_calc,current_row_index,empty_bit):
    #print(f"calcRow:: got  row_to_calc=  {row_to_calc} with src_weight_matrix \n{src_weight_matrix}\n" )
    size = len(row_to_calc)
    # give the original_row
    # calc new row based on original and w matrix
    curr_corrected_row = np.zeros(size).astype(int)
    current_column_index = 0
        # print(f" row[{current_row_index}]={row}")
        # for each char in the row , calculate the new value based on the Weight matrix
    for curr_char in row_to_calc:
        total_sum = 0
        for i in range(size):
            x = row_to_calc[i]
            y = src_weight_matrix[current_row_index][i]
            total_sum += (x * y)
            #print(f" curr_char={curr_char} in row = {row_to_calc}   i= {i}")

        z = src_weight_matrix[current_row_index][current_column_index]
        e = row_to_calc[current_column_index]
        total_sum -= (z * e)
        #print(f"  sum={total_sum}")
        if total_sum >= 0:
                total_sum = 1
        else:
            total_sum = empty_bit

        curr_corrected_row[current_column_index] = total_sum
        current_column_index += 1
        #print(  f"correctMatrix::   row num = {current_row_index}  curr_corrected_row={curr_corrected_row}   row_to_calc=(row)={row_to_calc} ")

    #print(f"#####correctMatrix::  Done returning {curr_corrected_row}")
    return curr_corrected_row




# if new_row equal original row we are done and return it
    # else calculate again for the new_row





# using the src_matrix Weight matrix  we try and fix the given wrong_matrix
def correctMatrix(src_weight_matrix, wrong_matrix,empty_bit):
    size = len(wrong_matrix)
    #print(f"correctMatrix:: got wrong_matrix of size={size}=\n")
    #print(wrong_matrix)
    #print(f"correctMatrix:: got src_weight_matrix of size={size}=\n")
    #print(src_weight_matrix)

    # go over each row in wrong_matrix
    corrected_matrix = np.zeros((size, size)).astype(int)

    current_row_index = 0
    for row in wrong_matrix:
        corrected_matrix[current_row_index]= calcRow(src_weight_matrix,row,current_row_index,empty_bit)
        current_row_index += 1

    print(f"Final corrected= \n{corrected_matrix}")
    return corrected_matrix

# recives an array with all examples for all numbers , create the average Weight Matrix for each number
# (made from all 10 examples for the number)
def createALLWeightMatrix(array_all):
    size = len(array_all)
    all_weight=list()
    for current_number_array_index in range(size):
        w = np.zeros((size, size)).astype(int)
        for current_number_example in array_all[current_number_array_index]:
            for row in range(size):
                for column in range(size):
                    #print(f"createALLWeightMatrix:: row={row}  column={column} w[row][column]={w[row][column]} current_number_example.weight_matrix[row][column]={current_number_example.weight_matrix[row][column]} ")
                    w[row][column]=w[row][column]+current_number_example.weight_matrix[row][column]


        for row in range(size):
            for column in range(size):
                w[row][column] = w[row][column]/size
        print(f"createALLWeightMatrix:: final w=\n{w}")
        all_weight.insert(current_number_array_index,w)
    return all_weight


# Create Weight matrix for a single matrix
def createWeightMatrix(matrix):
    try:
        #print(f"createWeightMatrix:: matrix=\n")
        #printMatrix(matrix)
        #print(matrix)
        size = len(matrix)

        w = np.zeros((size, size)).astype(int)
        for row in range(size):
            for column in range(size):
                # print(f"createWeightMatrix:: row={row}   column={column}")
                if row == column:
                    w[row, column] = 0
                else:
                    col_a = matrix[:, row]
                    col_b = matrix[:, column]
                    # print(f"createWeightMatrix:: col_a=matrix[:, row]=matrix[:, {row}]={col_a}   col_b=matrix[:, column]=matrix[:, {column}]={col_b}")
                    diff_sum=0
                    for currr_char in range(size):
                        if col_a[currr_char] == col_b[currr_char]:
                            diff_sum += 1
                        else:
                            diff_sum -= 1
                    w[row, column] = diff_sum
                    w[column, row] = w[row, column]
        # print(f"createWeightMatrix:: Final w=\n {w}")
        return w
    except TypeError as e:
        return createWeightMatrix(matrix.matrix)



def mat2vec(x):
    print(f"mat2vec:: got matrix=\n{x}")
    m = x.shape[0]*x.shape[1]
    #print(f"mat2vec:: m matrix={m}")

    tmp1 = np.zeros(m).astype(int)
   # print(f"mat2vec:: tmp1 matrix={tmp1}")

    c = 0
    for i in range(x.shape[0]):
        for j in range(x.shape[1]):
            #print(f"mat2vec:: i ={i} j={j}  c={c}   tmp1[c] = x[i, j] = {x[i, j]}  ")
            tmp1[c] = x[i, j]
            c += 1
    print(f"mat2vec:: FINAL tmp1 matrix={tmp1}")

    return tmp1


# Create Weight matrix for a single image
def create_W(x):
    # todo delete the condition
    if len(x.shape) != 1:
        print("The input is not vector")
        return
    else:
        w = np.zeros((len(x), len(x))).astype(int)
        for i in range(len(x)):
            for j in range(i, len(x)):
                if i == j:
                    w[i, j] = 0
                else:
                    # todo Check correctness
                    # w[i, j] = (2*x[i]-1)*(2*x[j]-1)
                    w[i, j] = (2*x[i]-1)*(2*x[j]-1)
                    w[j, i] = w[i, j]
    print(f"create_W FINAL =\n{w}")

    return w


def update(w, y_vec, empty_bit,theta=0.5, time=100):
    print(f"update:: w matrix=\n{w}  \n y_vec=\n {y_vec} ")
    y_vec_copy=copy.deepcopy(y_vec)
    for s in range(time):
        m = len(y_vec_copy)

        i = random.randint(0, m-1)

        u = np.dot(w[i][:], y_vec_copy) - theta
        #print(f"update:: s={s}   m(len y)= {m}   i = random 0 --> m-1=    {i}   u={u}")

        if u > 0:
            y_vec_copy[i] = 1
        elif u < 0:
            y_vec_copy[i] = empty_bit
    print(f"update:: FINAL Y =\n {y_vec_copy.reshape((10,10))}")

    return y_vec_copy

def xx():
    array = readDigitsFile(0)
    t = array[1][5].deepCopy()
    matrixRandomChange(t,10)
    size=len(t.matrix)
    calc_matrix=np.zeros((size, size)).astype(int)
    print("&&&&&&&&& changed t is:\n")
    printMatrix(t)
    all_w=createALLWeightMatrix(array)
    #for i in range(size):
        #calc_matrix[i]= calcRow(array[1][5].weight_matrix,t.matrix,i,0)
    calc_matrix = correctMatrix(all_w[1], t.matrix, 0)

    for i in range (10):
        calc_matrix=correctMatrix(array[1][5].weight_matrix,calc_matrix,0)
        print(f"&&&&&&&&& iteration= {i}  calc_matrix t is:\n")
        printMatrix(calc_matrix)




    #x=matrixRandomChange(t,25)

    #g=mat2vec(x)
    #wt=create_W(mat2vec(t.matrix))
    #printMatrix(wt)

    src_weight_matrix = np.zeros((4,4)).astype(int)
    i=1
    for x in range(len(src_weight_matrix)):
        for y in range(len(src_weight_matrix)):
            src_weight_matrix[x][y]=i
            i+=1
    src_weight_matrix.reshape((4,4))
    #src_w_cal=createWeightMatrix(src_weight_matrix)
   # print(f"src_weight_matrix=\n{src_weight_matrix}\n")
    #print(f"src_w_cal=\n{src_w_cal}\n")
    #print(f"src_w_cal  vac=\n{mat2vec(src_w_cal)}\n")

    row_to_calc= np.zeros(3)
    row_to_calc[0]=10
    row_to_calc[1]=20
    row_to_calc[2]=30

   # mat2vec(src_weight_matrix)
    #update(src_weight_matrix,row_to_calc)
    #print(f"src_weight_matrix={src_weight_matrix}")
    #print(f"row_to_calc={row_to_calc}")


    #calcRow2(src_weight_matrix, row_to_calc, 0)

   # x = array[0][0].deepCopy()
    #matrixRandomChange(x, 10)

    #correctMatrix(array[0][0].weight_matrix, x.matrix, 0)
    #calcRow2()
    #calcRow(array[4][0].weight_matrix, x.matrix[4], 4, 0)

    #fixMatrixValues(array[0][0].weight_matrix, x.matrix,0)
    #print(f"XXXX  num=1.0 drop0 = {dropzeros(1.)}")
    # w=np.array([[1,2,3],[4,5,6],[7,8,9]])
    # a=np.array([ [10,11,12],[13,14,15],[16,17,18]])
    # correctMatrix(w,a)



from mpl_toolkits import mplot3d
import numpy as np
import matplotlib.pyplot as plt


def d3():
    fig = plt.figure()
    ax = plt.axes(projection='3d')
    fig.suptitle("Trade Off")

    ax.set_xlabel('Num_Of_Letters')
    ax.set_ylabel('Change_%')
    ax.set_zlabel('Success_%')

    # Data for a three-dimensional line
    zline = np.linspace(0, 15, 1000)
    xline = np.sin(zline)
    yline = np.cos(zline)
    ax.plot3D(xline, yline, zline, 'gray')

    # Data for three-dimensional scattered points
    zdata = 15 * np.random.random(100)
    xdata = np.sin(zdata) + 0.1 * np.random.randn(100)
    ydata = np.cos(zdata) + 0.1 * np.random.randn(100)
    ax.scatter3D(xdata, ydata, zdata, c=zdata, cmap='Greens')

    plt.show()

def plotDataLine(ax,success_precent_array,num_of_letters,color):
    xdata=[num_of_letters,num_of_letters,num_of_letters,num_of_letters,num_of_letters]
    ydata=[10, 15, 20, 25, 30]
    ax.plot(xdata, ydata, success_precent_array, c=color)



def d13():
    fig = plt.figure()
    ax = plt.axes(projection='3d')
    fig.suptitle("Trade Off")

    ax.set_xlabel('Num_Of_Letters')
    ax.set_ylabel('Change_%')
    ax.set_zlabel('Success_%')
    ax.view_init(40, 35)

    ax.set_xlim(0, 10)
    ax.set_xticks([2, 4, 6, 8, 10])
    ax.set_ylim(10, 30)
    ax.set_yticks([10, 15, 20, 25, 30])
    ax.set_zlim(0, 100)
    ax.set_zticks([ 20, 40, 60,80, 100])
    color=['b','g','r','c','m','y','k','g','b','r']
    #color=["(0,0,0,0)"]
    for num_of_letters in range(10):
        success_precent_array=[55,22,57,32,77]
        plotDataLine(ax, success_precent_array, num_of_letters, color[num_of_letters])
    #xdata=[2, 4, 6, 8, 10]
    #xdata=[2,2,2,2,2]

    #ydata=[10, 15, 20, 25, 30]
    #zdata=[40,24,66,75,32]
    #ax.plot(xdata, ydata, zdata, 'gray')
    # Data for a three-dimensional line
    N=5

    #zline = np.linspace(0,100,N)
    #xline = np.linspace( 2,10,N)
    #yline = np.linspace(10,30,N)
    #ax.plot_wireframe(xline, yline, zline, color='black')
    #xline = np.sin(zline)
    #yline = np.cos(zline)
    #ax.plot3D(xline, yline, zline, 'gray')
    #ax.plot(xline, yline, zline, 'gray')

    #plt.plot(x1, y, 'o')

    # Data for three-dimensional scattered points
    #zdata = 15 * np.random.random(100)
    #xdata = np.sin(zdata) + 0.1 * np.random.randn(100)
    #ydata = np.cos(zdata) + 0.1 * np.random.randn(100)
    #ax.scatter3D(xdata, ydata, zdata, c=zdata, cmap='Greens')

    plt.show()


if __name__ == '__main__':
    d13()



