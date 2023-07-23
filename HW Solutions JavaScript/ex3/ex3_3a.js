

// This file is ex3_3a.js , made by modifing file ex3_2a.js to work with nodejs
// this solution will run and send url request's to the server in an asynchronous manner
// (i.e without waiting for the completion (response) of the previous request)
// the next request in the order is built and sent immedietly  (in a fully asynchronous manner)

function ex3_3a() {
        // find the current file relative path --- the ex3_ajax_utils.js need to be in the same directory as this file
        var dir_path=require('path').dirname(require.main.filename);
        ajaxUtils = require(dir_path+'/ex3_ajax_utils.js'); 
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
              ajaxUtils.sendGetRequest(url, function (request) {
              var data = request.responseText;
              console.log('-->' + i + ' id: ' + data.substring(1,2000));
              console.log("---------------------------\n")
            });
            // By taking the loop call outside of the sendGetRequest function and putting it after sending the request, 
            // we can make the request asynchronous - meaning we build the url requests in order and send them in order but
            // receive the request responses in an asynchronous manner. The request's call back function will be called
            // based on the response arrival order, regardless of the order the requests were sent in.  
              loop(i + 1);
        })(0);
}  


// call and use the function ex3_3a
ex3_3a();


// readyState  Holds the status of the XMLHttpRequest.
// 0: request not initialized
// 1: server connection established
// 2: request received
// 3: processing request
// 4: request finished and response is ready
