import numpy as np

def forward_diff(n, x, y):
    Diffs = np.zeros((n,2+n-1))     #2 cols for x,y and n-1 cols for diffs

    for i in range(n):              #initialize x/y columns
        Diffs[i][0] = x[i]
        Diffs[i][1] = y[i]

    for k in range (1, n):
        for i in range(n-k):
            Diffs[i][k+1] = Diffs[i+1][k] - Diffs[i][k] 

    print("\n----------Forward Differences----------")
    print("x\tf(x)\t")
    for i in range (np.shape(Diffs)[0]):
        for j in range (np.shape(Diffs)[1]):
            if(j>=2 and i>=n-j+1):             
                print("-", end="\t")
            else:
                print(str(Diffs[i][j]), end='\t')
        print()

#main
num = int(input("\nGive number of points: "))
x = []
y = [] 
for i in range(num):
    val = int(input("Give x"+str(i)+":  "))
    x.append(val)
    val = int(input("Give y"+str(i)+": "))
    print()
    y.append(val)

x = np.asarray(x)
y = np.asarray(y)

forward_diff(num, x, y)