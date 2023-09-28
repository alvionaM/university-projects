import math

def f(x):
    return math.sin(x)

def f_d(x):
    return math.cos(x)

def sign(a):
    return (a>0) - (a<0)

def newton_raphson(f, f_d, tol, x0):
    x=x0
    n=1
    while(abs(f(x))>tol):
        x = x - f(x)/f_d(x)
        n+=1
    print(n)
    return x

#main
print( newton_raphson(f, f_d, 10**(-16), 0.5) )