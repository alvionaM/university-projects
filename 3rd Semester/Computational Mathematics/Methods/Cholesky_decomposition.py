from math import sqrt
import numpy as np

def cholesky(a):
    n = np.shape(a)[0] 
    for j in range(n):
        for k in range(j):
            for i in range(j,n):
                a[i][j] = a[i][j] - a[i][k]*a[j][k]
    
        a[j][j] = sqrt(a[j][j])
        for k in range(j+1, n):
            a[k][j]=a[k][j]/a[j][j]
    
    return a

#main
a = np.array([[9,6,3], [6,8,4], [3,4,3]], dtype=float)
a = cholesky(a)
print(a)