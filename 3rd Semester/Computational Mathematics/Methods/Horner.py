from math import factorial
import numpy as np

def horner_0(A, x0):
    N = np.shape(A)[0] - 1
    B = np.zeros(N+1)
    B[N] = A[N]
    for i in range(N-1, -1, -1): #stop is exclusive
        B[i] = B[i+1]*x0 + A[i]
    
    return B

def horner_k(A, x0, k):
    if(k >= np.shape(A)[0]):
        return 0
    
    i=0
    while(i<=k):
        N = np.shape(A)[0]
        if(i==0):
            A = HornerR(A, x0) 
        else:
            A = HornerR(A[1:N], x0) #truncate previous result (position 0)
        i+=1

    return A[0] * factorial(k)
        

#main
A = np.array([186,-295,184,-53,6])
x0=2
print(Horner(A, x0, 4)) #k <-> derivative,  e.g. 1:  f',  2: f" etc