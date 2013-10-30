package com.aero.sayilarTeorisi.Arayuz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import com.aero.sayilarTeorisi.AsalSayilar.PrimesParentThread;
import com.aero.sayilarTeorisi.AsalSayilar.TwinPrimes;


public class TwinPrimesPanel extends PrimesParentPanel
{
	private static final long serialVersionUID = 1L;
	
	// Sýnýf dýþýndan eriþim için iþlem çubuðunun static kopyasý
	public static JProgressBar _progressBar;
	
	public TwinPrimesPanel()
	{
		super("Sýra no", "Ýkiz asal çiftleri");
		
		this.listeUzunluguLabel.setText("Liste boyutu :");
		this.listeUzunluguTextField.setText("100000");
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
					HesapThread1 = new TwinPrimesHesap(1);
					
					// Sayaç thread'inin oluþturulmasý
					sayacThread = new KronometreThread();
					
					HesapThread1.interrupt();
					HesapThread1.start();
				}
			}
		};
	};
	
	private class TwinPrimesHesap extends PrimesParentThread
	{
		public TwinPrimesHesap(int n)
		{
			super(n);
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
			PrimesStringList = TwinPrimes.Generate(Integer.parseInt(listeUzunluguTextField.getText()));
			
			// Bitiþte cpu pulse count:
			finishTime = System.nanoTime();
			sayacThread.Stop();
			
			// Sürenin hesaplanmasý
			double totalTime = (double) (finishTime - startTime) * Math.pow(10, (-9));
			
			// Listenin ekrandaki tabloya eklenmesi
			SetToList(PrimesStringList);
			
			StartButton.setEnabled(true);
			StartButton.setText("Baþlat");
			
			// Sonuç mesajýnýn görüntülenmesi
			JOptionPane.showMessageDialog(null, Double.toString(totalTime).substring(0, 6) + " sn. sürdü.", "Geçen süre", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
