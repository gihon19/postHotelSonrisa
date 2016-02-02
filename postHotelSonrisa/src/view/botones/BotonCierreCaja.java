package view.botones;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class BotonCierreCaja extends BotonesApp {

	public BotonCierreCaja() {
		// TODO Auto-generated constructor stub
		super("Pendientes");
		
		this.setIcon(new ImageIcon(BotonCierreCaja.class.getResource("/view/recursos/cierre.png")));
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
	}

}
