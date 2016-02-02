package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Proveedor;
import modelo.dao.ProveedorDao;
import view.tablemodel.TablaModeloProveedor;
import view.rendes.TablaRenderizadorProveedor;
import view.ViewCrearProveedor;
import view.ViewListaProveedor;

public class CtlProveedorLista  implements ActionListener, MouseListener, WindowListener,ItemListener {
	public ViewCrearProveedor viewProveedor;
	private ViewListaProveedor view;
	
	private Proveedor myProveedor;
	private ProveedorDao myProveedorDao;
	
	private int idProveedor;
	private int filaTabla;
	private String tipoBusqueda;
	private Conexion conexion=null;
	
	
	
	public CtlProveedorLista(ViewListaProveedor v,Conexion conn){
		conexion=conn;
		//myProveedorDao.conexion=this.conexion;
		this.view=v;
		myProveedor=new Proveedor();
		myProveedorDao=new ProveedorDao(conexion);
		view.conectarControlador(this);
		
		cargarTabla(myProveedorDao.todoProveedor());
		view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//se recoge el comando programado en el boton que se hizo click
		String comando=e.getActionCommand();
		
		
		
		
		
		
		//ProveedorDao myProveedorDao=new ProveedorDao();
		
		switch(comando){
		
		case "INSERTAR":
			viewProveedor= new ViewCrearProveedor(this.view);//crea la ventana para ingresar un nuevo proveedor
			CtlProveedor ctl=new CtlProveedor(viewProveedor, myProveedorDao);//se crea el controlador de la ventana y se le pasa la view
			viewProveedor.conectarCtl(ctl);//se llama la metodo conectarCtl encargado de hacer set al manejador de eventos
			
			boolean resul=ctl.agregarProveedor();//se llama el metodo agregar proveedor que devuelve un resultado
			
			if(resul){//se procesa el resultado de agregar proveeros
				this.view.modelo.agregarProveedor(ctl.getProveedor());//si el proveedor fue agregado a la bd, se agrega el nuevo proveedor registrado a la tabla de la vista
			}
			
			break;
			
		case "ELIMINAR":
			
			
			
			if(myProveedorDao.eliminarProveedor(idProveedor)){//llamamos al metodo para agregar 
				JOptionPane.showMessageDialog(null, "Se elimino exitosamente","Información",JOptionPane.INFORMATION_MESSAGE);
				this.view.modelo.eliminarProveedor(filaTabla);
				this.view.getBtnEliminar().setEnabled(false);
				
			}
			else{
				JOptionPane.showMessageDialog(null, "No se Registro");
			}
			
			
			
			
			break;
			
		case "BUSCAR":
			
			//si se seleciono el boton ID
			if(this.view.getRdbtnId().isSelected()){  
				myProveedor=myProveedorDao.buscarPro(Integer.parseInt(this.view.getTxtBuscar().getText()));
				if(myProveedor!=null){
					this.view.modelo.limpiarClientes();
					this.view.modelo.agregarProveedor(myProveedor);
				}else{
					JOptionPane.showMessageDialog(view, "No se encuentro el proveedor");
				}
			} 
			
			if(this.view.getRdbtnNombre().isSelected()){ //si esta selecionado la busqueda por nombre	
				
				cargarTabla(myProveedorDao.buscarProveedorNombre(this.view.getTxtBuscar().getText()));
		        
				}
			if(this.view.getRdbtnDireccion().isSelected()){  
				cargarTabla(myProveedorDao.buscarProveedorDireccion(this.view.getTxtBuscar().getText()));
				}
			
			if(this.view.getRdbtnTodos().isSelected()){  
				cargarTabla(myProveedorDao.todoProveedor());
				this.view.getTxtBuscar().setText("");
				}
			//if(myProveedorDao.buscarPro(Integer.parseInt(this.view.getTxtBuscar().getText())))
			//JOptionPane.showMessageDialog(view, "Apreto buscar");
			break;
			
			
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		//Recoger qué fila se ha pulsadao en la tabla
        int filaPulsada = this.view.tablaProvedores.getSelectedRow();
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	
        	//Se recoge el id de la fila marcada
            int identificador= (int)this.view.modelo.getValueAt(filaPulsada, 0);
            
        
            
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		
	        	//myProveedor=myProveedorDao.buscarPro(identificador);
	        	myProveedor=this.view.modelo.getProveedor(filaPulsada);//se consigue el proveedore de la fila seleccionada
	           
	            viewProveedor= new ViewCrearProveedor(myProveedor,this.view);//se crea la vista para agregar proveedor con un proveedor ya hecho
				CtlProveedor ctl=new CtlProveedor(viewProveedor,myProveedorDao);
				viewProveedor.conectarCtl(ctl);
				
				
				viewProveedor.setVisible(true);//se muestra la ventana de actualizar
				
				if(ctl.verificadorAcualizacion){//se verifica si se actualizo en la BD
					this.view.modelo.cambiarProveedor(filaPulsada, ctl.getProveedor());//se cambia en la vista
					this.view.modelo.fireTableDataChanged();//se refrescan los cambios
					this.view.tablaProvedores.getSelectionModel().setSelectionInterval(filaPulsada,filaPulsada);//se seleciona lo cambiado
					
				}
				
				
				
				
	        }//fin del if del doble click
        	else{//si solo seleccion la fila se guarda el id de proveedor para accion de eliminar
        		
        		this.view.getBtnEliminar().setEnabled(true);
        		idProveedor=identificador;
        		filaTabla=filaPulsada;
        		
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
	
	public void cargarTabla(List<Proveedor> proveedores){
		
		this.view.modelo.limpiarClientes();
		for(int c=0;c<proveedores.size();c++)
			this.view.modelo.agregarProveedor(proveedores.get(c));
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		//this.conexion.desconectar();
		this.view.setVisible(false);
		
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
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		this.view.getTxtBuscar().setText("");
	}


}
