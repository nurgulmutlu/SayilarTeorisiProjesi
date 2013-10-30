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
	
	// Hesaplama iþleminde kullanýlacak olan Thread
	protected PrimesParentThread HesapThread1;
	protected PrimesParentThread HesapThread2;
	protected PrimesParentThread HesapThread3;
	protected PrimesParentThread HesapThread4;
	
	// Süre ölçümü için
	protected long startTime;
	protected long finishTime;
	
	// Üretilen asal sayýlar listesi için ListeTabloModeli ve JTable tablo modeli
	protected ListeTabloModeli PrimesListView;
	protected JTable PrimesTablo;
	
	// Hesaplanacak asal sayýlar için liste uzunlugu - bit uzunluðu label
	protected JLabel listeUzunluguLabel;
	
	// Liste uzunkugunun girileceði text field
	protected JTextField listeUzunluguTextField;
	
	// Hesaplama iþlemini baþlat butonu
	protected JButton StartButton;

	// Tamamlanan iþlem yüzdesi
	protected JProgressBar progressBar;
	
	// Zaman sayacý - kronometre thread'i
	protected JLabel sayacLabel;
	protected KronometreThread sayacThread;
	
	// Asal sayýlar listesi
	protected List<String> PrimesStringList;
	
	// Liste boyutu deðerinin doðru girildiðine ait kontrol
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
		// Text field içerisine girilen deðerin doðru biçimde sayýsal bir deðer olmasýný garantiler.
		// Ayrýca girilen deðerin 0'dan büyük olmasýný da saðlar.
		// Yanlýþ deðer girildiðinde iþleme baþlanmaz.
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
		
		// Baþlat butonunun eklenmesi
		this.StartButton = new JButton("Baþlat");
		this.StartButton.setBounds(330, 100, 150, 30);
		this.add(StartButton);
		
		// Sayaç labelinin eklenmesi
		this.sayacLabel = new JLabel("0.0 sn.");
		this.sayacLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		this.sayacLabel.setBounds(330, 150, 150, 30);
		this.add(sayacLabel);
		
		// Listenin oluþturulmasý
		this.PrimesListView = new ListeTabloModeli(this.column1Name, this.column2Name);
		PrimesTablo = new JTable(PrimesListView);
		// Sütun geniþliðinin ayarlanmasý
		TableColumn sutun1 = PrimesTablo.getColumnModel().getColumn(0);
		sutun1.setPreferredWidth(2);
		sutun1.setWidth(2);
		// Tablonun bir scrollPane üzerine oturtulmasý
		JScrollPane listScroller = new JScrollPane(PrimesTablo);
		listScroller.setPreferredSize(new Dimension(300, 500));
		listScroller.setBounds(20, 20, 300, 500);
		// ScrollPane'in ana panele eklenmesi
		this.add(listScroller);

		// Ýþlem çubuðunun hazýrlanmasý
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
		// Bitiþte cpu pulse count:
		finishTime = System.nanoTime();
		sayacThread.Stop();
		
		// Sürenin hesaplanmasý
		double executeTime = (double) (finishTime - startTime) * Math.pow(10, (-9));
		
		// Tüm listelerin birleþtirilmesi;
		SetToList(this.PrimesStringList);
		
		StartButton.setEnabled(true);
		StartButton.setText("Baþlat");
		
		JOptionPane.showMessageDialog(null, Double.toString(executeTime).substring(0, 6) + " sn. sürdü.", "Geçen süre", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//
	// Listeye satýr eklemek için kullanýlýr. 1. sütun sýra no, 2. sütun ise asal sayý için kullanýlýr.
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
	// Kullanýlacak tablo için model hazýrlanmasý
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
