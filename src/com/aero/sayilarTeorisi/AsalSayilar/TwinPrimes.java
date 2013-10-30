/*
 * TwinPrimes.java
 * 
 * �kiz asal say�lar� tespit ve listelemede kullan�l�r. Generate() metodu ile parametre olarak girilen de�er say�s�nca
 * ikiz asal say� �ifti hesaplamada kullan�l�r.
 * 
 * AHMET ERTU�RUL �ZCAN 12060715
 * 
 * M�KA�L ��M�EK 13061390
 * 
 * 11.10.2013
 */

package com.aero.sayilarTeorisi.AsalSayilar;

import java.util.ArrayList;
import java.util.List;

import com.aero.sayilarTeorisi.Arayuz.TwinPrimesPanel;

public abstract class TwinPrimes
{
	//
	// Generate() metodu;
	// �stenilen uzunlukta ikiz asal say�lar listesi olu�turmak i�in kullan�l�r.
	// Parametre olarak liste i�in istenilen uzunluk de�eri girilir.
	//
	public static List<String> Generate(int maxindex)
	{
		// Sonu� listesi
		List<String> result = new ArrayList<String>();
		
		// Asal say� �ifti
		int sayi1, sayi2;
		
		// �lk 2 ikiz asal say� �iftinin eklenmesi
		// Not : (3, 5) ikiz asal �ifti (6n-1, 6n+1) ilkesi d���nda kald��� i�in; (5, 7) ikiz asal �ifti ise
		// algoritma gere�ince 5'in katlar�n�n listede ard���l olarak devam etmesi sebebiyle asall�k testinden
		// ge�emeyece�i i�in
		// listeye ayr�yeten eklenir.
		result.add("( " + 3 + ", " + 5 + " )");
		result.add("( " + 5 + ", " + 7 + " )");
		
		// �lk iki ikiz asal �ifti elle eklendi�i i�in maxindex-2'ye kadar kontrol yeterlidir.
		maxindex -= 2;
		
		// �kiz asal say�lar�n tespiti i�in (6n-1, 6n+1) ilkesi kullan�l�r.
		// i de�i�keni 1'den maxindex'e kadar istenilen say�da ikiz asal say�n�n tespitinde kontrol i�in kullan�l�r.
		// n de�i�keni ise formuldeki yerine yerle�tirilerek bulunan say�lar�n asal olup olmad��� kontrol edilir.
		int i = 1;
		int n = 1;
		while (i <= maxindex)
		{
			sayi1 = 6 * n - 1;
			sayi2 = sayi1 + 2;
			n++;
			
			// Listenin ba�lang�� durumunda a�a��daki durumlarda bulunan say� �iftlerinin ikiz asal olmad��� tespit
			// edilmi�tir.
			
			/*
			 * 1) 7,12,17,22,.... �eklinde devam eden 5n+2 indisli �iftler 2) 5'in tam kat� indisli �iftler 3)
			 * 9,16,23,.... �eklinde devam eden 7n+2 indisli �iftler 4) 7'nin tam kat� olan �iftler 5) 11'in tam kat�
			 * olan �iftler 6) 13'in tam kat� olan �iftler
			 */
			
			// Algoritma, asall���n listede ard���l olarak bozulmas�n� kullanarak, bu durumlardan birine d��en �iftleri
			// sonu� listesine alm�yor.
			if (((n - 2) % 5 == 0) || (n % 5 == 0) || ((n - 2) % 7 == 0) || (n % 7 == 0))
				continue;
			else
			{
				// Geriye kalan say� �iftleri i�in isPrime() metodu ile asall�k kontrol� yap�l�yor.
				// Asal olmayanlar sonu� listesine al�nm�yor.
				if (isPrime(sayi1) && isPrime(sayi2))
				{
					i++;
					result.add("( " + sayi1 + ", " + sayi2 + " )");
					
					// ��lem �ubu�unun g�ncellenmesi
					TwinPrimesPanel._progressBar.setValue(i*100/maxindex);
				}
				else
					continue;
			}
		}
		
		return result;
	}
	
	/*
	 * Parametre olarak verilen say�n�n asal olup olmad���n� kontrol eder. Verilen say�n�n karek�k� �st limit kabul
	 * edilerek bu limite kadar olan say�lar i�erisinden parametreyi tam b�len olup olmad���na bak�l�r. 2 haricindeki
	 * hi�bir �ift say� asal olamayaca�� i�in �ift say�lar� b�len olarak almaya gerek yoktur. Bu neden le d�ng� +=2 ile
	 * devam eder.
	 */
	public static boolean isPrime(int sayi)
	{
		int max = (int) Math.sqrt(sayi);
		
		// 6*n-1 veya 6*n+1 'den �ift say� gelmeyece�i i�in bu kontrole ihtiya� yoktur.
		/*
		 * if(sayi % 2 == 0) return false;
		 */
		
		for (int i = 3; i <= max; i += 2)
			if (sayi % i == 0)
				return false;
		return true;
	}
}

/*
 * Ara�t�r:
 * 
 * miller rabin primality test Fermat asallik testi miracl, gmplib, big integer library 23. Mersenne asallik test kac
 * ms? generalized mersenne prime bouncy castle
 */
