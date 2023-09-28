import math
import numpy as np
import sympy as sp

def f(x):
    return math.exp(-x**2)

def deriv(f):
    return sp.diff()

def trapezoid(x0, xn, n, f):
    h = (xn-x0) / n
    sum = f(x0) / 2 + f(xn) / 2
    for i in range(1, n):    
        currPoint = x0 + i*h
        sum += f(currPoint)
    return h*sum

#main
print(trapezoid(0,1, 120, f))