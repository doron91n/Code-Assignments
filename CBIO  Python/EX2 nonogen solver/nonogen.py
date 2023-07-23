import numpy as np
import sys
from matplotlib import animation
from numpy import random
from random import randrange
import matplotlib.pyplot as plt
from matplotlib.widgets import Button

import nonogram
from StopWatch import StopWatch
from util import readRulesFile, printSol, createConstraints, fitness as evaluateFitness, writeToFILe, readReportFile


class Solution:
    def __init__(self, points, constraints):
        self.points = points
        self.fitness = evaluateFitness(points, constraints)

    ## getter method to get the properties using an object
    def get_fitness(self):
        return self.__fitness

    ## setter method to change the value 'a' using an object
    def set_fitness(self, fitness):
        self.__fitness = fitness


class Nonogen(object):
    """ __init__ - Initialize the home screen select puzzle
    """

    def __init__(self, puzzle_file_name, Algorithm):
        self.found_solution = False

        self.Algorithm = Algorithm  # GA || Darwin || Lamarck
        self.puzzle_file_name = puzzle_file_name
        self.report_name = "ALGORITHM_" + self.Algorithm + "_PUZZLE_" + self.puzzle_file_name
        self.grid_size = 25
        self.current_iteration = 0
        self.max_iteration = 5000 # will run up to max_iteration times
        self.max_countidentity = 1000  # will run up to max_countidentity times where the best solution get the same fitness score

        self.nPopulation = 200
        self.countidentity = 0
        self.prevFitness = 1
        self.rules = readRulesFile('puzzles/' + self.puzzle_file_name + '.txt')
        self.constraints = createConstraints(self.rules, self.nPopulation)

        rules, self.nLines, self.nColumns, nPoints, nPopulation = self.constraints
        self.constraints = rules, self.nLines, self.nColumns, self.nLines * self.nColumns, nPopulation
        # create array with nPopulation solutions
        self.SolutionsArray = self.randomSolutions(self.constraints)

        self.best_solution = None  # will hold the best solution so far

        self.fig, self.ax = plt.subplots()
        self.stop_watch = None
        self.iteration_text_box = None
        self.fitness_text_box = None
        self.button_pause = None
        self.ani = None

        self.animate()

    ''' animate - creates the grid animation to run '''

    def animate(self):
        self.fig.suptitle("Ex2 Nonogram Genetic Evolution Simulator")
        self.fig.set_size_inches(20, 20, forward=True)
        self.ax.axis("off")
        a = self.createPauseWidget()
        b = self.createTimerWidget()
        c = self.createIterationWidget()
        d = self.createFitnessWidget()
        self.stop_watch.start()
        img = self.ax.imshow(self.getBoardSol(self.SolutionsArray[0]), cmap='Greys', interpolation='nearest',
                             aspect='auto')
        if self.Algorithm is "Darwin":
            self.ani = animation.FuncAnimation(self.fig, self.updateDarwin, fargs=(img,), frames=10, interval=100,
                                               blit=False)
        elif self.Algorithm is "Lamarck":
            self.ani = animation.FuncAnimation(self.fig, self.updateLamarck, fargs=(img,), frames=10, interval=100,
                                               blit=False)
        else:  # self.Algorithm is "GA":
            self.ani = animation.FuncAnimation(self.fig, self.updateGA, fargs=(img,), frames=10, interval=100,
                                               blit=False)
        self.ani.running = True
        plt.show()



    ''' updateGA - update the GA ALGORITHM iteration frame before displaying with new data, called each iteration '''

    def updateGA(self, frameNum, img):
        print("updateGA:::::::")
        x = self.stop_watch.updatetimer()
        if not self.found_solution:
            if  not self.converge(self.SolutionsArray, self.constraints):
                self.GAnextIteration()
            else:
                check_result=nonogram.checkSolution(nonogram.Game(self.nLines, self.nColumns, self.best_solution.points), self.rules)
                print("############### updateGA:: check_result for current solution = "+check_result)
                if check_result:
                    self.found_solution = True
                    plt.savefig(self.report_name+'.png')
            self.addBestTOReport()
            if self.max_iteration<self.current_iteration or self.countidentity == self.max_countidentity:
                plt.savefig(self.report_name + '.png')
                readReportFile(self.report_name)
                self.ani.event_source.stop()
                plt.close(self.fig)
        else:
            plt.savefig(self.report_name + '.png')
            readReportFile(self.report_name)
            self.ani.event_source.stop()
            plt.close(self.fig)
        self.iteration_text_box.set_text(str(f"Iteration: {self.current_iteration}"))
        self.fitness_text_box.set_text(str(f"Fitness: {self.best_solution.fitness}"))

        # update data
        img.set_data(self.getBoardSol(self.best_solution))
        return img, self.iteration_text_box, self.stop_watch.timer_box, self.fitness_text_box, self.button_pause,

    ''' updateDarwin - update the Darwin ALGORITHM iteration frame before displaying with new data, called each iteration '''

    def updateDarwin(self, frameNum, img):
        print("updateDarwin:::::::")

        x = self.stop_watch.updatetimer()
        if not self.found_solution:
            if not self.converge(self.SolutionsArray, self.constraints):
                self.DarwinNextIteration()
            else:
                check_result=nonogram.checkSolution(nonogram.Game(self.nLines, self.nColumns, self.best_solution.points), self.rules)
                print("############### updateDarwin:: check_result for current solution = "+check_result)
                if check_result:
                    self.found_solution = True
                    plt.savefig(self.report_name + '.png')
            self.addBestTOReport()
            if self.max_iteration<self.current_iteration or self.countidentity == self.max_countidentity:
                plt.savefig(self.report_name + '.png')
                readReportFile(self.report_name)
                self.ani.event_source.stop()
                plt.close(self.fig)
        else:
            plt.savefig(self.report_name + '.png')
            readReportFile(self.report_name)
            self.ani.event_source.stop()
            plt.close(self.fig)
        self.iteration_text_box.set_text(str(f"Iteration: {self.current_iteration}"))
        self.fitness_text_box.set_text(str(f"Fitness: {self.best_solution.fitness}"))

        # update data
        img.set_data(self.getBoardSol(self.best_solution))
        return img, self.iteration_text_box, self.stop_watch.timer_box, self.fitness_text_box, self.button_pause,

    ''' updateLamarck - update the Lamarck ALGORITHM iteration frame before displaying with new data, called each iteration '''

    def updateLamarck(self, frameNum, img):
        print("updateLamarck:::::::")
        x = self.stop_watch.updatetimer()
        if not self.found_solution:
            if not self.converge(self.SolutionsArray, self.constraints):
                self.LamarckNextIteration()
            else:
                check_result=nonogram.checkSolution(nonogram.Game(self.nLines, self.nColumns, self.best_solution.points), self.rules)
                print("############### updateLamarck:: check_result for current solution = "+check_result)
                if check_result:
                    self.found_solution = True
                    plt.savefig(self.report_name + '.png')
            self.addBestTOReport()
            if self.max_iteration<self.current_iteration or self.countidentity == self.max_countidentity:
                plt.savefig(self.report_name + '.png')
                readReportFile(self.report_name)
                self.ani.event_source.stop()
                plt.close(self.fig)
        else:
            plt.savefig(self.report_name + '.png')
            readReportFile(self.report_name)
            self.ani.event_source.stop()
            plt.close(self.fig)
        self.iteration_text_box.set_text(str(f"Iteration: {self.current_iteration}"))
        self.fitness_text_box.set_text(str(f"Fitness: {self.best_solution.fitness}"))

        # update data
        img.set_data(self.getBoardSol(self.best_solution))
        return img, self.iteration_text_box, self.stop_watch.timer_box, self.fitness_text_box, self.button_pause,

    def GAnextIteration(self):
        # crossover
        PP = self.crossover(self.SolutionsArray, self.constraints)
        # mutation
        PPP = self.mutation(PP, self.constraints)

        self.SolutionsArray = self.select(self.SolutionsArray, PPP, self.constraints)
        self.current_iteration += 1
        print(f"current iteration ={self.current_iteration}")
        # print the best solution
        if self.prevFitness == self.SolutionsArray[0].fitness:
            self.countidentity += 1
        else:
            self.countidentity = 0
        print(f"current best fitness ={self.SolutionsArray[0].fitness}")
        if self.countidentity == self.max_countidentity:
            print("SolutionsArray[0].fitness", self.SolutionsArray[0].fitness)
            self.best_solution = self.best(self.SolutionsArray, self.constraints)
        self.prevFitness = self.SolutionsArray[0].fitness
        printSol(self.SolutionsArray[0], self.constraints)
        self.best_solution = self.best(self.SolutionsArray, self.constraints)

    def LamarckNextIteration(self):
        updatedSolutionsArray = []
        for s in self.SolutionsArray:
            # print("s.pointsArraybefore: ", s.points)
            updatePointsArray = self.optimization(s)
            # print("pointsArrayAfter: ", updatePointsArray)
            updatedSolution = Solution(updatePointsArray, self.constraints)
            # fitnessAfterOptimization = evaluateFitness(updatedSolution, constraints)
            if updatedSolution.fitness > s.fitness:
                updatedSolutionsArray.append(updatedSolution)
            else:
                updatedSolutionsArray.append(s)
            # s.fitness = fitnessAfterOptimization
            # s.points = SolutionForEvaluateFitness.points
        self.SolutionsArray = []
        for i in range(0, len(updatedSolutionsArray)):
            self.SolutionsArray.append(updatedSolutionsArray[i])
        # crossover
        PP = self.crossover(self.SolutionsArray, self.constraints)
        # mutation
        PPP = self.mutation(PP, self.constraints)
        self.SolutionsArray = self.select(self.SolutionsArray, PPP, self.constraints)
        self.current_iteration += 1

        print(f"current iteration ={self.current_iteration}")
        # print the best solution
        if self.prevFitness == self.SolutionsArray[0].fitness:
            self.countidentity += 1
        else:
            self.countidentity = 0
        print(f"current best fitness ={self.SolutionsArray[0].fitness}")
        if self.countidentity == self.max_countidentity:
            print("SolutionsArray[0].fitness", self.SolutionsArray[0].fitness)
            self.best_solution = self.best(self.SolutionsArray, self.constraints)

        self.prevFitness = self.SolutionsArray[0].fitness
        printSol(self.SolutionsArray[0], self.constraints)

        self.best_solution = self.best(self.SolutionsArray, self.constraints)

    def DarwinNextIteration(self):
        for s in self.SolutionsArray:
            # before optimization
            updatepointsArray = self.optimization(s)
            # after optimization
            updatedSolution = Solution(updatepointsArray, self.constraints)
            fitnessOfSolutionAfterOptimization = updatedSolution.fitness
            s.set_fitness(fitnessOfSolutionAfterOptimization)
        # crossover
        PP = self.crossover(self.SolutionsArray, self.constraints)
        # mutation
        PPP = self.mutation(PP, self.constraints)

        self.SolutionsArray = self.select(self.SolutionsArray, PPP, self.constraints)
        self.current_iteration += 1

        print(f"current iteration ={self.current_iteration}")
        # print the best solution
        if self.prevFitness == self.SolutionsArray[0].fitness:
            self.countidentity += 1
        else:
            self.countidentity = 0
        print(f"current best fitness ={self.SolutionsArray[0].fitness}")
        if self.countidentity == self.max_countidentity:
            print("SolutionsArray[0].fitness", self.SolutionsArray[0].fitness)
            self.best_solution = self.best(self.SolutionsArray, self.constraints)

        self.prevFitness = self.SolutionsArray[0].fitness
        printSol(self.SolutionsArray[0], self.constraints)
        self.best_solution = self.best(self.SolutionsArray, self.constraints)


    def optimization(self, Solution):
        counter = 0
        pointsArray = None
        while counter <= 50:
            prevFitness = Solution.fitness
            # optimization for all the Solutions
            length = len(Solution.points)
            # print("Solution.points:", Solution.points)
            index1 = randrange(length)
            index2 = randrange(length)

            temp = Solution.points[index1]
            value1 = Solution.points[index2]
            value2 = temp
            if temp != value1:
                Solution.points[index1] = value1
                Solution.points[index2] = value2

            counter += 1
            pointsArray = Solution.points
        return pointsArray

    # s is array with length nPoints - nPopulation s array
    # SolutionsArray is array of Solution objects
    def randomSolutions(self, constraints):
        rules, nLines, nColumns, nPoints, nPopulation = constraints
        SolutionsArray = []
        for _ in range(nPopulation):
            s = []
            for _ in range(nPoints):
                if random.random() <= 0.5:
                    s += [True]
                else:
                    s += [False]
            #print("s: ", s)
            # create element of Solution with random True/False and constraints array and add to S
            # len(S): nPopulation
            SolutionsArray += [Solution(s, constraints)]
        return SolutionsArray

    def crossover(self, SolutionArray, constraints):
        rules, nLines, nColumns, nPoints, nPopulation = constraints

        G2 = []
        arr1 = SolutionArray
        SolutionArray = sorted(SolutionArray, key=lambda s: (s.fitness, random.random()))
        arr2 = SolutionArray
        n = (nPopulation * (nPopulation + 1)) / 2
        # array of probs (1/n,...,(nPopulation+1)/n)
        prob = [i / n for i in range(1, nPopulation + 1)]

        for _ in range(nPopulation):
            child1Points = []
            child2Points = []
            parent1, parent2 = random.choice(SolutionArray, p=prob, replace=False, size=2)
            for i in range(nPoints):
                if random.random() <= 0.5:
                    child1Points += [parent1.points[i]]
                    child2Points += [parent2.points[i]]
                else:
                    child1Points += [parent2.points[i]]
                    child2Points += [parent1.points[i]]
            G2 += [Solution(child1Points, constraints), Solution(child2Points, constraints)]

        return G2

    # a little change
    def mutation(self, P, constraints):
        rules, nLines, nColumns, nPoints, nPopulation = constraints
        PP = []
        for s in P:
            prob = 0.4 / 100
            if len(sys.argv) > 3:
                prob = float(sys.argv[3])
            newPoints = []
            for p in s.points:
                if random.random() > prob:
                    newPoints += [p]
                else:
                    newPoints += [not p]
            PP += [Solution(newPoints, constraints)]
        return PP

    def select(self, P, PP, constraints):
        rules, nLines, nColumns, nPoints, nPopulation = constraints

        P = sorted(P, key=lambda s: (s.fitness, random.random()), reverse=True)
        PP = sorted(PP, key=lambda s: (s.fitness, random.random()), reverse=True)

        nParents = int(2 * nPopulation / 10) + 1
        nChildren = int(5 * nPopulation / 10) + 1
        nRandom = nPopulation - nChildren - nParents

        bestOnes = P[:nParents] + PP[:nChildren]
        others = P[nParents:] + PP[nChildren:]

        nextP = bestOnes + np.ndarray.tolist(random.choice(others, size=nRandom, replace=False))

        return nextP

    def converge(self, SolutionsArray, constraints):
        rules, nLines, nColumns, nPoints, nPopulation = constraints
        for s in SolutionsArray:
            if s.fitness == 0:
                return True
        for i in range(len(SolutionsArray) - 1):
            if SolutionsArray[i].points != SolutionsArray[i + 1].points:
                return False
        return True

    def best(self, P, constraints):
        print(P)
        rules, nLines, nColumns, nPoints, nPopulation = constraints
        for s in P:
            if s.fitness == 0:
                return s
        return P[0]

    def getBoardSol(self, sol):
        rules, nLines, nColumns, nPoints, nPopulation = self.constraints
        return np.array(nonogram.Game(nLines, nColumns, sol.points).board).astype('uint8') * 255

# addBestTOReport - add each iteration best solution to a file report with the algorithm and puzzle name
    def addBestTOReport(self):
        a = "Iteration = " + self.current_iteration.__str__() + " Best-Fitness = " + self.best_solution.fitness.__str__() + "  Solved? = " + self.found_solution.__str__()
        writeToFILe(self.report_name,a)
    ''' createPauseWidget - creates the pause button widget'''

    def createPauseWidget(self):
        # add the pause button
        def pause(event):
            if self.ani.running:
                self.button_pause.label.set_text("Play")
                print("pause")
                self.ani.event_source.stop()
                self.stop_watch.pause()
            else:
                self.button_pause.label.set_text("Pause")
                self.ani.event_source.start()
                print("unpause")
                self.stop_watch.unpause()
            self.ani.running ^= True

        ax_pause_box = self.fig.add_axes([0.01, 0.3, 0.1, 0.075])
        self.button_pause = Button(ax_pause_box, 'Pause')
        self.button_pause.on_clicked(pause)
        return self.button_pause,

    ''' createTimerWidget - creates the timer number displaying widget'''

    def createTimerWidget(self):
        # add the timer displaying box
        ax_timer_box = self.fig.add_axes([-0.01, 0.5, 0.2, 0.05])
        ax_time_text_box = self.fig.add_axes([-0.09, 0.5, 0.2, 0.05])
        ax_time_text_box.axis("off")
        ax_timer_box.axis("off")
        time_text_box = ax_time_text_box.text(0.5, 0.5, str("Time Elapsed:"), ha="left", va="top")
        timer_text_box = ax_timer_box.text(0.5, 0.5, str("0 : 0"), ha="left", va="top")
        self.stop_watch = StopWatch(timer_text_box)
        return self.stop_watch.timer_box,

    ''' createIterationWidget - creates the iteration number displaying widget'''

    def createIterationWidget(self):
        # add iteration number displaying box
        ax_iteration_box = self.fig.add_axes([-0.09, 0.6, 0.2, 0.05])
        ax_iteration_box.axis("off")
        self.iteration_text_box = ax_iteration_box.text(0.5, 0.5, str("Iteraion: 0"), ha="left", va="top")
        return self.iteration_text_box,

    def createFitnessWidget(self):
        # add fitness number displaying box
        ax_fitness_box = self.fig.add_axes([-0.09, 0.4, 0.2, 0.05])
        ax_fitness_box.axis("off")
        self.fitness_text_box = ax_fitness_box.text(0.5, 0.5, str("Fitness: "), ha="left", va="top")
        return self.fitness_text_box,
