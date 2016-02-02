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
import modelo.Articulo;
import modelo.Conexion;
import modelo.Requisicion;
import modelo.dao.RequisicionDao;
import modelo.dao.UsuarioDao;
import view.ViewListaRequisiciones;

public class CtlRequisicionesLista implements ActionListener, MouseListener, ChangeListener {
	
	private ViewListaRequisiciones view=null;
	private Conexion conexion=null;
	private RequisicionDao myRequiDao=null;
	private Requisicion myRequi=null;
	//fila selecciona enla lista
	private int filaPulsada;
	
	private UsuarioDao myUsuarioDao=null;

	public CtlRequisicionesLista(ViewListaRequisiciones v, Conexion conn) {
		view=v;
		conexion=conn;
		myRequiDao=new RequisicionDao(conexion);
		myRequi=new Requisicion();
		myUsuarioDao=new UsuarioDao(conexion);
		
		cargarTabla(myRequiDao.todas());
		view.conectarCtl(this);
		view.setVisible(true);
	}
	
	public void cargarTabla(List<Requisicion> requisiciones){
		//JOptionPane.showMessageDialog(view, articulos);
		
		if(requisiciones!=null){
			this.view.getModelo().limpiarArticulos();
			for(int c=0;c<requisiciones.size();c++){
				this.view.getModelo().setRequisicion(requisiciones.get(c));
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
        filaPulsada = this.view.getTabla().getSelectedRow();
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	
        	//Se recoge el id de la fila marcada
            //int idFactura= (int)this.view.getModelo().getValueAt(filaPulsada, 0);
            
            this.view.getBtnEliminar().setEnabled(true);
            this.view.getBtnImprimir().setEnabled(true);
            this.myRequi=this.view.getModelo().getRequisicion(filaPulsada);
            //se consigue el proveedore de la fila seleccionada
            //myArticulo=this.view.getModelo().getArticulo(filaPulsada);
        
            
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		
        		
        		
        		try {
    				
    				//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul_Reimpresion.jasper",myFactura.getIdFactura() );
        			AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 3, myRequi.getNoRequisicion());
        			AbstractJasperReports.showViewer(this.view);
    				//AbstractJasperReports.imprimierFactura();
    				this.view.getBtnImprimir().setEnabled(false);
    				myRequi=null;
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
			//JOptionPane.showMessageDialog(view, "Click en buscar");
			//si la busqueda es por id
			if(this.view.getRdbtnId().isSelected()){
				myRequi=myRequiDao.requiPorId(Integer.parseInt(this.view.getTxtBuscar1().getText()));
				if(myRequi!=null){												
					this.view.getModelo().limpiarArticulos();
					this.view.getModelo().setRequisicion(myRequi);
				}else{
					JOptionPane.showMessageDialog(view, "No se encuentro la factura");
				}
				
			}
			//si la busqueda es por fecha
			if(this.view.getRdbtnFecha().isSelected()){  
				String fecha1=this.view.getTxtBuscar1().getText();
				String fecha2=this.view.getTxtBuscar2().getText();
				cargarTabla(myRequiDao.requiPorFechas(fecha1,fecha2));
				//this.view.getTxtBuscar1().setText("");
				//this.view.getTxtBuscar2().setText("");
				}
			
			
			//si la busqueda son tadas
			if(this.view.getRdbtnTodos().isSelected()){  
				cargarTabla(myRequiDao.todas());
				this.view.getTxtBuscar1().setText("");
				}
			break;
		case "ANULARFACTURA":
			
			//se verifica si la factura ya esta agregada al kardex
			if (myRequi.getAgregadoAkardex()==0){
				
					int resul=JOptionPane.showConfirmDialog(view, "¿Desea anular la requisicion no. "+myRequi.getNoRequisicion()+"?");
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
								if(myRequiDao.anularRequi(myRequi))
									myRequi.setEstado("NULA");
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
			
		case "IMPRIMIR":
			try {
				//this.view.setVisible(false);
				//this.view.dispose();
				AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul_Reimpresion.jasper",myRequi.getNoRequisicion() );
				//AbstractJasperReports.showViewer();
				AbstractJasperReports.showViewer(view);
				this.view.getBtnImprimir().setEnabled(false);
				myRequi=null;
			} catch (SQLException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
			break;
		}

	}

}
