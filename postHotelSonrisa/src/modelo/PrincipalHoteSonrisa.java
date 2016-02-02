package modelo;

import java.sql.SQLException;

import javax.sql.DataSource;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import controlador.CtlAgregarCompras;
import controlador.CtlFacturar;
import controlador.CtlLogin;
import controlador.CtlMenuPrincipal;
import view.ViewFacturar;
import view.ViewLogin;
import view.ViewMenuPrincipal;

public class PrincipalHoteSonrisa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Conexion conexion= new Conexion();
		
		AbstractJasperReports.loadFileReport();
		
		
		try {
			AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 1, 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		
		
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		ViewLogin viewLogin =new ViewLogin(); 
		CtlLogin ctlLogin=new CtlLogin(viewLogin,conexion);
		
		boolean login=ctlLogin.login();
		
		/*JDialog.setDefaultLookAndFeelDecorated(true);
		JFrame.setDefaultLookAndFeelDecorated(true);*/
		if(login){
			
			if(conexion.getUsuarioLogin().getTipoPermiso()==1){
				ViewMenuPrincipal principal=new ViewMenuPrincipal();
				CtlMenuPrincipal ctl=new CtlMenuPrincipal(principal,conexion);
				principal.conectarControlador(ctl);
			}
			if(conexion.getUsuarioLogin().getTipoPermiso()==2){
				//JOptionPane.showMessageDialog(viewLogin, "jola");
				ViewFacturar vistaFacturar=new ViewFacturar(null);
				//vistaFacturar.pack();
				CtlFacturar ctlFacturar=new CtlFacturar(vistaFacturar,conexion );
				vistaFacturar.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
				vistaFacturar.setVisible(true);
				System.exit(0);
				
			}
		
			
		
		}
		/*Conexion conexion=new Conexion();
		
		ViewAgregarCompras view= new ViewAgregarCompras();
		CtlAgregarCompras ctl=new CtlAgregarCompras(view,conexion);
		//view.conectarControlador(ctl);*/
		

	}

}
