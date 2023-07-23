

// this file contain my solution to ex 4.7
//Exercise 4.7: Modify reverse to reverse the characters of a []byte slice that represents a UTF-8-encoded string, in place.
//    Can you do it without allocating new memory?
// to run the reverse47_test function uncomment the call to the function at main
// to run reverse47 function , need to call it and send it a byte slice (by reference)
// example 	a := []byte{"Räksmörgås"} --> reverse47(&a) --> a = {"sågrömskäR"}
// used https://github.com/4hel/gopl/blob/master as reference
package main

import (
	"fmt"
	"reflect"
	"unicode/utf8"
)

// reverse_byte_slice - simple reveres in place function for byte slice
func reverse_byte_slice(b []byte) {
	size := len(b)
	for i := 0; i < len(b)/2; i++ {
		b[i], b[size-1-i] = b[size-1-i], b[i]
	}
}


// reverse47 - reverse the characters of a []byte slice that represents a UTF-8-encoded string, in place.
// the byte_slice_ptr is passed by reference
func reverse47(byte_slice_ptr *[]byte) {
	byte_slice := *byte_slice_ptr // save a ptr reference to original byte slice start
	// Reverse all the runes, needed for special char runes like "世" reversed to ->  "\x96\xb8\xe4" .
	for i := 0; i < len(byte_slice); {
		_, size := utf8.DecodeRune(byte_slice[i:])
		reverse_byte_slice(byte_slice[i : i+size])
		i += size
	}
	// reverse the whole slice after all the runes where reversed
	reverse_byte_slice(byte_slice)
	*byte_slice_ptr =byte_slice
}


// reverse47_test - a test function to check reverse47 function , create a byte slice and reverse it
// then compare the result with expected result
func reverse47_test() {
	a_bytes := []byte("a世cde")
	a_bytes_expected := []byte("edc世a")
	fmt.Printf("reverse47_test:: sending slice = %q to reverse47 by reference\n", a_bytes)
	reverse47(&a_bytes)
	if !reflect.DeepEqual(a_bytes, a_bytes_expected) {
		fmt.Printf("reverse47_test:: compare result for a_bytes: %q and a_bytes_expected: %q  is UNEQUAL\n\n", a_bytes, a_bytes_expected)
	}else{
		fmt.Printf("reverse47_test:: compare result for a_bytes: %q and a_bytes_expected: %q  is EQUAL\n\n", a_bytes, a_bytes_expected)
	}

	b_bytes := []byte("Räksmörgås" )
	b_bytes_expected := []byte("sågrömskäR" )
	fmt.Printf("reverse47_test:: sending slice = %q to reverse47 by reference\n", b_bytes)
	reverse47(&b_bytes)
	if !reflect.DeepEqual(b_bytes, b_bytes_expected) {
		fmt.Printf("reverse47_test:: compare result for b_bytes: %q and b_bytes_expected: %q  is UNEQUAL\n\n", b_bytes, b_bytes_expected)
	}else{
		fmt.Printf("reverse47_test:: compare result for b_bytes: %q and b_bytes_expected: %q  is EQUAL\n\n", b_bytes, b_bytes_expected)
	}

	c_bytes := []byte("abcde" )
	c_bytes_expected := []byte( "abcde")
	fmt.Printf("reverse47_test:: sending slice = %q to reverse47 by reference\n", c_bytes)
	reverse47(&c_bytes)
	if !reflect.DeepEqual(c_bytes, c_bytes_expected) {
		fmt.Printf("reverse47_test:: compare result for c_bytes: %q and c_bytes_expected: %q  is UNEQUAL\n\n", c_bytes, c_bytes_expected)
	}else{
		fmt.Printf("reverse47_test:: compare result for c_bytes: %q and c_bytes_expected: %q  is EQUAL\n\n", c_bytes, c_bytes_expected)
	}

}

func main() {
	//reverse47_test()
	}

