
/**
 * @author BingLi224
 * @version 2019.01.29
 *
 * Diffie-Hellman
 */
 
fun main(args: Array<String>) {

    // base ^ v (** mod)
    val base = 443L
    val mod = 743L

    println ( "public prime\t: $mod")
    println ( "public base\t: $base")

    val v1 = 241L //args [0].toLong ( )
    
    println ( "Alice's secret\t: $v1")

    val v2 = 87L //args [1].toLong ( )
    
    println ( "Bob's secret\t: $v2")

    // test if co-prime    
    println ( "co-prime($v1, $v2) = ${DiffieHellman.isCoPrime (v1, v2)}")
    
    val dh = DiffieHellman ( base, mod )
    val c1 = dh.encrypt ( v1 )
    val c2 = dh.encrypt ( v2 )
    
    println ( "Alice's cipher\t: $c1")
    println ( "Bob's cipher\t: $c2")
    
    println ( "Alice's decrypt\t: ${dh.encrypt(v1, base=c2)}")
    println ( "Bob's decrypt\t: ${dh.encrypt(v2, base=c1)}")
}

class DiffieHellman ( val base : Long, val mod : Long ) {
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
    }
    
    fun encrypt ( value : Long, base : Long = this.base ) : Long {
        var code = 1L
        for ( i in 1L .. value ) {
            code *= base
            if ( code >= mod )
                code = code.rem ( mod )
            //println ("v=$value b=$base c=$code")
        }
        
        return code
    }
}
