import math

def simpson3_8(x0, xn, n, f):
    h = (xn-x0)/n
    sum = f(x0) + f(xn)

    for i in range(1,n):
        currPoint = x0 + i*h
        if i%3 !=0:
            sum += 3*f(currPoint) 
        else:
            sum += 2*f(currPoint)
    return 3*h/8*sum

#main
x0=0
xn=1
f = lambda x: math.exp(-x**2)
print(simpson3_8(0,1,120,f))