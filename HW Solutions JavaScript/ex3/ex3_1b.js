

// This file is ex3_1b.js , by modifing file script2_xhr.js to work in asynchronous manner
// in this solution , after an html button is clicked, the script will run and begin sending
// url requests to the server without waiting for the completion (response) of the previous request, 
// the next request in order is built and sent (in a fully asynchronous manner)

document.addEventListener("DOMContentLoaded",
  function () {
    document.querySelector("button")
      .addEventListener("click", function () {
        var index = ["https://lemida.biu.ac.il/",
                     "https://ims.gov.il/",
                     "https://www.mizrahi-tefahot.co.il/",
                     "https://www.maariv.co.il/",
                     "https://www.wikipedia.org/"];            
        (function loop(i) {
            if (i>= index.length) {
                return;
            }
            var url = index[i];
            // By moving the creation of the request variable into the loop function, we can make sure that the asynchronous aspect
            // will work properly and we won`t have a situation where one url request will run over the data of another request,
            // leading to undesired and wrong results. 
            var request = new XMLHttpRequest();
            request.open("GET", url, true);
            request.onreadystatechange = function() {
                if(request.readyState === 4 && request.status === 200) {
                    var data = request.responseText;
                    console.log('-->' + i + ' id: ' + data.substring(1,1500));
                    console.log("---------------------------\n")
                }
            }
            request.send(); 
            // By taking the loop call outside of the request.onreadystatechange function and putting it after sending the request, 
            // we can make the request asynchronous - meaning we build the url requests in order and send them in order but
            // receive the request responses in an asynchronous manner. The request's onreadystatechange function will be called
            // based on the response arrival order, regardless of the order the requests were sent in.  
            loop(i + 1);
        })(0);
    });
});



