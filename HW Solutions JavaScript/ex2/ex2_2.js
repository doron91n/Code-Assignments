
/*
This file: ex2_2.js contains a Function that will receive a second (pure) Function and will return a memeoized
version of the given (second) Function. note: it is assumed the function has only a single input
input: Func() -any defined, Pure Function , with 1 argument
output: memoized value for said input function
run example: console.log("ggg(5)="+ggg(5));
to use memoize with some_function use like: const var_name = memoize(some_function); and call it like: var_name(arguments);
***NOTE: ALL CODE COMMENTS OF TYPE console.log(...) ARE FOR TESTING, WHEN UNCOMMENTED, WILL SHOW INNER WORKING OF THE FUNCTION

*/

//receives a function with one argument
function memoize(func) {
  var cache = {};
  return function() {
    //we assign each function a unique key value (unique for any PURE Function) ,according to the function AND it's given arguement/s.
    var key = JSON.stringify(arguments);
    //if we already have this function (with the same arguement) cached, we return the cached value.
    if(cache[key]) {
      //console.log("memoize::: ** FOUND ** key=" + key+"  returning cache[key]="+cache[key]);
      return cache[key];
    }
    //if we don't have the function cached then we run function as normal, and cache the final return value
    else {
      var val = func.apply(this, arguments);
      //console.log("memoize::: $$ NOT FOUND $$ key=" + key+"  calculating and storing result in cache value:"+val);
      cache[key] = val;
      return val;
    }
  };
}



/*
//  TEST EXAMPLE NUMBER 1:
//  function name: ggg 
//  input: integer value of x
//  output: the value calculated for the given x , ggg(x)= x+ggg(x-1), ggg(0)=1
//  to run the test simply remove the / * and the * / on lines 40 and 77

const ggg = memoize(
  (x) => {
    if (x === 0) {
      result= 1;
    }
    else {
      result= x + ggg(x - 1);
    }
    console.log("ggg(x)::: x = "+x+" returning x + ggg(x - 1) = "+result);
    return result;
  }
);


//the first time we will need to calculate by brute force
console.log("ggg(5)="+ggg(5)); // = 16

//from then on we can use previously cached calculations to speed up the process
//(gets the value of ggg(5) from the cache and then adds 6)
console.log("ggg(6)="+ggg(6)); // = 22

//and so on: (gets the value of ggg(6) from the cache and then adds 7)
console.log("ggg(7)="+ggg(7)); // = 29

//since we skipped a few we still need to do some serious calcualtions.  
//But we at least saved all calculations up until ggg (7)
console.log("ggg(15)="+ggg(15)); // = 121

//if we go back to something we already calculated, we don't need any calculations. The value is already cached
console.log("ggg(10)="+ggg(10)); // = 56
*/


/*
// TEST EXAMPLE NUMBER 2:
// just for testing- I used a factorial calculating function (to test computing speed at very high values)
// to run the test simply remove the / * and the * / on lines 80 and 116
// note: the tests are run using the factorial function we created for ex2_3a.js
// note II: above x values of (around) 170 factorial can't be easily calculated- the values are too high and the answer received will be "infinity"

const factorial = memoize(
  (x) => {
    if (x === 0) {
      result = 1;
    }
    else {
      result = x*factorial(x - 1);
    }
    //console.log("factorial(x)::: x = "+x+" returning x*factorial(x - 1) = "+result);
    return result;
  }
);
//the first time we will need to calculate by brute force
console.log("factorial(5)="+factorial(5)); // = 120

//from then on we can use previously cached calculations to speed up the process
//(gets the value of factorial(5) from the cache and then multiplies by 6)
console.log("factorial(6)="+factorial(6)); // = 720

//and so on: (gets the value of factorial(6) from the cache and then multiplies by 7)
console.log("factorial(7)="+factorial(7)); // = 5040


//since we skipped a few we still need to do some serious calcualtions.  
//But we at least saved all calculations up until factorial (7)
console.log("factorial(15)="+factorial(15)); // = 1307674368000

//if we go back to something we already calculated, we don't need any calculations. The value is already cached
console.log("factorial(10)="+factorial(10)); // = 3628800
*/



