

/*
This file: ex2_4.js contains a Function that will receive a Function (with any amount of arguments)and will return a memeoized
value for the given Function. , run example: console.log("### fff(9,3,22)::"+fff(9,3,22));
to use memoize with some_function use like: const var_name = memoize(some_function); and call it like: var_name(arguments)

NOTE 1: to ensure this function works as intended we create the relevant key with the help of the encodeKey function, as opposed to 
Array.prototype.join.call()  This ensures that no matter what sort of functions or what arguments are needed as inputs, that the created key
will be unique for EVERY type of input. (Something that should exist for any Pure function) this ensures that no matter what form of input
we will recieve, the process undertaken by (most) function can be reversed in order to find the original input.

// ***NOTE 2: for extending the javascript class. It must that be noted that all additional functions must be defined via const.
// If not you MAY create cacheKeys that apply to multiple functions- (undesirable for any pure functions and computations)
// ***NOTE 3: ALL CODE COMMENTS OF TYPE console.log(...) ARE FOR TESTING, WHEN UNCOMMENTED, WILL SHOW INNER WORKING OF THE FUNCTION
/*


//  function name: memoize
//  input: function with more then one argument
//  output: the memoized version function for given function.
*/
function memoize(func) {
  var cache = {}
  return function() {  
  //  we create a unique key from all the combined arguments the function received
  //  (we can assume that different arguments all have distinct string representations)-from the targil
  //  in any pure function (a prerequsite to be using memoization) each individual key/list of arguments will have a unique, repeatable value to return
    var argsKey = encodeKey(arguments);
    //console.log("memoize:::created argsKey = " + argsKey+"  argsKey length = " + arguments.length);

    //  if we have already solved this function (defined via provided arguments) then we return the already cached values
    if (argsKey in cache){
      //console.log("memoize::: ** FOUND ** argsKey= " + argsKey+"  returning cache[argsKey]="+cache[argsKey]);
      return cache[argsKey];
    }
    //  if not, we solve the function, cache the final value, and return the newly found value
    else{
    //console.log("memoize::: $$ NOT FOUND $$ argsKey= " + argsKey+"  calculating and storing result in cache");
      return (cache[argsKey] = func.apply(this, arguments));
    }
  }
}

// function name: encodeKey 
// input: array
// output: a encoded string build from given array, array like: x,y,z will return string 3[$[x,y,z]$]3 
// the number 3 at the start and end is the number of arguments in the array ,[$[ and ]$] are unique delimiters.
function encodeKey(key){
var key_length=key.length; // the length of the array
var x= key_length+"[$["+Array.prototype.join.call(key)+"]$]"+key_length; // returns string with added delimeters 
//console.log("generateKey():: for key:"+key+" generated:"+x);
return x;
}

// function name: decodeKey 
// input: string that was generated with generateKey()
// output: a clean string of the original array without the generated extra stuff 3[$[x,y,z]$]3 will return x,y,z.
function decodeKey(key){
var key_length=key.length; // the length of encoded string the first 4 and last 4 chars were added at encoding and to be removed
var x=key.substr(4,(key_length-8)); // remove the extra stuff we added when generated key
//console.log("decodeKey():: for key:"+key+" length:"+key_length+" decoded:"+x);
return x;
}



// to run next example uncomment next section from lines 70 and 105
/*
//Example 1:
// function name: fff 
// input: integer value of x , y , z
// output: the value calculated for the given x,y,z , fff(x,y,z)=p*F(x-1,y-1,z-1), p=(x+y+z) , if (x or y or z) < 0 return 1.
const fff = memoize(
  (x,y,z) => {
    if ((x < 0) ||(z < 0)||(y < 0)){
      result=1;
      //console.log("fff():: x or y or z is smaller then zero return 1");
    }else {
      result=fff((x-1),(y-1),(z-1));
    }
  var p=(x+y+z);
  //console.log("fff():: for x="+x+", y="+y+", z="+z+"   p=(x+y+z)=("+x+"+"+y+"+"+z+")="+p+" returning [p*fff(x-1,y-1,z-1)] = "+"["+p+"*fff("+(x-1)+","+(y-1)+","+(z-1)+")]="+(p*result));
  return (p* result);
  }
);

//   the first time we will need to calculate by brute force
console.log("### fff(11,6,25)::"+fff(11,6,25));  // result = 794412178560 = 42*39
//   the stored values from fff(11,6,25) are : fff(11,6,25)=794412178560,fff(10,5,24)=18914575680,fff(9,4,23)=484989120,
//   fff(8,3,22)=13471920,fff(7,2,21)=408240,fff(6,1,20)=13608,fff(5,0,19)=504,fff(4,-1,18)=21 
//   for any consequent calculations, we can use any value that we have previously cached, for example we will ask for fff(8,3,22)
console.log("### fff(8,3,22)::"+fff(8,3,22));  // result = 13471920 = 33*30
//   we can see that we got the result from cache and it wasnt recalculated.

//    next we will calculate a new set of variables and see that it calculates it from the start and not using wrong keys and values(unique key)
//    the stored values from fff(22,3,8) are : fff(22,3,8),fff(21,2,7),fff(20,1,6),fff(19,0,5),fff(18,-1,4)
console.log("### fff(22,3,8)::"+fff(22,3,8));  // result = 13471920 = 33*30
//    the stored values from fff(6,5,9) are : fff(6,5,9),fff(5,4,8),fff(4,3,7),fff(3,2,6),fff(2,1,5),fff(1,0,4),fff(0,-1,3)
console.log("### fff(6,5,9)::"+fff(6,5,9));  // result = 4188800 = 20*17
//    the stored values from fff(5,6,9) are : fff(5,6,9),fff(4,5,8),fff(3,4,7),fff(2,3,6),fff(1,2,5),fff(0,1,4),fff(-1,0,3), 
console.log("### fff(5,6,9)::"+fff(5,6,9));  // result = 4188800 = 17*20

*/


// to run next example uncomment next section from lines 108 and 143

/*
//Example 2:
// function name: reverse 
// input: variables x , y , z can be numbers or strings
// output: returns z+y+x , for numbers will be a simple addition, for strings it will combine them in reverse order
const reverse = memoize(
  (x,y,z) => {
    var p=(z+y+x);
    if(typeof z === 'undefined' ){
        p=(y+x);
        //console.log("reverse():: got z undifiend");
    }
    if(typeof x === 'undefined' ){
        p=(z+y);
        //console.log("reverse():: got x undifiend");
    }
    if(typeof y === 'undefined' ){
        p=(z+x);
        //console.log("reverse():: got y undifiend");
    }
  //console.log("reverse():: for x="+x+", y="+y+", z="+z+"   p=(z+y+x)=("+z+"+"+y+"+"+x+")="+p);
  return p;
  }
);
// here we show that it works for numbers
console.log("### reverse(22,3,8)::"+reverse(22,3,8));  // result = 33  = 8+3+22
// in the next two lines we will demonstrate that we created a unique key for the cache:
// here if we didnt create a unique key out of the arguments , 
// the key for the next 2 lines will be the same and therefor the second result would have been (WRONG) the same.
// if we did var argsKey = Array.prototype.join.call(arguments); instead of var argsKey = encodeKey(arguments); at line 30
console.log("### reverse(a,b,c)::"+reverse("a","b","c"));  // result =  cba = c+b+a
console.log("### reverse('a,b',c)::"+reverse("a,b","c"));  // result  =ca,b = ca,+b and not cba

*/
 