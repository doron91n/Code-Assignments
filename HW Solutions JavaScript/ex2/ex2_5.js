
// this file ex2_5.js containes a add function, this function is invoked when listend event (button click) happens at ex2_5.html , 
// after user enters two round numbers and once he presses the button Add(on html),a button click event is invoked and
// in turn calles the add function: calculates the result of addition"number 1 + number 2 = result" and displayes the answer on the html page



// here we listen to any events pooping from html and invoke certain functions once the right event occured
document.addEventListener("DOMContentLoaded",
  function () {
    console.log(document.getElementById("title"));

// function name: add
// input: integer value of number_1 at element id=number_1 , number_2 at element id=number_2 from calling html page
// output: the string of the calculated value of number_1+number_2=result , ingected into the html page at element id=result
function add(){
// Assign all elements
var number_1 = document.getElementById("number_1").value; // the 1 user entered number as text from html
var number_2 = document.getElementById("number_2").value; // the 2 user entered number as text from html
var add_result=parseFloat(number_1)+parseFloat(number_2); // the addition result after parsing numbers to int
var x=number_1+" + "+number_2+" = "+add_result.toString(); // the addition result string to be displayed on html page.
document.getElementById("result").innerHTML = x; // display the result string

}
	// define what happens when listened event is happening, 
	// here once we get event (button click) the event pops up and add function is called
    document.querySelector("button")
          .addEventListener("click", add);
  
})

