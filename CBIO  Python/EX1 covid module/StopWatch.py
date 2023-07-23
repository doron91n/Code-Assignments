import math
import time


class StopWatch(object):
    ''' __init__ - creates the timer at given timer_box
        timer_box - the box where the current time will be updated and shown '''

    def __init__(self, timer_box):
        """Initialize a new `Stopwatch`, but do not start timing."""
        self.start_time = None
        self.stop_time = None
        self.paused_at_time = None
        self.total_pause_time = 0
        self.update_time = True
        self.timer_box = timer_box

    ''' start - start the stopwatch start time'''

    def start(self):
        """Start timing."""
        self.start_time = time.monotonic()
        # print("StopWatch::: Start:: current start time = "+str(self.start_time))

    ''' stop - save the time the stopwatch was stopped'''

    def stop(self):
        """Stop timing."""
        print("start clock")
        self.stop_time = time.monotonic()
        # print("StopWatch::: stop:: current stop time = "+str(self.stop_time))

    ''' pause - pauses the stopWatch , saves the time we paused for calculations'''

    def pause(self):
        self.paused_at_time = time.monotonic()
        self.update_time = False
        # print("StopWatch::: pause:: current pause time = "+str(self.paused_at_time))

    ''' unpause - unpause the stopWatch , update total pause time for correct time'''

    def unpause(self):
        self.total_pause_time += round(time.monotonic() - self.paused_at_time)
        self.update_time = True
        # print("StopWatch::: unpause:: current unpause time = "+str(time.monotonic())+ " total paused time is = "+str(   self.total_pause_time ))

    ''' updatetimer - update and display the timer box with correct seconds and minutes passed'''

    def updatetimer(self):
        if self.update_time:
            total_secs = round(time.monotonic() - self.start_time - self.total_pause_time)
            # print("StopWatch::: updateTimer:: total_secs = " + str(
            #    total_secs) + " total paused time is = " + str(self.total_pause_time))
            seconds = total_secs % 60
            minutes = round(math.floor((total_secs / 60) * 1) / 1)
            self.timer_box.set_text(str(minutes) + " : " + str(seconds))
            return self.timer_box,
