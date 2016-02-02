package controlador;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import modelo.Articulo;
import modelo.dao.ArticuloDao;
import modelo.dao.DepartamentoDao;
import modelo.dao.RequisicionDao;
import modelo.Cliente;
import modelo.Conexion;
import modelo.Departamento;
import modelo.DetalleFacturaProveedor;
import modelo.Empleado;
import modelo.Factura;
import modelo.dao.KardexDao;
import modelo.Requisicion;
import view.tablemodel.TablaModeloMarca;
import view.tablemodel.TabloModeloRequisicion;
import view.ViewListaArticulo;
import view.ViewRequisicion;

public class CtlRequisicion implements ActionListener, MouseListener, TableModelListener, KeyListener, ItemListener   {
	private ViewRequisicion view=null;
	private Conexion conexion=null;
	private Requisicion myRequisicion=null;
	private Articulo myArticulo=null;
	private ArticuloDao myArticuloDao=null;
	private int filaPulsada=0;
	private KardexDao myKardex;
	private DepartamentoDao deptDao=null;
	private RequisicionDao myRequiDao=null;
	
	public CtlRequisicion(ViewRequisicion v,Conexion conn){
		view=v;
		conexion=conn;
		myRequisicion=new Requisicion();
		
		
		myArticuloDao=new ArticuloDao(conexion);
		view.conectarContralador(this);
		myKardex=new KardexDao(conexion);
		deptDao=new DepartamentoDao(conexion);
		
		myRequiDao=new RequisicionDao(conexion);
		
		view.getTxtFecha().setText(myRequiDao.getFechaSistema());
		
		cargarComboBox();
		view.getModelo().agregarDetalle();


		
		view.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//comprobamos si hay algo que buscar
		if(e.getComponent()==this.view.getTxtBuscar()&&view.getTxtBuscar().getText().trim().length()!=0){
			//JOptionPane.showMessageDialog(view, "2");
			//se busca el articulo y se asigna el resultado en el objeto articulo
			this.myArticulo=this.myArticuloDao.buscarArticuloNombre(view.getTxtBuscar().getText());
			
			//se comprueba si la busqueda devolvio un articulo
			if(myArticulo!=null){
				view.getTxtArticulo().setText(myArticulo.getArticulo());
				view.getTxtPrecio().setText("L. "+myArticulo.getPrecioVenta());
				
			}else{//si no se encontro ningun articulo se elemina la busqueda anterior
				myArticulo=null;
				view.getTxtArticulo().setText("");
				view.getTxtPrecio().setText("");
			}
		}else{///sino hay nada que buscar se elemina la vista y el articulo
			myArticulo=null;
			view.getTxtArticulo().setText("");
			view.getTxtPrecio().setText("");
		}
		
		//Recoger qué fila se ha pulsadao en la tabla
		filaPulsada = this.view.getTablaArticulos().getSelectedRow();
		char caracter = e.getKeyChar();
		
		
		//para quitar los simnos mas o numero que ingrese en la busqueda
		if(e.getComponent()==this.view.getTxtBuscar()){
			Character caracter1 = new Character(e.getKeyChar());
	        if (!esValido(caracter1))
	        {
	           String texto = "";
	           for (int i = 0; i < view.getTxtBuscar().getText().length(); i++)
	                if (esValido(new Character(view.getTxtBuscar().getText().charAt(i))))
	                    texto += view.getTxtBuscar().getText().charAt(i);
	           			view.getTxtBuscar().setText(texto);
	                //view.getToolkit().beep();
	        }
		}
		
		if(caracter=='+'){
			if(filaPulsada>=0){
				//JOptionPane.showMessageDialog(view,e.getKeyChar()+" FIla:"+filaPulsada);
				this.view.getModelo().masCantidad(filaPulsada);
				//JOptionPane.showMessageDialog(view,view.getModeloTabla().getDetalle(filaPulsada).getCantidad());
				this.calcularTotales();
			}
		}
		if(caracter=='-'){
			if(filaPulsada>=0){
				//JOptionPane.showMessageDialog(view,e.getKeyChar()+" FIla:"+filaPulsada);
				this.view.getModelo().restarCantidad(filaPulsada);
				//JOptionPane.showMessageDialog(view,view.getModeloTabla().getDetalle(filaPulsada).getCantidad());
				this.calcularTotales();
			}
		}
		
	}

	private boolean esValido(Character caracter)
    {
        char c = caracter.charValue();
        if ( !(Character.isLetter(c) //si es letra
                || c == ' ' //o un espacio
                || c == 8 //o backspace
                || (Character.isDigit(c))
            ))
            return false;
        else
            return true;
    }
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
		int colum=e.getColumn();
		int row=e.getFirstRow();
		
		
		switch(e.getType()){
		
			case TableModelEvent.UPDATE:
				
				//Se establece el departamento seleccionado
				Departamento depart= (Departamento) this.view.getCbxDepatOrigen().getSelectedItem();
				//JOptionPane.showMessageDialog(view, "Se modifico el dato en la celda "+e.getColumn()+", "+e.getFirstRow());
				if(colum==0){
					
					//Se recoge el id de la fila marcada
			        int identificador= (int)this.view.getModelo().getValueAt(row, 0);
			        
			        myArticulo=this.view.getModelo().getDetalle(row).getArticulo();
					//myArticulo=this.myArticuloDao.buscarArticulo(identificador);
					
					//se ingreso un codigo de barra y si el articulo en la bd 
			        if(myArticulo.getId()==-2){
						String cod=this.view.getModelo().getDetalle(row).getArticulo().getCodBarra().get(0).getCodigoBarra();
						this.myArticulo=this.myArticuloDao.buscarArticuloBarraCod(cod);
						
					}else{//sino se ingreso un codigo de barra se busca por id de articulo
						this.myArticulo=this.myArticuloDao.buscarArticulo(identificador);
					}
					
			        //si el articulo se encontro se procesa
					if(myArticulo!=null){
						
						
						
						//se comprueba que exista el producto en el inventario
						boolean resul=myKardex.comprobarKardex(myArticulo.getId(), depart.getId());
						if(resul){
							//se consigue el articulo en la bd
							this.view.getModelo().setArticulo(myArticulo, row);
							
							//se consiguie el precio de costo 
							this.view.getModelo().setPricioCompra(myKardex.buscarKardexPrecio(myArticulo.getId(), depart.getId()), row);
							//this.view.getModelo().getDetalle(row).setCantidad(1);
							boolean toggle = false;
							boolean extend = false;
							this.view.getTablaArticulos().requestFocus();
								
							this.view.getTablaArticulos().changeSelection(row,colum+2, toggle, extend);
								
							//
							
							//se agrega otra fila en la tabla
							this.view.getModelo().agregarDetalle();
							
							calcularTotales();
						}else{
							JOptionPane.showMessageDialog(view, "El articulo no se encuentra en la "+depart.getDescripcion());  
						}
					}else{
						JOptionPane.showMessageDialog(view, "No se encuentra el articulo");
						
						//sino se encuentra se estable un id de -1 para que sea eliminado el articulo en la tabla
						this.view.getModelo().getDetalle(row).getArticulo().setId(-1);
						
						//se agrega la nueva fila de la tabla
						//this.view.getModelo().agregarDetalle();
						
						// se vuelve a calcular los totales
						//calcularTotales();
					}
					
					
				}
				if(colum==2){
					
					//Se recoge el id de la fila marcada
			        int identificador= (int)this.view.getModelo().getValueAt(row, 0);
			        
			        myArticulo=this.view.getModelo().getDetalle(row).getArticulo();
			        
					BigDecimal cantidadSaldoKardex=myKardex.buscarCantidadSaldo(myArticulo.getId(), depart.getId());
					
					BigDecimal cantidadSaldoItem=view.getModelo().getDetalle(row).getCantidad();
					
					BigDecimal diferencia=cantidadSaldoKardex.subtract(cantidadSaldoItem);
					
					if(diferencia.doubleValue()>0.00){
						calcularTotales();
					}else{
						JOptionPane.showMessageDialog(view, "No se puede requerir la cantidad de "+cantidadSaldoItem.setScale(0, BigDecimal.ROUND_HALF_EVEN).doubleValue()+" del articulo en la bodega "+depart.getDescripcion());  
						view.getModelo().eliminarDetalle(row);
					}
					
					
				}
				
			break;
		}
		
		
		
	}
	
public void calcularTotales(){
		
		//se establecen los totales en cero
		this.myRequisicion.resetTotales();
		
		//se recoren los detalles de la factura
		for(int x=0; x<this.view.getModelo().getDetalles().size();x++){
			
			//se obtiene cada detalle por separado de la factura
			DetalleFacturaProveedor detalle=this.view.getModelo().getDetalle(x);
			
			
			if(detalle.getArticulo().getId()!=-1)//si el detalle es valido
				//if(detalle.getCantidad()!=0 && detalle.getPrecioCompra()!=0)
				if(detalle.getCantidad().doubleValue()!=0 && detalle.getPrecioCompra().doubleValue()!=0){
					
					
					
					//se obtien la cantidad y el precio de compra por unidad
					BigDecimal cantidad=detalle.getCantidad();
					BigDecimal precioCompra=detalle.getPrecioCompra();
					
					//se calcula el total del item
					BigDecimal totalItem=cantidad.multiply(precioCompra);
					
					
					//se estable el total y impuesto en el modelo
					myRequisicion.setTotal(totalItem);
					
					detalle.setTotal(totalItem.setScale(2, BigDecimal.ROUND_HALF_EVEN));
					
					//se establece el total e impuesto en el vista
					this.view.getTxtTotal().setText(""+myRequisicion.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
					
					//se agrega la nueva fila de la tabla
					this.view.getModelo().agregarDetalle();
					
				
					
					//this.view.getModelo().fireTableDataChanged();
				}//fin del if
			
		}//fin del for
		}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		
		switch (comando){
		case "BUSCARARTICULO2":
			if(myArticulo!=null){
				//Se establece el departamento seleccionado
				Departamento depart= (Departamento) this.view.getCbxDepatOrigen().getSelectedItem();
				//se comprueba que exista el producto en el inventario
				boolean resul=myKardex.comprobarKardex(myArticulo.getId(), depart.getId());
				if(resul){
					this.view.getModelo().setArticulo(myArticulo);
					
					
					this.view.getModelo().agregarDetalle();
					view.getTxtArticulo().setText("");
					view.getTxtPrecio().setText("");
					view.getTxtBuscar().setText("");
					
					
					int row =  this.view.getTablaArticulos().getRowCount () - 2;
					//this.view.getModelo().getDetalle(row).setCantidad(1);
					//JOptionPane.showMessageDialog(view, row);
					this.view.getModelo().setPricioCompra(myKardex.buscarKardexPrecio(myArticulo.getId(), 1), row);
					calcularTotales();
					selectRowInset();
				}else{
					JOptionPane.showMessageDialog(view, "El articulo no se encuentra en la "+depart.getDescripcion());  
				}
			}
			break;
			
		case "BUSCARARTICULO":
			this.buscarArticulo();
		break;
		
		case "CERRAR":
			this.salir();
			
			break;
		case "GUARDAR":
			this.guardar();
			break;
		}
		
	}
	private void guardar(){
		
		if(view.getModelo().getRowCount()>1){
			setRequisicion();
			boolean resul=myRequiDao.registrar(myRequisicion);
			
			if(resul){
				JOptionPane.showMessageDialog(view, "Se guardo correctamente.");
				view.setVisible(false);
			}
			else{
				JOptionPane.showMessageDialog(view, "No se guardo correctamente.");
			}
		}else
		{
			JOptionPane.showMessageDialog(view, "Se debe tener articulos para crear la requision. Agrege Articulos primero.");
		}
		
		/*setFactura();
		facturaDao.registrarFacturaTemp(myFactura);
		myFactura.setIdFactura(facturaDao.getIdFacturaGuardada());
		resultado=true;
		//this.view.setVisible(false);
		setEmptyView();*/
		
	}
	private void setRequisicion() {
		
		Departamento depart= (Departamento) this.view.getCbxDepatOrigen().getSelectedItem();
		Departamento departDestino= (Departamento) this.view.getCbxModeloDestino().getSelectedItem();
		
		this.myRequisicion.setDepartamentoOrigen(depart);
		this.myRequisicion.setDepartamentoDestino(departDestino);
		
		String total=view.getTxtTotal().getText();
		this.myRequisicion.setTotal(new BigDecimal(total));
		this.myRequisicion.setDetalles(view.getModelo().getDetalles());
		//fasdf
		/*/sino se ingreso un cliente en particular que coge el cliente por defecto
		if(myCliente==null){
			myCliente=new Cliente();
			myCliente.setId(Integer.parseInt(this.view.getTxtIdcliente().getText()));
			myCliente.setNombre(this.view.getTxtNombrecliente().getText());
			
		}
		
		if(this.view.getRdbtnContado().isSelected()){
			myFactura.setTipoFactura(1);
		}
		
		if(this.view.getRdbtnCredito().isSelected()){
			myFactura.setTipoFactura(2);
		}
		
		myFactura.setCliente(myCliente);
		myFactura.setDetalles(this.view.getModeloTabla().getDetalles());
		myFactura.setFecha(facturaDao.getFechaSistema());
		//Se establece el vendedor seleccionado
		Empleado emp= (Empleado) this.view .getCbxEmpleados().getSelectedItem();
		myFactura.setVendedor(emp);*/
		//myArticulo.setImpuestoObj(imp);
		//JOptionPane.showMessageDialog(view, myCliente);*/
		
	}

	private void salir(){
		//facturaDao.desconectarBD();
		//this.clienteDao.desconectarBD();
		//this.myArticuloDao.desconectarBD();
		//this.myFactura.setIdFactura(-1);
		this.view.setVisible(false);
		
		
	}
	
	private void buscarArticulo(){
		
		//se llama el metodo que mostrar la ventana para buscar el articulo
		ViewListaArticulo viewListaArticulo=new ViewListaArticulo(view);
		CtlArticuloBuscar ctlArticulo=new CtlArticuloBuscar(viewListaArticulo,conexion);
		
		viewListaArticulo.pack();
		ctlArticulo.view.getTxtBuscar().setText("");
		ctlArticulo.view.getTxtBuscar().selectAll();
		//ctlArticulo.view.getTxtBuscar().requestFocus(true);
		//ctlArticulo.view.getTxtBuscar().selectAll();
		view.getTxtBuscar().requestFocusInWindow();
		viewListaArticulo.conectarControladorBuscar(ctlArticulo);
		Articulo myArticulo1=ctlArticulo.buscarArticulo(view);
		
		//JOptionPane.showMessageDialog(view, myArticulo1);
		//se comprueba si le regreso un articulo valido
		if(myArticulo1!=null && myArticulo1.getId()!=-1){
			
			//Se establece el departamento seleccionado
			Departamento depart= (Departamento) this.view.getCbxDepatOrigen().getSelectedItem();
			//se comprueba que exista el producto en el inventario
			boolean resul=myKardex.comprobarKardex(myArticulo1.getId(), depart.getId());
			if(resul){
				this.view.getModelo().setArticulo(myArticulo1);
				
				
				this.view.getModelo().agregarDetalle();
				view.getTxtArticulo().setText("");
				view.getTxtPrecio().setText("");
				view.getTxtBuscar().setText("");
				
				
				int row =  this.view.getTablaArticulos().getRowCount () - 2;
				//this.view.getModelo().getDetalle(row).setCantidad(1);
				//JOptionPane.showMessageDialog(view, row);
				this.view.getModelo().setPricioCompra(myKardex.buscarKardexPrecio(myArticulo1.getId(), 1), row);
				calcularTotales();
				selectRowInset();
			}else{
				JOptionPane.showMessageDialog(view, "El articulo no se encuentra en la "+depart.getDescripcion());  
			}
			
			selectRowInset();
		}
		
		myArticulo=null;
		viewListaArticulo.dispose();
		ctlArticulo=null;
		
	}
	
	private void cargarComboBox(){
		//se crea el objeto para obtener de la bd los impuestos
		//myImpuestoDao=new ImpuestoDao(conexion);
	
		//se obtiene la lista de los impuesto y se le pasa al modelo de la lista
		this.view.getCbxModeloOrigen().setLista(this.deptDao.todos());
		
		
		
		//se remueve la lista por defecto
		
		this.view.getCbxDepatOrigen().removeAllItems();
		
		
		this.view.getCbxDepatOrigen().setSelectedIndex(1);
		
	
		
		
	}
	private void selectRowInset(){
		/*<<<<<<<<<<<<<<<selecionar la ultima fila creada>>>>>>>>>>>>>>>*/
		int row =  this.view.getTablaArticulos().getRowCount () - 2;
		Rectangle rect = this.view.getTablaArticulos().getCellRect(row, 0, true);
		this.view.getTablaArticulos().scrollRectToVisible(rect);
		this.view.getTablaArticulos().clearSelection();
		this.view.getTablaArticulos().setRowSelectionInterval(row, row);
		TabloModeloRequisicion modelo = (TabloModeloRequisicion)this.view.getTablaArticulos().getModel();
		modelo.fireTableDataChanged();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED) {
	          //Object item = e.getItem();
	          // do something with object
			//se remueve la lista por defecto
			//this.view.getCbxDepatOrigen().removeAllItems();
			
			//Se establece el departamento seleccionado
			view.getModelo().setEmptyDetalles();
			
			Departamento depart= (Departamento) this.view.getCbxDepatOrigen().getSelectedItem();
			
			
			//se obtiene la lista de los impuesto y se le pasa al modelo de la lista
			this.view.getCbxModeloDestino().setLista(this.deptDao.todosExecto(depart.getId()));
			//se remueve la lista por defecto
			this.view.getCbxDepartDestino().removeAllItems();
			this.view.getCbxDepartDestino().setSelectedIndex(0);
			view.getModelo().agregarDetalle();
			
			
	       }
	}

}
