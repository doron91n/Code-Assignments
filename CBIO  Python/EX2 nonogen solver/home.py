import matplotlib.pyplot as plt
from matplotlib.widgets import Button, TextBox

from nonogen import Nonogen
from util import readReportFile


class HomeScreen(object):
    """ __init__ - Initialize the home screen select puzzle
    """
    def __init__(self):
        self.fig = plt.gcf()
        self.algorit ="GA"
        self.msg_text_box=None
        # create homeScreen
        self.homeScreen()

    ''' homeScreen - create all home screen widgets'''

    def homeScreen(self):
        self.fig.suptitle("Ex2 Nonogram Genetic Evolution Simulator")
        # create all widgets , save instance for widget to work and display properly
        a=self.createEasyWidget()
        b =self.createMediumWidget()
        c=self.createHardWidget()
        d=self.createALGOGAWidget()
        e=self.createALGOLamarckWidget()
        f=self.createALGODarwinWidget()
        i=self.createMSGWidget()
        plt.draw()
        plt.show()

    ''' runAnim - start the Nonogram animation'''

    def runAnim(self, puzzle_file_name):
        x = Nonogen(puzzle_file_name,self.algorit)
        return 0


    ''' createMSGWidget - creates the MSG lable Widget '''


    def createMSGWidget(self):
        # add  message window
        ax_msg_box = self.fig.add_axes([0.3, 0.8, 0.1, 0.075])
        ax_msg_box.axis("off")
        self.msg_text_box = ax_msg_box.text(0.5, 0.5, "1) press the Algorithm you want to use (GA=DEFALUT)\n2) press the puzzle you want to solve", ha="left", va="top")
        return self.msg_text_box,

    ''' createALGOGAWidget - creates the GA  Algorithm button Widget '''

    def createALGOGAWidget(self):
        def GAAlgo(event):
            self.algorit="GA"

        ax_GAAlgo_box = self.fig.add_axes([0.2, 0.6, 0.25, 0.075])
        button_GA = Button(ax_GAAlgo_box, 'GA - Algorithm')
        button_GA.on_clicked(GAAlgo)
        return button_GA,


    ''' createALGOLamarckWidget - creates the Lamarck  Algorithm button Widget '''

    def createALGOLamarckWidget(self):
        def LamarckAlgo(event):
            self.algorit="Lamarck"

        ax_LamarckAlgo_box = self.fig.add_axes([0.2, 0.5, 0.25, 0.075])
        button_Lamarck = Button(ax_LamarckAlgo_box, 'Lamarck - Algorithm')
        button_Lamarck.on_clicked(LamarckAlgo)
        return button_Lamarck,


    ''' createALGODarwinWidget - creates the Darwin  Algorithm button Widget '''

    def createALGODarwinWidget(self):
        def DarwinAlgo(event):
            self.algorit="Darwin"

        ax_DarwinAlgo_box = self.fig.add_axes([0.2, 0.4, 0.25, 0.075])
        button_Darwin = Button(ax_DarwinAlgo_box, 'Darwin - Algorithm')
        button_Darwin.on_clicked(DarwinAlgo)
        return button_Darwin,



    ''' createStartWidget - creates the easy Flower diffuclty button Widget '''

    def createEasyWidget(self):
        def startEasy(event):
            plt.close(self.fig)
            self.runAnim("flower_easy")

        # add the easy button
        ax_easy_box = self.fig.add_axes([0.5, 0.6, 0.2, 0.075])
        button_easy = Button(ax_easy_box, 'Easy - Flower')
        button_easy.on_clicked(startEasy)
        return button_easy,


    ''' createMediumWidget - creates the medium - FISH diffuclty button Widget '''

    def createMediumWidget(self):
        def startMedium(event):
            plt.close(self.fig)
            self.runAnim("fish_medium")

        # add the Medium button
        ax_medium_box = self.fig.add_axes([0.5, 0.5, 0.2, 0.075])
        button_medium = Button(ax_medium_box, 'Medium - Fish')
        button_medium.on_clicked(startMedium)
        return button_medium,



    ''' createHardWidget - creates the HARD - SHEEP diffuclty button Widget '''

    def createHardWidget(self):
        def startHard(event):
            plt.close(self.fig)
            self.runAnim("sheep_hard")

        # add the HARD button
        ax_hard_box = self.fig.add_axes([0.5, 0.4, 0.2, 0.075])
        button_hard = Button(ax_hard_box, 'Hard - Sheep')
        button_hard.on_clicked(startHard)
        return button_hard,

def report():

    y = Nonogen("flower_easy","Darwin")
    t = Nonogen("sheep_hard","Darwin")

    b = Nonogen("fish_medium","Darwin")
    c = Nonogen("fish_medium","Lamarck")
    z = Nonogen("flower_easy","Lamarck")
    w = Nonogen("sheep_hard","Lamarck")

    a = Nonogen("fish_medium","GA")
    e = Nonogen("sheep_hard","GA")

    x = Nonogen("flower_easy","GA")


if __name__ == '__main__':
    HomeScreen()
