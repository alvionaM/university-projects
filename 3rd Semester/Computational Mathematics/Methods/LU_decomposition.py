import numpy as np
import sympy as sp
y = sp.symbols('y')
x = sp.symbols('x')

def LU(a):
    n = np.shape(a)[0]  #square

    indexes = np.arange(n)  #row indexes

    swaps = 0 #needed for det
    p = 1     #needed for det

    for k in range(n-1):
        #find maximum of cols and bring it up (make it driver)
        maxi = k
        for i in range(k+1, n):
            if( abs(a[i][k]) > abs(a[maxi][k]) ):
                maxi = i

        if(maxi!=k):
            temp = indexes[k]
            indexes[k] = indexes[maxi]
            indexes[maxi] = temp
            swaps +=1    

        p*=a[indexes[k]][k] #main diagonal

        for i in range(k+1, n):
            #find multiplier of row i
            m = - a[ indexes[i] ][k] / a[ indexes[k] ][k]

            for j in range(k+1, n): #iterate row i
                a[ indexes[i] ][j] += a[ indexes[k] ][j] * m

            a[ indexes[i] ][k] = 0
            #a[ indexes[i] ][k]=m

    p *=a[indexes[n-1]][n-1]

    permutation = np.zeros((n,n))
    for i in range(np.shape(indexes)[0]):
        permutation[i] [ indexes[i] ] = 1

    return np.dot(permutation, a), (-1)**swaps*p



def LU_stathmisi(a):
    n = np.shape(a)[0]  #square

    indexes = np.arange(n)  #row indexes

    s = np.zeros(n)
    for i in range(n):
        for j in range(n):
            if abs(a[i][j]) > s[i]:
                s[i] = a[i][j]
        

    for k in range(n-1):
        maxi = k
        for i in range(k+1, n):
            if( abs(a[i][k])/s[i] > abs(a[maxi][k])/s[i] ):
                maxi = i

        temp = indexes[k]
        indexes[k] = indexes[maxi]
        indexes[maxi] = temp

        for i in range(k+1, n):
            #find multiplier of row i
            
            m = - a[ indexes[i] ][k] / a[ indexes[k] ][k]

            for j in range(k+1, n): #iterate row i
                a[ indexes[i] ][j] += a[ indexes[k] ][j] * m

            a[ indexes[i] ][k] = 0
            #a[ indexes[i] ][k]=m

    permutation = np.zeros((n,n))
    for i in range(np.shape(indexes)[0]):
        permutation[i] [ indexes[i] ] = 1
    return np.dot(permutation, a)
            
#main
A = np.array([[5,1,-6], [2,1,-1], [6,12,1]], dtype=float)
A, det = LU(A)

print(A)
print()
print(det)