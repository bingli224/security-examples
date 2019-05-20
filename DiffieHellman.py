"""
By BingLi224

01:11 THA 01/02/2019

Diffie Hellman
"""

last_prime_check_value = 2
prime_set = [ 2 ]

def update_prime_set ( value ) :
    global last_prime_check_value, prime_set
    if value > last_prime_check_value :
        ## check all numbers in the current target
        for v in range ( last_prime_check_value + 1, value ) :
            b_prime = True ## presume to be prime
            for p in prime_set :
                if v % p == 0 :
                    ## not prime
                    b_prime = False
                    break
                
            if b_prime :
                prime_set += [ v ]
            
            last_prime_check_value = value

def is_prime ( value ) :
    global prime_set
    update_prime_set ( value )
    return value in prime_set    

def is_co_prime ( v1, v2 ) :
    global prime_set
    update_prime_set ( v1 if v1 > v2 else v2 )
    
    for p in prime_set :
        if p > v1 or p > v2 :
            return True
        if v1 % p == 0 and v2 % p == 0 :
            return False
            
    return True

class DiffieHellman :
    base = 0
    mod = 0
    
    def __init__ ( this, base, mod ) :
        this.base = base
        this.mod = mod
    
    def encrypt ( this, value, base = None ) :
        if base is None :
            base = this.base
            
        code = 1
        for i in range ( 1, value ) :
            code *= base
            if code >= mod :
                code %= mod

        return code

###############################################################################
## test

base = 443
mod = 743

print ( "public prime\t: " + str ( mod ) )
print ( "public base\t: " + str ( base ) )

print ( "Alice's secret\t: " )
try :
    v1 = int ( input ( ) ) # 241
except :
    v1 = 241
    print ( "\tDefault: " + str ( v1 ) )
    
print ( "Bob's secret\t: " )
try :
    v2 = int ( input ( ) ) # 87
except :
    v2 = 87
    print ( "\tDefault: " + str ( v2 ) )
    
## test if co-prime
print ( "co-prime(" + str ( v1 ) + "," + str ( v2 ) + ") = " + str ( is_co_prime ( v1, v2 ) ) )

dh = DiffieHellman ( base, mod )
c1 = dh.encrypt ( v1 )
c2 = dh.encrypt ( v2 )

print ( "Alice's cipher\t: " + str ( c1 ) )
print ( "Bob's cipher\t: " + str ( c2 ) )
print ( "Alice's decrypt\t: " + str ( dh.encrypt ( v1, base = c2 ) ) )
print ( "Bob's decrypt\t: " + str ( dh.encrypt ( v2, base = c1 ) ) )
