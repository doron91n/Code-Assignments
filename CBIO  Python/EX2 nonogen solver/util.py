import matplotlib.pyplot as plt
import nonogram
import numpy as np

size = 2560000, 2560000
def printSol(sol, constraints):
    rules, nLines, nColumns, nPoints, nPopulation = constraints
    print(nonogram.Game(nLines,  nColumns, sol.points))

def printFinalSol(sol, constraints):
    rules, nLines, nColumns, nPoints, nPopulation = constraints
    board = np.array(nonogram.Game(nLines,  nColumns, sol.points).board).astype('uint8')*255
    plt.imshow(board, cmap='Greys', interpolation='nearest')
    plt.title("Solution")
    plt.show()

# writeToFILe - appends msg to a given file
def writeToFILe(file_name, msg):
        with open(file_name + ".txt", "a") as myfile:
            s = msg + "\n"
            myfile.write(s)
            myfile.close()

# function that reads the generated report and summrize it
def readReportFile(file_name):
    iteration_start="0"
    current_fitness="0"
    last_fitness="0"
    current_Iteration="0"
    solved=False
    s=file_name+" Report summarize:\n"
    with open(file_name+".txt") as f:
        for line in f: # Iteration = 1 Best-Fitness = -742  Solved? = False
            inner_list = [elt.strip() for elt in line.split('=')] # inner_list = ['Iteration', '2 Best-Fitness', '-420  Solved?', 'False']
            current_Iteration=inner_list[1].strip(" Best-Fitness")
            current_fitness=inner_list[2].strip("  Solved?")
            solved=inner_list[3]
            if current_fitness != last_fitness:
                x = "Best-Fitness = " + last_fitness.__str__() + "  From Iteration = " + iteration_start.__str__() + " Untill Iteration = " + current_Iteration.__str__() + " solved? = " + solved.__str__() + "\n"
                s+=x
                last_fitness=current_fitness
                iteration_start=current_Iteration
            print(f"readReportFile:: current_Iteration={current_Iteration}  current_fitness={current_fitness}  solved={solved}")
        x = "Best-Fitness = " + last_fitness.__str__() + "  From Iteration = " + iteration_start.__str__() + " Untill Iteration = " + current_Iteration.__str__() + " solved? = " + solved.__str__() + "\n"
        s += x
        f.close()
    writeToFILe(file_name+"ReportSummarized",s)

def readRulesFile(file_name):
    line_index = 0
    size = 25
    column_constraints_list = []
    row_constraints_list = []
    with open(file_name) as f:
        for line in f:
            inner_list = [int(elt.strip()) for elt in line.split(' ')]
            if line_index >= size:
                row_constraints_list.append(inner_list)
            else:
                column_constraints_list.append(inner_list)
            line_index += 1
        print(
            f"readConstraintsFile: final column list = {column_constraints_list}\n            final rows list = {row_constraints_list}\n")
        f.close()
        return nonogram.Rules(columns=column_constraints_list, lines=row_constraints_list)

def createConstraints(rules, nPopulation):
    nColumns = len(rules.columns)
    nLines = len(rules.lines)

    nPoints = 0

    # Count total number of points
    for line in rules.lines:
        for rule in line:
            nPoints += rule
    return (rules, nLines, nColumns, nPoints, nPopulation)


def fitness(sol, constraints):
    rules, nLines, nColumns, nPoints, nPopulation = constraints

    # Count how many rules it is following
    count = 0
    game = nonogram.Game(nLines, nColumns, sol)
    board = sol

    # Count in lines in ascending order
    for lineIndex in range(nLines):
        rulesQtt = len(rules.lines[lineIndex])

        columnIndex = 0
        ruleIndex = 0

        while columnIndex < nColumns or ruleIndex < rulesQtt:
            countSegment = 0
            currRule = rules.lines[lineIndex][ruleIndex] if ruleIndex < rulesQtt else 0

            while columnIndex < nColumns and not board[lineIndex * nColumns + columnIndex]:
                columnIndex += 1

            while columnIndex < nColumns and board[lineIndex * nColumns + columnIndex]:
                countSegment += 1
                columnIndex += 1

            count -= abs(countSegment - currRule)
            ruleIndex += 1

    # Count in columns in ascending order
    for columnIndex in range(nColumns):
        rulesQtt = len(rules.columns[columnIndex])

        lineIndex = 0
        ruleIndex = 0

        while lineIndex < nLines or ruleIndex < rulesQtt:
            countSegment = 0
            currRule = rules.columns[columnIndex][ruleIndex] if ruleIndex < rulesQtt else 0

            while lineIndex < nLines and not board[lineIndex * nColumns + columnIndex]:
                lineIndex += 1

            while lineIndex < nLines and board[lineIndex * nColumns + columnIndex]:
                countSegment += 1
                lineIndex += 1

            count -= abs(countSegment - currRule)
            ruleIndex += 1

    return count