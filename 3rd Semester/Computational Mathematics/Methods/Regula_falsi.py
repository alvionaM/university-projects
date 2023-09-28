def regula_falsi(a, b, tol, f):

    c = ( a*f(b) - b*f(a) ) / (f(b) - f(a))

    while(b - a > tol and f(c) != 0):
        if(c + tol == c): #tol << Emach
            break
        if(sign(f(b))*sign(f(c)) <= 0): #upper segment
            a = c
        else:                           #lower segment
            b = c
        c = ( a*f(b) - b*f(a) ) / (f(b) - f(a))

    return c


def f(x):
    return x*x*x

def sign(a):
    return (a>0) - (a<0)