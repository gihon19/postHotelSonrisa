package view.botones;

import javax.swing.ImageIcon;

public class BotonEliminar extends BotonesApp {
	
	public BotonEliminar(){
		setIcon(new ImageIcon(BotonEliminar.class.getResource("/view/recursos/recycle43.png"))); 
		setToolTipText("Eliminar");
	}

}
