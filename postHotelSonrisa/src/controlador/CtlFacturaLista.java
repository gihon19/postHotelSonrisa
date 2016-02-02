package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.AbstractJasperReports;
import modelo.Articulo;
import modelo.Conexion;
import modelo.Factura;
import modelo.dao.FacturaDao;
import view.ViewFacturar;
import view.ViewListaFactura;

public class CtlFacturaLista implements ActionListener, MouseListener {
	
	private Conexion conexion=null;
	private ViewListaFactura view=null;
	private FacturaDao myFacturaDao=null;
	private int filaPulsada;
	private Factura myFactura;
	
	public CtlFacturaLista(){
		
	}

	public CtlFacturaLista(ViewListaFactura v, Conexion conn) {
		// TODO Auto-generated constructor stub
		view=v;
		view.conectarControlador(this);
		conexion=conn;
		myFacturaDao=new FacturaDao(conexion);
		cargarTabla(myFacturaDao.facturasEnProceso());
		myFactura=new Factura();
		
		view.setVisible(true);
	}
	
	public void cargarTabla(List<Factura> facturas){
		//JOptionPane.showMessageDialog(view, " "+facturas.size());
		this.view.getModelo().limpiarFacturas();
		
		if(facturas!=null){
			for(int c=0;c<facturas.size();c++){
				this.view.getModelo().agregarFactura(facturas.get(c));
				
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		filaPulsada=this.view.getTablaFacturas().getSelectedRow();
		
		 //si seleccion una fila
        if(filaPulsada>=0){
        	
        	
        	//se consigue el proveedore de la fila seleccionada
        	myFactura=this.view.getModelo().getFactura(filaPulsada);
        	
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		
        		ViewFacturar viewFacturar=new ViewFacturar(this.view);
        		CtlFacturar ctlFacturar=new CtlFacturar(viewFacturar,conexion);
        		
        		viewFacturar.getBtnPendientes().setEnabled(false);
        		
        		//viewFacturar.getBtnCobrar()
        		
        		//si se cobro la factura se debe eleminiar el temp por eso se guarda el id
        		int idFactura=myFactura.getIdFactura();
        		
        		//se llama al controlador de la factura para que la muestre 
        		myFactura=ctlFacturar.actualizarFactura(myFactura);
        		
        		
        		//si la factura se cobro se regresara null sino modificamos la factura en la lista
        		if(myFactura==null){
        			this.view.getModelo().eliminarFactura(filaPulsada);
        			myFacturaDao.EliminarTemp(idFactura);
        		}else{
        			this.view.getModelo().cambiarArticulo(filaPulsada, myFactura);
        			this.view.getTablaFacturas().getSelectionModel().setSelectionInterval(filaPulsada,filaPulsada);//se seleciona lo cambiado
        		}
        		viewFacturar.dispose();
        		ctlFacturar=null;
        		
        	}//fin del if del doble click
        	else{//si solo seleccion la fila se guarda el id de proveedor para accion de eliminar
        		
        		this.view.getBtnEliminar().setEnabled(true);
        		//this.view.getBtnCobrar().setEnabled(true);
        		/*idProveedor=identificador;
        		filaTabla=filaPulsada;*/
        		
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
	
private void cobrar(){
		
		
		boolean resul=myFacturaDao.registrarFactura(myFactura);
				
		if(resul){
			
			int idFacTemp=myFactura.getIdFactura();
			myFactura.setIdFactura(myFacturaDao.getIdFacturaGuardada());
			
				try {
					//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul.jasper",myFactura.getIdFactura() );
					AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 1, myFactura.getIdFactura());
					//this.view.setModal(false);
					AbstractJasperReports.imprimierFactura();
					//this.view.setModal(true);
					//JOptionPane.showMessageDialog(view, "El id factura:"+myFactura.getIdFactura());
					myFacturaDao.EliminarTemp(idFacTemp);
					
					
					
					this.view.getModelo().eliminarFactura(this.filaPulsada);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//AbstractJasperReports.showViewer();
				//AbstractJasperReports.imprimierFactura();
				
			
		}else{
			
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String comando=e.getActionCommand();
		
		switch(comando){
		case "INSERTAR":
			
			ViewFacturar vistaFacturar=new ViewFacturar(this.view);
			vistaFacturar.pack();
			CtlFacturar ctlFacturar=new CtlFacturar(vistaFacturar,conexion );
				
			vistaFacturar.getTxtBuscar().requestFocusInWindow();
			
			//myFac=ctlFacturar.getAccion();
			boolean resul=ctlFacturar.getAccion();
			//JOptionPane.showMessageDialog(view, myFac);
			
			if(resul){
				Factura myFac=new Factura();
				myFac=ctlFacturar.getFactura();
				this.view.getModelo().agregarFactura(myFac);
			}
			
			vistaFacturar.dispose();
			vistaFacturar=null;
			ctlFacturar=null;
			break;
		case "COBRAR":
				cobrar();
				//this.view.getBtnEliminar().setEnabled(true);
        		this.view.getBtnCobrar().setEnabled(false);
        		this.view.getBtnEliminar().setEnabled(false);
			break;
		case "ELIMINAR":
			this.myFacturaDao.EliminarTemp(this.myFactura.getIdFactura());
			this.view.getModelo().eliminarFactura(this.filaPulsada);
			this.view.getBtnCobrar().setEnabled(false);
    		this.view.getBtnEliminar().setEnabled(false);
		break;
		}

	}

}
