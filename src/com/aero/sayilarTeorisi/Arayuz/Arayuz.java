/*
 * Arayuz.java
 * 
 * SAYILAR TEOR�S� DERS� PROJES� �K�Z ASAL SAYILARIN BULUNMASI BU PROGRAM 3sn. ��ER�S�NDE �LK 100000 �K�Z ASAL SAYIYI
 * L�STELEMEY� HEDEFLER.
 * 
 * AHMET ERTU�RUL �ZCAN 12060715
 * 
 * M�KA�L ��M�EK 13061390
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
	
	// �ok sekmeli panel
	private JTabbedPane tabbedPane;
	
	// Hakk�nda butonu
	private JButton hakkindaButon;
	
	//
	// Constructor - Kurucu metod
	//
	public Arayuz()
	{
		this.Pencere = new JFrame("Say�lar Teorisi Projesi");
		this.Pencere.setSize(500, Toolkit.getDefaultToolkit().getScreenSize().height - 100);
		this.Pencere.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.Pencere.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.Pencere.getSize().height) / 2);
		this.Pencere.setResizable(false);
		this.Pencere.setLayout(null);
		
		// Panellerin haz�rlanmas�
		this.twinPrimesPanel = new TwinPrimesPanel();
		this.mersennePrimesPanel = new MersennePrimesPanel();
		
		// TabbedPane'in haz�rlanmas� ve sekmelerin eklenmesi
		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.addTab("�kiz asal say�lar", twinPrimesPanel);
		this.tabbedPane.addTab("Mersenne asallar�", mersennePrimesPanel);
		this.tabbedPane.setBounds(0, 0, this.Pencere.getWidth() - 5, this.Pencere.getHeight() - 80);
		this.Pencere.getContentPane().add(tabbedPane);
		
		// Hakk�nda butonu
		this.hakkindaButon = new JButton("Hakk�nda");
		this.hakkindaButon.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(null, "Say�lar Teorisi\r\n   " + "Proje amac� : \r\n   3sn'de ilk 100000 ikiz asal say� �iftinin listelenmesi\r\n\r\n\r\n" + "Haz�rlayanlar :\r\n   Ahmet Ertu�rul �zcan\r\n   12060715\r\n\r\n   Mikail �im�ek\r\n   13061390", "Hakk�nda", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		this.hakkindaButon.setBounds(this.Pencere.getSize().width - 130, this.Pencere.getSize().height - 65, 110, 25);
		this.Pencere.getContentPane().add(hakkindaButon);
		
		// Son ayarlar
		this.Pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.Pencere.setVisible(true);
	}
	
}
