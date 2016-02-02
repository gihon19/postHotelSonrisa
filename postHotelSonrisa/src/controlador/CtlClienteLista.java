package controlador;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Articulo;
import modelo.Cliente;
import modelo.dao.ClienteDao;
import modelo.dao.CodBarraDao;
import modelo.Conexion;
import view.tablemodel.TablaModeloMarca;
import view.ViewCrearArticulo;
import view.ViewCrearCliente;
import view.ViewListaClientes;

public class CtlClienteLista implements ActionListener, MouseListener {
	private ViewListaClientes view;
	private Conexion conexion=null;
	private ClienteDao clienteDao=null;
	private Cliente myCliente=null;
	
	//fila selecciona enla lista
		private int filaPulsada;
	
	public CtlClienteLista(ViewListaClientes v,Conexion conn){
		view=v;
		conexion=conn;
		view.conectarControlador(this);
		
		clienteDao=new ClienteDao(conexion);
		cargarTabla(clienteDao.todoClientes());
		view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		switch (comando){
		
		case "BUSCAR":
			//si se seleciono el boton ID
			if(this.view.getRdbtnId().isSelected()){  
				myCliente=clienteDao.buscarCliente(Integer.parseInt(this.view.getTxtBuscar().getText()));
				if(myCliente!=null){												
					this.view.getModelo().limpiarClientes();
					this.view.getModelo().agregarCliente(myCliente);
				}else{
					JOptionPane.showMessageDialog(view, "No se encuentro el articulo");
				}
			} 
			
			if(this.view.getRdbtnNombre().isSelected()){ //si esta selecionado la busqueda por nombre	
				
				cargarTabla(clienteDao.buscarCliente(this.view.getTxtBuscar().getText()));
		        
				}
			if(this.view.getRdbtnRtn().isSelected()){  
				cargarTabla(clienteDao.buscarClienteRtn(this.view.getTxtBuscar().getText()));
				}
			
			if(this.view.getRdbtnTodos().isSelected()){  
				cargarTabla(clienteDao.todoClientes());
				this.view.getTxtBuscar().setText("");
				}
			break;
		case "NUEVO":
			ViewCrearCliente view=new ViewCrearCliente();
			CtlCliente ctlCliente=new CtlCliente(view,conexion);
			
			boolean resuldoGuarda=ctlCliente.agregarCliente();
			if(resuldoGuarda){
				this.view.getModelo().agregarCliente(ctlCliente.getClienteGuardado());
				
				/*<<<<<<<<<<<<<<<selecionar la ultima fila creada>>>>>>>>>>>>>>>*/
				int row =  this.view.getTablaClientes().getRowCount () - 1;
				Rectangle rect = this.view.getTablaClientes().getCellRect(row, 0, true);
				this.view.getTablaClientes().scrollRectToVisible(rect);
				this.view.getTablaClientes().clearSelection();
				this.view.getTablaClientes().setRowSelectionInterval(row, row);
				TablaModeloMarca modelo = (TablaModeloMarca)this.view.getTablaClientes().getModel();
				modelo.fireTableDataChanged();
			}
			view=null;
			ctlCliente=null;
			break;
		}
	}
	public void cargarTabla(List<Cliente> clientes){
		//JOptionPane.showMessageDialog(view, articulos);
		this.view.getModelo().limpiarClientes();
		for(int c=0;c<clientes.size();c++){
			this.view.getModelo().agregarCliente(clientes.get(c));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//Recoger qué fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTablaClientes().getSelectedRow();
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	
        	//Se recoge el id de la fila marcada
            int identificador= (int)this.view.getModelo().getValueAt(filaPulsada, 0);
            
          //se consigue el proveedore de la fila seleccionada
            myCliente=this.view.getModelo().getCliente(filaPulsada);
        
            
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		
        		myCliente=this.view.getModelo().getCliente(filaPulsada);
        		//myArticulo=this.view.getModelo().getArticulo(filaPulsada);//se consigue el Marca de la fila seleccionada
	           
	        	//crea la ventana para ingresar un nuevo proveedor
				ViewCrearCliente viewCliente= new ViewCrearCliente();
				
				//se crea el controlador de la ventana y se le pasa la view
				CtlCliente ctlActulizarCliente=new CtlCliente(viewCliente,conexion);
				
				
						
				
				//se llama del metodo actualizar marca para que se muestre la ventanda y procesa la modificacion
				boolean resultado=ctlActulizarCliente.actualizarCliente(myCliente);
				
				//se proceso el resultado de modificar la marca
				if(resultado){
					this.view.getModelo().cambiarCliente(filaPulsada, ctlActulizarCliente.getClienteGuardado());//se cambia en la vista
					this.view.getModelo().fireTableDataChanged();//se refrescan los cambios
					this.view.getTablaClientes().getSelectionModel().setSelectionInterval(filaPulsada,filaPulsada);//se seleciona lo cambiado
				}	
			
				ctlActulizarCliente=null;
				viewCliente=null;
				
	        }//fin del if del doble click
        	else{//si solo seleccion la fila se guarda el id de proveedor para accion de eliminar
        		
        		//this.view.getBtnEliminar().setEnabled(true);
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

}
