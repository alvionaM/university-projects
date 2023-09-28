def secant_method(f, xp, xt, tol):

    xn = xt - f(xt)*(xt-xp)/(f(xt) - f(xp))

    while(abs(f(xn)) > 0):

        if (xt == xp):
            break
        
        xn = xt - f(xt)*(xt-xp)/(f(xt) - f(xp))
        xp = xt
        xt = xn
    
    return xn