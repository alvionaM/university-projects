import sympy as sp
import numpy as np

var = sp.symbols('x')

def langrange_interpolation(x, y):
    n = np.shape(x)[0] - 1
    S = 0                   #a sympy expression
    for i in range(n+1):
        P=1
        for j in range(n+1):
            if(i!=j):
                P *= (var-x[j]) / (x[i] - x[j]) 
        S += P*y[i]
    return S


#main
x = np.array([-2,-1,0,4])
y = np.array([-2,4,1,8])

expr = langrange_interpolation(x,y)
expr = sp.simplify(expr)

print(expr)
print(expr.subs(var,2))