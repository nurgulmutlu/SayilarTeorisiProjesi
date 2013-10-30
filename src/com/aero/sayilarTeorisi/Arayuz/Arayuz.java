/*
 * Arayuz.java
 * 
 * SAYILAR TEORÝSÝ DERSÝ PROJESÝ ÝKÝZ ASAL SAYILARIN BULUNMASI BU PROGRAM 3sn. ÝÇERÝSÝNDE ÝLK 100000 ÝKÝZ ASAL SAYIYI
 * LÝSTELEMEYÝ HEDEFLER.
 * 
 * AHMET ERTUÐRUL ÖZCAN 12060715
 * 
 * MÝKAÝL ÞÝMÞEK 13061390
 * 
 * 11.10.2013
 */

package com.aero.sayilarTeorisi.Arayuz;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Arayuz
{
	//
	// Main() metodu
	//
	public static void main(String[] args)
	{
		new Arayuz();
	}
	
	//
	// Ana Pencere
	//
	private JFrame Pencere;
	
	// Paneller
	private JPanel twinPrimesPanel;
	private JPanel mersennePrimesPanel;
	
	// Çok sekmeli panel
	private JTabbedPane tabbedPane;
	
	// Hakkýnda butonu
	private JButton hakkindaButon;
	
	//
	// Constructor - Kurucu metod
	//
	public Arayuz()
	{
		this.Pencere = new JFrame("Sayýlar Teorisi Projesi");
		this.Pencere.setSize(500, Toolkit.getDefaultToolkit().getScreenSize().height - 100);
		this.Pencere.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.Pencere.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.Pencere.getSize().height) / 2);
		this.Pencere.setResizable(false);
		this.Pencere.setLayout(null);
		
		// Panellerin hazýrlanmasý
		this.twinPrimesPanel = new TwinPrimesPanel();
		this.mersennePrimesPanel = new MersennePrimesPanel();
		
		// TabbedPane'in hazýrlanmasý ve sekmelerin eklenmesi
		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.addTab("Ýkiz asal sayýlar", twinPrimesPanel);
		this.tabbedPane.addTab("Mersenne asallarý", mersennePrimesPanel);
		this.tabbedPane.setBounds(0, 0, this.Pencere.getWidth() - 5, this.Pencere.getHeight() - 80);
		this.Pencere.getContentPane().add(tabbedPane);
		
		// Hakkýnda butonu
		this.hakkindaButon = new JButton("Hakkýnda");
		this.hakkindaButon.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(null, "Sayýlar Teorisi\r\n   " + "Proje amacý : \r\n   3sn'de ilk 100000 ikiz asal sayý çiftinin listelenmesi\r\n\r\n\r\n" + "Hazýrlayanlar :\r\n   Ahmet Ertuðrul Özcan\r\n   12060715\r\n\r\n   Mikail Þimþek\r\n   13061390", "Hakkýnda", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		this.hakkindaButon.setBounds(this.Pencere.getSize().width - 130, this.Pencere.getSize().height - 65, 110, 25);
		this.Pencere.getContentPane().add(hakkindaButon);
		
		// Son ayarlar
		this.Pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.Pencere.setVisible(true);
	}
	
}
