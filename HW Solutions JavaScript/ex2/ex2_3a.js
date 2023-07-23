

/*
This file: ex2_3a.js contains a Function that will receive a factorial Function (with one arguments)and will return a memeoized
value for the given factorial Function. , run example: console.log("factorial(5)="+factorial(5));
to use memoize with some_function use like: const var_name = memoize(some_function); and call it like: var_name(arguments);
*/



// I used the same memoize function I created in ex2_2.js for ease of use-assume all relevant comments are the same
// ***NOTE: ALL CODE COMMENTS OF TYPE console.log(...) ARE FOR TESTING, WHEN UNCOMMENTED, WILL SHOW INNER WORKING OF THE FUNCTION

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
      //console.log("memoize::: $$ NOT FOUND $$ key=" + key+"  calculating and storing result in cache");
      var val = func.apply(this, arguments);
      cache[key] = val;
      return val;
    }
  };
}


// function name: factorial
// input: integer value of x
// output: the factorial value calculated for the given x, F(x)=x*F(x-1)=x! , F(0)=1
// note: past values of (around) 170 factorial can't be calculated- the values are too high for Javascript unaided and the answer received will be "infinity"

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

// to run examples uncomment next section from lines 55 and 78

/*
//the first time we will need to calculate by brute force
console.log("factorial(5)="+factorial(5));  // = 120

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

// the next two example are showing the infinity, cant be calculated
//console.log("factorial(170)="+factorial(170)); // = 7.257415615307994e+306
//console.log("factorial(171)="+factorial(171)); // = Infinity

*/