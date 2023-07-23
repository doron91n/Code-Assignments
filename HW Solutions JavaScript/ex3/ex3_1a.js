

// This file is ex3_1a.js , by modifing file script2.js to work in asynchronous manner
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
            $ajaxUtils.sendGetRequest(url, function (request) {
              var data = request.responseText;
              console.log('-->' + i + ' id: ' + data.substring(1,2000));
              console.log("---------------------------\n")
              
            });
            //by moving the i++ outside of the 'request', we tell the function to send out each individual
            //request as it's own individual (asynchronus) request. This results in each request being added
            //to the "event loop" (and thus returned to the main thread) as soon as it finishes running
            //with no need to wait for any other requests to complete first
            loop(i + 1);
        })(0);
  });
});

