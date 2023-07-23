

# this class is a helper struct for each player in given list
# input url_ - the player url to rank
# input  final_rank_ - the final rank given to player
# input  prev_rank_ - the prev rank given to player
# input  num_of_neighbors_ - the number of neighbors for the player being ranked [player,neighbor]
# input  neighbors_list - a list of the player and its neighbors in form [ [a],[b],[c]..]


class URLStruct():
    def __init__(self, url_, final_rank_, prev_rank_, num_of_neighbors_, neighbors_list_):
        self.url = url_
        self.final_rank = final_rank_
        self.prev_rank = prev_rank_
        self.num_of_neighbors = num_of_neighbors_
        self.neighbors_list = neighbors_list_
        # print("NEW URLStruct:: url:",self.url," final_rank:",self.final_rank," prev_rank:",self.prev_rank," num_of_neighbors:",self.num_of_neighbors)
        #print("neighbors_list: ", self.neighbors_list)


# this class implements a web page ranker, returns PageRank Score for each player in given list
# input listOfPairs - is a list of lists in the format  [ [a,b], [a,c] ... ] each inner list [X, Y] as a link from X to Y.
# input numIters_  - the number of time we rank the players
# output - The class returns a dict where the keys are URLs and the values are the final score of each URL,
#          e.g., {'https://en.wikipedia.org/wiki/Andy_Ram': 0.1,'https://en.wikipedia.org/wiki/Jonathan_Erlich': 0.05 …}


class Ranker:
    # Constructor
    def __init__(self, listOfPairs_, numIters_):
        self.listOfPairs = listOfPairs_
        self.numIters = numIters_
        self.num_of_total_urls = 0

    # Initialize page ranks to be 𝑟𝑗^(0) =1/𝑁 for every page j
    # At each iteration, compute the rank of each player as
    # 𝑟𝑗^(𝑡+1) = 0.8 * Σ 𝑖→𝑗 (𝑟𝑖^(𝑡))/𝑑𝑖 + 0.2 ⋅ 1/𝑁 + 0.8 Σ 𝑖∈deadEnds (𝑟𝑖^(𝑡))/𝑁
    # final PageRank of each page j is its 𝑟𝑗^(numIters)
    # N - total number of vertex , ri - rank of vertex i , di - number of neighbors of i , i are all the vertex leading to j
    # rank function calculates all the players ranks and returns a dict of type
    #  {'https://en.wikipedia.org/wiki/Andy_Ram': 0.1, 'https://en.wikipedia.org/wiki/Jonathan_Erlich': 0.05 …}

    def rank(self):
        temp_list = {}
        struct_dict = {}
        # build lists of neighbors for each url from pair[0]
        for pair in self.listOfPairs:
            try:
                if pair[0] not in temp_list:
                    temp_list[pair[0]] = [pair[1]]
                else:
                    temp_list[pair[0]].append(pair[1])
            except KeyError:
                pass
        #  struct =  url_, final_rank_=r^(t+1), prev_rank_= r^(t),num_of_neighbors_,neighbors_list_
        for key, entry in temp_list.items():
            self.num_of_total_urls += (1 + len(entry))
        for key, entry in temp_list.items():
            struct_dict[key] = URLStruct(key, (1 / self.num_of_total_urls), (1 / self.num_of_total_urls), len(entry),
                                         entry)

        # recursive calculate of page rank
        # 𝑟𝑗^(𝑡+1) = 0.8 * Σ 𝑖→𝑗 (𝑟𝑖^(𝑡))/𝑑𝑖 + 0.2 ⋅ 1/𝑁 + 0.8 Σ 𝑖∈deadEnds (𝑟𝑖^(𝑡))/𝑁
        a = (1 / self.num_of_total_urls)
        b = (0.2 * a)
        # 𝑟𝑗^(𝑡+1) = 0.8 * Σ 𝑖→𝑗 (𝑟𝑖^(𝑡))/𝑑𝑖 + b + 0.8 Σ 𝑖∈deadEnds (𝑟𝑖^(𝑡))*a
        for i in range(self.numIters):
            # key = url_ value = struct  url_, final_rank_=r^(t+1), prev_rank_= r^(t),num_of_neighbors_,neighbors_list_
            for key, entry in struct_dict.items():
                x = entry.final_rank
                entry.final_rank = 0.8 * (entry.prev_rank / entry.num_of_neighbors) + b + (0.8 * a * entry.prev_rank)
                entry.prev_rank = x
                # print("key=",key," current i:",i,"  entry.prev_rank:", entry.prev_rank,"   entry.final_rank:", entry.final_rank)

        # build return answer dict {'https://en.wikipedia.org/wiki/Andy_Ram': 0.1, 'https://en.wikipedia.org/wiki/Jonathan_Erlich': 0.05 …}
        answer_dict = {}
        for key, entry in struct_dict.items():
            answer_dict[key] = entry.final_rank
        return answer_dict


# tennisRank -   implements a web page ranker, returns PageRank Score for each player in given list
# input listOfPairs - is a list of lists in the format  [ [a,b], [a,c] ... ] each inner list [X, Y] as a link from X to Y.
# input numIters_  - the number of time we rank the players
# output -  returns a dict where the keys are URLs and the values are the final score of each URL,
#          e.g., {'https://en.wikipedia.org/wiki/Andy_Ram': 0.1,'https://en.wikipedia.org/wiki/Jonathan_Erlich': 0.05 …}
def tennisRank(listOfPairs, numIters):
    r = Ranker(listOfPairs, numIters)
    return r.rank()

# a simple main that uses tennisRank
#if __name__ == '__main__':
    #a = [['https://en.wikipedia.org/wiki/Andy_Ram',
    #      'https://en.wikipedia.org/w/index.php?title=Andy_Ram&oldid=993067417'],
    #     ['https://en.wikipedia.org/wiki/Andy_Ram',
    #      'https://en.wikipedia.org/w/index.php?title=Template:ATP_Masters_Series_tournament_doubles_winners&action=edit'],
    #     ['https://en.wikipedia.org/wiki/Andy_Ram',
    #      'https://en.wikipedia.org/w/index.php?title=Template:Australian_Open_men%27s_doubles_champions&action=edit'],
    #     ['https://en.wikipedia.org/wiki/%C3%89douard_Roger-Vasselin',
    #      'https://en.wikipedia.org/w/index.php?title=Template:Top_ten_French_male_doubles_tennis_players&action=edit'],
    #     ['https://en.wikipedia.org/wiki/%C3%89douard_Roger-Vasselin',
    #      'https://en.wikipedia.org/w/index.php?title=Édouard_Roger-Vasselin&oldid=996354602'],
    #     ['https://en.wikipedia.org/wiki/%C3%89douard_Roger-Vasselin',
    #      'https://en.wikipedia.org/wiki/%C5%81ukasz_Kubot'],
    #     ['https://en.wikipedia.org/wiki/%C3%89douard_Roger-Vasselin',
    #      'https://en.wikipedia.org/wiki/2005_French_Open_%E2%80%93_Men%27s_Doubles']]
#print(tennisRank(a, 10))
