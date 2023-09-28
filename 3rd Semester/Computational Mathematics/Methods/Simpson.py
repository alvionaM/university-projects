import math
import sympy as sp

x = sp.symbols('x')

def simpson(x0, xn, n, expr):

    if n % 2==1:
        return -1
    h = (xn - x0) / n
    sum=0
    sum = expr.subs(x, x0) + expr.subs(x, xn)
    for i in range(1,n):
        currPoint = x0 + i*h
        if i % 2 == 1:
            sum += 4*expr.subs(x, currPoint)
        else:
            sum += 2*expr.subs(x, currPoint)
    return h/3*sum

#main
x0 = 0
xn = 1
x = sp.symbols('x')
expr  = sp.exp(-x**2)
print(simpson(0, 1, 14, expr).evalf(10))