function constfunc(i) {
	return function() {return i};
}

var funcs =[];
for(var i=0; i<10; i++) 
	funcs[i]=constfunc(i);

for(var i=0; i<10; i++) 
	console.log(funcs[i]+":",funcs[i]())
