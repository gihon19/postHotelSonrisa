package controlador;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.Conexion;
import modelo.Marca;
import modelo.dao.MarcaDao;
import view.tablemodel.TablaModeloMarca;
import view.ViewCrearMarca;
import view.ViewListaMarca;

public class CtlMarcaLista implements ActionListener, MouseListener,WindowListener, ItemListener {
	
	//formulario para Modificar, insertar marcas
	private ViewCrearMarca viewMarca;
	//lista de marcas
	private ViewListaMarca view;
	
	//modelo para consultar la base de datos
	private MarcaDao myMarcaDao;
	
	//modelo de datos
	private Marca myMarca;
	
	//fila selecciona enla lista
	private int filaPulsada;
	
	private Conexion conexion=null;
	
	public CtlMarcaLista(ViewListaMarca v,Conexion conn){
		conexion=conn;
		this.view=v;
		myMarca=new Marca();
		myMarcaDao=new MarcaDao(conexion);
		cargarTabla(myMarcaDao.todoMarcas());
		
		//conectar los controles al objeto que ctlMarca
		view.conectarControlador(this);
		
		//hacer visible la view lista de marcas
		view.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String comando=e.getActionCommand();
		
		switch(comando){
		case "INSERTAR":
				viewMarca=new ViewCrearMarca(this.view);
				CtlMarca ctlMarca=new CtlMarca(viewMarca,myMarcaDao);
				viewMarca.conectarControlador(ctlMarca);
				//ctlMarca.setMyMarcaDao(myMarcaDao);
				
				boolean resul=ctlMarca.agregarMarca();//se llama el metodo agregar proveedor que devuelve un resultado
				
				if(resul){//se procesa el resultado de agregar proveeros
					this.view.getModelo().agregarMarca(ctlMarca.getMarca());//si el proveedor fue agregado a la bd, se agrega el nuevo proveedor registrado a la tabla de la vista
					
					
					/*<<<<<<<<<<<<<<<selecionar la ultima fila creada>>>>>>>>>>>>>>>*/
					int row =  this.view.getTablaMarca().getRowCount () - 1;
					Rectangle rect = this.view.getTablaMarca().getCellRect(row, 0, true);
					this.view.getTablaMarca().scrollRectToVisible(rect);
					this.view.getTablaMarca().clearSelection();
					this.view.getTablaMarca().setRowSelectionInterval(row, row);
					TablaModeloMarca modelo = (TablaModeloMarca)this.view.getTablaMarca().getModel();
					modelo.fireTableDataChanged();
				}
			break;
		case "ELIMINAR":
			if(myMarcaDao.eliminarMarca(myMarca.getId())){//llamamos al metodo para agregar 
				JOptionPane.showMessageDialog(this.view, "Se elimino exitosamente","Informacion",JOptionPane.INFORMATION_MESSAGE);
				this.view.getModelo().eliminarMarca(filaPulsada);
				this.view.getBtnEliminar().setEnabled(false);
				
			}
			else{
				JOptionPane.showMessageDialog(null, "No se Registro");
			}
			break;
		case "BUSCAR":
			//si se seleciono el boton ID
			if(this.view.getRdbtnId().isSelected()){  
				myMarca=myMarcaDao.buscarMarca(Integer.parseInt(this.view.getTxtBuscar().getText()));
				if(myMarca!=null){
					this.view.getModelo().limpiarMarcas();
					this.view.getModelo().agregarMarca(myMarca);
				}else{
					JOptionPane.showMessageDialog(view, "No se encuentro el proveedor");
				}
			} 
			
			if(this.view.getRdbtnObservacion().isSelected()){ //si esta selecionado la busqueda por nombre	
				
				cargarTabla(myMarcaDao.buscarMarcasObservacion(this.view.getTxtBuscar().getText()));
		        
				}
			if(this.view.getRdbtnMarca().isSelected()){  
				cargarTabla(myMarcaDao.buscarMarcas(this.view.getTxtBuscar().getText()));
				}
			
			if(this.view.getRdbtnTodos().isSelected()){  
				cargarTabla(myMarcaDao.todoMarcas());
				this.view.getTxtBuscar().setText("");
				}
			//if(myProveedorDao.buscarPro(Integer.parseInt(this.view.getTxtBuscar().getText())))
			//JOptionPane.showMessageDialog(view, "Apreto buscar");
			break;
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		//Recoger qu� fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTablaMarca().getSelectedRow();
        
        //si seleccion una fila
        if(filaPulsada>=0){
        	
        	//Se recoge el id de la fila marcada
            int identificador= (int)this.view.getModelo().getValueAt(filaPulsada, 0);
            myMarca=this.view.getModelo().getMarca(filaPulsada);//se consigue el proveedore de la fila seleccionada
        
            
        	//si fue doble click mostrar modificar
        	if (e.getClickCount() == 2) {
        		
	        	//myProveedor=myProveedorDao.buscarPro(identificador);
	        	myMarca=this.view.getModelo().getMarca(filaPulsada);//se consigue el Marca de la fila seleccionada
	           
	        	//se crea la vista para modificar proveedor con un proveedor ya hecho
	        	viewMarca= new ViewCrearMarca(myMarca,this.view);
	        	
	        	//se crea el controlador para la vista modificar
				CtlMarca ctlMarcaActualizar=new CtlMarca(viewMarca,myMarcaDao);
				
				//se asigna el contrador a los elementos de la vista
				viewMarca.conectarControlador(ctlMarcaActualizar);
				
				//se llama del metodo actualizar marca para que se muestre la ventanda y procesa la modificacion
				boolean resultado=ctlMarcaActualizar.actualizarMarca();
				
				//se proceso el resultado de modificar la marca
				if(resultado){
					this.view.getModelo().cambiarMarca(filaPulsada, ctlMarcaActualizar.getMarca());//se cambia en la vista
					this.view.getModelo().fireTableDataChanged();//se refrescan los cambios
					this.view.getTablaMarca().getSelectionModel().setSelectionInterval(filaPulsada,filaPulsada);//se seleciona lo cambiado
				}			
				
				
				
	        }//fin del if del doble click
        	else{//si solo seleccion la fila se guarda el id de proveedor para accion de eliminar
        		
        		this.view.getBtnEliminar().setEnabled(true);
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
	
	public void cargarTabla(List<Marca> marcas){
		
		this.view.getModelo().limpiarMarcas();
		for(int c=0;c<marcas.size();c++)
			this.view.getModelo().agregarMarca(marcas.get(c));
		
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		this.view.getTxtBuscar().setText("");
		
	}

}
