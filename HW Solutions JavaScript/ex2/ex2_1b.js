


// this file ex2_1b.js containes a memoized fibonacci function where F(n)=F(n-1)+F(n-2), F(0)=0, F(1)=F(2)=1
// this function uses a cache to save previous calculated fibonacci result values, and uses them if needed again. 
// I left my consol.log prints commented , if uncommented will provide step by step indication of how F works.
// function F can be called and used by writing F(n) where n belong to N+ (zero and above). 
// run example: the next line will print result of fibonacci(5),  console.log(" RESULT F(5): ",F(5))
// ***NOTE: ALL CODE COMMENTS OF TYPE console.log(...) ARE FOR TESTING, WHEN UNCOMMENTED, WILL SHOW INNER WORKING OF THE FUNCTION


var F = (function() {
  var cache = [0,1,1];  // we initialize the cache array where [key n=0 -> value=0],[key n=1 -> value=1],[key n=2 -> value=1]

  function memoize_fibonacci(n) {
    var value;
    if (n in cache) { // if we find memoize_fibonacci(n) result in cache return its value at cache[n]
        //console.log("memoize_fibonacci::: ** FOUND ** n=" + n+"  returning cache[n]="+cache[n]);
      	return cache[n];
    } else {
	 // memoize_fibonacci(n) result wasnt found at cache calculate its result value and store at cache[n]
      value = memoize_fibonacci(n - 1) + memoize_fibonacci(n - 2);
      //console.log("memoize_fibonacci::: $$ NOT FOUND $$ n=" + n+"  calculating and storing result in cache, result= "+value);
      cache[n] = value;
    }
    return value;
  }
  return memoize_fibonacci;
})();

// in order to run the function please refer to the next 2 commented run examples
// here we have to calculate brute force for fibonacci(5)
//console.log("%%%%%%%%%%%%% RESULT F(5): ",F(5))    //  %%%%%%% RESULT F(5):  5

// here we use the fibonacci(5) that was saved once fibonacci(8) have to calculate fibonacci(7)=fibonacci(6)+fibonacci(5)
//console.log("%%%%%%%%%%%%% RESULT F(8): ",F(8))    //  %%%%%%% RESULT F(8):  21

// here we use the fibonacci(6) that was saved when we did fibonacci(8) , note no further calculation needed
//console.log("%%%%%%%%%%%%% RESULT F(6): ",F(6))    //  %%%%%%% RESULT F(6):  8


