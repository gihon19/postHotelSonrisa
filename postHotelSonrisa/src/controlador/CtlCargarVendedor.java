package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import modelo.Conexion;
import modelo.Empleado;
import modelo.dao.EmpleadoDao;
import view.ViewCargarVenderor;

public class CtlCargarVendedor implements ActionListener, KeyListener, WindowListener {
	private ViewCargarVenderor view=null;
	private Conexion conexion;
	private Empleado myEmpleado=null;
	private EmpleadoDao myDao=null;
	private boolean Estado=false;
	public CtlCargarVendedor(ViewCargarVenderor v,Conexion con) {
		// TODO Auto-generated constructor stub
		myEmpleado=new Empleado();
		myDao=new EmpleadoDao(conexion);
		conexion=con;
		view=v;
		view.conectarCtl(this);
		view.getTxtIdVendedor().requestFocusInWindow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		
		switch (comando){
		case "BUSCAR":
			myEmpleado=myDao.buscarPorId(Integer.parseInt(view.getTxtIdVendedor().getText()));
			if(myEmpleado!=null){
				view.getTxtNombre().setText(myEmpleado.getNombre());
				view.getTxtApellido().setText(myEmpleado.getApellido());
				this.Estado=true;
				
				view.getTxtNombre().requestFocusInWindow();
			}
			break;
		case "IMPRIMIR":
				cobrar();
			break;
			
		case "CERRAR":
			salir();
			break;
		case "COBRAR":
			cobrar();
			break;
		}
	}

	private void cobrar() {
		// TODO Auto-generated method stub
		if(this.Estado){
			this.view.setVisible(false);
		}else{
			view.getTxtIdVendedor().setText("");
			view.getTxtIdVendedor().requestFocusInWindow();
			view.getToolkit().beep();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			salir();
		}
		if(e.getKeyCode()==KeyEvent.VK_F2){
			cobrar();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_F3){
			cobrar();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		this.Estado=false;
		view.setVisible(false);
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

	public boolean cargarVendedor() {
		// TODO Auto-generated method stub
		this.view.setVisible(true);
		return this.Estado;
	}
	public Empleado getVendetor(){
		return myEmpleado;
	}
	private void salir() {
		// TODO Auto-generated method stub
		Estado=false;
		this.view.setVisible(false);
	}
	

}
