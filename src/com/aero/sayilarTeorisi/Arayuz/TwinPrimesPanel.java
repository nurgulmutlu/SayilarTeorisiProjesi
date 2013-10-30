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
	
	// S�n�f d���ndan eri�im i�in i�lem �ubu�unun static kopyas�
	public static JProgressBar _progressBar;
	
	public TwinPrimesPanel()
	{
		super("S�ra no", "�kiz asal �iftleri");
		
		this.listeUzunluguLabel.setText("Liste boyutu :");
		this.listeUzunluguTextField.setText("100000");
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
					HesapThread1 = new TwinPrimesHesap(1);
					
					// Saya� thread'inin olu�turulmas�
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
			// �kiz asal say�ar�n hesaplanmas� ve hesap s�resinin �l��m�
			// Ba�lang��ta cpu pulse count:
			startTime = System.nanoTime();
			sayacThread.interrupt();
			sayacThread.start();
			
			// Listenin olu�turulmas�
			PrimesStringList = TwinPrimes.Generate(Integer.parseInt(listeUzunluguTextField.getText()));
			
			// Biti�te cpu pulse count:
			finishTime = System.nanoTime();
			sayacThread.Stop();
			
			// S�renin hesaplanmas�
			double totalTime = (double) (finishTime - startTime) * Math.pow(10, (-9));
			
			// Listenin ekrandaki tabloya eklenmesi
			SetToList(PrimesStringList);
			
			StartButton.setEnabled(true);
			StartButton.setText("Ba�lat");
			
			// Sonu� mesaj�n�n g�r�nt�lenmesi
			JOptionPane.showMessageDialog(null, Double.toString(totalTime).substring(0, 6) + " sn. s�rd�.", "Ge�en s�re", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
