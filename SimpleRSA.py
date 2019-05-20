"""

By BingLi224
23:20 THA 01/02/2019

Simple RSA

The 2 keys--p and q--of RSA are positive values and co-prime to each other.

The data to be encrypted (and decrypted) value must be < p * q.

Thanks to sololearn.com

"""

from random import randint as rnd

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
	
def lcm ( p, q ) :
	if p <= 0 or q <= 0 :
		raise ValueError ( "Value must be positive (>=1)." )

	p1 = p
	q1 = q
	while p1 != q1 :
		if p1 < q1 :
			p1 += p
		else :
			q1 += q
	
	return p1

class SimpleRSA :
	p = 0   ## a key of modulo
	q = 0   ## another key of modulo
	n = 0   ## modulo
	e = 0   ## for encryption
	d = 0   ## for decryption
	
	def __init__ ( this, p = 61, q = 53 ) :
		## check if is co-prime
		if is_co_prime ( p, q ) == False :
			raise ValueError ( "Given p and q must be co-prime." )

		this.p = p
		this.q = q
		
		## calc the modulo
		this.n = p * q

		totient = lcm ( p - 1, q - 1 )
		
		## calc e
		e = 0
		while  e <= 1 or is_co_prime ( totient, e ) == False :
			e = rnd ( 2, totient - 1 )
		this.e = e

		## calc d
		d = 1
		while d < 0 or ( ( e * d ) % totient ) != 1 :
			d += 1
		this.d = d
		
	def convert ( this, value1, value2 ) :
		## calculate manually
		result = 1
		for i in range ( 1, value2 + 1 ) :
			result *= value1
			
			if result >= this.n :
				result %= this.n

		return result

	def encrypt ( this, value ) :
		return this.convert ( value, this.e )
	
	def decrypt ( this, value ) :
		return this.convert ( value, this.d )
		
	def status ( this ) :
		return "p=" + str ( this.p ) + \
			"\tq=" + str ( this.q ) + \
			"\ttotient=" + str ( lcm ( this.p - 1, this.q - 1 ) ) + \
			"\te=" + str ( this.e ) + \
			"\td=" + str ( this.d )
			
################################################################################

rsa = SimpleRSA ( )

print ( "Generated RSA:\t" + rsa.status ( ) )

## generate the original value, which is < n in RSA
original = rnd ( 2, rsa.n )
print ( "Original\t: " + str ( original ) )

encrypted = rsa.encrypt ( original )
print ( "Encrypted\t: " + str ( encrypted ) )
print ( "Decrypted\t: " + str ( rsa.decrypt ( encrypted ) ) )
