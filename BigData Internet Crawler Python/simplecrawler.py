import requests 
import lxml.html
import random
import time
# url - to start crawling from
# urls - accumulating the discovered URLs during crawling
# test with urls = crawl("https://en.wikipedia.org/wiki/Elizabeth_II", [])
def crawl(url, urls):
    # stop condition - 100 URLs
    if len(urls) == 100:
        return urls
    res = requests.get(url) 
    doc = lxml.html.fromstring(res.content)
    # Results are also appended to a text file
    f = open('urls.txt', 'a+')
    f.write(url + "\n")
    i=0
    # XPath expression: seek links to https address (or at least that contain 'https')
    for t in doc.xpath("//a[contains(@href, 'https')]"):
        current_url = t.attrib['href']
        urls.append(current_url)
        f.write("---- " + current_url + "\n")
        if len(urls) == 100:
            return urls
        # crawl at most 6 URLs per page
        if i> 5:
            break
        i=i+1
    f.close()
    if i > 0:
        while len(urls) > 0:
            # Choose random URL from the discovered ones
            j = random.randint(0, len(urls)-1)
            try:
                time.sleep(1)
                return crawl(urls[j], urls)
            except:
                # error in crawling URL, remove from list
                urls.pop(j)
                pass


