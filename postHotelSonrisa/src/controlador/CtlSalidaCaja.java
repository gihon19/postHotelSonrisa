package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import modelo.AbstractJasperReports;
import modelo.Conexion;
import modelo.SalidaCaja;
import modelo.dao.SalidaCajaDao;
import view.ViewSalidaCaja;

public class CtlSalidaCaja implements ActionListener {
	
	private ViewSalidaCaja view;
	private Conexion conexion;
	private SalidaCaja mySalida;
	private SalidaCajaDao myDao;

	public CtlSalidaCaja(ViewSalidaCaja v , Conexion conn) {
		// TODO Auto-generated constructor stub
		conexion=conn;
		view=v;
		
		mySalida=new SalidaCaja();
		
		myDao=new SalidaCajaDao(conexion);
		
		view.conectarControlador(this);
		
		view.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		
		switch(comando){
		
		case "GUARDAR":
			if(validar()){
				setSalida();
				if(myDao.registrarSalida(mySalida)){
					
					try {
						//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Cierre_Caja_Saint_Paul.jasper",1 );
						AbstractJasperReports.createReportSalidaCaja(conexion.getPoolConexion().getConnection(), myDao.getIdRegistrado());
						
						//AbstractJasperReports.Imprimir2();
						//JOptionPane.showMessageDialog(view, "Se realizo el corte correctamente.");
						
						view.setVisible(false);
						AbstractJasperReports.imprimierFactura();
						AbstractJasperReports.showViewer(view);
						
						//this.view.setModal(false);
						//AbstractJasperReports.imprimierFactura();
						
					} catch (SQLException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(view, "Ocurrio un problema para registrar la salida");
				}
				
			}
			
			break;
			
		case "CANCELAR":
			view.setVisible(false);
			break;
		}
		
	}

	private void setSalida() {
		// TODO Auto-generated method stub
		mySalida.setCantidad(new BigDecimal(view.getTxtCantidad().getText()));
		mySalida.setConcepto(view.getTxtConcepto().getText());
	}

	private boolean validar() {
		// TODO Auto-generated method stub
		boolean resul=false;
		if(view.getTxtCantidad().getText().trim().length()==0){
			JOptionPane.showMessageDialog(view, "Debe rellenar todos los campos");
			view.getTxtCantidad().requestFocusInWindow();
		}else
			if(view.getTxtConcepto().getText().trim().length()==0){
				JOptionPane.showMessageDialog(view, "Debe rellenar todos los campos");
				view.getTxtConcepto().requestFocusInWindow();
			}else{
				resul=true;
			}
		return resul;
	}

	
}
