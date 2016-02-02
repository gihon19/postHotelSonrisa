package view.botones;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;



public class BotonGuardar extends BotonesApp {
	private ImageIcon imgGuardar;
	
	
	public BotonGuardar(){
		super("Guardar");
		
		/*imgGuardar=new ImageIcon(BotonCancelar.class.getResource("/view/recursos/guardar_2.png"));
		
		 Image image = imgGuardar.getImage();
		    // reduce by 50%
		 image = image.getScaledInstance(image.getWidth(null)/2, image.getHeight(null)/2, Image.SCALE_SMOOTH);
		 imgGuardar.setImage(image);
	
		this.setIcon(imgGuardar);*/
		this.setSize(136, 77);
		this.setPreferredSize(new Dimension(136, 77));
		
		this.setIcon(new ImageIcon(BotonGuardar.class.getResource("/view/recursos/guardar.png")));
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
			
		
	}
	
	
	
  

}
