from random import randrange

import numpy as np


class Grid(object):
    INFECT = 1
    DONT_INFECT = 0
    EMPTY = 0
    HEALTHY = 1
    VAC = 2
    SICK = 3
    MAX_SOLVE_TRIES = 10

    ''' all comments that start with ##### are comments related to code that handles 2 cells wont be at same place  '''

    # need to be::  prob_infect_vac << prob_infect_healthy     smaller then <<

    def __init__(self, grid_size, healthy_num, vac_num, sick_num, T_iteration_can_infect, prob_infect_healthy,
                 prob_infect_vac):
        """Initialize a new `Grid`, but do not build it yet ."""
        self.grid_size = grid_size
        self.healthy_num = healthy_num
        self.vac_num = vac_num
        self.sick_num = sick_num
        self.total_people = self.sick_num + self.vac_num + self.healthy_num
        self.T_iteration_can_infect = T_iteration_can_infect
        # need to be::  prob_infect_vac << prob_infect_healthy     smaller then <<
        self.prob_infect_healthy = prob_infect_healthy
        self.prob_infect_vac = prob_infect_vac
        self.current_iteration = 1
        self.status_array = np.arange(self.total_people)
        self.iterations_left_to_vac_array = np.arange(self.total_people)
        self.location_array = np.arange(self.total_people * 2).reshape(self.total_people, 2)
        self.grid_cells_status = self.buildGrid()  # a 2d grid that each cell is just the cell status(sick\healthy\vac\empty)

    ''' buildGrid -  will randomly create the grid and arrays , 
        status_array = holds each cell status , location_array = holds cell location type (x,y),
        iterations_left_to_vac_array = holds how many iteration left for the cell to be sick
        each array will hold no zeros(empty cells) the array element index will be the cell id'''

    def buildGrid(self):
        current_healthy_num, current_vac_num, current_sick_num, total = 0, 0, 0, 0
        grid = np.zeros(shape=(self.grid_size, self.grid_size), dtype=int)
        # select random empty cell
        while (self.sick_num > current_sick_num) or (self.healthy_num > current_healthy_num) or (
                self.vac_num > current_vac_num):
            column = randrange(self.grid_size)
            row = randrange(self.grid_size)
            ##### next if will make sure we don`t put a new cell on existing one in grid build
            if grid[row, column] == self.EMPTY:
                cell_state = randrange(1, 4)  # 1 - healthy , 2 - vac , 3 - sick
                # add healthy cell
                if cell_state == self.HEALTHY and self.healthy_num > current_healthy_num:
                    grid[row, column] = cell_state
                    self.location_array[total] = [row, column]
                    self.status_array[total] = self.HEALTHY
                    self.iterations_left_to_vac_array[total] = self.EMPTY
                    current_healthy_num += 1
                    total += 1
                # add vac cell
                elif cell_state == self.VAC and self.vac_num > current_vac_num:
                    grid[row, column] = cell_state
                    self.location_array[total] = [row, column]
                    self.status_array[total] = self.VAC
                    self.iterations_left_to_vac_array[total] = self.EMPTY
                    current_vac_num += 1
                    total += 1
                # add sick cell
                elif cell_state == self.SICK and self.sick_num > current_sick_num:
                    grid[row, column] = cell_state
                    self.location_array[total] = [row, column]
                    self.status_array[total] = self.SICK
                    self.iterations_left_to_vac_array[total] = self.T_iteration_can_infect
                    current_sick_num += 1
                    total += 1
        return grid

    ''' nextIteration - handle one iteration , each iteration is infect -> move '''

    def nextIteration(self):
        # first infect all the cells that need to be infected
        self.infectAll()
        # after infecting move all cells , we will try to solve the grid movements up to MAX_TRIES number
        try_num = 0
        solve_result = -1
        result = None
        # we try and solve the grid movement until we get solve status 0 , solved all grid movements
        while solve_result == -1 and try_num < self.MAX_SOLVE_TRIES:  # if we want to limit the solve by number
            # while solve_result == -1 :
            result = self.moveCells()
            solve_result = result[3]
            try_num += 1
        if result is not None:  # update the grid arrays to new solved ones
            self.grid_cells_status = result[1]
            self.status_array = result[0]
            self.location_array = result[2]

        # updates iterations_left_to_vac_array elements ->   element -= 1 (need to update every iteration)
        # every element that is bigger then -1 will change to element_value-1 all that are less then zero will be zero
        self.iterations_left_to_vac_array = np.where(self.iterations_left_to_vac_array > -1,
                                                     self.iterations_left_to_vac_array - 1, 0)
        self.iterations_left_to_vac_array = np.where(self.iterations_left_to_vac_array > -1,
                                                     self.iterations_left_to_vac_array, 0)
        self.current_iteration += 1

    ''' moveCells - handle the grid cells movement
        try_solve_num - the number of times we tried to solve the grid cells movement
        locations_to_move_array - the array of cells we need to handle,if none given use this class self.location_array
        new_status_array - the status array to update, optional variable  , if none given use this class self.status_array
        new_grid_cells_status - the status grid to update, optional variable  , if none given use this class self.grid_cells_status
        new_location_array - the location array to update, optional variable  , if none given use this class self.location_array
         '''

    ##### next is part of how 2 cells wont be at same location, we use a list of cells that need to be moved and iterate
    ##### over it , the cell can be moved only to empty neighbors or stay at place , if it has no options then its added
    ##### to the list of unhandled cells and we call this function untill all are handled
    ##### we send the current solve arrays we need(to this func) and work on them , only after grid is solved correctly we update the saved arrays.
    def moveCells(self, try_solve_num=0, locations_to_move_array=None, new_status_array=None,
                  new_grid_cells_status=None,
                  new_location_array=None, ):
        if try_solve_num > self.MAX_SOLVE_TRIES:  # tried to solve for max solve tries , return -1 status - not solved
            return [new_status_array, new_grid_cells_status, new_location_array, -1]

        # create array of cells locations that need to be moved ,create copy of all the grid arrays and work on them
        if new_status_array is None:
            new_status_array = np.copy(self.status_array)  # updates this result array with every new change
        if new_grid_cells_status is None:
            new_grid_cells_status = np.copy(self.grid_cells_status)  # updates this result array with every new change
        if new_location_array is None:
            new_location_array = np.copy(self.location_array)  # updates this result array with every new change
        if locations_to_move_array is None:
            locations_to_move_array = np.copy(self.location_array)  # the array of locations to iterate over
        locations_left_to_handle_array = np.copy(
            locations_to_move_array)  # the array of locations left to handle,changed during iteration

        for current_location in locations_to_move_array:
            current_index = self.findLocationIndex(current_location, locations_to_move_array)
            # create neighbors lists
            current_neighbors_list = self.neighborsList(current_location)  # get this sick cell neighbours lists
            empty_list, healthy_list, vac_list, sick_list = self.separateNeighborsList(current_neighbors_list,
                                                                                       new_grid_cells_status)
            if empty_list.__len__() != 0:  # cant move , no empty neighbor cells,stay at place or handle later
                # there is space to move to , add the cell current location to the list and use
                # probability of 1/(empty_list_len+1) to choose where to move next
                empty_list.append(current_location)
                empty_list_chosen_index = np.random.randint(empty_list.__len__())
                # after choosing a new location , move and update all local arrays
                new_chosen_location = empty_list[empty_list_chosen_index]
                current_status = new_status_array[current_index]
                # move the status of current moving cell and update the cell moved from to empty
                new_grid_cells_status[current_location[0], current_location[1]] = self.EMPTY
                new_grid_cells_status[new_chosen_location[0], new_chosen_location[1]] = current_status
                # change the location in the location array to the new chosen one
                new_location_array[current_index] = new_chosen_location
                # remove this cell from locations_left_to_handle_array after handling
                locations_left_to_handle_array = np.delete(locations_left_to_handle_array,
                                                           self.findLocationIndex(current_location,
                                                                                  locations_left_to_handle_array),
                                                           axis=0)
        # still have unhandled cells, call again with cells left to move
        if locations_left_to_handle_array.__len__() > 1:
            return self.moveCells(try_solve_num + 1, locations_left_to_handle_array, new_status_array,
                                  new_grid_cells_status,
                                  new_location_array)
        else:  # locations_left_to_handle_array is empty , return this solve try result arrays , return 0 status = solved
            return [new_status_array, new_grid_cells_status, new_location_array, 0]

    '''infect - go over each of the grid  sick cells, then check all his neighbours for healthy and vac cells to infect 
        updates the sick,vac,healthy numbers upon change'''

    def infectAll(self):
        # create an index array of all the sick cells ,
        # the array will be the index of a sick cell in location array,status_array,iteration array
        sick_index_array = self.findStatusIndexes(self.SICK)

        for sick_cell_index in sick_index_array:  # iterate over the sick indexes
            sick_cell_location = self.location_array[sick_cell_index]
            # check if iterations number of being sick (can still infect) is bigger then 0
            if self.iterations_left_to_vac_array[sick_cell_index] > 0:
                neighbors_list = self.neighborsList(sick_cell_location)  # get this sick cell neighbours lists
                empty_list, healthy_list, vac_list, sick_list = self.separateNeighborsList(neighbors_list)
                # infect all vac and healthy neighbors cells with the right probability
                self.infectHealthy(healthy_list)
                self.infectVac(vac_list)

            else:  # iterations until vac are done, turn cell from sick to vac
                self.status_array[sick_cell_index] = self.VAC
                self.grid_cells_status[sick_cell_location[0], sick_cell_location[1]] = self.VAC
                self.sick_num -= 1
                self.vac_num += 1

    ''' infectHealthy - infect all healthy cells at given healthy_list with the infect_healthy_probability
        healthy_list - the list of all healthy cells to infect '''

    def infectHealthy(self, healthy_list):
        infect_healthy_result_index = 0
        infect_healthy_result = np.random.choice([self.INFECT, self.DONT_INFECT], healthy_list.__len__(),
                                                 p=[self.prob_infect_healthy, 1 - self.prob_infect_healthy])
        for result in infect_healthy_result:
            if result == self.INFECT:
                cell_location = healthy_list[infect_healthy_result_index]
                # find index in arrays for the healthy cell location
                location_index = self.findLocationIndex(cell_location)
                if location_index > -1:  # found the right index
                    # update the cell status and  iteration number (can infect ) to default
                    self.grid_cells_status[cell_location[0], cell_location[1]] = self.SICK
                    self.status_array[location_index] = self.SICK
                    self.iterations_left_to_vac_array[location_index] = self.T_iteration_can_infect
                    self.sick_num += 1
                    self.healthy_num -= 1
            infect_healthy_result_index += 1

    ''' infectVac - infect all vac cells at given vac_list with the infect_vac_probability
        vac_list - the list of all vac cells to infect '''

    def infectVac(self, vac_list):
        infect_vac_result_index = 0
        infect_vac_result = np.random.choice([self.INFECT, self.DONT_INFECT], vac_list.__len__(),
                                             p=[self.prob_infect_vac, 1 - self.prob_infect_vac])
        for result in infect_vac_result:
            if result == self.INFECT:
                cell_location = vac_list[infect_vac_result_index]
                # find index in arrays for the vac cell location
                location_index = self.findLocationIndex(cell_location)
                if location_index > -1:  # found the right index
                    # update the cell status and  iteration number (can infect ) to default
                    self.grid_cells_status[cell_location[0], cell_location[1]] = self.SICK
                    self.status_array[location_index] = self.SICK
                    self.iterations_left_to_vac_array[location_index] = self.T_iteration_can_infect
                    self.sick_num += 1
                    self.vac_num -= 1
            infect_vac_result_index += 1

    ''' findLocationIndex - returns the index of given location at given location_array 
        location - the location we want the index of, location is of type (x,y)
        location_array - the array to check , optional variable  , if none given use this class self.location_array
        '''

    def findLocationIndex(self, location, location_array=None):
        if location_array is None:
            location_array = self.location_array
        index = 0
        for current_location in location_array:
            if current_location[0] == location[0] and current_location[1] == location[1]:
                return index
            index += 1
        # didnt find the given location index return -1
        if index >= location_array.__len__():
            return -1

    ''' findStatusIndexes - creates and returns an array that each element is the index of element with the given status
        # example: if we have status=SICK then returns array of all the sick cells indexes.
        status - the status we wanna find,  healthy = 1 \ vac = 2 \ sick = 3 
        status_array- the array to check , optional variable  , if none given use this class self.status_array '''

    def findStatusIndexes(self, status, status_array=None):
        if status_array is None:
            status_array = self.status_array
        x = np.where(status_array == status)
        return x[0]

    ''' separateNeighborsList - receive a list of neighbour cells , (row,column) then separates the list by the cells status
    returns empty_list,healthy_list,vac_list,sick_list  each list contains the cells from received list 
    grid_cells_status - the grid to check status from ,optional variable  , if none given use this class self.grid_cells_status 
    '''

    def separateNeighborsList(self, cells_list, grid_cells_status=None):
        if grid_cells_status is None:
            grid_cells_status = self.grid_cells_status
        empty_list, healthy_list, vac_list, sick_list = [], [], [], []
        # list of (row,column)
        for cell in cells_list:
            current_cell_status = grid_cells_status[cell[0], cell[1]]
            if current_cell_status == self.EMPTY:
                empty_list.append(cell)
            elif current_cell_status == self.HEALTHY:
                healthy_list.append(cell)
            elif current_cell_status == self.VAC:
                vac_list.append(cell)
            elif current_cell_status == self.SICK:
                sick_list.append(cell)
        return empty_list, healthy_list, vac_list, sick_list

    ''' neighborsList - creates and returns a list of tuples (row,column) for the neighbors at this cell location ,
        all neighbors are checked and optimized for warp around'''

    def neighborsList(self, location):
        #  returned list not include the given location itself.
        return [
            self.checkWarpAround(location[0] - 1, location[1] + 1),
            self.checkWarpAround(location[0] + 1, location[1] - 1),
            self.checkWarpAround(location[0] + 1, location[1] + 1),
            self.checkWarpAround(location[0] - 1, location[1] - 1),
            self.checkWarpAround(location[0] - 1, location[1]),
            self.checkWarpAround(location[0] + 1, location[1]),
            self.checkWarpAround(location[0], location[1] + 1),
            self.checkWarpAround(location[0], location[1] - 1)]

    ''' checkWarpAround - optimize the given row and column to be warp around, if either are out of the grid bounds 
        then its changed to a new location inside the grid , returns tuple (row,column)'''

    def checkWarpAround(self, row, column):
        result_row = row
        result_column = column
        if row < 0:
            result_row = self.grid_size - 1
        if row >= self.grid_size:
            result_row = 0
        if column < 0:
            result_column = self.grid_size - 1
        if column >= self.grid_size:
            result_column = 0
        return (result_row, result_column)

    ''' cellString - returns a string that represent a cell at given index
        index - the cell index at location_array and status_array '''

    def cellString(self, index):
        location = self.location_array[index]
        status = self.status_array[index]
        iteration = self.iterations_left_to_vac_array[index]
        x = f"({str(location[0])},{str(location[1])}) status is {status}"
        # print("cellString:: status:"+str(status))
        if status == self.SICK:
            x += " Iteration Left until VAC = " + str(iteration)
        return x

    ''' statusString - returns a string representation of the given cell status '''

    def statusString(self, status_num):
        if status_num == self.EMPTY:
            return "EMPTY"
        if status_num == self.HEALTHY:
            return "HEALTHY"
        if status_num == self.VAC:
            return "VAC"
        if status_num == self.SICK:
            return "SICK"

    ''' printCurrentGridStatus - this function prints the current grid '''

    def printCurrentGridStatus(self):
        print("printCurrentGridStatus:: current healthy_num = " + str(self.healthy_num) + " vac_num = " + str(
            self.vac_num) + "  sick num= " + str(self.sick_num))
        print("printCurrentGridStatus:: array status:" + str(self.status_array))
        print("printCurrentGridStatus:: array location_array:")
        print(str(self.location_array))
        print("printCurrentGridStatus:: iterations_left_to_vac_array status:" + str(self.iterations_left_to_vac_array))
        print("printCurrentGridStatus:: current status grid:")
        print(self.grid_cells_status)
