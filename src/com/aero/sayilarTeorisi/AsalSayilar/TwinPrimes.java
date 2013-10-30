/*
 * TwinPrimes.java
 * 
 * Ýkiz asal sayýlarý tespit ve listelemede kullanýlýr. Generate() metodu ile parametre olarak girilen deðer sayýsýnca
 * ikiz asal sayý çifti hesaplamada kullanýlýr.
 * 
 * AHMET ERTUÐRUL ÖZCAN 12060715
 * 
 * MÝKAÝL ÞÝMÞEK 13061390
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
	// Ýstenilen uzunlukta ikiz asal sayýlar listesi oluþturmak için kullanýlýr.
	// Parametre olarak liste için istenilen uzunluk deðeri girilir.
	//
	public static List<String> Generate(int maxindex)
	{
		// Sonuç listesi
		List<String> result = new ArrayList<String>();
		
		// Asal sayý çifti
		int sayi1, sayi2;
		
		// Ýlk 2 ikiz asal sayý çiftinin eklenmesi
		// Not : (3, 5) ikiz asal çifti (6n-1, 6n+1) ilkesi dýþýnda kaldýðý için; (5, 7) ikiz asal çifti ise
		// algoritma gereðince 5'in katlarýnýn listede ardýþýl olarak devam etmesi sebebiyle asallýk testinden
		// geçemeyeceði için
		// listeye ayrýyeten eklenir.
		result.add("( " + 3 + ", " + 5 + " )");
		result.add("( " + 5 + ", " + 7 + " )");
		
		// Ýlk iki ikiz asal çifti elle eklendiði için maxindex-2'ye kadar kontrol yeterlidir.
		maxindex -= 2;
		
		// Ýkiz asal sayýlarýn tespiti için (6n-1, 6n+1) ilkesi kullanýlýr.
		// i deðiþkeni 1'den maxindex'e kadar istenilen sayýda ikiz asal sayýnýn tespitinde kontrol için kullanýlýr.
		// n deðiþkeni ise formuldeki yerine yerleþtirilerek bulunan sayýlarýn asal olup olmadýðý kontrol edilir.
		int i = 1;
		int n = 1;
		while (i <= maxindex)
		{
			sayi1 = 6 * n - 1;
			sayi2 = sayi1 + 2;
			n++;
			
			// Listenin baþlangýç durumunda aþaðýdaki durumlarda bulunan sayý çiftlerinin ikiz asal olmadýðý tespit
			// edilmiþtir.
			
			/*
			 * 1) 7,12,17,22,.... þeklinde devam eden 5n+2 indisli çiftler 2) 5'in tam katý indisli çiftler 3)
			 * 9,16,23,.... þeklinde devam eden 7n+2 indisli çiftler 4) 7'nin tam katý olan çiftler 5) 11'in tam katý
			 * olan çiftler 6) 13'in tam katý olan çiftler
			 */
			
			// Algoritma, asallýðýn listede ardýþýl olarak bozulmasýný kullanarak, bu durumlardan birine düþen çiftleri
			// sonuç listesine almýyor.
			if (((n - 2) % 5 == 0) || (n % 5 == 0) || ((n - 2) % 7 == 0) || (n % 7 == 0))
				continue;
			else
			{
				// Geriye kalan sayý çiftleri için isPrime() metodu ile asallýk kontrolü yapýlýyor.
				// Asal olmayanlar sonuç listesine alýnmýyor.
				if (isPrime(sayi1) && isPrime(sayi2))
				{
					i++;
					result.add("( " + sayi1 + ", " + sayi2 + " )");
					
					// Ýþlem çubuðunun güncellenmesi
					TwinPrimesPanel._progressBar.setValue(i*100/maxindex);
				}
				else
					continue;
			}
		}
		
		return result;
	}
	
	/*
	 * Parametre olarak verilen sayýnýn asal olup olmadýðýný kontrol eder. Verilen sayýnýn karekökü üst limit kabul
	 * edilerek bu limite kadar olan sayýlar içerisinden parametreyi tam bölen olup olmadýðýna bakýlýr. 2 haricindeki
	 * hiçbir çift sayý asal olamayacaðý için çift sayýlarý bölen olarak almaya gerek yoktur. Bu neden le döngü +=2 ile
	 * devam eder.
	 */
	public static boolean isPrime(int sayi)
	{
		int max = (int) Math.sqrt(sayi);
		
		// 6*n-1 veya 6*n+1 'den çift sayý gelmeyeceði için bu kontrole ihtiyaç yoktur.
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
 * Araþtýr:
 * 
 * miller rabin primality test Fermat asallik testi miracl, gmplib, big integer library 23. Mersenne asallik test kac
 * ms? generalized mersenne prime bouncy castle
 */
