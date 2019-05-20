/**
 * Vigenere cipher
 *
 * @autho BingLi224
 * @version 2019.01.20
 */

fun main ( args: Array <String> ) {
    val txt = "sololearn"
    val code = Vigenere ( "web" )

    println ( "original:\t$txt" )
    println ( "encrypted:\t${code.encrypt ( txt )}" )
    println ( "decrypted(encrypted):\t${code.decrypt ( code.encrypt ( txt ) )}" )
}

class Vigenere (  key : String ) {
    val keyset : IntArray
    
    init {
        keyset = IntArray ( key.length )
        for ( idx in 0 .. key.length - 1 ) {
            val ch = key [ idx ]
            keyset [ idx ] = when {
                ch >= 'a' && ch <= 'z' -> ( ch - 'a' ).toInt ( )
                ch >= 'A' && ch <= 'Z' -> ( ch - 'A' ).toInt ( )
                else -> 0
            }
        }
    }
    
    fun encrypt ( text : String ) : String  {
        val cipher = CharArray (text.length)
        for ( idx in 0 .. text.length - 1 ) {
            val ch = text [ idx ]
            cipher [idx] = when {
                ch >= 'a' && ch <= 'z' -> ( ( ch - 'a' + keyset [ idx % keyset.size ] ) % 26 + 'a'.toInt ( ) ).toChar ( )
                ch >= 'A' && ch <= 'Z' -> ( ( ch - 'A' + keyset [ idx % keyset.size ] ) % 26 + 'A'.toInt ( ) ).toChar ( )
                else -> ch
            }
        }
        return String ( cipher )
    }
    
    fun decrypt ( cipher : String ) : String {
        val text = CharArray (cipher.length)
        for ( idx in 0 .. cipher.length - 1 ) {
            val ch = cipher [ idx ]
            text [idx] = when {
                ch >= 'a' && ch <= 'z' -> ( ( ch - 'a' - keyset [ idx % keyset.size ] + 26 ) % 26 + 'a'.toInt ( ) ).toChar ( )
                ch >= 'A' && ch <= 'Z' -> ( ( ch - 'A' - keyset [ idx % keyset.size ] + 26 ) % 26 + 'A'.toInt ( ) ).toChar ( )
                else -> ch
            }
        }
        return String ( text )
    }
}
