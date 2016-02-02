package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import modelo.Articulo;
import modelo.Cliente;
import modelo.dao.ClienteDao;
import modelo.Conexion;
import view.ViewCrearCliente;

public class CtlCliente implements ActionListener,WindowListener {
	
	private ViewCrearCliente view;
	private Conexion conexion=null;
	private Cliente myCliente=null;
	private ClienteDao myClienteDao=null;
	private boolean resultaOperacion=false;
	
	public CtlCliente(ViewCrearCliente v,Conexion conn){
		view=v;
		conexion=conn;
		view.conectarControlador(this);
		myCliente=new Cliente();
		myClienteDao=new ClienteDao(conn);
		view.setLocationRelativeTo(view);
		view.setModal(true);
		//view.setVisible(true);
		
	}
	private void getCliente(){
		myCliente.setNombre(view.getTxtNombre().getText());
		myCliente.setDireccion(view.getTxtDireccion().getText());
		myCliente.setTelefono(view.getTxtTelefono().getText());
		myCliente.setCelular(view.getTxtMovil().getText());
		myCliente.setRtn(view.getTxtRtn().getText());
	}
	
	public boolean agregarCliente(){
		this.view.setVisible(true);
		return resultaOperacion;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<< metodo que devuelve el cliente guardado >>>>>>>>>>>>>>>>>>>>>>>>>         */
	public Cliente getClienteGuardado(){
		return myCliente;
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		
		switch(comando){
		case "GUARDAR":
			getCliente();
			//se ejecuta la accion de guardar
			if(myClienteDao.registrarCliente(myCliente)){
				JOptionPane.showMessageDialog(this.view, "Se ha registrado Exitosamente","Información",JOptionPane.INFORMATION_MESSAGE);
				myCliente.setId(myClienteDao.getIdClienteRegistrado());//se completa el proveedor guardado con el ID asignado por la BD
				resultaOperacion=true;
				this.view.setVisible(false);
			}else{
				JOptionPane.showMessageDialog(this.view, "No se Registro");
				resultaOperacion=false;
				this.view.setVisible(false);
			}
			break;
		case "CANCELAR":
			this.view.setVisible(false);
			break;
		case "ACTUALIZAR":
			getCliente();
				if(myClienteDao.actualizarCliente(myCliente)){//se manda actualizar el cliente en la bd
					JOptionPane.showMessageDialog(view, "Se Actualizo el articulo");
					resultaOperacion=true;
					this.view.dispose();
				}
			break;
		}
	}
	
	
	public boolean actualizarCliente(Cliente cliente){
		//se carga la configuracionde la view articulo para la actulizacion
		this.view.configActualizar();
		
		
		//se establece el nombre de articulo en la view
		this.view.getTxtNombre().setText(cliente.getNombre());
		
		//se establece la marca en la view
		this.view.getTxtDireccion().setText(cliente.getDereccion());
		
		this.view.getTxtTelefono().setText(cliente.getTelefono());
		this.view.getTxtMovil().setText(cliente.getCelular());
		this.view.getTxtRtn().setText(cliente.getRtn());
		
		
		
		
		
		//se establece el articulo de la clase this
		myCliente=cliente;
		
		
				
		// se hace visible la ventana modal
		this.view.setVisible(true);
		
		
		
		return this.resultaOperacion;
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
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

}
