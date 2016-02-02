package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Empleado;
import modelo.Usuario;
import modelo.dao.EmpleadoDao;
import view.ViewCrearEmpleado;

public class CtlEmpleado implements ActionListener {
	
	private ViewCrearEmpleado view;
	private Conexion conexion;
	
	private Empleado myEmpleado;
	private EmpleadoDao myDao;
	private boolean resultaOperacion;
	
	public CtlEmpleado(ViewCrearEmpleado v, Conexion conn){
		view=v;
		conexion=conn;
		myEmpleado=new Empleado();
		myDao=new EmpleadoDao(conexion);
		view.conectarCtl(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
String comando=e.getActionCommand();
		
		switch(comando){
		case "GUARDAR":
			if(validar()){
				setUser();
				guardarEmpleado();
			}
			break;
		case "CANCELAR":
			view.setVisible(false);
			break;
		case "ACTUALIZAR":
			
			if(validar()){
				setUser();
				actualizarEmpleado();
			}
			
			break;
		}
		
	}

	private void actualizarEmpleado() {
		// TODO Auto-generated method stub
		if(myDao.actualizar(myEmpleado)){
			JOptionPane.showMessageDialog(view, "El Usuario se actualizo correctamente.");
			this.resultaOperacion=true;
			view.setVisible(false);
		}else{
			JOptionPane.showMessageDialog(view, "Ocurrio un problema para actualizar el usuario");
		}
	}

	private void guardarEmpleado() {
		// TODO Auto-generated method stub
		if(myDao.registrar(myEmpleado)){
			myEmpleado.setCodigo(myDao.getIdRegistrado());
			JOptionPane.showMessageDialog(view, "El empleado se guardo correctamente.");
			this.resultaOperacion=true;
			view.setVisible(false);
			
		}else{
			JOptionPane.showMessageDialog(view, "Ocurrio un problema para guardar el usuario");
		}
		
	}
	
	public boolean agregarEmpleado() {
		// TODO Auto-generated method stub
		view.setVisible(true);
		return resultaOperacion;
	}

	private void setUser() {
		// TODO Auto-generated method stub
		myEmpleado.setNombre(view.getTxtNombre().getText());
		myEmpleado.setApellido(view.getTxtApellido().getText());
		myEmpleado.setTelefono(view.getTxtTelefono().getText());
		myEmpleado.setCorreo(view.getTxtCorreo().getText());
	}

	private boolean validar() {
		// TODO Auto-generated method stub
		boolean resul=false;
		if(view.getTxtNombre().getText().trim().length()==0){
			JOptionPane.showMessageDialog(view, "Debe rellenar todos los campos");
			view.getTxtNombre().requestFocusInWindow();
		}else
			if(view.getTxtApellido().getText().trim().length()==0){
				JOptionPane.showMessageDialog(view, "Debe rellenar todos los campos");
				view.getTxtApellido().requestFocusInWindow();
			}else
				if(view.getTxtTelefono().getText().trim().length()==0){
					JOptionPane.showMessageDialog(view, "Debe rellenar todos los campos");
					view.getTxtTelefono().requestFocusInWindow();
				}else
					resul=true;
		return resul;
	}

	public Empleado getEmpleado() {
		// TODO Auto-generated method stub
		return myEmpleado;
	}
	
	public boolean actualizarEmpleado(Empleado emple){
		myEmpleado=emple;
		
		
		//JOptionPane.showMessageDialog(view, myUsuario);
		
		view.getBtnActualizar().setVisible(true);
		view.getBtnGuardar().setVisible(false);
		loadEmpleado();
		view.setVisible(true);
		return resultaOperacion;
	}

	private void loadEmpleado() {
		// TODO Auto-generated method stub
		
		view.getTxtNombre().setText(myEmpleado.getNombre());
		view.getTxtApellido().setText(myEmpleado.getApellido());
		view.getTxtTelefono().setText(myEmpleado.getTelefono());
		view.getTxtCorreo().setText(myEmpleado.getCorreo());
		
	}

}
