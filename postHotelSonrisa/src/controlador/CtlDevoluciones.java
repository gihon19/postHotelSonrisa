package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import view.ViewFacturaDevolucion;
import modelo.AbstractJasperReports;
import modelo.Articulo;
import modelo.Cliente;
import modelo.Conexion;
import modelo.Factura;
import modelo.dao.ArticuloDao;
import modelo.dao.ClienteDao;
import modelo.dao.DevolucionesDao;
import modelo.dao.EmpleadoDao;
import modelo.dao.FacturaDao;
import modelo.dao.PrecioArticuloDao;

public class CtlDevoluciones implements ActionListener, MouseListener, TableModelListener, WindowListener, KeyListener {
	
	private ViewFacturaDevolucion view;
	private Conexion conexion;
	
	private Factura myFactura=null;
	private FacturaDao facturaDao=null;//=new FacturaDao();
	private ClienteDao clienteDao=null;//=new ClienteDao();
	private Articulo myArticulo=null;
	private ArticuloDao myArticuloDao=null;
	private EmpleadoDao myEmpleadoDao=null;
	private PrecioArticuloDao preciosDao=null;
	private Cliente myCliente=null;
	private int filaPulsada=0;
	private boolean resultado=false;
	private int tipoView=1;
	private int netBuscar=0;
	private static final Pattern numberPattern=Pattern.compile("-?\\d+");
	
	private DevolucionesDao devolucionDao=null;

	public CtlDevoluciones(ViewFacturaDevolucion v,Conexion conn) {
		// TODO Auto-generated constructor stub
		view=v;
		conexion=conn;
		myFactura=new Factura();
		myArticuloDao=new ArticuloDao(conexion);
		clienteDao=new ClienteDao(conexion);
		facturaDao=new FacturaDao(conexion);
		myEmpleadoDao=new EmpleadoDao(conexion);
		preciosDao=new PrecioArticuloDao(conexion);
		view.conectarContralador(this);
		devolucionDao=new DevolucionesDao(conexion);
		
		//this.setEmptyView();
		//cargarComboBox();
	}
	
	private void guardar() {
		//se verifica que hay facturas que cobrar
		if(view.getModeloTabla().getDetalles().size()>0 && view.getModeloTabla().hayDevoluciones()==true){
			
			//se manda aguardar el recibo con los pagos realizados
			//boolean resul=this.myReciboDao.registrar(myRecibo);
			boolean resul=true;
			if(resul){
				
				//*myRecibo.setNoRecibo(myReciboDao.idUltimoRecibo);
				//se cambia el estado de las factura que fueron cobradas
				for(int x=0;x<view.getModeloTabla().getDetalles().size();x++){
					
					//se cambia el estado de las facturas pagas
					if(view.getModeloTabla().getDetalles().get(x).getAccion()==true){
						boolean resu=this.devolucionDao.agregarDetalle(view.getModeloTabla().getDetalles().get(x), myFactura.getIdFactura());
					}
				}
				//JOptionPane.showMessageDialog(view, "El recibo se guardo correctamente.");
				this.view.setVisible(false);
				
				
				try {
					/*this.view.setVisible(false);
					this.view.dispose();*/
					//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Factura_Saint_Paul.jasper",myFactura.getIdFactura() );
					AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 6,myFactura.getIdFactura());
					//AbstractJasperReports.showViewer(view);
					AbstractJasperReports.showViewer(view);
					
					//myFactura.
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{//
				JOptionPane.showMessageDialog(view, "La devolucion no se guardo correctamente.");
			}//fin del if que verefica la acccion de guardar el recibo
		}else{
			JOptionPane.showMessageDialog(view, "Seleccione por lo menos un articulo de la factura");
		}
		
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
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		view.setVisible(false);
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
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
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
		
		switch(comando){
		case "GUARDAR":
			guardar();
			break;
		}
		
	}
public void cargarFacturaView(){
		
		this.view.getTxtIdcliente().setText(""+myFactura.getCliente().getId());;
		this.view.getTxtNombrecliente().setText(myFactura.getCliente().getNombre());
		
		//se establece el total e impuesto en el vista
		this.view.getTxtTotal().setText(""+myFactura.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		this.view.getTxtImpuesto().setText(""+myFactura.getTotalImpuesto().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		this.view.getTxtSubtotal().setText(""+myFactura.getSubTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		view.getTxtFechafactura().setText(myFactura.getFecha());
		this.view.getModeloTabla().setDetalles(myFactura.getDetalles());
	}


	public Factura actualizarFactura(Factura f) {
		// TODO Auto-generated method stub
		this.myFactura=f;
		cargarFacturaView();
		//this.view.getBtnGuardar().setVisible(false);
		//this.view.getBtnActualizar().setVisible(true);
		this.view.getModeloTabla().agregarDetalle();
		tipoView=2;
		this.view.setVisible(true);
		
		//para controlar que es una actualizacion la que se hace
		
		
		return myFactura;
		
	}


	public boolean getAccion() {
		view.setVisible(true);
		return resultado;
	}

}
