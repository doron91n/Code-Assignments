

// this file contain my solution to ex 4.4
// Exercise 4.4: Write a version of rotate (page 86) that operates in a single pass.
//       number of places to rotate must be a parameter and not constant.
// to run the rotate44_test function uncomment the call to the function at main
// to run rotate44 function , need to call it and send an slice of ints(by reference) to rotate and the number of positions to rotate
// ***ALL ROTATIONS ARE TO THE LEFT , example: rotate44(a,2) will rotate slice named a 2 positions to the left.
// example: slice = [ 1 2 3 4 5 ] , rotate_places_num =  2   -->  slice = [ 3 4 5 1 2]
// uses modulu on rotate_places_num to keep it in slice bounds
// used https://github.com/torbiak/gopl as reference

package main

import (
	"fmt"
	"reflect"
)
// rotate44 - ex4.4 function that rotates a slice of ints by given number of positions to the left.
// *slicePtr - a slice of ints that is sent by reference
// rotate_places_num - the number of places to rotate towards left, using mod it will be in slice bounds

func rotate44(slicePtr *[]int,rotate_places_num int) {
	slice := *slicePtr  // save a ptr reference to original slice start
	// do positive modulu for the rotate_places_num % len(slice) , to not get out of bounds
	rotate_places_num=pmod(rotate_places_num,len(slice))
	// change the slice start ptr to start where the part we wanna rotate ends
	// example: *slicePtr = [ 1 2 3 4 5 ] , rotate_places_num = 2  -->  *slicePtr = [ 3 4 5 ]
	*slicePtr = slice[rotate_places_num :]
	// append the part we wanna rotate at the end of *slicePtr
	// example: *slicePtr = [ 3 4 5 ] , slice[:rotate_places_num] = [ 1 2 ]  -->  *slicePtr = [ 3 4 5 1 2]
	*slicePtr = append(*slicePtr, slice[:rotate_places_num]...)
}

// pmod - Positive modulo, returns non negative solution to x % d
func pmod(x, d int) int {
	x = x % d
	if x >= 0 { return x }
	if d < 0 { return x - d }
	return x + d
}


// rotate44_test - a test function to check rotate44 function , create a slice and rotates it by different rotation numbers
func rotate44_test() {
	a := []int{1, 2, 3, 4, 5}
	a_rotate_left_3_result:=[]int{ 4, 5,1,2 ,3}
	fmt.Printf("rotate44_test:: array = %v  ,before rotate toward left by %d places : \n", a,3)
	rotate44(&a,3)
	fmt.Printf("rotate44_test:: array = %v  ,after rotate toward left by %d places : \n", a,3)

	if !reflect.DeepEqual(a, a_rotate_left_3_result) {
		fmt.Printf("rotate44_test:: compare result for a: %v and a_rotate_left_3_result: %v  is UNEQUAL\n\n", a, a_rotate_left_3_result)
	}else{
		fmt.Printf("rotate44_test:: compare result for a: %v and a_rotate_left_3_result: %v  is EQUAL\n\n", a, a_rotate_left_3_result)
	}
	// example of rotations with negative number of rotations, shows uses of pmod function -> from -7 to 1 places
	b := []int{1, 2, 3, 4, 5,6}
	fmt.Printf("rotate44_test:: array = %v  ,before rotate toward left by %d -> 1 places : \n", b,-7)
	rotate44(&b,-7)
	fmt.Printf("rotate44_test:: array = %v  ,after rotate toward left by %d -> 1 places : \n", b,-7)
	b_rotate_left_minus7_result:=[]int{6 ,1 ,2, 3 ,4 ,5}
	if !reflect.DeepEqual(b, b_rotate_left_minus7_result) {
		fmt.Printf("rotate44_test:: compare result for b: %v and b_rotate_left_minus7_result: %v  is UNEQUAL\n\n", b, b_rotate_left_minus7_result)
	}else{
		fmt.Printf("rotate44_test:: compare result for b: %v and b_rotate_left_minus7_result: %v  is EQUAL\n\n", b, b_rotate_left_minus7_result)
	}

	// example of rotations with number of rotations bigger then slice, shows uses of pmod function -> from 10 to 4 places
	c := []int{1, 2, 3, 4, 5,6}
	fmt.Printf("rotate44_test:: array = %v  ,before rotate toward left by %d -> 4 places : \n", c,10)
	rotate44(&c,10)
	fmt.Printf("rotate44_test:: array = %v  ,after rotate toward left by %d -> 4 places : \n", c,10)
	c_rotate_left_10_result:=[]int{5 ,6, 1, 2 ,3, 4}
	if !reflect.DeepEqual(c, c_rotate_left_10_result) {
		fmt.Printf("rotate44_test:: compare result for c: %v and c_rotate_left_10_result: %v  is UNEQUAL\n\n", c, c_rotate_left_10_result)
	}else{
		fmt.Printf("rotate44_test:: compare result for c: %v and c_rotate_left_10_result: %v  is EQUAL\n\n", c, c_rotate_left_10_result)
	}

}


func main() {
	//rotate44_test()
	}

