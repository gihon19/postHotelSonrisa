package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.AbstractJasperReports;
import modelo.CierreCaja;
import modelo.Conexion;
import modelo.Usuario;
import modelo.dao.CierreCajaDao;
import view.ViewListaCierresCaja;

public class CtlCierresCajaLista implements ActionListener, MouseListener {
	private ViewListaCierresCaja view;
	private Conexion conexion;
	private CierreCajaDao myDao;
	private int filaPulsada;
	private CierreCaja myCierre;

	public CtlCierresCajaLista(ViewListaCierresCaja v,Conexion conn) {
		// TODO Auto-generated constructor stub
		view=v;
		conexion=conn;
		myDao=new CierreCajaDao(conexion);
		myCierre=new CierreCaja();
		view.conectarCtl(this);
		cargarTabla(myDao.todos());
		view.setVisible(true);
	}
	
	private void cargarTabla(List<CierreCaja> cierres) {
		// TODO Auto-generated method stub
		view.getModelo().limpiar();
		for(int x=0;x<cierres.size();x++)
			view.getModelo().agregar(cierres.get(x));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//Recoger qu� fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTabla().getSelectedRow();
        
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	myCierre=view.getModelo().getCierreCaja(filaPulsada);
        	
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		try {
        			AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 4, myCierre.getId());
        			AbstractJasperReports.showViewer(this.view);
        			
        		}catch (SQLException ee) {
    				// TODO Auto-generated catch block
    				ee.printStackTrace();
    			}
        	}
        }
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		
		switch(comando){
		
		case "IMPRIMIR":
			try {
				//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Cierre_Caja_Saint_Paul.jasper",1 );
				AbstractJasperReports.createReportCierresCaja(conexion.getPoolConexion().getConnection(), conexion.getUsuarioLogin().getUser());
				
				//this.view.setModal(false);
				//AbstractJasperReports.imprimierFactura();
				AbstractJasperReports.showViewer(this.view);
				
				
			} catch (SQLException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
			break;
		case "BUSCAR":
			if(view.getRdbtnTodos().isSelected()){
				cargarTabla(myDao.todos());
			}
			
			if(view.getRdbtnFechas().isSelected()){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date1 = sdf.format(this.view.getBuscar1().getDate());
				String date2 = sdf.format(this.view.getBuscar2().getDate());
				
				//JOptionPane.showMessageDialog(view, date1+" al  "+date2);
				cargarTabla(myDao.porFecha(date1,date2));
			}
			break;
		}
		
	}

}
