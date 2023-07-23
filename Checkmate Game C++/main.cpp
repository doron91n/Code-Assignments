/*
 * main.cpp
 *
 */

#include <iostream>
#include "ConsolGame.h"

using namespace std;

int main()
{
	const int row = 8;
	const int column = 8;

	Game *g = new ConsolGame(row, column);
	delete g;
	return (0);
}
