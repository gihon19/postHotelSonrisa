package view.botones;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class BotonBuscar1  extends BotonesApp {
	
private ImageIcon imgGuardar;
	
	
public BotonBuscar1(){
		super("F1 Buscar");
		
		/*imgGuardar=new ImageIcon(BotonCancelar.class.getResource("/view/recursos/buscar_2.png"));
		
		 Image image = imgGuardar.getImage();
		    // reduce by 50%
		 image = image.getScaledInstance(image.getWidth(null)/4, image.getHeight(null)/4, Image.SCALE_SMOOTH);
		 imgGuardar.setImage(image);
	
		this.setIcon(imgGuardar);*/
		this.setIcon(new ImageIcon(BotonBuscar1.class.getResource("/view/recursos/buscar.png")));
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setPreferredSize(new Dimension(128,200));
		
			
		
	}
	

}
