import matplotlib.pyplot as plt
from matplotlib.widgets import Button, TextBox

from CovidModule import CovidModule


class HomeScreen(object):
    """ __init__ - Initialize the home screen selections to default values
        grid_size - the size to make the grid , creates grid of (grid_size X grid_size)
        healthy_num - initial number of healthy people
        vac_num - initial number of vac people
        sick_num - initial number of sick people
        T_iteration_can_infect - sick cell can infect for only T iterations ,after T the cell will turn to vac
        # need to be::  prob_infect_vac << prob_infect_healthy     smaller then <<
        prob_infect_healthy - the probability to infect healthy cell,  0 < prob < 1
        prob_infect_vac - the probability to infect vac cell,  0 < prob < 1
    """
    def __init__(self):
        self.grid_size = 250
        self.healthy_num = 70
        self.vac_num = 50
        self.sick_num = 60
        self.T_iteration_can_infect = 100
        self.def_prob_infect_healthy = 0.6
        self.prob_infect_healthy = 0.6
        self.def_prob_infect_vac = 0.3
        self.prob_infect_vac = 0.3
        self.error_text_box = None
        self.prob_infect_vac_text_box = None
        self.prob_infect_healthy_text_box = None
        self.fig = plt.gcf()
        # create homeScreen
        self.homeScreen()

    ''' homeScreen - create all home screen widgets'''

    def homeScreen(self):
        self.fig.suptitle("Ex1 Covid-19")
        # create all widgets , save instance for widget to work and display properly
        a = self.createErrorWidget()
        b = self.createGridSizeWidget()
        c = self.createHealtyNumWidget()
        d = self.createVacNumWidget()
        e = self.createSickNumWidget()
        f = self.createIterationNumWidget()
        g = self.createProbInfectHealtyWidget()
        h = self.createProbInfectVacWidget()
        i = self.createStartWidget()
        plt.draw()
        plt.show()

    ''' runAnim - start the CovidModule animation'''

    def runAnim(self, grid_size, healthy_num, vac_num, sick_num, T_iteration_can_infect, prob_infect_healthy,
                prob_infect_vac):
        x = CovidModule(grid_size, healthy_num, vac_num, sick_num, T_iteration_can_infect, prob_infect_healthy,
                        prob_infect_vac)

    ''' displayErrorMSG - shows given error string in error box'''

    def displayErrorMSG(self, error_msg):
        self.error_text_box.set_text(error_msg)

    ''' createErrorWidget - creates the Error Widget for displaying needed errors'''

    def createErrorWidget(self):
        # add error message window
        ax_error_box = self.fig.add_axes([0.4, 0.4, 0.1, 0.075])
        ax_error_box.axis("off")
        self.error_text_box = ax_error_box.text(0.5, 0.5, "", ha="left", va="top")
        return self.error_text_box,

    ''' createGridSizeWidget - creates the GridSize Widget'''

    def createGridSizeWidget(self):
        def submitGridSize(text):
            if text.isdigit() and int(text) > 2:
                self.grid_size = text
            else:
                self.displayErrorMSG("Valid Grid size is round number bigger then 2")
                grid_size_text_box.set_val(str(self.grid_size))

        # add the grid size text box
        ax_grid_size_box = plt.axes([0.3, 0.7, 0.1, 0.075])
        grid_size_text_box = TextBox(ax_grid_size_box, 'Grid Size: ', initial=str(self.grid_size))
        grid_size_text_box.on_submit(submitGridSize)
        return grid_size_text_box,

    ''' createHealtyNumWidget - creates the Healthy num  Widget'''

    def createHealtyNumWidget(self):
        def submitHealthyNum(text):
            if text.isdigit():
                self.healthy_num = text
            else:
                self.displayErrorMSG("Valid Healthy Num is round Natural number")
                healthy_num_text_box.set_val(str(self.healthy_num))

        # add the healthy num text box
        ax_healthy_num_box = plt.axes([0.3, 0.6, 0.1, 0.075])
        healthy_num_text_box = TextBox(ax_healthy_num_box, 'Healthy Num: ', initial=str(self.healthy_num))
        healthy_num_text_box.on_submit(submitHealthyNum)
        return healthy_num_text_box,

    ''' createVacNumWidget - creates the VacNum  Widget'''

    def createVacNumWidget(self):
        def submitVacNum(text):
            if text.isdigit():
                self.vac_num = text
            else:
                self.displayErrorMSG("Valid Vac Num is round Natural number")
                vac_num_text_box.set_val(str(self.vac_num))

        # add the vac num text box
        ax_vac_num_box = plt.axes([0.3, 0.5, 0.1, 0.075])
        vac_num_text_box = TextBox(ax_vac_num_box, 'Vac Num: ', initial=str(self.vac_num))
        vac_num_text_box.on_submit(submitVacNum)
        return vac_num_text_box,

    ''' createSickNumWidget - creates the SickNum  Widget'''

    def createSickNumWidget(self):
        def submitSickNum(text):
            if text.isdigit():
                self.sick_num = text
            else:
                self.displayErrorMSG("Valid Sick Num is round Natural number")
                sick_num_text_box.set_val(str(self.sick_num))

        # add the sick num text box
        ax_sick_num_box = plt.axes([0.3, 0.4, 0.1, 0.075])
        sick_num_text_box = TextBox(ax_sick_num_box, 'Sick Num: ', initial=str(self.sick_num))
        sick_num_text_box.on_submit(submitSickNum)
        return sick_num_text_box,

    ''' createIterationNumWidget - creates the IterationNum  Widget'''

    def createIterationNumWidget(self):
        def submitIterationNum(text):
            if text.isdigit():
                self.T_iteration_can_infect = text
            else:
                self.displayErrorMSG("Valid Iteration Can Infect Num is round Natural number")
                iteration_num_text_box.set_val(str(self.T_iteration_can_infect))

        # add the iteration  until vac num text box
        # sick cell can infect for only T iterations ,after T the cell will turn to vac
        ax_iteration_num_box = plt.axes([0.3, 0.3, 0.1, 0.075])
        iteration_num_text_box = TextBox(ax_iteration_num_box, 'Iterations Can Infect: ',
                                         initial=str(self.T_iteration_can_infect))
        iteration_num_text_box.on_submit(submitIterationNum)
        return iteration_num_text_box,

    ''' createProbInfectHealtyWidget - creates the ProbInfectHealty  Widget'''

    def createProbInfectHealtyWidget(self):
        def submitProbInfectHealty(text):
            try:
                float(text)
                if not text.isdigit():
                    self.prob_infect_healthy = text
                else:
                    self.displayErrorMSG("Valid Probability To Infect Healthy\nis a number between o and 1")
                    self.prob_infect_healthy_text_box.set_val(str(self.prob_infect_healthy))
            except ValueError:
                self.displayErrorMSG("Valid Probability To Infect Healthy\nis a number between o and 1")
                self.prob_infect_healthy_text_box.set_val(str(self.prob_infect_healthy))

        # add the Probability Infect Healthy num text box
        # need to be::  prob_infect_vac << prob_infect_healthy     smaller then <<
        ax_prob_infect_healthy_num_box = plt.axes([0.3, 0.2, 0.1, 0.075])
        self.prob_infect_healthy_text_box = TextBox(ax_prob_infect_healthy_num_box, 'Probability Infect Healthy: ',
                                                    initial=str(self.prob_infect_healthy))
        self.prob_infect_healthy_text_box.on_submit(submitProbInfectHealty)
        return self.prob_infect_healthy_text_box,

    ''' createProbInfectVacWidget - creates the ProbInfectVac  Widget'''

    def createProbInfectVacWidget(self):
        def submitProbInfectVac(text):
            try:
                float(text)
                if not text.isdigit():
                    self.prob_infect_vac = text
                else:
                    self.displayErrorMSG("Valid Probability To Infect Vac\nis a number between o and 1")
                    self.prob_infect_vac_text_box.set_val(str(self.prob_infect_vac))
            except ValueError:
                self.displayErrorMSG("Valid Probability To Infect Vac\nis a number between o and 1")
                self.prob_infect_vac_text_box.set_val(str(self.prob_infect_vac))

        # add the Probability Infect Vac num text box
        ax_prob_infect_vac_num_box = plt.axes([0.3, 0.1, 0.1, 0.075])
        self.prob_infect_vac_text_box = TextBox(ax_prob_infect_vac_num_box, 'Probability Infect Vac: ',
                                                initial=str(self.prob_infect_vac))
        self.prob_infect_vac_text_box.on_submit(submitProbInfectVac)
        return self.prob_infect_vac_text_box,

    ''' checkValues -check for correct values before starting, if all ok return True else False'''

    def checkValues(self):
        #  need to be::  prob_infect_vac << prob_infect_healthy     smaller then <<
        if float(self.prob_infect_healthy) <= float(self.prob_infect_vac):
            self.displayErrorMSG(
                "Probability To Infect Healthy needs to be\n bigger then Probability To Infect Vac")
            self.prob_infect_healthy = self.def_prob_infect_healthy
            self.prob_infect_vac = self.def_prob_infect_vac
            self.prob_infect_vac_text_box.set_val(str(self.prob_infect_vac))
            self.prob_infect_healthy_text_box.set_val(str(self.prob_infect_healthy))
            return False

        if int(self.grid_size) * int(self.grid_size) <= (
                int(self.healthy_num) + int(self.vac_num) + int(self.sick_num)):
            self.displayErrorMSG("Number of people chosen can\nnot be more or equal the grid cells")
            return False
        return True



    ''' createStartWidget - creates the Start button Widget and check for correct values before starting'''

    def createStartWidget(self):
        def start(event):
            if self.checkValues() is True:
                self.runAnim(int(self.grid_size), int(self.healthy_num), int(self.vac_num), int(self.sick_num),
                             int(self.T_iteration_can_infect),
                             float(self.prob_infect_healthy), float(self.prob_infect_vac))
                plt.close(self.fig)

        # add the start button
        ax_start_box = self.fig.add_axes([0.8, 0.5, 0.1, 0.075])
        button_start = Button(ax_start_box, 'Start')
        button_start.on_clicked(start)
        return button_start,
