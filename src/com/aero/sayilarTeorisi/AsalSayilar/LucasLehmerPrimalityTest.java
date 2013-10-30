package com.aero.sayilarTeorisi.AsalSayilar;

import java.math.BigInteger;

public class LucasLehmerPrimalityTest
{
	public static boolean isPrime(int n)
	{
		/*
		if(n == 2)
			return true;
		*/
		
		BigInteger mp = Mp(n);
		
		return (S(n-2, mp).mod(mp).compareTo(BigInteger.ZERO) == 0);
	}
	
	private static BigInteger Mp(int n)
	{
		return BigInteger.ONE.shiftLeft(n).subtract(BigInteger.ONE);
	}
	
	private static BigInteger S(int p, BigInteger mp)
	{
		if (p == 0)
			return BigInteger.valueOf(4);
		else
		{
			BigInteger s = S(p - 1, mp);
			return s.multiply(s).subtract(BigInteger.valueOf(2)).mod(mp);
		}
	}
	
}

/*
 * 
 * Lucas-Lehmer Testi Örnek;
 * 
 * n = 7 için; Mp(7) = 2^7 - 1 = 127 127 sayýsý asal mý ?
 * 
 * S(0) = 4 (baþlangýç þartý) S(1) = (4*4 - 2) % 127 = 14 % 127 = 14 S(2) = (14*14 - 2) % 127 = 194 % 127 = 67 S(3) =
 * (67*67 - 2) % 127 = 4487 % 127 = 42 S(4) = (42*42 - 2) % 127 = 1762 % 127 = 111 S(5) = (111*111 - 2) % 127 = 12319 %
 * 127 = 0
 * 
 * S(n-2) % Mp(n) = 0 olduðu için Mp(7) asaldýr denilebilir.
 * 
 * Genelleþtirilmiþ S(x) fonksiyonu;
 * 
 * if(x == 0) S(x) = 4 else S(x) = (S(x-1)*S(x-1) - 2) % Mp(x+2);
 * 
 * Mp(n) fonksiyonu;
 * 
 * 2^n - 1 iþleminde n'in herhangi bir deðeri için; Örneðin n = 6 olsun.
 * 
 * 2^6 - 1 = 64 - 1 = 63 63 sayýsýnýn binary karþýlýðý 111111 'tür. Yani 6 adet 1 bitinden oluþur.
 * 
 * Yani 2^n - 1 = binary olarak n adet 1 bitinden oluþan sayý demektir.
 */
