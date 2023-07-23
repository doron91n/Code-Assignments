

// This file is ex3_2b.js , by modifing file script2_xhr.js to work with nodejs
// in this solution the script will run and send url request's to the server in synchronous manner
// only after the completion (response) of the previous request, 
// will the next request in order is build and sent (in full synchronous manner)

 function ex3_2b() {
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
                    // By calling the loop from within onreadystatechange we make sure the requests are sent in syncronized order.
                    // Only after we send the request and the url request response is done is the onreadystatechange function preformed.
                    // In turn we call the loop for the next url in order.
                    loop(i + 1); 
                }
            }
            request.send(); 
        })(0);
    };

// call and use the function ex3_2b
ex3_2b();


// readyState  Holds the status of the XMLHttpRequest.
// 0: request not initialized
// 1: server connection established
// 2: request received
// 3: processing request
// 4: request finished and response is ready
