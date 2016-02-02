package view.botones;

import java.awt.Dimension;

import javax.swing.ImageIcon;



public class BotonAgregar extends BotonesApp {
	
	
	public BotonAgregar(){
		
		setIcon(new ImageIcon(BotonAgregar.class.getResource("/view/recursos/plus72.png"))); // NOI18N
		//this.setSize(200, 100);.
		setToolTipText("Agregar");
		//setSize(136, 77);
		
	}

}
