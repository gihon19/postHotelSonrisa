package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Usuario;
import modelo.dao.UsuarioDao;
import view.ViewLogin;

public class CtlLogin implements ActionListener {
	private ViewLogin view=null;
	private Conexion conexion=null;
	private Usuario myUsuario=null;
	private boolean resultado=false;
	private UsuarioDao myUsuarioDao=null;
	
	public CtlLogin(ViewLogin v ,Conexion conn){
		view=v;
		conexion=conn;
		myUsuario=new Usuario();
		myUsuarioDao=new UsuarioDao(conexion);
		view.conectarControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String comando=e.getActionCommand();
		
		switch(comando){
		case "LOGIN":
				loginBD();
			break;
		case "SALIR":
			System.exit(0);
		break;
		
		}
		
	}

	private void loginBD() {
		// TODO Auto-generated method stub
		try
		{  
			
            //chekar si el usuario escrbio el nombre de usuario y pw
            if (view.getTxtUser().getText().length() > 0 && view.getTxtPass().getText().length() > 0 )
            {
            	myUsuario.setPwd(view.getTxtPass().getText());
            	myUsuario.setUser(view.getTxtUser().getText());
            	resultado=myUsuarioDao.setLogin(myUsuario);
            	
            	if(!resultado){//si el resultado el usuario en la bd no existe o el pwd
            		JOptionPane.showMessageDialog(null, "El nombre de usuario y/o contrasenia no son validos.");
                    JOptionPane.showMessageDialog(null, view.getTxtUser().getText()+" " +view.getTxtPass().getText() );
                    view.getTxtUser().setText("");    //limpiar campos
                    view.getTxtPass().setText("");        
                     
                    view.getTxtUser().requestFocusInWindow();
            	}else{
            		this.view.setVisible(false);
            		this.view.dispose();
            		
            	}
            	
                /*/ Si el usuario si fue validado correctamente
                if( validarUsuario( txtUser.getText(), txtPass.getText() ) )    //enviar datos a validar
                {
                    // Codigo para mostrar la ventana principal
                    setVisible(false);
                    VentanaPrincipal ventana1 = new VentanaPrincipal();
                    ventana1.mostrar();


                }
                else
                {
                    JOptionPane.showMessageDialog(null, "El nombre de usuario y/o contrasenia no son validos.");
                    JOptionPane.showMessageDialog(null, view.getTxtUser().getText()+" " +view.getTxtPass().getText() );
                    view.getTxtUser().setText("");    //limpiar campos
                    view.getTxtPass().setText("");        
                     
                    view.getTxtUser().requestFocusInWindow();
                }*/

            }
            else
            {
                JOptionPane.showMessageDialog(null, "Debe escribir nombre de usuario y contrasenia.\n" +
                    "NO puede dejar ningun campo vacio");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
		
	}

	public boolean login() {
		// TODO Auto-generated method stub
		view.setVisible(true);
		return resultado;
	}

}
