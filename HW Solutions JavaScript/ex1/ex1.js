
function constfuncs() {
	 var funcs1 = [];
	 var x= function(h){ return function() {return h}};
 	for(var d=0; d<10; d++) 
   		funcs1[d]=x(d);
	return funcs1;
}

var funcs = constfuncs();

for(var i=0; i<10; i++) 
	console.log(funcs[i]+":",funcs[i]())




 