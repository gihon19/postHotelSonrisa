package view.botones;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class BotonPendientes extends BotonesApp {

	public BotonPendientes() {
		// TODO Auto-generated constructor stub
		super("Pendientes");
		
		this.setIcon(new ImageIcon(BotonPendientes.class.getResource("/view/recursos/pendientes.png")));
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
	}

}
