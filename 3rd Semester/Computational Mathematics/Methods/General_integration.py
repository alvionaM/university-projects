import math


def trapezoid(x0, xn, n, f):
    h = (xn-x0) / n
    sum = f(x0) / 2 + f(xn) / 2
    for i in range(1, n):    
        currPoint = x0 + i*h
        sum += f(currPoint)
    return h*sum

def calc_integral(a,b,f,tol):
    I_h = trapezoid(a,b,1,f)
    Ih_2 = trapezoid(a,b,2,f)
    if(abs(I_h - Ih_2) < tol):
        return Ih_2
    else:
        m = a + (b-a)/2    #stability
        return calc_integral(a,m,f,tol) + calc_integral(m,b,f,tol)

#main
f = lambda x: math.exp(-x**2)
print("Integral", calc_integral(0,1,f,10**(-6)))