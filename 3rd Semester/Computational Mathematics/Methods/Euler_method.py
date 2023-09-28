import math
import numpy as np
import matplotlib.pyplot as plt

def euler(x0, y0, xn, n, f):
    h = (xn-x0)/n
    #yi = y0
    x = np.zeros(n+1)
    x[0] = x0
    y = np.zeros(n+1)
    y[0] = y0
    err = np.zeros(n+1)
    err[0] = 0
    for i in range(1,n+1):
        x[i] = x0 + h*i
        y[i] = y[i-1] + h*f(x[i-1], y[i-1])
        err[i] = abs(y[i] - math.exp(x[i]))
    return x,y,err


def plotting(x,y):

    fig, (ax) = plt.subplots(2,2)

    ax[0][0].plot(x,y, label="Euler", marker='o', color='m', linestyle='-.' )
    ax[0][0].set_title("EULER METHOD")
    ax[0][0].set_xlabel("x axis")
    ax[0][0].set_ylabel("y axis")
    plt.legend()
    
    ax[0][1].plot(x, np.exp(x), label="Analytic", marker='.', color = 'g')
    ax[0][1].set_title("ANALYTIC")
    ax[0][1].set_xlabel("x axis")
    ax[0][1].set_ylabel("y axis")
    plt.legend()

    ax[1][0].plot(x,y, label="Euler", marker='o', color='m', linestyle='' )
    ax[1][0].plot(x, np.exp(x), label="Analytic", marker='.', color = 'g')
    ax[1][0].set_xlabel("x axis")
    ax[1][0].set_ylabel("y axis")
    plt.legend()

    ax[1][1].plot(x, err, label="Error", marker = 'x', color='b')
    ax[1][1].set_title("ERROR")
    ax[1][1].set_xlabel("x axis")
    ax[1][1].set_ylabel("y axis")

    plt.legend()
    plt.show()


#main
f = lambda x,y: y
x,y,err = euler(0,1,1,30,f)
plotting(x,y)