package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import modelo.AbstractJasperReports;
import modelo.Conexion;
import modelo.FacturaCompra;
import modelo.dao.FacturaCompraDao;
import modelo.dao.UsuarioDao;
import view.ViewListaFacturasCompra;

public class CtlFacturasCompra implements ActionListener, MouseListener, ChangeListener {
	private ViewListaFacturasCompra view;
	private Conexion conexion=null;
	private FacturaCompraDao facturaCompraDao=null;
	private UsuarioDao myUsuarioDao=null;
	private FacturaCompra myFacturaCompra=null;
	
	//fila selecciona enla lista
		private int filaPulsada;
	
	
	
	public CtlFacturasCompra(ViewListaFacturasCompra v,Conexion conn){
		
		view =v;
		view.conectarControlador(this);
		conexion=conn;
		
		facturaCompraDao=new FacturaCompraDao(conexion);
		myUsuarioDao=new UsuarioDao(conexion);
		
		myFacturaCompra=new FacturaCompra();
		
		cargarTabla(facturaCompraDao.todasfacturas());
		view.setVisible(true);
		
	}
	
	public void cargarTabla(List<FacturaCompra> facturas){
		//JOptionPane.showMessageDialog(view, " "+facturas.size());
		this.view.getModelo().limpiarFacturas();
		
		if(facturas!=null){
			for(int c=0;c<facturas.size();c++){
				this.view.getModelo().agregarFactura(facturas.get(c));
				
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//Recoger qué fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTablaFacturas().getSelectedRow();
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	
        	//Se recoge el id de la fila marcada
            int idFactura= (int)this.view.getModelo().getValueAt(filaPulsada, 0);
            
            this.view.getBtnEliminar().setEnabled(true);
            //this.view.getBtnImprimir().setEnabled(true);
            this.myFacturaCompra=this.view.getModelo().getFactura(filaPulsada);
            //se consigue el proveedore de la fila seleccionada
           // myArticulo=this.view.getModelo().getArticulo(filaPulsada);
        
            
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		
        		try {
    				//this.view.setVisible(false);
    				//this.view.dispose();
    				//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Compra_Saint_Paul.jasper",idFactura);
    				AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 2, idFactura);
    				AbstractJasperReports.showViewer(this.view);
    				//AbstractJasperReports.imprimierFactura();
    				this.view.getBtnImprimir().setEnabled(false);
    				myFacturaCompra=null;
    			} catch (SQLException ee) {
    				// TODO Auto-generated catch block
    				ee.printStackTrace();
    			}
				
				
	        }//fin del if del doble click
        	else{//si solo seleccion la fila se guarda el id de proveedor para accion de eliminar
        		
        		this.view.getBtnEliminar().setEnabled(true);
        		
        		
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
		
		switch (comando){
		case "FECHA":
			this.view.getTxtBuscar2().setEditable(true);
			//JOptionPane.showMessageDialog(view, "Clip en fecha");
			break;
			
		case "TODAS":
			this.view.getTxtBuscar2().setEditable(false);
			this.view.getTxtBuscar2().setText("");
			this.view.getTxtBuscar1().setText("");
			
			break;
		case "ID":
			this.view.getTxtBuscar2().setEditable(false);
			this.view.getTxtBuscar2().setText("");
			this.view.getTxtBuscar1().setText("");
			
			break;
		case "BUSCAR":
			
			//si la busqueda es por id
			if(this.view.getRdbtnId().isSelected()){
				myFacturaCompra=facturaCompraDao.facturasPorId(Integer.parseInt(this.view.getTxtBuscar1().getText()));
				if(myFacturaCompra!=null){												
					this.view.getModelo().limpiarFacturas();
					this.view.getModelo().agregarFactura(myFacturaCompra); 
				}else{
					JOptionPane.showMessageDialog(view, "No se encuentro la factura");
				}
				
			}
			//si la busqueda es por fecha
			if(this.view.getRdbtnFecha().isSelected()){  
				String fecha1=this.view.getTxtBuscar1().getText();
				String fecha2=this.view.getTxtBuscar2().getText();
				cargarTabla(facturaCompraDao.facturasPorFechas(fecha1,fecha2));
				//this.view.getTxtBuscar1().setText("");
				//this.view.getTxtBuscar2().setText("");
				}
			
			
			//si la busqueda son tadas
			if(this.view.getRdbtnTodos().isSelected()){  
				cargarTabla(facturaCompraDao.todasfacturas());
				this.view.getTxtBuscar1().setText("");
				}
			break;
		case "ANULARFACTURA":
			//se verifica si la factura ya esta agregada al kardex
			if (myFacturaCompra.getAgregadoAkardex()==0){
					int resul=JOptionPane.showConfirmDialog(view, "¿Desea anular la factura no "+myFacturaCompra.getIdFactura()+"?");
					//sin confirmo la anulacion
					if(resul==0){
						String pwd=JOptionPane.showInputDialog(view, "Escriba la contraseña admin", "Seguridad", JOptionPane.INFORMATION_MESSAGE);
						
						//comprabacion del permiso administrativo
						if(this.myUsuarioDao.comprobarAdmin(pwd)){
							//se anula la factura en la bd
							if(facturaCompraDao.anularFactura(myFacturaCompra))
								myFacturaCompra.setEstado("NULA");
							//JOptionPane.showMessageDialog(view, "Usuario Valido");
							this.view.getBtnEliminar().setEnabled(false);
						}else{
							JOptionPane.showMessageDialog(view, "Usuario Invalido");
						}
						
						
					}
			}else{
				JOptionPane.showMessageDialog(view, "No se puede anular la compra porque ya esta en el Kardex!!!");
				this.view.getBtnEliminar().setEnabled(false);
			}
			
			break;
			
		/*case "IMPRIMIR":
			try {
				//this.view.setVisible(false);
				//this.view.dispose();
				AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul_Reimpresion.jasper",myFactura.getIdFactura() );
				//AbstractJasperReports.showViewer();
				AbstractJasperReports.imprimierFactura();
				this.view.getBtnImprimir().setEnabled(false);
				myFactura=null;
			} catch (SQLException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
			break;*/
		}

	}

	

}
