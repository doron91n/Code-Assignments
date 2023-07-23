normal simplfy :
support all commutative expressions for example ((x + z) + y)-(y + (z + x))=0
x * 1 = x
1 * x = x
x * 0 = 0
0 * x = 0
x + 0 = x
0 + x = x
x / x = 1
X / 1 = x
X - 0 = X
0 - X = -X
X - X = 0
log(x, x) = 1

Pow:
(x^y)^z => x^(y*z)
x^1=x
(-1)^odd  =-1   -> was done by checking the pow rightExpression % Mod 2
(-1)^even = 1 -> was done by checking the pow rightExpression % Mod 2
x^0=1
0^x=0
1^x=1

an expression without variables evaluates to its result. ((2*8)-6)^2 => 100.

advanced simplfy :
sin(-x)  = -sin(x)
cos(x)   = cos(-x)

Log:
log(z,mult(x,y)) = log(z,x)+log(z,y)
log(z,Div(x,y))  = log(z,x)-log(z,y)
log(z,Pow(x,y))  = y*log(z,x)

Plus:
((2x) + (4x)) => 6*x

mult:
((2*y)*4) to (8*y))
Minus:
(x-neg(y)) to (x + y)


all the simplefctions are inside the normal simplfy and none of them use instanceOf,
by using if conditions and covering all possible combinations  i was able to support all commutative actions,even nested and complex.
((t+x)+(z+y)) = ((y+x)+(z+t)) and all other combinations will work too.
((t*x)*(z*y)) = ((y*x)*(z*t)) and all other combinations will work too.







