package view.botones;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class BotonActualizar extends BotonesApp {

	public BotonActualizar() {
		super("Actualizar");
		this.setSize(136, 77);
		this.setPreferredSize(new Dimension(136, 77));
		// TODO Auto-generated constructor stub
		this.setIcon(new ImageIcon(BotonActualizar.class.getResource("/view/recursos/actualizar.png")));
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
	}

}
