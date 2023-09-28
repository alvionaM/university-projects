from math import *

def Div_n_Conq(A, p, r, a):
    n = r-p+1
    popl = set()

    if n <= 1:
        popl.add(A[p])
        return popl

    m = floor ( (p + r) / 2 )

    left = Div_n_Conq(A, p, m, a)
    right = Div_n_Conq(A, m+1, r, a)
                                    #popl = left.intersection(right)
    candidates = right.union(left)
    for el in candidates:
        count = 0 
        for i in range(p, r+1):
            if A[i] == el:
                count +=1
        if (count >= a*n):
            popl.add(el)
    return popl
    
def driver(A,p,r,a):
    if(a>1):
        return set()
    else: 
        return Div_n_Conq(A,p,r,a)

A = [7,6,2,7,1,3,2,6,2,4,4,5,5,8,6,7,7,6,8,19,21,4,1,5,7,4,7]
print(len(A))
populars = (Div_n_Conq(A, 0, len(A)-1, 0.1))

print(populars)