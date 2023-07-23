
// this file ex2_1a.js containes a memoized factorial function where F(n)=n!, F(n)=n*F(n-1) ,F(0)=1
// this function uses a cache to save previous calculated factorial result values, and uses them if needed again. 
// I left my consol.log prints commented , if uncommented will provide step by step indication of how F works.
// function F can be called and used by writing F(n) where n belong to N+ (zero and above). 
// run EXAMPLE: the next line will print result of factorial(5),  console.log(" RESULT F(5): ",F(5))

//***NOTE: ALL CODE COMMENTS OF TYPE console.log(...) ARE FOR TESTING, WHEN UNCOMMENTED, WILL SHOW INNER WORKING OF THE FUNCTION

var F = (function() {
  var cache = [1];  // we initialize the cache array where [key n=0 -> value=1]

  function memoize_factorial(n) {
    var result;
    if (n in cache) { // if we find memoize_factorial(n) result in cache return its value at cache[n]
      //console.log("memoize_factorial::: ** FOUND ** n=" + n+"  returning cache[n]="+cache[n]);
      result= cache[n];
    } else {
	    //memoize_factorial(n) result wasnt found at cache calculate its result value and store at cache[n]
      result = n*memoize_factorial(n - 1);
      //console.log("memoize_factorial::: $$ NOT FOUND $$ n=" + n+"  calculating and storing result in cache, result= "+result);
      cache[n] = result;
    }
    return result;
  }
return memoize_factorial;
})();



// in order to run the function please refer to the next 3 commented run examples



// here we have to calculate brute force for factorial(5)
//console.log("%%%%%%%%%%%%% RESULT F(5): ",F(5))    //   F(5) =  120

// here we use the factorial(5) that was saved once factorial(8) have to calculate factorial(6)=6*factorial(5)
//console.log("%%%%%%%%%%%%% RESULT F(8): ",F(8))    //   F(8) = 40320

// here we use the factorial(6) that was saved when we did factorial(8) , note no further calculation needed
//console.log("%%%%%%%%%%%%% RESULT F(6): ",F(6))    //  %%%%%%% RESULT F(6):  720