package controlador;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Empleado;
import modelo.Usuario;
import modelo.dao.EmpleadoDao;
import view.ViewCrearEmpleado;
import view.ViewCrearUsuario;
import view.ViewListaEmpleados;
import view.tablemodel.TmEmpleados;
import view.tablemodel.TmUsuarios;

public class CtlEmpleadosLista implements ActionListener, MouseListener {
	private ViewListaEmpleados view;
	private Conexion conexion;
	private EmpleadoDao myDao;
	private int filaPulsada;
	private Empleado myEmpleado;
	
	public CtlEmpleadosLista(ViewListaEmpleados v, Conexion conn){
		view=v;
		conexion=conn;
		
		myDao=new EmpleadoDao(conexion);
		
		view.conectarCtl(this);
		
		myEmpleado=new Empleado();
		
		cargarTabla(myDao.todos());
		
		view.setVisible(true);
	}
	
	private void cargarTabla(List<Empleado> empleados) {
		// TODO Auto-generated method stub
		view.getModelo().limpiar();
		for(int x=0;x<empleados.size();x++)
			view.getModelo().agregar(empleados.get(x));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//Recoger qu� fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTabla().getSelectedRow();
        
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	
        	//String user=(String)view.getModelo().getValueAt(filaPulsada, 0);
        	myEmpleado=view.getModelo().getEmpleado(filaPulsada);  
        	
        	
        	
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		
        		
        		ViewCrearEmpleado viewCrearEmpleado=new ViewCrearEmpleado(view);
				CtlEmpleado ctlCrearEmpleado=new CtlEmpleado(viewCrearEmpleado, conexion);
				
        		
        		boolean resul=ctlCrearEmpleado.actualizarEmpleado(myEmpleado);
        		
        		if(resul){
        			
        			this.view.getModelo().fireTableDataChanged();//se refrescan los cambios
        		}
        		
        		viewCrearEmpleado.dispose();
				viewCrearEmpleado=null;
				ctlCrearEmpleado=null;
        		
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
			ViewCrearEmpleado viewCrearEmpleado=new ViewCrearEmpleado(view);
			CtlEmpleado ctlCrearEmpleado=new CtlEmpleado(viewCrearEmpleado, conexion);
			//viewCrearUsuario.setVisible(true);
			
			boolean resul=ctlCrearEmpleado.agregarEmpleado();
			if(resul){
				this.view.getModelo().agregar(ctlCrearEmpleado.getEmpleado());
				
				/*<<<<<<<<<<<<<<<selecionar la ultima fila creada>>>>>>>>>>>>>>>*/
				int row =  this.view.getTabla().getRowCount () - 1;
				Rectangle rect = this.view.getTabla().getCellRect(row, 0, true);
				this.view.getTabla().scrollRectToVisible(rect);
				this.view.getTabla().clearSelection();
				this.view.getTabla().setRowSelectionInterval(row, row);
				TmEmpleados modelo = (TmEmpleados)this.view.getTabla().getModel();
				modelo.fireTableDataChanged();
			}
			break;
		case "BUSCAR":
			if(view.getRdbtnTodos().isSelected()){
				cargarTabla(myDao.todos());
				view.getTxtBuscar().setText("");
			}else
				if(view.getRdbtnNombre().isSelected()){
					cargarTabla(myDao.porNombre(view.getTxtBuscar().getText()));
				}else
					if(view.getRdbtnApellido().isSelected()){
						cargarTabla(myDao.porApellido(view.getTxtBuscar().getText()));
					}else
						if(view.getRdbtnId().isSelected()){
							myEmpleado=myDao.buscarPorId(Integer.parseInt(view.getTxtBuscar().getText()));
							if(myEmpleado!=null){
								view.getModelo().limpiar();
								view.getModelo().agregar(myEmpleado);
							}else{
								JOptionPane.showMessageDialog(view, "No se encontro el empleado.");
							}
						}
			
			break;
		/*case "ELIMINAR":
			if(myDao.eliminarUsuario(myUsuario.getUser())){//llamamos al metodo para agregar 
				JOptionPane.showMessageDialog(this.view, "Se elimino exitosamente","Informacion",JOptionPane.INFORMATION_MESSAGE);
				this.view.getModelo().eliminar(filaPulsada);
				this.view.getBtnEliminar().setEnabled(false);
				
			}
			else{
				JOptionPane.showMessageDialog(null, "No se Registro");
			}
			break;*/
		}
		
	}

}
