/**
 * @author BingLi224
 * @version 2019.01.30
 *
 * Simple RSA
 *
 * The 2 keys--p and q--of RSA are positive values and co-prime to each other.
 *
 * The data to be encrypted (and decrypted) value must be < p * q.
 *
 * Thanks to sololearn.com
 */
 
// cannot import in this playground
import kotlin.math.abs

import java.util.Random

fun main(args: Array<String>) {
	val rsa = SimpleRSA ( )
	
	println ( "Generated RSA:\t${rsa.toString ( )}")

	// generate the original value, which is < n in RSA
	val original = Random ( ).nextInt ( rsa.n.toInt ( ) ).toLong ( )
	println ( "Original\t: $original" )

	val encrypted = rsa.encrypt ( original )
	println ( "Encrypted\t: $encrypted" )
	println ( "Decrypted\t: ${rsa.decrypt ( encrypted )}" )
}

/**
 * RSA with 2 given keys which are positive value and co-prime.
 */
class SimpleRSA (
	val p : Long = 61L,	// random key 1
	val q : Long = 53L	// random key 2
) {
	// modulo
	val n : Long
	val e : Long  // public part
	val d : Long  // private part
	
	init {
		if ( ! isCoPrime ( p, q ) )
			throw IllegalArgumentException ( "Given p and q must be co-prime." )
			
		// calc the modulo
		n = p * q
		
		val rnd = Random ( )
		
		val totient = SimpleRSA.lcm ( p - 1, q - 1 )

		// calc e
		var e1 = 0L
		while ( e1 <= 1L || ! isCoPrime ( totient, e1 ) ) {
			e1 = ( abs (
					rnd.nextLong ( ) ) + 2L
				).rem ( totient )
		}
		e = e1
		
		// calc d
		var d1 = 1L
		while ( d1 <= 0L || ( e * d1 ).rem ( totient ) != 1L ) {
			d1 ++
		}
		d = d1

	}

	companion object {
		var lastPrimeCheckValue = 2L
		val primeSet = mutableSetOf <Long> (2L)

		fun updatePrimeSet ( value : Long ) {
			if ( value > lastPrimeCheckValue ) {
				// check all numbers to currect target
				for ( v in lastPrimeCheckValue + 1 .. value ) {
				
					var bPrime = true // presume to be prime
					testRem@ for ( p in primeSet ) {
						if ( v.rem ( p ) == 0L ) {
							// not prime
							bPrime = false 
							break@testRem
						}
					}
					
					if ( bPrime )
						primeSet.add ( v )
				}
				
				lastPrimeCheckValue = value
			}
		}
		
		fun isPrime ( value : Long ) : Boolean {
			updatePrimeSet ( value )
			return primeSet.contains (value)
		}
		
		fun isCoPrime ( v1 : Long, v2 : Long ) : Boolean {
			updatePrimeSet ( if ( v1 > v2 ) v1 else v2 )
			for ( p in primeSet ) {
				if ( p > v1 || p > v2 )
					return true 
				if ( v1.rem ( p ) == 0L && v2.rem ( p ) == 0L )
					return false 
			}
			
			return true 
		}
 
		/**
		 * Least common multiple.
		 *
		 * @param   p   Positive long value.
		 * @param   q   Positive long value.
		 * @return  Least common multiple of p and q.
		 */
		fun lcm ( p : Long, q : Long ) : Long {
			if ( p <= 0 || q <= 0 )
				throw IllegalArgumentException ( "value must be positive (>= 1)." )
				
			var p1 = p
			var q1 = q
			while ( p1 != q1 ) {
				if ( p1 < q1 )
					p1 += p
				else
					q1 += q
			}
			
			return p1
		}
	}

	fun encrypt ( value : Long ) : Long {
		return convert ( value, e )
	}
	fun decrypt ( value : Long ) : Long {
		return convert ( value, d )
	}
	
	fun convert ( value1 : Long, value2 : Long ) : Long {
		// calculate manually
		var result = 1L
		for ( i in 1 .. value2 ) {
			result *= value1
			
			if ( result >= n )
				result = result.rem ( n )
		}
		
		return result
	}

	override fun toString ( ) : String {
		return "p=$p\tq=$q\tn=$n\ttotient=${SimpleRSA.lcm ( p-1L, q-1L )}\te=$e\td=$d"
	}
}
