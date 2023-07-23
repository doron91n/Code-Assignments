

// this file contain my solution to ex 4.6
//Exercise 4.6: Write an in-place function that squashes each run of adjacent Unicode spaces (see unicode.IsSpace)
//    in a UTF-8-encoded []byte slice into a single ASCII space.
// to run the squash46_test function uncomment the call to the function at main
// to run squash46 function , need to call it and send it a byte slice (by reference)
// example 	a := []rune{'a',' ','c',' ', ' ','d','世', '	' ,'	','e'} --> squash46(&a) --> a = {'a',' ',' ','c',' ','d','世',' ','e'}
// used https://github.com/4hel/gopl/blob/master as reference
package main

import (
	"fmt"
	"reflect"
	"unicode"
	"unicode/utf8"
)

// squash46 - gets slice of bytes(by reference) and squashes adjacent Unicode spaces chars in place,
// to a UTF-8-encoded []byte slice into a single ASCII space.
func squash46(byte_slice_ptr *[]byte)  {
	byte_slice := *byte_slice_ptr // save a ptr reference to original byte slice start
	last_added_is_space := false  // used to find when we are on the last space char among adjacent space chars
	read_ptr := 0 // index of our current char
	write_ptr := 0 // index of the char we need to write
	for read_ptr < (len(byte_slice)) {
		curr_rune, curr_rune_size := utf8.DecodeRune(byte_slice[read_ptr:])
		if !unicode.IsSpace(curr_rune) { // current char is not a space char , we just write into our slice
			utf8.EncodeRune(byte_slice[write_ptr:], curr_rune)
			write_ptr += curr_rune_size // advance the writer index by the size of the added current char
			last_added_is_space = false
		} else { // current char is of type space
			// used to make sure we dont add adjacent space chars ,
			// if the last written char was space char then we skip writing this current space char
			if !last_added_is_space {
				curr_rune_size = utf8.EncodeRune(byte_slice[write_ptr:], int32(' '))
				write_ptr += curr_rune_size
			}
			last_added_is_space = true
		}
		read_ptr += curr_rune_size
	}
	*byte_slice_ptr= byte_slice[:write_ptr]  // make the byte slice pointer , point to the start of the changed slice
}

// squash46_test - a test function to check squash46 function , create a string byte slice and cleans adjacent spaces
// then compare the result with expected result
func squash46_test() {
	a_bytes := []byte(string([]rune{'a',' ','c',' ', ' ','d','世', '	' ,'	','e'}))
	a_bytes_expected := []byte(string([]rune{'a',' ','c', ' ','d','世', ' ','e'}))
	fmt.Printf("squash46_test:: sending slice = %q to squash46 by reference\n", a_bytes)
	squash46(&a_bytes)
	if !reflect.DeepEqual(a_bytes, a_bytes_expected) {
		fmt.Printf("squash46_test:: compare result for a_bytes: %q and a_bytes_expected: %q  is UNEQUAL\n\n", a_bytes, a_bytes_expected)
	}else{
		fmt.Printf("squash46_test:: compare result for a_bytes: %q and a_bytes_expected: %q  is EQUAL\n\n", a_bytes, a_bytes_expected)
	}

	b_bytes := []byte(string([]rune{'a','a','世',' ', '世',' ','	', '	' ,'	',' ','.'}))
	b_bytes_expected := []byte(string([]rune{'a','a','世', ' ','世',' ', '.'}))
	fmt.Printf("squash46_test:: sending slice = %q to squash46 by reference\n", b_bytes)
	squash46(&b_bytes)
	if !reflect.DeepEqual(b_bytes, b_bytes_expected) {
		fmt.Printf("squash46_test:: compare result for b_bytes: %q and b_bytes_expected: %q  is UNEQUAL\n\n", b_bytes, b_bytes_expected)
	}else{
		fmt.Printf("squash46_test:: compare result for b_bytes: %q and b_bytes_expected: %q  is EQUAL\n\n", b_bytes, b_bytes_expected)
	}

	c_bytes := []byte(string([]rune{' ',' ','a','	', ' ','b','世', '	' ,'	','g',' ',' ', ' '}))
	c_bytes_expected := []byte(string([]rune{' ','a',' ', 'b','世', ' ','g',' '}))
	fmt.Printf("squash46_test:: sending slice = %q to squash46 by reference\n", c_bytes)
	squash46(&c_bytes)
	if !reflect.DeepEqual(c_bytes, c_bytes_expected) {
		fmt.Printf("squash46_test:: compare result for c_bytes: %q and c_bytes_expected: %q  is UNEQUAL\n\n", c_bytes, c_bytes_expected)
	}else{
		fmt.Printf("squash46_test:: compare result for c_bytes: %q and c_bytes_expected: %q  is EQUAL\n\n", c_bytes, c_bytes_expected)
	}

}

func main() {
	//squash46_test()
	}

