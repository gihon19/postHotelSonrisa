package view.botones;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class BotonesApp extends JButton {
	private static final int ancho=128;
	private static final int alto=45;
	private static Dimension dim=new Dimension(ancho,alto);
	private static Font myFon;
	
	public BotonesApp(){
		this.setSize(ancho, alto);
		setBoton();
		
	}
	public BotonesApp(String titulo){
		super(titulo);
		setBoton();
		
	}
	
	private void setBoton(){
		this.setSize(ancho, alto);
		//this.setPreferredSize(new Dimension(ancho, alto));
		myFon=new Font("Georgia", Font.PLAIN, 13);
		this.setFont(myFon);
		Color color1 =new Color(60, 179, 113);
		setBackground(color1);
	}
	

}
