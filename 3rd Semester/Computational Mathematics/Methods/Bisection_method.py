def bisection(a, b, tol, f):

    c = a + (b-a)/2

    while(b - a > tol and f(c) != 0): 
        if(c + tol == c): #tol << Emach
            break
        if(sign(f(b))*sign(f(c)) <= 0): #upper segment
            a = c
        else:                           #lower segment
            b = c
        c = a + (b-a)/2
    
    return c


def f(x):
    return x*x*x

def sign(a):
    return (a>0) - (a<0)


#main
print(bisection(-1, 3, 10**(-10), f))
print(bisection(-100, 120, 10**(-10), f))