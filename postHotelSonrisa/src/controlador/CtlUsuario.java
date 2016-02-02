package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Usuario;
import modelo.dao.UsuarioDao;
import view.ViewCrearUsuario;

public class CtlUsuario implements ActionListener {
	private ViewCrearUsuario view=null;
	private Conexion conexion=null;
	private Usuario myUsuario=null;
	private UsuarioDao myDao=null;
	private boolean resultaOperacion=false;
	
	public CtlUsuario(ViewCrearUsuario v, Conexion conn){
		view=v;
		conexion=conn;
		myUsuario=new Usuario();
		myDao=new UsuarioDao(conexion);
		
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
				guardarUsuario();
			}
			break;
		case "CANCELAR":
			view.setVisible(false);
			break;
		case "ACTUALIZAR":
			
			if(validar()){
				setUser();
				actualizarUsuario();
			}
			
			break;
		}
		
	}
	
	private void actualizarUsuario() {
		// TODO Auto-generated method stub
		if(myDao.actualizar(myUsuario)){
			JOptionPane.showMessageDialog(view, "El Usuario se actualizo correctamente.");
			this.resultaOperacion=true;
			view.setVisible(false);
		}else{
			JOptionPane.showMessageDialog(view, "Ocurrio un problema para actualizar el usuario");
		}
	}

	private void guardarUsuario(){
		if(myDao.registrar(myUsuario)){
			JOptionPane.showMessageDialog(view, "El Usuario se guardo correctamente.");
			this.resultaOperacion=true;
			view.setVisible(false);
		}else{
			JOptionPane.showMessageDialog(view, "Ocurrio un problema para guardar el usuario");
		}
	}

	private void setUser() {
		// TODO Auto-generated method stub
		myUsuario.setUser(view.getTxtUser().getText());
		myUsuario.setApellido(view.getTxtApellido().getText());
		myUsuario.setNombre(view.getTxtNombre().getText());
		myUsuario.setPwd(view.getPwd().getText());
		if(view.getRdbtnAdministrador().isSelected()){
			myUsuario.setTipoPermiso(1);
			myUsuario.setPermiso("Administrador");
		}
		if(view.getRdbtnCajero().isSelected()){
			myUsuario.setTipoPermiso(2);
			myUsuario.setPermiso("Cajero");
		}
		
		
	}

	private boolean validar() {
		// TODO Auto-generated method stub
		
		
		String jpf1Text=Arrays.toString(view.getPwd().getPassword());//get the char array of password and convert to string represenation
		String jpf2Text=Arrays.toString(view.getRePwd().getPassword());
		boolean resul=false;
		if(view.getTxtUser().getText().trim().length()==0){
			JOptionPane.showMessageDialog(view, "Debe rellenar todos los campos");
			view.getTxtUser().requestFocusInWindow();
		}else
		if(view.getTxtNombre().getText().trim().length()==0){
			JOptionPane.showMessageDialog(view, "Debe rellenar todos los campos");
			view.getTxtNombre().requestFocusInWindow();
		}else
			if(view.getTxtApellido().getText().trim().length()==0){
				JOptionPane.showMessageDialog(view, "Debe rellenar todos los campos");
				view.getTxtApellido().requestFocusInWindow();
			}else
				if(view.getPwd().getPassword().length==0){
					JOptionPane.showMessageDialog(view, "Debe rellenar todos los campos");
					view.getPwd().requestFocusInWindow();
				}else
					if(view.getRePwd().getPassword().length==0){
						JOptionPane.showMessageDialog(view, "Debe rellenar todos los campos");
						view.getRePwd().requestFocusInWindow();
					}else
						if(!jpf1Text.equals(jpf2Text)){
							JOptionPane.showMessageDialog(view, "Password no son iguales!!!");
							view.getPwd().requestFocusInWindow();
						}else
							resul=true;
		
		return resul;
	}

	public boolean agregarUsuario() {
		// TODO Auto-generated method stub
		view.setVisible(true);
		return resultaOperacion;
	}

	public Usuario getUsuario() {
		// TODO Auto-generated method stub
		return myUsuario;
	}
	public void loadUsuario(){
		view.getTxtUser().setText(myUsuario.getUser());
		view.getTxtNombre().setText(myUsuario.getNombre());
		view.getTxtApellido().setText(myUsuario.getApellido());
		
		if(myUsuario.getTipoPermiso()==1){
			view.getRdbtnAdministrador().setSelected(true);
		}
		if(myUsuario.getTipoPermiso()==2){
			view.getRdbtnCajero().setSelected(true);
		}
	}
	
	public boolean actualizarUsuario(Usuario user){
		myUsuario=user;
		myUsuario.setUserOld(new String(user.getUser()));
		
		//JOptionPane.showMessageDialog(view, myUsuario);
		
		view.getBtnActualizar().setVisible(true);
		view.getBtnGuardar().setVisible(false);
		loadUsuario();
		view.setVisible(true);
		return resultaOperacion;
	}

}
