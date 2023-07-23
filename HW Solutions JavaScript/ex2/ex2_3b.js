
/*
This file: ex2_3b.js contains a Function that will receive a fibonacci Function (with one arguments)and will return a memeoized
value for the given fibonacci Function. , run example: console.log("fibonacci(0)="+fibonacci(0));
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


// function name: fibonacci 
// input: integer value of x
// output: the fibonacci value calculated for the given x , F(x)=F(x-1)+F(x-2), F(0)=0, F(1)=F(2)=1

const fibonacci = memoize(
  (x) => {
    if (x === 0) {
      result = 0;
    }else{
        if((x === 1)||(x === 2)){
          result= 1;
        }else {
           result= (fibonacci(x - 1)+ fibonacci(x-2));
       }
   }
  //console.log("fibonacci(x)::: x = "+x+" returning (fibonacci(x-1)+ fibonacci(x-2)) = "+result);
  return result;
  }
);

// to run examples uncomment next section from lines 57 and 82

/*
// tests: in order to run and print the results simply remove the // from the relevant test line
//the first few calculations will be run brute force (only O(1) actions -not expensive)
console.log("fibonacci(0)="+fibonacci(0)); // = 0
console.log("fibonacci(1)="+fibonacci(1)); // = 1
console.log("fibonacci(2)="+fibonacci(2)); // = 1

//from here onwards we can use previously cached calculations to speed up the process
console.log("fibonacci(3)="+fibonacci(3)); // = 2
console.log("fibonacci(4)="+fibonacci(4)); // = 3
console.log("fibonacci(5)="+fibonacci(5)); // = 5
//since we skipped a few  values we still need to do some serious calcualtions. 
//But we at least saved all calculations up until fibonacci (5)
console.log("fibonacci(11)="+fibonacci(11)); // = 89

//if we go back to values we have already calculated, we don't need any calculations. The value is already cached
console.log("fibonacci(7)="+fibonacci(7)); // = 13

//we see a remarkable increase in efficiency at larger values. By calculating and then caching fibonacci(100) we see a massive increase
//in efficiency for fibonacci(500)- since for every fibonacci value from 100 until 500 no longer needs to do any calculations from fibonacci(0) until fibonacci(100)
//(an increase of almost 100 milliseconds- quite a substantial saving)

// console.log("fibonacci(100)="+fibonacci(100)); // = 354224848179262000000
// console.log("fibonacci(500)="+fibonacci(500)); // = 1.394232245616977e+104

*/