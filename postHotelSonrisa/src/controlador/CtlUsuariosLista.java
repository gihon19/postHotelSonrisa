package controlador;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Usuario;
import modelo.dao.UsuarioDao;
import view.ViewCrearUsuario;
import view.ViewListaUsuarios;
import view.tablemodel.TablaModeloMarca;
import view.tablemodel.TmUsuarios;

public class CtlUsuariosLista implements ActionListener, MouseListener {
	
	private ViewListaUsuarios view=null;
	private Conexion conexion=null;
	private Usuario myUsuario=null;
	private UsuarioDao myDao=null;
	
	//fila selecciona enla lista
	private int filaPulsada=-1;
	
	
	
	
	public CtlUsuariosLista(ViewListaUsuarios v,Conexion conn){
		
		view=v;
		conexion=conn;
		myDao=new UsuarioDao(conexion);
		
		cargarTabla(myDao.todos());
		
		view.conectarCtl(this);
		
		view.setVisible(true);
		
		
		
	}

	private void cargarTabla(List<Usuario> usuarios) {
		// TODO Auto-generated method stub
		view.getModelo().limpiar();
		for(int x=0;x<usuarios.size();x++)
			view.getModelo().agregar(usuarios.get(x));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//Recoger qu� fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTabla().getSelectedRow();
        
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	
        	String user=(String)view.getModelo().getValueAt(filaPulsada, 0);
        	myUsuario=view.getModelo().getUsuario(filaPulsada);  
        	
        	
        	
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		
        		
        		ViewCrearUsuario viewCrear=new ViewCrearUsuario(view);
        		CtlUsuario ctlUsuario=new CtlUsuario(viewCrear,conexion);
        		
        		boolean resul=ctlUsuario.actualizarUsuario(myUsuario);
        		
        		if(resul){
        			
        			this.view.getModelo().fireTableDataChanged();//se refrescan los cambios
        		}
        		
        		viewCrear.dispose();
        		view=null;
        		ctlUsuario=null;
        		
        	}
        	
        }
        
        /*if(e.getClickCount()==1){
        	this.view.getBtnEliminar().setEnabled(true);
        }*/
		
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
		switch(comando){
		case "INSERTAR":
			ViewCrearUsuario viewCrearUsuario=new ViewCrearUsuario(view);
			CtlUsuario ctlCrearUsuario=new CtlUsuario(viewCrearUsuario,conexion);
			//viewCrearUsuario.setVisible(true);
			
			boolean resul=ctlCrearUsuario.agregarUsuario();
			if(resul){
				this.view.getModelo().agregar(ctlCrearUsuario.getUsuario());
				
				/*<<<<<<<<<<<<<<<selecionar la ultima fila creada>>>>>>>>>>>>>>>*/
				int row =  this.view.getTabla().getRowCount () - 1;
				Rectangle rect = this.view.getTabla().getCellRect(row, 0, true);
				this.view.getTabla().scrollRectToVisible(rect);
				this.view.getTabla().clearSelection();
				this.view.getTabla().setRowSelectionInterval(row, row);
				TmUsuarios modelo = (TmUsuarios)this.view.getTabla().getModel();
				modelo.fireTableDataChanged();
			}
			break;
		case "ELIMINAR":
			if(myDao.eliminarUsuario(myUsuario.getUser())){//llamamos al metodo para agregar 
				JOptionPane.showMessageDialog(this.view, "Se elimino exitosamente","Informacion",JOptionPane.INFORMATION_MESSAGE);
				this.view.getModelo().eliminar(filaPulsada);
				this.view.getBtnEliminar().setEnabled(false);
				
			}
			else{
				JOptionPane.showMessageDialog(null, "No se Registro");
			}
			break;
		case "BUSCAR":
			if(view.getRdbtnTodos().isSelected()){
				view.getTxtBuscar().setText("");;
				cargarTabla(myDao.todos());
			}else
				if(view.getRdbtnNombre().isSelected()){
					cargarTabla(myDao.porNombre(view.getTxtBuscar().getText()));
				}else
					if(view.getRdbtnUser().isSelected()){
						cargarTabla(myDao.porUser(view.getTxtBuscar().getText()));
					}
			break;
		}
		
	}

}
