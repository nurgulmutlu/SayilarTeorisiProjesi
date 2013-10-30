package com.aero.sayilarTeorisi.Arayuz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.aero.sayilarTeorisi.AsalSayilar.PrimesParentThread;

public abstract class PrimesParentPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	// Hesaplama i�leminde kullan�lacak olan Thread
	protected PrimesParentThread HesapThread1;
	protected PrimesParentThread HesapThread2;
	protected PrimesParentThread HesapThread3;
	protected PrimesParentThread HesapThread4;
	
	// S�re �l��m� i�in
	protected long startTime;
	protected long finishTime;
	
	// �retilen asal say�lar listesi i�in ListeTabloModeli ve JTable tablo modeli
	protected ListeTabloModeli PrimesListView;
	protected JTable PrimesTablo;
	
	// Hesaplanacak asal say�lar i�in liste uzunlugu - bit uzunlu�u label
	protected JLabel listeUzunluguLabel;
	
	// Liste uzunkugunun girilece�i text field
	protected JTextField listeUzunluguTextField;
	
	// Hesaplama i�lemini ba�lat butonu
	protected JButton StartButton;

	// Tamamlanan i�lem y�zdesi
	protected JProgressBar progressBar;
	
	// Zaman sayac� - kronometre thread'i
	protected JLabel sayacLabel;
	protected KronometreThread sayacThread;
	
	// Asal say�lar listesi
	protected List<String> PrimesStringList;
	
	// Liste boyutu de�erinin do�ru girildi�ine ait kontrol
	protected boolean uzunlukGirisiUygun = true;
	
	private String column1Name;
	private String column2Name;
	
	public PrimesParentPanel(String c1, String c2)
	{
		super();
		
		this.setLayout(null);
		
		PrimesStringList = new ArrayList<String>();
		
		this.column1Name = c1;
		this.column2Name = c2;
		
		// Liste boyutu label
		this.listeUzunluguLabel = new JLabel("Liste boyutu :");
		this.listeUzunluguLabel.setBounds(330, 35, 120, 30);
		this.add(listeUzunluguLabel);
		
		// listeUzunlugu TextField eklenmesi
		this.listeUzunluguTextField = new JTextField("100000");
		// Text field i�erisine girilen de�erin do�ru bi�imde say�sal bir de�er olmas�n� garantiler.
		// Ayr�ca girilen de�erin 0'dan b�y�k olmas�n� da sa�lar.
		// Yanl�� de�er girildi�inde i�leme ba�lanmaz.
		this.listeUzunluguTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				testInput();
			}
			
			public void removeUpdate(DocumentEvent e)
			{
				testInput();
			}
			
			public void insertUpdate(DocumentEvent e)
			{
				testInput();
			}
			
			public void testInput()
			{
				int sayi = 0;
				try
				{
					sayi = Integer.parseInt(listeUzunluguTextField.getText());
				}
				catch (NumberFormatException ex)
				{
					uzunlukGirisiUygun = false;
					listeUzunluguTextField.setBackground(Color.RED);
					StartButton.setEnabled(false);
				}
				finally
				{
					if (sayi <= 0)
					{
						uzunlukGirisiUygun = false;
						listeUzunluguTextField.setBackground(Color.RED);
						StartButton.setEnabled(false);
					}
					else
					{
						uzunlukGirisiUygun = true;
						listeUzunluguTextField.setBackground(Color.WHITE);
						StartButton.setEnabled(true);
					}
				}
			}
		});
		this.listeUzunluguTextField.setBounds(330, 60, 120, 30);
		this.add(listeUzunluguTextField);
		
		// Ba�lat butonunun eklenmesi
		this.StartButton = new JButton("Ba�lat");
		this.StartButton.setBounds(330, 100, 150, 30);
		this.add(StartButton);
		
		// Saya� labelinin eklenmesi
		this.sayacLabel = new JLabel("0.0 sn.");
		this.sayacLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		this.sayacLabel.setBounds(330, 150, 150, 30);
		this.add(sayacLabel);
		
		// Listenin olu�turulmas�
		this.PrimesListView = new ListeTabloModeli(this.column1Name, this.column2Name);
		PrimesTablo = new JTable(PrimesListView);
		// S�tun geni�li�inin ayarlanmas�
		TableColumn sutun1 = PrimesTablo.getColumnModel().getColumn(0);
		sutun1.setPreferredWidth(2);
		sutun1.setWidth(2);
		// Tablonun bir scrollPane �zerine oturtulmas�
		JScrollPane listScroller = new JScrollPane(PrimesTablo);
		listScroller.setPreferredSize(new Dimension(300, 500));
		listScroller.setBounds(20, 20, 300, 500);
		// ScrollPane'in ana panele eklenmesi
		this.add(listScroller);

		// ��lem �ubu�unun haz�rlanmas�
		this.progressBar = new JProgressBar(0, 100);
		this.progressBar.setValue(0);
		this.progressBar.setStringPainted(true);
		this.progressBar.setBounds(20, 530, 450, 20);
		this.add(this.progressBar);
	}
	
	public boolean isCompletedAllThreads()
	{
		return (this.HesapThread1.completed && this.HesapThread2.completed && this.HesapThread3.completed && this.HesapThread4.completed);
	}
	
	public void completingThreads()
	{
		// Biti�te cpu pulse count:
		finishTime = System.nanoTime();
		sayacThread.Stop();
		
		// S�renin hesaplanmas�
		double executeTime = (double) (finishTime - startTime) * Math.pow(10, (-9));
		
		// T�m listelerin birle�tirilmesi;
		SetToList(this.PrimesStringList);
		
		StartButton.setEnabled(true);
		StartButton.setText("Ba�lat");
		
		JOptionPane.showMessageDialog(null, Double.toString(executeTime).substring(0, 6) + " sn. s�rd�.", "Ge�en s�re", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//
	// Listeye sat�r eklemek i�in kullan�l�r. 1. s�tun s�ra no, 2. s�tun ise asal say� i�in kullan�l�r.
	//
	protected void AddToList(String c1, String c2)
	{
		this.PrimesListView.addRow(new Object[] { c1, c2 });
	}
	
	protected void AddToList(List<String> liste)
	{
		int i = 0;
		while (i < liste.size())
		{
			this.AddToList((i + 1) + ".", liste.get(i));
			i++;
		}
	}
	
	// Listeyi temizler.
	protected void ClearList()
	{
		this.PrimesListView = new ListeTabloModeli(this.column1Name, this.column2Name);
		this.PrimesTablo.setModel(this.PrimesListView);
		
		// Eski liste bellekten silinir.
		System.gc();
	}
	
	protected void SetToList(List<String> list)
	{
		ClearList();
		
		int i = 0;
		while (i < list.size())
		{
			this.AddToList((i + 1) + ".", list.get(i));
			i++;
		}
	}
	
	//
	// Kullan�lacak tablo i�in model haz�rlanmas�
	//
	public class ListeTabloModeli extends DefaultTableModel
	{
		private static final long serialVersionUID = 1L;
		
		public ListeTabloModeli(String c1, String c2)
		{
			this.addColumn(c1);
			this.addColumn(c2);
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex)
		{
			switch (columnIndex)
			{
				case 0:
					return int.class;
				case 1:
					return String.class;
			}
			return null;
		}
	}
	
	class KronometreThread extends Thread
	{
		private double startTime;
		private boolean isInterrupt = false;
		
		@Override
		public void run()
		{
			this.startTime = System.nanoTime();
			
			while(!isInterrupt)
			{
				double count = (System.nanoTime() - startTime) / Math.pow(10, 9);
				BigDecimal temp = new BigDecimal(count);
				count = temp.setScale(1, BigDecimal.ROUND_UP).doubleValue();
				if(count < 60)
					sayacLabel.setText(count + " sn.");
				else
				{
					double saniye = count % 60;
					int dakika = (int) ((count - saniye) / 60);
					temp = new BigDecimal(saniye);
					saniye = temp.setScale(1, BigDecimal.ROUND_UP).doubleValue();
					
					sayacLabel.setText(dakika + " dk. " + saniye + " sn.");
				}
			}
		}
		
		public void Stop()
		{
			isInterrupt = true;
		}
	}
}
