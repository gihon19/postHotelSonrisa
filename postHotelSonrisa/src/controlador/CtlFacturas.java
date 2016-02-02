package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import modelo.AbstractJasperReports;
import modelo.dao.CodBarraDao;
import modelo.Conexion;
import modelo.Factura;
import modelo.dao.DetalleFacturaDao;
import modelo.dao.FacturaDao;
import modelo.dao.UsuarioDao;
import view.ViewCrearArticulo;
import view.ViewFacturaDevolucion;
import view.ViewFacturar;
import view.ViewFacturas;

public class CtlFacturas implements ActionListener, MouseListener, ChangeListener {
	private ViewFacturas view;
	
	private FacturaDao myFacturaDao=null;
	private Conexion conexion=null;
	private Factura myFactura;
	private UsuarioDao myUsuarioDao=null;
	private DetalleFacturaDao detallesDao=null;
	
	
	//fila selecciona enla lista
	private int filaPulsada;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public CtlFacturas(ViewFacturas v,Conexion conn) {
		view =v;
		view.conectarControlador(this);
		conexion=conn;
		myFacturaDao=new FacturaDao(conexion);
		cargarTabla(myFacturaDao.todasfacturas(view.getModelo().getLimiteInferior(),view.getModelo().getLimiteSuperior()));
		
		view.getTxtPagina().setText(""+view.getModelo().getNoPagina());
		myFactura=new Factura();
		myUsuarioDao=new UsuarioDao(conexion);
		detallesDao=new DetalleFacturaDao(conexion);
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
		
		//Recoger qu� fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTablaFacturas().getSelectedRow();
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	
        	//Se recoge el id de la fila marcada
            //int idFactura= (int)this.view.getModelo().getValueAt(filaPulsada, 0);
            
            this.view.getBtnEliminar().setEnabled(true);
            this.view.getBtnImprimir().setEnabled(true);
            view.getBtnAgregar().setEnabled(true);
            this.myFactura=this.view.getModelo().getFactura(filaPulsada);
            //se consigue el proveedore de la fila seleccionada
            //myArticulo=this.view.getModelo().getArticulo(filaPulsada);
        
            
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		
        		
        		
        		try {
    				
    				//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul_Reimpresion.jasper",myFactura.getIdFactura() );
        			AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 3, myFactura.getIdFactura());
        			AbstractJasperReports.showViewer(this.view);
    				//AbstractJasperReports.imprimierFactura();
    				this.view.getBtnImprimir().setEnabled(false);
    				myFactura=null;
    			} catch (SQLException ee) {
    				// TODO Auto-generated catch block
    				ee.printStackTrace();
    			}
        	
        		/*ViewFacturar viewFacturar=new ViewFacturar(this.view);
        		CtlFacturar ctlFacturar=new CtlFacturar(viewFacturar,conexion);
        		
        		//si se cobro la factura se debe eleminiar el temp por eso se guarda el id
        		int idFactura=myFactura.getIdFactura();
        		
        		//se llama al controlador de la factura para que la muestre 
        		ctlFacturar.viewFactura(myFactura);//actualizarFactura(myFactura);
        		
        		//si la factura se cobro se regresara null sino modificamos la factura en la lista
        		if(myFactura==null){
        			this.view.getModelo().eliminarFactura(filaPulsada);
        			myFacturaDao.EliminarTemp(idFactura);
        		}else{
        			this.view.getModelo().cambiarArticulo(filaPulsada, myFactura);
        			this.view.getTablaFacturas().getSelectionModel().setSelectionInterval(filaPulsada,filaPulsada);//se seleciona lo cambiado
        		}
        		viewFacturar.dispose();
        		ctlFacturar=null;*/
        		
	        	
			
				
				
				
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
				myFactura=myFacturaDao.facturasPorId(Integer.parseInt(this.view.getTxtBuscar1().getText()));
				if(myFactura!=null){												
					this.view.getModelo().limpiarFacturas();
					this.view.getModelo().agregarFactura(myFactura); 
				}else{
					JOptionPane.showMessageDialog(view, "No se encuentro la factura");
				}
				
			}
			//si la busqueda es por fecha
			if(this.view.getRdbtnFecha().isSelected()){  
				String fecha1=this.view.getTxtBuscar1().getText();
				String fecha2=this.view.getTxtBuscar2().getText();
				cargarTabla(myFacturaDao.facturasPorFechas(fecha1,fecha2));
				//this.view.getTxtBuscar1().setText("");
				//this.view.getTxtBuscar2().setText("");
				}
			
			
			//si la busqueda son tadas
			if(this.view.getRdbtnTodos().isSelected()){  
				cargarTabla(myFacturaDao.todasfacturas(view.getModelo().getLimiteInferior(),view.getModelo().getLimiteSuperior()));
				
				view.getTxtPagina().setText(""+view.getModelo().getNoPagina());
				this.view.getTxtBuscar1().setText("");
				}
			break;
		case "ANULARFACTURA":
			
			//se verifica si la factura ya esta agregada al kardex
			if (myFactura.getAgregadoAkardex()==0){
				
					int resul=JOptionPane.showConfirmDialog(view, "¿Desea anular la factura no "+myFactura.getIdFactura()+"?");
					//sin confirmo la anulacion
					if(resul==0){
						JPasswordField pf = new JPasswordField();
						int action = JOptionPane.showConfirmDialog(view, pf,"Escriba la contraseña admin",JOptionPane.OK_CANCEL_OPTION);
						//String pwd=JOptionPane.showInputDialog(view, "Escriba la contraseña admin", "Seguridad", JOptionPane.INFORMATION_MESSAGE);
						if(action < 0){
							
							
						}else{
							String pwd=new String(pf.getPassword());
							//comprabacion del permiso administrativo
							if(this.myUsuarioDao.comprobarAdmin(pwd)){
								//se anula la factura en la bd
								if(myFacturaDao.anularFactura(myFactura))
									myFactura.setEstado("NULA");
								//JOptionPane.showMessageDialog(view, "Usuario Valido");
							}else{
								JOptionPane.showMessageDialog(view, "Usuario Invalido");
							}
						}
						
					}
			}else{
				JOptionPane.showMessageDialog(view, "No se puede anular la compra porque ya esta en el Kardex!!!");
				this.view.getBtnEliminar().setEnabled(false);
			}
			break;
		case "INSERTAR":
			if(this.filaPulsada>0){
				myFactura.setDetalles(detallesDao.getDetallesFactura(myFactura.getIdFactura()));
				ViewFacturaDevolucion viewDevolucion=new ViewFacturaDevolucion(view);
				CtlDevoluciones ctlDevolucion=new CtlDevoluciones(viewDevolucion,conexion);
				ctlDevolucion.actualizarFactura(myFactura);
				viewDevolucion.dispose();
				viewDevolucion=null;
				ctlDevolucion=null;
			}
			
			break;
			
		case "IMPRIMIR":
			try {
				//this.view.setVisible(false);
				//this.view.dispose();
				AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul_Reimpresion.jasper",myFactura.getIdFactura() );
				//AbstractJasperReports.showViewer();
				AbstractJasperReports.showViewer(view);
				this.view.getBtnImprimir().setEnabled(false);
				myFactura=null;
			} catch (SQLException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
			break;
			
		case "NEXT":
			view.getModelo().netPag();
			cargarTabla(myFacturaDao.todasfacturas(view.getModelo().getLimiteInferior(),view.getModelo().getLimiteSuperior()));
			
			view.getTxtPagina().setText(""+view.getModelo().getNoPagina());
			break;
		case "LAST":
			view.getModelo().lastPag();
			cargarTabla(myFacturaDao.todasfacturas(view.getModelo().getLimiteInferior(),view.getModelo().getLimiteSuperior()));
			
			view.getTxtPagina().setText(""+view.getModelo().getNoPagina());
			break;
		}
		

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub facturasPorId
		
	}

}
