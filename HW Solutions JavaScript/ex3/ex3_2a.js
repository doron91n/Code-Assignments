
// This file is ex3_2a.js , made by modifing file script2.js to work with nodejs
// (this solution the script will run in an synchronous manner)
// i.e only after the completion (response) of the previous request, 
// will the next request in order be built and sent (in a fully synchronous manner)

 function ex3_2a() {
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
              loop(i + 1);
            });
        })(0);
  }
// call and use the function ex3_2a
ex3_2a();
// readyState  Holds the status of the XMLHttpRequest.
// 0: request not initialized
// 1: server connection established
// 2: request received
// 3: processing request
// 4: request finished and response is ready