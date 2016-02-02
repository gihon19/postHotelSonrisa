package controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Marca;
import modelo.dao.MarcaDao;
import view.ViewCrearMarca;
import view.ViewListaMarca;

public class CtlMarcaBuscar implements ActionListener, MouseListener,WindowListener, ItemListener{
	
	//formulario para Modificar, insertar marcas
		public ViewCrearMarca viewMarca;
		//lista de marcas
		public ViewListaMarca view;
		
		//modelo para consultar la base de datos
		public MarcaDao myMarcaDao;
		
		//modelo de datos
		public Marca myMarca=null;
		
		//fila selecciona enla lista
		private int filaPulsada;
		
		private Conexion conexion;
		
		public CtlMarcaBuscar(ViewListaMarca view, Conexion conn){
			conexion=conn;
			this.view=view;
			myMarca=new Marca();
			myMarcaDao=new MarcaDao(conexion);
			cargarTabla(myMarcaDao.todoMarcas());
			
		}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		this.view.getTxtBuscar().setText("");
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
		this.myMarcaDao.close();
		this.view.setVisible(false);
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//Recoger qué fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTablaMarca().getSelectedRow();
		if (e.getClickCount() == 2){
			myMarca=this.view.getModelo().getMarca(filaPulsada);
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		String comando=e.getActionCommand();
		
		switch(comando){
		case "BUSCAR":
			//si se seleciono el boton ID
			if(this.view.getRdbtnId().isSelected()){  
				myMarca=myMarcaDao.buscarMarca(Integer.parseInt(this.view.getTxtBuscar().getText()));
				if(myMarca!=null){
					this.view.getModelo().limpiarMarcas();;
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
	public void cargarTabla(List<Marca> marcas){
		
		this.view.getModelo().limpiarMarcas();
		for(int c=0;c<marcas.size();c++)
			this.view.getModelo().agregarMarca(marcas.get(c));
		
	}
	
	public Marca buscarMarca(){
		
		this.view.setVisible(true);
		return myMarca;
	}

}
