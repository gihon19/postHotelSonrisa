package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;

import javax.swing.JOptionPane;

import modelo.CodBarra;
import view.ViewCambioPago;

public class CtlCambioPago implements ActionListener,ItemListener, WindowListener ,KeyListener{
	private ViewCambioPago view=null;
	private boolean estadoPago=false;
	private BigDecimal efectivo;
	private BigDecimal cambio;
	private int formaPago=1;
	private String refencia;
	
	private BigDecimal total;
	
	
	public CtlCambioPago(ViewCambioPago v,BigDecimal t){
		total=t;
		view=v;
		view.conectarCtl(this);
		view.getTxtEfectivo().requestFocusInWindow();
		//view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		switch (comando){
		case "CAMBIO":
			
			try{
				efectivo=new BigDecimal(view.getTxtEfectivo().getText());
				if(efectivo.doubleValue()  >=total.doubleValue()){
					estadoPago=true;
				}else{
					JOptionPane.showMessageDialog(view, "Cantidad de efectivo incorrecta","Error",JOptionPane.ERROR_MESSAGE);
					estadoPago=false;
				}
				
			}catch(NumberFormatException ee){
				estadoPago=false;
				JOptionPane.showMessageDialog(view, "Escriba un cantidad valida","Error",JOptionPane.ERROR_MESSAGE);
				view.getTxtEfectivo().setText("");
				view.getTxtEfectivo().requestFocusInWindow();
			}
			//parseBigDecimal( view.getTxtEfectivo().getText());
			if(this.estadoPago){ 
				cambio=efectivo.subtract(total);
				view.getTxtCambio().setText(""+cambio.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				/*
				int resul=JOptionPane.showConfirmDialog(view, "¿Imprimir Factura?","Confirmar factura", JOptionPane.YES_NO_OPTION);
				//sin confirmo la anulacion
				if(resul==0){
					this.cobrar();
					
				}*/
				view.getTxtCambio().requestFocusInWindow();
				//view.getBtnCobrar().requestFocusInWindow();
			}
			
		break;
		case "CERRAR":
			salir();
			break;
		case "COBRAR":
			cobrar();
			break;
		case "IMPRIMIR":
			this.cobrar();
			break;
		}
	}
	
	public int getFormaPago(){
		return formaPago;
	}
	public String getRefencia(){
		return refencia;
	}

	public boolean pagar() {
		// TODO Auto-generated method stub
		view.setVisible(true);
		return estadoPago;
	}
	public BigDecimal getEfectivo(){
		return efectivo;
	}
	public BigDecimal getCambio(){
		return cambio;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(view.getTglbtnEfectivo().isSelected()){
			//JOptionPane.showMessageDialog(view, "Efectivo");
			view.getPanelTarjeta().setVisible(true);
			view.getPanelEfectivo().setVisible(false);
			view.getTxtEfectivo().requestFocusInWindow();
		}
		
		if(view.getTglbtnTarjetaCredito().isSelected()){
			view.getPanelEfectivo().setVisible(true);
			view.getPanelTarjeta().setVisible(false);
			view.getTxtReferencia().requestFocusInWindow();
			
			//JOptionPane.showMessageDialog(view, "Tarjeta Credito");
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
		salir();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			salir();
		}
		if(e.getKeyCode()==KeyEvent.VK_F2){
			cobrar();
		}
		
		
		
	}

	private void salir() {
		// TODO Auto-generated method stub
		estadoPago=false;
		this.view.setVisible(false);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_F3){
			cobrar();
		}
	}

	private void cobrar() {
		// TODO Auto-generated method stub

		
		if(view.getTglbtnEfectivo().isSelected()){
			if(estadoPago){
				this.formaPago=1;
				this.view.setVisible(false);
			}else{
				view.getTxtEfectivo().requestFocusInWindow();
				view.getToolkit().beep();
			}
			
		}
		if(view.getTglbtnTarjetaCredito().isSelected()){
			if(view.getTxtReferencia().getText().trim().length()==0){
				
				view.getTxtReferencia().requestFocusInWindow();
				JOptionPane.showMessageDialog(view, "Escriba la referencia de pago","Error",JOptionPane.ERROR_MESSAGE);
			}else{
				this.formaPago=2;
				this.refencia=view.getTxtReferencia().getText();
				this.estadoPago=true;
				view.setVisible(false);
			}
				
			
		}
		
	
	}

}
