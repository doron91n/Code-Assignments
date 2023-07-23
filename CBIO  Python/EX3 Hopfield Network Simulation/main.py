
from matplotlib import animation
import matplotlib.pyplot as plt

from Utill import readDigitsFile, createALLWeightMatrix


class Gui(object):
    """ __init__ - Initialize the home screen select puzzle
    """

    def __init__(self,empty_bit):
        self.empty_bit=empty_bit
        self.array_all_examples = readDigitsFile(self.empty_bit)  # hold all the number examples matrix from Digits.txt
        #                                  array_ALL= [ [ matrix_0_num_1 ,matrix_0_num_2 ,.. ,matrix_0_num_10],
        #                                               [ matrix_1_num_1 ,matrix_1_num_2 ,.. ,matrix_1_num_10],...
        #                                           ....[ matrix_9_num_1 ,matrix_9_num_2 ,.. ,matrix_9_num_10] ]
        self.array_all_examples_weights=None
        self.current_iteration = 0
        self.percent_of_bits_to_change=10 # the percent of 1 bits to swap location with empty bits
        self.success_percent_threshold=90 # its a success if the hopfield networked learned a number,
        # it will be considered as number learned  if it restored more then success_percent_threshold(90%) of the number of changed examples

        self.current_success_percent=0 # will be calculated each iteration
        self.tried_learning_num="" # the number of matrix (numbers) we try to learn currently
        self.file_name=""
        self.folder_name=""
        self.msg=""
        self.current_matrix_to_display=self.array_all_examples[0][0].matrix






        self.fig, self.ax = plt.subplots()
        self.iteration_text_box = None
        self.msg_text_box = None
        self.ani = None
        self.animate()

    def updateMSG(self):
        x="empty_bit: "+self.empty_bit.__str__()+"\n"
        x+="percent_of_bits_to_change: "+self.percent_of_bits_to_change.__str__()+"%\n"
        x+="success_percent_threshold: "+self.success_percent_threshold.__str__()+"%\n"
        x+="current_success_percent:     "+self.current_success_percent.__str__()+"%\n"
        x+= "tried_learning_num:        " +self.tried_learning_num+"\n"

        self.msg=x



    ''' animate - creates the grid animation to run '''

    def animate(self):
        self.fig.suptitle("Ex3 Hopfield Network")
        self.fig.set_size_inches(6, 6, forward=True)
        self.ax.axis("off")
        c = self.createIterationWidget()
        d = self.createMsgWidget()

        img = self.ax.imshow(self.current_matrix_to_display, cmap='Greys', interpolation='nearest',
                             aspect='auto')
        self.ani = animation.FuncAnimation(self.fig, self.update, fargs=(img,), frames=10, interval=100,
                                               blit=False)
        self.ani.running = True
        plt.subplots_adjust(bottom=0.3)
        plt.show()



    ''' update - update the iteration frame before displaying with new data, called each iteration '''

    def update(self, frameNum, img):
        print("update:::::::")
        # do next iteration
        self.current_iteration+=1
        # calculate the new matrix based on the weighted matrix

        # update the self.current_matrix_to_display to the one we want



        self.updateMSG()
        self.iteration_text_box.set_text(str(f"Iteration: {self.current_iteration}"))
        self.msg_text_box.set_text(self.msg)
        # update data
        img.set_data(self.current_matrix_to_display)
        return img, self.iteration_text_box, self.msg_text_box,




    ''' createIterationWidget - creates the iteration number displaying widget'''

    def createIterationWidget(self):
        # add iteration number displaying box
        ax_iteration_box = self.fig.add_axes([-0.09, 0.6, 0.2, 0.05])
        ax_iteration_box.axis("off")
        self.iteration_text_box = ax_iteration_box.text(0.5, 0.5, str("Iteraion: 0"), ha="left", va="top")
        return self.iteration_text_box,

    def createMsgWidget(self):
        # add msg displaying box
        ax_msg_text_box = self.fig.add_axes([-0.09, 0.3, 0.2, 0.05])
        ax_msg_text_box.axis("off")
        self.msg_text_box = ax_msg_text_box.text(0.5, 0.5, str("MSG: "), ha="left", va="top")
        return self.msg_text_box,




# the code for question A, learn one example from each number, do so in rising order: 0 ,0 1, 0 1 2,... 0 1.. 9
def A():
    c = Gui(0)
    c.folder_name="Results/A/"



def C():
    c = Gui(0)
    c.folder_name="Results/C/"
    c.array_all_examples_weights = createALLWeightMatrix(c.array_all_examples)


if __name__ == '__main__':
   A()