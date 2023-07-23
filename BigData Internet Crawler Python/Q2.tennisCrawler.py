

from collections import defaultdict
import random
import time
import lxml.html
import requests


# this class implements a web crawler,
# input start_url_ - the start web page url to start crawl from
# input xpaths  - a list of xpaths expressions to crawl by
# output - a list of lists of type [ [a,b], [a,c] ... ] where c,b were found at a by xpaths
class Crawler:
    # Constructor
    def __init__(self, start_url_, xpaths):
        self.x_paths = xpaths
        self.start_url = start_url_
        self.times_crawled = 0
        self.max_times_crawled = 80
        self.all_urls = defaultdict(list)  # Create a dict to store all found urls
        self.unvisited_urls = defaultdict(list)  # Create a dict to store all unvisited urls
        self.visited_url = set()  # Create a set to store all visited urls

    # crawl- this function crawls 3 times in dfs form and once in bfs until we reach max_times_crawled
    # input url - the url entry to crawl
    # input distance_from_start  - the distance from start url
    # input iteration  - the current dfs iteration
    # output - list of lists of type [ [a,ab] ,[a,ac] , [ac,acd]...]
    def crawl(self, url, distance_from_start, iteration):
        while self.times_crawled < self.max_times_crawled:
            if self.times_crawled == self.max_times_crawled:
                break
            if (self.lenDict(self.unvisited_urls) == 0) and (len(self.all_urls) > 0):
                return self.dictTolist(self.all_urls)
            # do 3 dfs steps
            if self.times_crawled == self.max_times_crawled:
                break
            if iteration < 3:
                self.crawl3DFS(url, distance_from_start, iteration)
            # do one bfs step, find the closest unvisited_urls dict entry to start page
            if self.times_crawled == self.max_times_crawled:
                break
            self.crawlBFS(url)
        return self.dictTolist(self.all_urls)

    # crawlBFS- this function crawls in bfs form , the chosen url will be the closest to start url
    # input url - the url entry to crawl
    # output - none , crawl is being called to crawl
    def crawlBFS(self, url):
        try:
            if url not in self.visited_url:
                start_time_sec = time.time()  # used for the 3 sec sleep between page reads
                # find the closest unvisited_urls dict entry to start page
                distance_to_crawl = 1
                unvisited_urls_len = len(self.unvisited_urls)
                next_url_to_crawl = url
                unvisited_list = []
                while distance_to_crawl < unvisited_urls_len:
                    # unvisited_list =  [['a', 'ay'],['a', 'ac'],['a', 'ad']]
                    unvisited_list = self.unvisited_urls[distance_to_crawl]
                    if len(unvisited_list) > 0:
                        j = random.randint(0, len(unvisited_list) - 1)
                        # make sure there is at least 3 sec sleep time between page reads
                        self.checkTime(start_time_sec)
                        # unvisited_list[j][1] = ['a', 'ad'] , next_url_to_crawl = 'ad'
                        next_url_to_crawl = unvisited_list[j][1]
                        if next_url_to_crawl not in self.visited_url:
                            return self.crawl(next_url_to_crawl, distance_to_crawl, 0)
                        else:  # go for next  while iteration, until we get a url we didnt crawl yet
                            continue
                    else:  # unvisited_list is empty go up a lvl
                        distance_to_crawl += 1
        except:
            x = 0

    # crawl3DFS- this function crawls 3 times in dfs form , a->ab->abc->abcd
    # input url - the url entry to crawl
    # input distance_from_start  - the distance from start url
    # input iteration  - the current dfs iteration
    # output - none , crawl/crawlBack is being called to crawl
    def crawl3DFS(self, url, distance_from_start, iteration):
        try:
            if iteration < 3:
                if url not in self.visited_url:
                    start_time_sec = time.time()  # used for the 3 sec sleep between page reads
                    current_url_dict = self.buildUrls(url, distance_from_start + 1)
                    if self.lenDict(current_url_dict) > 0:
                        # add new found urls into {all_urls , unvisited_urls} , add the url into visited
                        self.all_urls = self.mergeDict(self.all_urls, current_url_dict)
                        self.unvisited_urls = self.mergeDict(self.unvisited_urls, current_url_dict)
                        self.visited_url.add(url)
                        # find the next crawl target
                        j = random.randint(0, self.lenDict(current_url_dict) - 1)
                        next_url_to_crawl = current_url_dict[distance_from_start + 1][j][1]
                        self.safeDictDel([url, next_url_to_crawl], self.unvisited_urls)
                        if next_url_to_crawl not in self.visited_url:
                            # make sure there is at least 3 sec sleep time between page reads
                            self.checkTime(start_time_sec)
                            return self.crawl(next_url_to_crawl, distance_from_start + 1, iteration + 1)
                            # a->ab,ac,af  ab->abf        a->ab->abf -> none ->ab-> none -> a->ac
                    else:  # buildUrls returned empty dict , no wiki urls found, we need to take a new crawl from father url
                        return self.crawlBack(url, distance_from_start, iteration)
                else:  # url is in visited, choose another url  , we need to take a new crawl from father url
                    return self.crawlBack(url, distance_from_start + 1, iteration)
            # did 3 iterations of dfs
            return
        except:
            x = 0

    # crawlBack- this function crawls up the chain for given web page url, in case we found dead end while dfs crawling
    # input url - the url entry to crawl
    # input distance_from_start  - the distance from start url
    # input iteration  - the current dfs iteration
    # output - none , crawl is being called to crawl a father url
    def crawlBack(self, url, distance_from_start, iteration):
        try:
            # after crawling at url, child url not found , we search at all_urls for entry [ x , url ] and we crawl from x
            # until iteration reaches 3, if not found url at x we run again and crawl for [y,x] .
            if iteration < 3:
                if distance_from_start > 0:
                    # we get a list of type {0: [['ay', 'aya'],['ay', 'ayb'],['ay', 'ayc'],['ay', 'ayd']],
                    #                        1: [['af', 'afa'],['af', 'afb'],['af', 'afc'],['af', 'afd']] ... }
                    next_list = self.all_urls[distance_from_start - 1]
                    # next_list =  [['ay', 'aya'],['ay', 'ayb'],['ay', 'ayc'],['ay', 'ayd']]
                    for current_list in next_list:
                        # we found ['ay', 'ayb'] where url='ayb' , we find another ['ay', 'ayd'] that was`nt
                        # visited and call call crawl('ayd')
                        if url == current_list[1]:
                            unvisited_list = []
                            distance_to_crawl = distance_from_start - 1
                            while distance_to_crawl > 0:
                                # unvisited_list =  [['ay', 'aya'],['ay', 'ayc'],['ay', 'ayd']]
                                unvisited_list = self.unvisited_urls[distance_to_crawl]
                                if len(unvisited_list) > 0:
                                    j = random.randint(0, len(unvisited_list) - 1)
                                    # unvisited_list[j][1] = ['ay', 'ayd'] , next_url_to_crawl = 'ayd'
                                    return self.crawl(unvisited_list[j][1], distance_to_crawl, iteration)
                                else:  # unvisited_list is empty go up a lvl
                                    distance_to_crawl -= 1
                            break
        except:
            x = 0

    # buildUrls- this function crawls given web page url, and  builds a dict of found urls
    # input url - the url entry to crawl
    # input distance  - the distance from start url
    # output - the dict of type { distance_from_start: [url,url_found_at_url] , ..} with no duplicates
    # each dict entry of type [ key=distance_from_start , value=list of lists of type [start_url,url_found_at_start_url] ]
    def buildUrls(self, url, distance):
        res = requests.get(url)
        self.times_crawled += 1
        doc = lxml.html.fromstring(res.content)
        urls = []
        # use all xpaths to get links from  https://en.wikipedia.org
        for current_xpath in self.x_paths:
            for t in doc.xpath(current_xpath):
                try:
                    current_url = t.attrib['href']
                    if current_url.startswith('https://en.wikipedia.org'):
                        urls.append(current_url)
                    elif current_url.startswith('/wiki/'):
                        urls.append('https://en.wikipedia.org' + current_url)
                except:
                    pass
        # clean duplicates and build the dict of type { distance_from_start: [url,url_found_at_url] , ..}
        clean_url = list(dict.fromkeys(urls))
        urls = []
        for item in clean_url:
            urls.append([url, item])
        final = defaultdict(list)
        final[distance] = urls
        return final

    # dictTolist- this function returns a list from the given dict
    # input d - a dictionary to turn to list
    # output - final , a list of lists build from given dict
    # each dict entry of type [ key=distance_from_start , value=list of lists of type [start_url,url_found_at_start_url] ]
    # the returned list will be list of lists of type [start_url,url_found_at_start_url]
    def dictTolist(self, d):
        final = []
        for value in d.items():
            final.extend(value[1])
            # print(value[1])
        return final

    # lenDict- this function counts and returns the number of lists of type [start_url,url_found_at] in given dict
    # input d - a dictionary to count items length of
    # output - length - the number of lists of type [start_url,url_found_at] in given dict
    # each dict entry of type [ key=distance_from_start , value=list of lists of type [start_url,url_found_at_start_url] ]
    def lenDict(self, d):
        length = 0
        if len(d) == 0:
            return length
        for value in d.items():
            length += len(value[1])
        return length

    # mergeDict- this function safely merges the 2 given dicts (d1,d2) and returns a result dict
    # that contains no duplicates
    # input d1 - a dictionary to merge with d2
    # input d2  - a dictionary to merge with d1
    # output - d , a merged dict from d1,d2 with no duplicates
    # each dict entry of type [ key=distance_from_start , value=list of lists of type [start_url,url_found_at_start_url] ]
    def mergeDict(self, d1, d2):
        # examples for check purposes
        # d1 = {1: [['a11', 'b11'], ['a12', 'b12'], ['a13', 'b13']], 2: [['a21', 'b21'], ['a22', 'b22']]}
        # d2 = {1: [['c21', 'd21'], ['c22', 'd22'], ['c23', 'd23'],['a11', 'b11']]}
        d = {}
        for key in set(list(d1.keys()) + list(d2.keys())):
            k = []
            try:
                k.extend(d1[key])
            except KeyError:
                pass
            try:
                k.extend(d2[key])
            except KeyError:
                pass
            try:
                k.sort()
                k_no_dup = [k[i] for i in range(len(k)) if i == 0 or k[i] != k[i - 1]]
                d.setdefault(key, []).extend(k_no_dup)
            except KeyError:
                pass
        return d

    # checkTime- this function make sure we sleep up to 3 sec between page reads
    # input start_time - the time we started counting time
    # output - no output
    def checkTime(self, start_time):
        # make sure there is at least 3 sec sleep time between page reads
        time_diff = time.time() - start_time
        if time_diff < 3:
            time.sleep((3 - time_diff))

    # safeDictDel- this function safely removes given url_to_delete entry from dict d ,
    # input url_to_delete - the url entry to remove
    # input d  - a dictionary to remove given entry from
    # output - no output, the given dict d is being modified
    # each dict entry of type [ key=distance_from_start , value=list of lists of type [start_url,url_found_at_start_url] ]
    # url_entry is list of type [start_url,url_found_at_start_url]
    def safeDictDel(self, url_to_delete, d):
        if len(d) > 0:
            # create a list of urls to delete from d
            delete_list = []
            for key, val in d.items():
                for curr_list in val:
                    if curr_list == url_to_delete:
                        delete_list.append([key, url_to_delete])
            # delete from d all entries in delete_list
            for i in delete_list:
                del d[i[0]][d[i[0]].index(i[1])]
            # remove empty dict entries
            delete_list = []
            for key, val in d.items():
                if (len(val) == 0):
                    delete_list.append(key)
            for key in delete_list:
                del d[key]


# tennisCrawler- this function implements a tennis web crawler,
# input start_url_ - the start web page url to start crawl from
# input xpaths  - a list of xpaths expressions to crawl by
# output - a list of lists of type [ [a,b], [a,c] ... ] where c,b were found at a by xpaths
def tennisCrawler(url, xpaths):
    c = Crawler(url, xpaths)
    return c.crawl(url, 0, 0)

# a simple main that uses tennisCrawler
#if __name__ == '__main__':
#     xpath2 = "//a[contains(@href, 'https')]"
#     xpath12 = "/html/body/div[@id='content']/div[@id='bodyContent']//table/tbody/tr/td//a[contains(@href,'/wiki')]"
#     x = tennisCrawler("https://en.wikipedia.org/wiki/Andy_Ram", [xpath2, xpath12])
#     print("got final::", x)
