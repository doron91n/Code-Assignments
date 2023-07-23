

// this file contain my solution to ex 4.5
// Exercise 4.5: Write an in-place function to eliminate adjacent duplicates in a []string slice (example of the term “in-place” in page 86).
// to run the cleanDup45_test function uncomment the call to the function at main
// to run cleanDup45 function , need to call it and send an slice of strings(by reference)  to remove adjacent duplicates
// example 	a := []string{"a","b","b","c"} --> cleanDup45(&a) --> a = ["a","b","c"]
// used https://github.com/torbiak/gopl as reference
package main

import (
	"fmt"
	"reflect"
)

// cleanDup45 - gets slice of strings(by reference) and removes adjacent duplicates in place
func cleanDup45(str_slice_ptr *[]string)  {
	str_slice := *str_slice_ptr // save a ptr reference to original slice start
	i := 0 // index of last written string in slice
	for _, current_string := range str_slice {
		if str_slice[i] == current_string { // go over the strings and if you found adjacent duplicates then skip it
			continue
		}
		i++
		str_slice[i] = current_string
	}
	*str_slice_ptr= str_slice[:i+1] // update the ptr reference to the start of the changed slice
}



// cleanDup45_test - a test function to check cleanDup45 function , create a string slice and cleans adjacent duplicates
// then compare the result with expected result
func cleanDup45_test() {
	a := []string{"a","b","b","c","r","r","r","t"}
	a_clean_dup := []string{"a","b","c","r","t"}
	fmt.Printf("cleanDup45_test:: sending array = %v to cleanDup45 by reference\n", a)
	cleanDup45(&a)
	if !reflect.DeepEqual(a, a_clean_dup) {
		fmt.Printf("cleanDup45_test:: compare result for a: %v and a_clean_dup: %v  is UNEQUAL\n\n", a, a_clean_dup)
	}else{
		fmt.Printf("cleanDup45_test:: compare result for a: %v and a_clean_dup: %v  is EQUAL\n\n", a, a_clean_dup)
	}
	b := []string{"aaaa","a","bb","bb","b","ccc","cc","r","r"}
	b_clean_dup := []string{"aaaa","a","bb","b","ccc","cc","r"}
	fmt.Printf("cleanDup45_test:: sending array = %v to cleanDup45 by reference\n", b)
	cleanDup45(&b)
	if !reflect.DeepEqual(b, b_clean_dup) {
		fmt.Printf("cleanDup45_test:: compare result for b: %v and b_clean_dup: %v  is UNEQUAL\n\n", b, b_clean_dup)
	}else{
		fmt.Printf("cleanDup45_test:: compare result for b: %v and b_clean_dup: %v  is EQUAL\n\n", b, b_clean_dup)
	}
}


func main() {
	//cleanDup45_test()
	}

