## By BingLi224
## 11:40 THA 20/01/2019
##
## Vigerene cipher
##

class Vigerene :
    keyset = []
    def __init__ ( self, key ) :
        BIG_A = ord ( 'A' )
        SMALL_A = ord ( 'a' )
        self.keyset = [ 0 for _ in range ( len ( key ) ) ]

        for idx in range ( len ( key ) ) :
            ch = key [ idx ]

            if ch >= 'a' and ch <= 'z' :
                self.keyset [ idx ] = ord ( key [ idx ] ) - SMALL_A
            elif  ch >= 'A' and ch <= 'Z' :
                self.keyset [ idx ] = ord ( key [ idx ] ) - BIG_A
            else :
                self.keyset [ idx ] = 0
            
    def encrypt ( self, txt ) :
        BIG_A = ord ( 'A' )
        SMALL_A = ord ( 'a' )
        keyset_size = len ( self.keyset )
        cipher = [ chr ( 0 ) for _ in range ( len ( txt ) ) ]

        for idx in range ( len ( txt ) ) :
            ch = txt [ idx ]
            
            if ch >= 'a' and ch <= 'z' :
                cipher [ idx ] = chr ( ( ord ( txt [ idx ] ) - SMALL_A + self.keyset [ idx % keyset_size ] ) % 26 + SMALL_A )
            elif  ch >= 'A' and ch <= 'Z' :
                cipher [ idx ] = chr ( ( ord ( txt [ idx ] ) - BIG_A + self.keyset [ idx % keyset_size ] ) % 26 + BIIG_A )
            else :
                cipher [ idx ] = txt [ idx ]
            
        return ''.join ( cipher )

    def decrypt ( self, cipher ) :
        BIG_A = ord ( 'A' )
        SMALL_A = ord ( 'a' )
        keyset_size = len ( self.keyset )
        txt = [ chr ( 0 ) for _ in range ( len ( cipher ) ) ]

        for idx in range ( len ( cipher ) ) :
            ch = cipher [ idx ]
            
            if ch >= 'a' and ch <= 'z' :
                txt [ idx ] = chr ( ( ord ( cipher [ idx ] ) - SMALL_A - self.keyset [ idx % keyset_size ] + 26 ) % 26 + SMALL_A )
            elif  ch >= 'A' and ch <= 'Z' :
                txt [ idx ] = chr ( ( ord ( cipher [ idx ] ) - BIG_A - self.keyset [ idx % keyset_size ] + 26 ) % 26 + BIIG_A )
            else :
                txt [ idx ] = cipher [ idx ]
            
        return ''.join ( txt )
        
v = Vigerene ( 'web' )
txt = input ()
print ( 'original:\t' + txt )
print ( 'encrypted:\t' + v.encrypt ( txt ) )
print ( 'decrypted(encrypted):\t' + v.decrypt ( v.encrypt ( txt ) ) )

