import math

# an example of a LINEAR sequence is the one produced by the newton-raphson method when m>1
def f(x):
    return x*(1-math.cos(x))

def f_d(x):
    d = (1-math.cos(x)) + x*(math.sin(x))
    return d

def sign(a):
    return (a>0) - (a<0)

def newton_raphson(f, f_d, tol, x0):
    seq = [x0]
    x=x0
    
    while(abs(f(x))>tol):
        x = x - f(x)/f_d(x)
        seq+=[x]

    print(x)
    return seq



#-----------------------------------------------
sequence = newton_raphson(f, f_d, 10**(-10),3)

#STEFFENSEN

def steffensen(seq, tol, g):
    steff_seq = []

    xn  = seq[0]
    xn1 = seq[1]
    xn2 = seq[2]

    xn_ = xn - ((xn1-xn)**2)/(xn2-2*xn1+xn)

    steff_seq += [xn_]

    while(abs(xn_) > tol):
        xn = xn_ 
        xn1 = g(xn)
        xn2 = g(xn1)
        xn_ = xn - ((xn1-xn)**2)/(xn2-2*xn1+xn)
        steff_seq += [xn_]
    
    return steff_seq