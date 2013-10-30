package com.aero.sayilarTeorisi.Arayuz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JProgressBar;

import com.aero.sayilarTeorisi.AsalSayilar.MersennePrimes;
import com.aero.sayilarTeorisi.AsalSayilar.PrimesParentThread;

public class MersennePrimesPanel extends PrimesParentPanel
{
	private static final long serialVersionUID = 1L;
	
	// S�n�f d���ndan eri�im i�in i�lem �ubu�unun static kopyas�
	public static JProgressBar _progressBar;
		
	public MersennePrimesPanel()
	{
		super("S�ra no", "Mersenne asallar� ve katsay�lar�");
		
		this.listeUzunluguLabel.setText("Bit uzunlu�u :");
		this.listeUzunluguTextField.setText("10000");
		this.StartButton.addActionListener(baslatButtonActionListener());
		
		_progressBar = this.progressBar;
	}
	
	//
	// Ba�lat butonu i�in ActionListener
	//
	private ActionListener baslatButtonActionListener()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (uzunlukGirisiUygun)
				{
					StartButton.setEnabled(false);
					StartButton.setText("Hesaplan�yor");
					
					// Hesap Thread'inin olu�turulmas�
					HesapThread1 = new MersennePrimesHesap(1);
					HesapThread2 = new MersennePrimesHesap(2);
					HesapThread3 = new MersennePrimesHesap(3);
					HesapThread4 = new MersennePrimesHesap(4);
					
					// Ba�lang��ta cpu pulse count:
					startTime = System.nanoTime();
					
					// Saya� thread'inin olu�turulmas�
					sayacThread = new KronometreThread();
					
					sayacThread.interrupt();
					sayacThread.start();
					
					HesapThread4.interrupt();
					HesapThread4.start();
					
					HesapThread3.interrupt();
					HesapThread3.start();
					
					HesapThread2.interrupt();
					HesapThread2.start();
					
					HesapThread1.interrupt();
					HesapThread1.start();
				}
			}
		};
	};
	
	private class MersennePrimesHesap extends PrimesParentThread
	{
		public MersennePrimesHesap(int n)
		{
			super(n);
		}
		
		@Override
		public void run()
		{
			int maxbit = Integer.parseInt(listeUzunluguTextField.getText());
			
			// Listenin olu�turulmas�
			switch(this.threadNumber)
			{
				case 1 :
					MersennePrimes.Generate(3, maxbit/100, PrimesStringList, false);
					break;
				case 2 :
					MersennePrimes.Generate(maxbit/100 + 1, maxbit/100, PrimesStringList, false);
					break;
				case 3 :
					MersennePrimes.Generate(2*maxbit/100 + 1, maxbit/100, PrimesStringList, false);
					break;
				case 4 :
					MersennePrimes.Generate(3*maxbit/100 + 1, maxbit/100, PrimesStringList, false);
					break;
			}
			
			this.completed = true;
			
			System.out.println(this.threadNumber + ". thread tamamland�.");
			
			if(isCompletedAllThreads())
				completingThreads();
		}
	}
	
}

// Eski-Orjinal MersennePrimesThreda s�n�f�

/*

private class MersennePrimesHesap extends Thread
	{
		private double executeTime = 0;
		private boolean completed = false;
		
		public double getExecuteTime()
		{
			return this.executeTime;
		}
		
		@Override
		public void run()
		{
			// �kiz asal say�ar�n hesaplanmas� ve hesap s�resinin �l��m�
			// Ba�lang��ta cpu pulse count:
			startTime = System.nanoTime();
			sayacThread.interrupt();
			sayacThread.start();
			
			// Listenin olu�turulmas�
			//PrimesStringList = MersennePrimes.Generate(Integer.parseInt(listeUzunluguTextField.getText()));
			MersennePrimes.Generate(Integer.parseInt(listeUzunluguTextField.getText()), PrimesListView);
			
			// Biti�te cpu pulse count:
			finishTime = System.nanoTime();
			sayacThread.Stop();
			
			// S�renin hesaplanmas�
			this.executeTime = (double) (finishTime - startTime) * Math.pow(10, (-9));
			this.completed = true;
			
			// Listenin ekrandaki tabloya eklenmesi
			//SetToList(PrimesStringList);
			
			StartButton.setEnabled(true);
			StartButton.setText("Ba�lat");
			
			// Sonu� mesaj�n�n g�r�nt�lenmesi
			JOptionPane.showMessageDialog(null, Double.toString(this.executeTime).substring(0, 6) + " sn. s�rd�.", "Ge�en s�re", JOptionPane.INFORMATION_MESSAGE);
		}
	}

*/