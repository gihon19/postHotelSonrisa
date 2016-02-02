package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import modelo.AbstractJasperReports;
import modelo.Cliente;
import modelo.Conexion;
import modelo.NumberToLetterConverter;
import modelo.ReciboPago;
import modelo.dao.ClienteDao;
import modelo.dao.FacturaDao;
import modelo.dao.ReciboPagoDao;
import view.ViewCobro;
import view.ViewListaClientes;
import view.ViewSalidaCaja;

public class CtlCobro implements ActionListener, KeyListener {
	
	private ViewCobro view=null;
	private Conexion conexion=null;
	private Cliente myCliente=null;
	private ClienteDao clienteDao=null;
	private FacturaDao myFacturaDao=null;
	
	private ReciboPago myRecibo=null;
	private ReciboPagoDao myReciboDao=null;

	public CtlCobro(ViewCobro v,Conexion conn) {
		view=v;
		conexion=conn;
		view.conectarContralador(this);
		clienteDao=new ClienteDao(conexion);
		myFacturaDao=new FacturaDao(conexion);
		myReciboDao=new ReciboPagoDao(conexion);
		myRecibo=new ReciboPago();
		view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		//JOptionPane.showMessageDialog(view, "paso de celdas");
		switch(comando){
		case "BUSCARCLIENTE":
			myCliente=null;
			myCliente=clienteDao.buscarCliente(Integer.parseInt(this.view.getTxtIdcliente().getText()));
			
			if(myCliente!=null){
				this.view.getTxtNombrecliente().setText(myCliente.getNombre());
				view.getTxtLimiteCredito().setText("L. "+myCliente.getLimiteCredito());
				view.getTxtSaldo().setText("L. "+myCliente.getSaldoCuenta());
				//cargarFacturasClientes(myFacturaDao.sinPagarCliente(myCliente));
			}else{
				this.view.getTxtIdcliente().setText("");
				this.view.getTxtNombrecliente().setText("");
				JOptionPane.showMessageDialog(view, "Cliente no encontrado");
			}
			
			break;
			
		case "BUSCARCLIENTES":
			this.buscarCliente();
			break;
			
			
		case "COBRAR":
			 cobrar();
			break;
			
		case "CERRAR":
				view.setVisible(false);
			break;
		
		}
		
		
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
switch(e.getKeyCode()){
		
		case KeyEvent.VK_F1:
			
			break;
			
		case KeyEvent.VK_F2:
			cobrar();
			break;
			
		case KeyEvent.VK_F3:
				buscarCliente();
			break;
			
		case KeyEvent.VK_F4:
			
			break;
			
		case KeyEvent.VK_F5:
			
			break;
			
		case KeyEvent.VK_F6:
			
			break;
			
		case KeyEvent.VK_F7:
			
			break;
			
		case KeyEvent.VK_F8:
			
			break;
		case KeyEvent.VK_F9:
			
			break;
			
		case KeyEvent.VK_F10:
			
			
			break;
			
		case KeyEvent.VK_F11:
			
			break;
			
		case KeyEvent.VK_F12:
			
			break;
			
		case  KeyEvent.VK_ESCAPE:
			view.setVisible(false);
		break;
		
		case KeyEvent.VK_DELETE:
			
			break;
			
		case KeyEvent.VK_DOWN:
			
		case KeyEvent.VK_UP:
			
			break;
		case KeyEvent.VK_LEFT:
			
			break;
		case KeyEvent.VK_RIGHT:
			
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void buscarCliente(){
		//se crea la vista para buscar los cliente
		ViewListaClientes viewListaCliente=new ViewListaClientes (this.view);
		
		CtlClienteBuscar ctlBuscarCliente=new CtlClienteBuscar(viewListaCliente,conexion);
		
		myCliente=ctlBuscarCliente.buscarCliente(view);
		//se comprueba si le regreso un articulo valido
		if(myCliente.getNombre()!=null && myCliente.getId()!=-1){
			this.view.getTxtIdcliente().setText(""+myCliente.getId());;
			this.view.getTxtNombrecliente().setText(myCliente.getNombre());
			view.getTxtLimiteCredito().setText("L. "+myCliente.getLimiteCredito());
			view.getTxtSaldo().setText("L. "+myCliente.getSaldoCuenta());
			//cargarFacturasClientes(myFacturaDao.sinPagarCliente(myCliente));
		
		}else{
			JOptionPane.showMessageDialog(view, "No se encontro el cliente");
			this.view.getTxtIdcliente().setText("1");;
			this.view.getTxtNombrecliente().setText("Cliente Normal");
		}
		viewListaCliente.dispose();
		ctlBuscarCliente=null;
	}
	
	private void cobrar() {
		
			setRecibo();
			//se manda aguardar el recibo con los pagos realizados
			boolean resul=this.myReciboDao.registrar(myRecibo);
			
			if(resul){
				
				myRecibo.setNoRecibo(myReciboDao.idUltimoRecibo);
				
				JOptionPane.showMessageDialog(view, "El recibo se guardo correctamente.");
				this.view.setVisible(false);
				
				
				try {
				
					AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 5, myRecibo.getNoRecibo());
					//AbstractJasperReports.showViewer(view);
					AbstractJasperReports.showViewer(view);
					
					//myFactura.
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{//
				JOptionPane.showMessageDialog(view, "El recibo no se guardo correctamente.");
			}//fin del if que verefica la acccion de guardar el recibo
		
		
	}
	private void setRecibo() {
		// TODO Auto-generated method stub
		myRecibo.setCliente(myCliente);
		
		//se estable el concepto de pago del recibo
		String concepto="Pago de factura(s) no.";
		
		myRecibo.setTotal(new BigDecimal(view.getTxtTotal().getText()));
		myRecibo.setConcepto(concepto);
		//se establece la cantidad en letras
		myRecibo.setTotalLetras(NumberToLetterConverter.convertNumberToLetter(myRecibo.getTotal().setScale(0, BigDecimal.ROUND_HALF_EVEN).doubleValue()));
	}

}
