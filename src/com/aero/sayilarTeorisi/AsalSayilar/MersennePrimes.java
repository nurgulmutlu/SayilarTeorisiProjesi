/*
 * MersennePrimes.java
 * 
 * Mersenne asal sayýlarý tespit ve listelemede kullanýlýr. Generate() metodu ile parametre olarak girilen bit uzunluðu
 * deðerine kadar mevcut mersenne sayýlarýný ve katsayýlarýný hesaplamada kullanýlýr.
 * 
 * AHMET ERTUÐRUL ÖZCAN 12060715
 * 
 * MÝKAÝL ÞÝMÞEK 13061390
 * 
 * 12.10.2013
 */

package com.aero.sayilarTeorisi.AsalSayilar;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.aero.sayilarTeorisi.Arayuz.MersennePrimesPanel;
import com.aero.sayilarTeorisi.Arayuz.PrimesParentPanel.ListeTabloModeli;

public abstract class MersennePrimes
{
	public static List<String> Generate(int bitLength)
	{
		List<String> result = new ArrayList<String>();
		
		// Algoritma dýþýnda kaldýðý için ilk mersenne katsayýsý olan 2 elle eklenir.
		result.add("n = " + 2 + ", Mp(" + 2 + ") = " + Mp(2));
		
		int n = 3;
		while (n < bitLength)
		{
			if (isPrime(n))
				if (LucasLehmerPrimalityTest.isPrime(n))
					result.add("n = " + n + ", Mp(" + n + ") = " + Mp(n));
			n += 2;
			
			// Ýþlem çubuðunun güncellenmesi
			MersennePrimesPanel._progressBar.setValue(n*100/bitLength);
		}
		
		return result;
	}
	
	public static void Generate(int bitLength, ListeTabloModeli tablo)
	{
		// Algoritma dýþýnda kaldýðý için ilk mersenne katsayýsý olan 2 elle eklenir.
		tablo.addRow(new Object[] { "1.", "n = " + 2 + ", Mp(" + 2 + ") = " + Mp(2) });
		
		int i = 2;
		int n = 3;
		while (n < bitLength)
		{
			if (isPrime(n))
				if (LucasLehmerPrimalityTest.isPrime(n))
					tablo.addRow(new Object[] { i++ + ".", "n = " + n + ", Mp(" + n + ") = " + Mp(n) });
			n += 2;
			
			// Ýþlem çubuðunun güncellenmesi
			MersennePrimesPanel._progressBar.setValue(n*100/bitLength);
		}
	}
	
	public static void Generate(int startbit, int finishbit, ListeTabloModeli tablo)
	{
		// Algoritma dýþýnda kaldýðý için ilk mersenne katsayýsý olan 2 elle eklenir.
		tablo.addRow(new Object[] { "1.", "n = " + 2 + ", Mp(" + 2 + ") = " + Mp(2) });
				
		int i = 2;		
		int n = startbit;
		while (n < finishbit)
		{
			if (isPrime(n))
				if (LucasLehmerPrimalityTest.isPrime(n))
					tablo.addRow(new Object[] { i++ + ".", "n = " + n + ", Mp(" + n + ") = " + Mp(n) });
			
			n += 2;
		}
	}
	
	public static void Generate(int startbit, int finishbit, List<String> liste)
	{
		
		int n = startbit;
		while (n < finishbit)
		{
			if (isPrime(n))
				if (LucasLehmerPrimalityTest.isPrime(n))
					liste.add("n = " + n);// + ", Mp(" + n + ") = " + Mp(n));
			
			n += 2;
		}
		
		// Ýþlem çubuðunun güncellenmesi
		//MersennePrimesPanel._progressBar.setValue((n-startbit)*100/(finishbit-startbit));
	}
	
	public static void Generate(int startbit, int accrual, List<String> liste, boolean unused)
	{
		int maxbit = 100 * accrual;
		int n = startbit;
		int i = 1;
		
		while(n < maxbit)
		{
			while (i++ < accrual/2)
			{
				if (isPrime(n))
					if (LucasLehmerPrimalityTest.isPrime(n))
						liste.add("n = " + n);// + ", Mp(" + n + ") = " + Mp(n));
				
				n += 2;
			}
			
			n += 3 * accrual;
			i = 1;
		}
		
		
		
		// Ýþlem çubuðunun güncellenmesi
		//MersennePrimesPanel._progressBar.setValue((n-startbit)*100/(finishbit-startbit));
	}
	
	private static BigInteger Mp(int n)
	{
		return BigInteger.ONE.shiftLeft(n).subtract(BigInteger.ONE);
	}
	
	/*
	 * Parametre olarak verilen sayýnýn asal olup olmadýðýný kontrol eder. Verilen sayýnýn karekökü üst limit kabul
	 * edilerek bu limite kadar olan sayýlar içerisinden parametreyi tam bölen olup olmadýðýna bakýlýr. 2 haricindeki
	 * hiçbir çift sayý asal olamayacaðý için çift sayýlarý bölen olarak almaya gerek yoktur. Bu neden le döngü +=2 ile
	 * devam eder.
	 */
	
	private static int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 39, 41, 43, 47, 51, 53, 59,
			61, 67, 71, 73, 79, 83, 89, 97};
	
	public static boolean isPrime(int sayi)
	{
		int max = (int) Math.sqrt(sayi);
		
		/*
		if (sayi % 2 == 0)
			return false;
		*/
		
		if(max > 101)
		{
			for (int i = 0; i < primes.length; i++)
				if (sayi % primes[i] == 0)
					return false;
			
			for (int i = 3; i <= max; i += 2)
				if (sayi % i == 0)
					return false;
		}
		else
		{
			for (int i = 3; i <= max; i += 2)
				if (sayi % i == 0)
					return false;
		}
		
		return true;
	}
}