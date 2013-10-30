package com.aero.sayilarTeorisi.Arayuz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JProgressBar;

import com.aero.sayilarTeorisi.AsalSayilar.MersennePrimes;
import com.aero.sayilarTeorisi.AsalSayilar.PrimesParentThread;

public class MersennePrimesPanel extends PrimesParentPanel
{
	private static final long serialVersionUID = 1L;
	
	// Sýnýf dýþýndan eriþim için iþlem çubuðunun static kopyasý
	public static JProgressBar _progressBar;
		
	public MersennePrimesPanel()
	{
		super("Sýra no", "Mersenne asallarý ve katsayýlarý");
		
		this.listeUzunluguLabel.setText("Bit uzunluðu :");
		this.listeUzunluguTextField.setText("10000");
		this.StartButton.addActionListener(baslatButtonActionListener());
		
		_progressBar = this.progressBar;
	}
	
	//
	// Baþlat butonu için ActionListener
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
					StartButton.setText("Hesaplanýyor");
					
					// Hesap Thread'inin oluþturulmasý
					HesapThread1 = new MersennePrimesHesap(1);
					HesapThread2 = new MersennePrimesHesap(2);
					HesapThread3 = new MersennePrimesHesap(3);
					HesapThread4 = new MersennePrimesHesap(4);
					
					// Baþlangýçta cpu pulse count:
					startTime = System.nanoTime();
					
					// Sayaç thread'inin oluþturulmasý
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
			
			// Listenin oluþturulmasý
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
			
			System.out.println(this.threadNumber + ". thread tamamlandý.");
			
			if(isCompletedAllThreads())
				completingThreads();
		}
	}
	
}

// Eski-Orjinal MersennePrimesThreda sýnýfý

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
			// Ýkiz asal sayýarýn hesaplanmasý ve hesap süresinin ölçümü
			// Baþlangýçta cpu pulse count:
			startTime = System.nanoTime();
			sayacThread.interrupt();
			sayacThread.start();
			
			// Listenin oluþturulmasý
			//PrimesStringList = MersennePrimes.Generate(Integer.parseInt(listeUzunluguTextField.getText()));
			MersennePrimes.Generate(Integer.parseInt(listeUzunluguTextField.getText()), PrimesListView);
			
			// Bitiþte cpu pulse count:
			finishTime = System.nanoTime();
			sayacThread.Stop();
			
			// Sürenin hesaplanmasý
			this.executeTime = (double) (finishTime - startTime) * Math.pow(10, (-9));
			this.completed = true;
			
			// Listenin ekrandaki tabloya eklenmesi
			//SetToList(PrimesStringList);
			
			StartButton.setEnabled(true);
			StartButton.setText("Baþlat");
			
			// Sonuç mesajýnýn görüntülenmesi
			JOptionPane.showMessageDialog(null, Double.toString(this.executeTime).substring(0, 6) + " sn. sürdü.", "Geçen süre", JOptionPane.INFORMATION_MESSAGE);
		}
	}

*/