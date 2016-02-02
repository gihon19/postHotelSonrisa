package controlador;

import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.dao.ClienteDao;
import modelo.Conexion;
import view.tablemodel.TablaModeloMarca;
import view.ViewCrearCliente;
import view.ViewFacturar;
import view.ViewListaClientes;

public class CtlClienteBuscar implements ActionListener ,MouseListener, WindowListener{
	
	private Conexion conexion=null;
	private ViewListaClientes view;
	
	private ClienteDao clienteDao=null;
	private Cliente myCliente=null;
	//fila selecciona enla lista
	private int filaPulsada;
	
	public CtlClienteBuscar(ViewListaClientes v, Conexion conn){
		conexion=conn;
		view=v;
		view.conectarControladorBuscar(this);
		clienteDao=new ClienteDao(conexion);
		cargarTabla(clienteDao.todoClientes());
		//view.setVisible(true);
	}
	
	
	public void cargarTabla(List<Cliente> clientes){
		//JOptionPane.showMessageDialog(view, articulos);
		this.view.getModelo().limpiarClientes();
		for(int c=0;c<clientes.size();c++){
			this.view.getModelo().agregarCliente(clientes.get(c));
		}
	}
	
public Cliente buscarCliente(Window v){
		
		//this.myArticuloDao.cargarInstrucciones();
		cargarTabla(clienteDao.todoClientes());
		//this.view.getBtnEliminar().setEnabled(false);
		//this.view.getBtnAgregar().setEnabled(false);
		this.view.setLocationRelativeTo(v);
		this.view.setModal(true);
		this.view.setVisible(true);
		return this.myCliente;
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//Recoger qué fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTablaClientes().getSelectedRow();
		if (e.getClickCount() == 2){
			myCliente=this.view.getModelo().getCliente(filaPulsada);
			//clienteDao.desconectarBD();
			this.view.setVisible(false);
			//JOptionPane.showMessageDialog(null,myMarca);
			this.view.dispose();
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
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
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

}
