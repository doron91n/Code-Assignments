import warnings

import matplotlib.animation as animation
import matplotlib.cbook
import matplotlib.pyplot as plt
from matplotlib.widgets import Button

from Grid import Grid
from StopWatch import StopWatch

# next line - suppress a raised "Toggling axes" warning when changing settings in homeScreen
warnings.filterwarnings("ignore", category=matplotlib.cbook.mplDeprecation)


class CovidModule(object):
    """ __init__ - Initialize the Module with given values
        grid_size - the size to make the grid , creates grid of (grid_size X grid_size)
        healthy_num - initial number of healthy people
        vac_num - initial number of vac people
        sick_num - initial number of sick people
        T_iteration_can_infect - sick cell can infect for only T iterations ,after T the cell will turn to vac
        # need to be::  prob_infect_vac << prob_infect_healthy     smaller then <<
        prob_infect_healthy - the probability to infect healthy cell,  0 < prob < 1
        prob_infect_vac - the probability to infect vac cell,  0 < prob < 1
     """

    def __init__(self, grid_size, healthy_num, vac_num, sick_num, T_iteration_can_infect, prob_infect_healthy,
                 prob_infect_vac):
        self.grid_size = grid_size
        self.healthy_num = healthy_num
        self.vac_num = vac_num
        self.sick_num = sick_num
        self.T_iteration_can_infect = T_iteration_can_infect
        self.prob_infect_healthy = prob_infect_healthy
        self.prob_infect_vac = prob_infect_vac
        self.current_iteration = 0
        self.fig, self.ax = plt.subplots()

        self.grid = Grid(self.grid_size, self.healthy_num, self.vac_num, self.sick_num, self.T_iteration_can_infect,
                         self.prob_infect_healthy,
                         self.prob_infect_vac)
        self.stop_watch = None
        self.iteration_text_box = None
        self.healthy_color_text_box = None
        self.vac_color_text_box = None
        self.sick_color_text_box = None
        self.ani = None
        self.animate()

    ''' update - update the iteration frame before displaying with new data, called each iteration '''

    def update(self, frameNum, img):
        x = self.stop_watch.updatetimer()
        # call grid one Iteration that will infect All and then move all
        self.grid.nextIteration()
        self.current_iteration += 1
        # print("current iteration: "+str(current_iteration))
        self.iteration_text_box.set_text(str(f"Iteration: {self.current_iteration}"))
        self.healthy_color_text_box.set_text(str(f"Healthy = {self.grid.healthy_num}"))
        self.vac_color_text_box.set_text(str(f"Vac = {self.grid.vac_num}"))
        self.sick_color_text_box.set_text(str(f"Sick = {self.grid.sick_num}"))
        # update data
        img.set_data(self.grid.grid_cells_status)
        return img, self.iteration_text_box, self.stop_watch.timer_box, self.healthy_color_text_box,

    ''' animate - creates the covid grid animation to run '''

    def animate(self):
        self.fig.suptitle("Ex1 Covid-19")
        self.fig.set_size_inches(40, 40, forward=True)
        self.ax.axis("off")
        a = self.createPauseWidget()
        b = self.createTimerWidget()
        c = self.createIterationWidget()
        d = self.createColorSchemeWidget()
        self.stop_watch.start()
        img = self.ax.imshow(self.grid.grid_cells_status, interpolation='nearest', aspect='auto')

        self.ani = animation.FuncAnimation(self.fig, self.update, fargs=(img,), frames=10, interval=100, blit=False)
        self.ani.running = True
        plt.show()


    ''' createPauseWidget - creates the pause button widget'''

    def createPauseWidget(self):
        # add the pause button
        def pause(event):
            if self.ani.running:
                button_pause.label.set_text("Play")
                print("pause")
                self.ani.event_source.stop()
                self.stop_watch.pause()
            else:
                button_pause.label.set_text("Pause")
                self.ani.event_source.start()
                print("unpause")
                self.stop_watch.unpause()
            self.ani.running ^= True

        ax_pause_box = self.fig.add_axes([0.01, 0.3, 0.1, 0.075])
        button_pause = Button(ax_pause_box, 'Pause')
        button_pause.on_clicked(pause)
        return button_pause,

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

    ''' createColorSchemeWidget - creates all the empty/healthy/vac/sick widgets , display the color and number for each'''

    def createColorSchemeWidget(self):
        # add the color scheme (purple = empty , blue = healthy , green= vac yellow = sick )
        ax_empty_color_box = self.fig.add_axes([-0.04, 0.8, 0.1, 0.075])
        ax_empty_color_box.axis("off")
        empty_color_text_box = ax_empty_color_box.text(0.5, 0.5, str(" Empty "), ha="left", va="center",
                                                       bbox=dict(boxstyle="square",
                                                                 edgecolor='rebeccapurple',
                                                                 facecolor='rebeccapurple',
                                                                 ))
        ax_healthy_color_box = self.fig.add_axes([-0.04, 0.75, 0.1, 0.075])
        ax_healthy_color_box.axis("off")
        self.healthy_color_text_box = ax_healthy_color_box.text(0.5, 0.5, str(f"Healthy = {self.grid.healthy_num}"),
                                                                ha="left", va="center",
                                                                bbox=dict(boxstyle="square",
                                                                          edgecolor='steelblue',
                                                                          facecolor='steelblue',
                                                                          ))

        ax_vac_color_box = self.fig.add_axes([-0.04, 0.7, 0.1, 0.075])
        ax_vac_color_box.axis("off")
        self.vac_color_text_box = ax_vac_color_box.text(0.5, 0.5, str(f"Vac = {self.grid.vac_num}"), ha="left",
                                                        va="center",
                                                        bbox=dict(boxstyle="square",
                                                                  edgecolor='palegreen',
                                                                  facecolor='palegreen',
                                                                  ))
        ax_sick_color_box = self.fig.add_axes([-0.04, 0.65, 0.1, 0.075])
        ax_sick_color_box.axis("off")
        self.sick_color_text_box = ax_sick_color_box.text(0.5, 0.5, str(f"Sick = {self.grid.sick_num}"), ha="left",
                                                          va="center",
                                                          bbox=dict(boxstyle="square",
                                                                    edgecolor='yellow',
                                                                    facecolor='yellow',
                                                                    ))
        return self.healthy_color_text_box, self.vac_color_text_box, self.sick_color_text_box,
