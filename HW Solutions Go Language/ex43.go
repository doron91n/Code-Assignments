

// this file contain my solution to ex 4.3 reverses an int array in place using an array pointer instead of a slice.
// Exercise 4.3: Rewrite reverse to use an array pointer instead of a slice.
// to run the reverse43_test function uncomment the call to the function at main
// to run reverse43 function , need to call it and send an array of size 10 by reference, example: reverse43(&a)
// used https://github.com/torbiak/gopl as reference

package main
import (
	"fmt"
	"reflect"
)

// reverse43 - ex 4.3 reverses an int array in place using an array pointer instead of a slice.
// array_to_reverse need to be sent by reference, example: reverse43(&a)
// array_to_reverse is of size 10, to send bigger a array need to manually change it
func reverse43(array_to_reverse *[10]int) {
	for i := 0; i < len(array_to_reverse)/2; i++ {
		end := len(array_to_reverse) - i - 1
		array_to_reverse[i], array_to_reverse[end] = array_to_reverse[end], array_to_reverse[i]
	}
}

// reverse43_test - a test function to check reverse43 function, creates arrays, reverse some of them and compare them
func reverse43_test() {
	// define 2 array with initial size 10, the reverse function expect to get and reverse an int array of size 10
	// if needed to use a bigger array then 10, need to change the size of array argument in reverse43 function.
	a:=[10]int{1, 2, 3, 4, 5,6,7,8,9,10}
	reversed_a:=[10]int{10,9,8,7,6,5, 4, 3, 2, 1}
	b:=a // create 2 copies of a array, reversing a copy wont affect original
	c:=a
	fmt.Printf("reverse43_test:: orginal Array: %v, reversed Array result expacted: %v\n", a, reversed_a)
	fmt.Printf("reverse43_test:: calling reverse on Arrays a and b by reference\n\n")
	reverse43(&a)
	reverse43(&b)
	// compare a reverse of original a array with the expected reversed_a result array -- need to be the same
	fmt.Printf("reverse43_test:: calling array_cmp for array a and array reversed_a (manualy reversed copy of a) - need to be the same\n")
	array_cmp(a,reversed_a)
	// compare a reverse of original a array with the reversed result of array b (copy of original array a) -- need to be the same
	fmt.Printf("reverse43_test:: calling array_cmp for array a and array b (reversed copy of a) - need to be the same\n")
	array_cmp(a,b)
	fmt.Printf("reverse43_test:: calling array_cmp for array a and array c (unreversed copy of a) - need to be different\n")
	// compare a reverse of original a array with the none reversed result of array c (copy of original array a) -- need to be the different
	array_cmp(a,c)
	fmt.Printf("reverse43_test:: calling reverse on Array c by reference\n")
	reverse43(&c)
	fmt.Printf("reverse43_test:: calling array_cmp for array a and array c (reversed copy of a) - need to be same\n")
	array_cmp(a,c)
}

// array_cmp - simple function that gets 2 int array of size 10 , compares them and print result
func array_cmp(a, b [10]int ){
	if !reflect.DeepEqual(a, b) {
		fmt.Printf("reverse43_test::array_cmp:: compare result for a: %v and b: %v  is UNEQUAL\n\n", a, b)
	}else{
		fmt.Printf("reverse43_test::array_cmp:: compare result for a: %v and b: %v  is EQUAL\n\n", a, b)
	}
}

func main() {
	//reverse43_test()
	}

