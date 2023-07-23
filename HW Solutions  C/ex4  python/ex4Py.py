# ******************************************************************************#
# Student name: Doron Norani
# Student ID: 305419020
# Course Exercise Group: 01
# Exercise name: ex4py
# ******************************************************************************#
def cleanText(text):
    """ this function reads sent text turns all letters to lower case and
        cleans it from all unnecessary spaces tabs \n \r ] [ '  """
    text = " ".join(text.split())
    text = text.lower()
    text = text.strip()
    text = text.replace(", ", ",")
    text = text.replace(" , ", ",")
    text = text.replace(" ,", ",")
    text = text.replace("\n", "")
    text = text.replace("\r", "")
    text = text.replace("[", "")
    text = text.replace("'", "")
    text = text.replace("]", "")
    return text
# ******************************************************************************#
""" this code reads the file movies.txt and builds a dictionary called myMDB
    (his values are actors and keys are the movies)"""
# Define a filename.
filename = "movies.txt"
# define a dictionary called myMDB (his values are actors and keys are the movies).
myMDB = {}
# Open the file as database.
with open(filename) as database:
    # Loop over each line in the file.
    for line in database.readlines():
        # sends each line to cleanText function
        line = cleanText(line)
        # Split the line to parts where each part is separated by "," .
        parts = line.split(",")
        # first word in line is the actor.
        actor = parts[0]
        # all the words after the first(second.. etc) are the movies the actor played in.
        movies = parts[1:]
        # for each movie in movies(for each line).
        for movie in movies:
            # check if movie is already a key in myMDB.
            if movie not in myMDB:
                # add the movie to a set of keys in myMDB.
                myMDB[movie] = set()
            # add the actor of each line as value in myMDB for the movies he played in.
            myMDB[movie].add(actor)
# ******************************************************************************#
def finalPrint(text):
    """this function gets a text and formats it to the right final printout """
    text = text.replace("," , ", " )
    text = text.title()
    return text
# ******************************************************************************#
def SearchByMovie( lookUp):
    """ this function gets the key we want to Search for in the dictionary - myMDB
        and returns the values of the desired key """
    for keys, values in myMDB.items():
        if lookUp in keys:
            return myMDB[lookUp]
# ******************************************************************************#
def setToString(argument):
    """ this func' sorts the argument and changes the argument from set to string """
    # sorting the actors by alphabetic order
    argument = sorted(argument)
    # changes the argument from set to string
    argument = repr(argument)
    # returns the argument after cleanText func' and finalPrint func'
    return finalPrint(cleanText(argument))
# ******************************************************************************#
def menuOption1():
    """ this func' reads two movies and an operator, based on the operator preforms
        (union|intersection|symmetric_difference) for the actors in those two movies """
    # gets the user input and sends it to cleanText function
    movieQuery = cleanText(raw_input())
    # Split the movieQuery to parts where each part is separated by "," .
    part = movieQuery.split(",")
    # first word in movieQuery is the first movie.
    movie1 = part[0]
    # second word in movieQuery is the second movie.
    movie2 = part[1]
    # third word in movieQuery is the operator.
    operator = part[2]
    if (movie1 in myMDB.keys()) and (movie2 in myMDB.keys()):
        if ((operator is '^') or (operator is '|') or (operator is '&')):
            firstMovie = set(SearchByMovie( movie1))
            secondMovie = set(SearchByMovie( movie2))
            # union of all actors in movie1 & movie2
            if operator is '|':
                print "The union actors are:"
                result = firstMovie.union(secondMovie)
                # prints result after sending it to setToString func'
                print setToString(result)
            # intersection of all actors in movie1 & movie2
            if operator is '&':
                result = firstMovie.intersection(secondMovie)
                # checks if the set is empty
                if len(result) == 0:
                    print "There are no actors in this group."
                else:
                    print "The intersection actors are:"
                    # prints result after sending it to setToString func'
                    print setToString(result)
            # symmetric_difference of all actors in movie1 & movie2
            if operator is '^':
                print "The symmetric difference actors are:"
                result = firstMovie.symmetric_difference(secondMovie)
                # prints result after sending it to setToString func'
                print setToString(result)
        else:
            print "Error"
    else:
        print "Error"
    return
# ******************************************************************************#
def menuOption2():
    """ this func' reads actor name  and prints all his co-stars in all the movies he played in """
    # gets the user input and sends it to cleanText func'
    actorQuery = cleanText(raw_input())
    # create a new empty list
    coStars = list()
    for keys, values in myMDB.items():
        # checks for actorQuery in all myMDB values
        if actorQuery in values:
            # creates a list called b with all actors where actorQuery is
            b = list(myMDB[keys])
            # adds all the co-stars of actorQuery to coStars
            coStars += b
    # remove actorQuery from the list of co stars
    while actorQuery in coStars:
        coStars.remove(actorQuery)
    # checks if a is empty (there is no actorQuery in myMDB or he has no co-stars)
    if len(coStars) == 0:
        print "Error"
    else:
        print "The actor's co-stars are:"
        # prints the final list of co-stars after sending it to setToString func'
        print (setToString(set(coStars)))
    return
# ******************************************************************************#
def Menu():
    """ this function is the menu func' prints the menu options and send the user to the
        appropriate func' based on his input """
print "Processing..."
while True:
    print "Please select an option:"
    print "1) Query by movies"
    print "2) Query by actor"
    print "3) Quit"
    # read menu input(number 1|2|3)
    menuInput = int(raw_input())
    # ----------------------------------------------------------#
    if menuInput is 1:
        print "Please enter two movies and an operator(&,|,^) separated with ',':"
        menuOption1()
    # ----------------------------------------------------------#
    elif menuInput is 2:
        print "Please enter an actor:"
        menuOption2()
    # ----------------------------------------------------------#
    elif menuInput is 3:
        exit()
# ******************************************************************************#