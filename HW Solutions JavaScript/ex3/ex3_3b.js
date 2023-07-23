

// This file is ex3_3b.js , by modifing file ex3_2b.js to work with nodejs
// in this solution the script will run and send url request's to the server in asynchronous manner
// without waiting for the completion (response) of the previous request, 
// the next request in order is build and sent (in full asynchronous manner)




 function ex3_3b() {
        var index = ["https://lemida.biu.ac.il/",
                     "https://ims.gov.il/",
                     "https://www.mizrahi-tefahot.co.il/",
                     "https://www.maariv.co.il/",
                     "https://www.wikipedia.org/"];   
        // Set up a namespace for our XMLHttpRequest
        var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;         
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
    };

// call and use the function ex3_3b
ex3_3b();


// readyState  Holds the status of the XMLHttpRequest.
// 0: request not initialized
// 1: server connection established
// 2: request received
// 3: processing request
// 4: request finished and response is ready
