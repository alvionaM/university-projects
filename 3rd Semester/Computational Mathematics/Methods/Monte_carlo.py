import math
from random import randint, random

def monte_carlo_G(a,b,f,maxf, throws):
    E = (b-a)*maxf
    count=0
    for i in range(throws):
        x = a + (b-a)*random()
        y = maxf*random()
        if f(x) > y:
            count+=1
    return (count/throws)*E

def monte_carlo_E(a,b,f,throws):
    sum=0
    for i in range(throws):
        x = a + (b-a)*random()
        sum+=f(x)
    #average
    avg = sum / throws
    return (b-a)*avg

#main
f = lambda x: math.exp(-x**2)
g = lambda x: x**2
print(monte_carlo_G(0,1,f,1, 10**6))
print(monte_carlo_E(0,1,f, 10**6))