package controlador;


import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Articulo;
import modelo.dao.ArticuloDao;
import modelo.Conexion;
import view.ViewFacturar;
import view.ViewListaArticulo;

public class CtlArticuloBuscar implements ActionListener,MouseListener, WindowListener,KeyListener {
	
	public ViewListaArticulo view;
	//public ViewCrearArticulo viewArticulo;
	
	
	private Articulo myArticulo;
	private ArticuloDao myArticuloDao;
	
	//fila selecciona enla lista
	private int filaPulsada;
	
	//pool de conexion
	private Conexion conexion=null;
	
	public CtlArticuloBuscar(ViewListaArticulo view, Conexion conn){
		
		conexion=conn;
		this.view=view;
		myArticulo=new Articulo();
		myArticuloDao=new ArticuloDao(conexion);
		cargarTabla(myArticuloDao.todoArticulos());
		
		view.getRdbtnArticulo().setSelected(true);
		
		//this.view.setVisible(true);
	}
	
	public void cargarTabla(List<Articulo> articulos){
		//JOptionPane.showMessageDialog(view, articulos);
		this.view.getModelo().limpiarArticulos();
		for(int c=0;c<articulos.size();c++){
			this.view.getModelo().agregarArticulo(articulos.get(c));
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		//myArticuloDao.desconectarBD();
		//JOptionPane.showMessageDialog(view, "Se esta Cerrando");
		this.myArticulo=null;
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//Recoger qu� fila se ha pulsadao en la tabla
        filaPulsada = this.view.getTablaArticulos().getSelectedRow();
        //JOptionPane.showMessageDialog(view, filaPulsada);
		if (e.getClickCount() == 2){
			
			myArticulo=this.view.getModelo().getArticulo(filaPulsada);
			
			
			//myArticuloDao.desconectarBD();
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
		//se recoge el comando programado en el boton que se hizo click
				String comando=e.getActionCommand();
				

				
				
				switch(comando){
				
				case "BUSCAR":
					//si se seleciono el boton ID
					if(this.view.getRdbtnId().isSelected()){  
						myArticulo=myArticuloDao.buscarArticulo(Integer.parseInt(this.view.getTxtBuscar().getText()));
						if(myArticulo!=null){												
							this.view.getModelo().limpiarArticulos();
							this.view.getModelo().agregarArticulo(myArticulo);
							view.setVisible(false);
						}else{
							JOptionPane.showMessageDialog(view, "No se encuentro el articulo");
							this.view.getTxtBuscar().setText("");
						}
					} 
					
					if(this.view.getRdbtnArticulo().isSelected()){ //si esta selecionado la busqueda por nombre	
						
						//cargarTabla(myArticuloDao.buscarArticulo(this.view.getTxtBuscar().getText()));
						//JOptionPane.showMessageDialog(view, myArticulo);
				        view.setVisible(false);
						}
					if(this.view.getRdbtnMarca().isSelected()){  
						cargarTabla(myArticuloDao.buscarArticuloMarca(this.view.getTxtBuscar().getText()));
							if(myArticulo!=null){
								view.setVisible(false);
							}else{
								this.view.getTxtBuscar().setText("");
							}
						}
					
					if(this.view.getRdbtnTodos().isSelected()){  
						cargarTabla(myArticuloDao.todoArticulos());
						this.view.getTxtBuscar().setText("");
						}
					break;
				
				
					
						
					}
	}
	//public void buscarArticulo()
	public Articulo buscarArticulo(Window v){
		
		//this.myArticuloDao.cargarInstrucciones();
		cargarTabla(myArticuloDao.todoArticulos());
		myArticulo=null;
		this.view.getBtnEliminar().setEnabled(false);
		this.view.getBtnAgregar().setEnabled(false);
		this.view.setLocationRelativeTo(v);
		this.view.setModal(true);
		view.getTxtBuscar().requestFocusInWindow();
		
		//view.setFocusable(true);
		this.view.setVisible(true);
		//
		return this.myArticulo;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
			if(e.getComponent()==this.view.getTxtBuscar()&&view.getTxtBuscar().getText().trim().length()!=0){
						
						//si esta activado la busqueda por articulo
						if(this.view.getRdbtnArticulo().isSelected()){
							
							int fila=this.view.getTablaArticulos().getSelectedRow();
							
							if(e.getKeyCode()==KeyEvent.VK_DOWN){
								fila++;
								this.view.getTablaArticulos().setRowSelectionInterval(0	,fila);
								
								myArticulo=myArticulo=view.getModelo().getArticulo(fila);
								
							}else
								if(e.getKeyCode()==KeyEvent.VK_UP){
									
									fila--;
									this.view.getTablaArticulos().setRowSelectionInterval(0	, fila);
									myArticulo=myArticulo=view.getModelo().getArticulo(fila);
								}
							
							
							
							//this.view.getTablaArticulos().setRowSelectionInterval(0	, 0);
							
							//myArticulo=view.getModelo().getArticulo(0);
						}
						
						
				}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//filaPulsada = this.view.getTablaArticulos().getSelectedRow();
		//JOptionPane.showConfirmDialog(view, filaPulsada);
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
			this.myArticulo=null;
	         view.setVisible(false);
	      }
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			
			if(filaPulsada>0){
				//Se recoge el id de la fila marcada
	            int identificador= (int)this.view.getModelo().getValueAt(filaPulsada, 0);
	            
	            //se consigue el proveedore de la fila seleccionada
	            myArticulo=this.view.getModelo().getArticulo(filaPulsada);// .getCliente(filaPulsada);
	            
				//myArticulo=view.getModelo().getArticulo(filaPulsada-1);
				view.setVisible(false);
			}
		}
		
		if(e.getComponent()==this.view.getTxtBuscar()&&view.getTxtBuscar().getText().trim().length()!=0&&e.getKeyCode()!=KeyEvent.VK_UP&&e.getKeyCode()!=KeyEvent.VK_DOWN){
			
			//si esta activado la busqueda por articulo
			if(this.view.getRdbtnArticulo().isSelected()){
				
				cargarTabla(myArticuloDao.buscarArticulo(this.view.getTxtBuscar().getText()));
				
				this.view.getTablaArticulos().setRowSelectionInterval(0	, 0);
				
				myArticulo=view.getModelo().getArticulo(0);
			}
			
			//si esta activado las busqueda por Marca
			if(this.view.getRdbtnMarca().isSelected()){  
				cargarTabla(myArticuloDao.buscarArticuloMarca(this.view.getTxtBuscar().getText()));
				this.view.getTablaArticulos().setRowSelectionInterval(0	, 0);
				
				myArticulo=view.getModelo().getArticulo(0);
			}
			
			//si esta activado la busqueda por id
			if(this.view.getRdbtnId().isSelected()){  
				myArticulo=myArticuloDao.buscarArticulo(Integer.parseInt(this.view.getTxtBuscar().getText()));
				if(myArticulo!=null){												
					this.view.getModelo().limpiarArticulos();
					this.view.getModelo().agregarArticulo(myArticulo);
					this.view.getTablaArticulos().setRowSelectionInterval(0	, 0);
					
					myArticulo=view.getModelo().getArticulo(0);
				}else{
					JOptionPane.showMessageDialog(view, "No se encuentro el articulo");
				}
			} 
		}
	}

}
