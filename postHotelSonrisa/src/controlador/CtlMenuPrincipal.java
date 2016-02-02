package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.AbstractJasperReports;
import modelo.dao.CierreCajaDao;
import modelo.Conexion;
import view.ViewAgregarCompras;
import view.ViewCrearEmpleado;
import view.ViewCrearUsuario;
import view.ViewCxCPagos;
import view.ViewFacturar;
import view.ViewFacturas;
import view.ViewFiltroReportDei;
import view.ViewListaArticulo;
import view.ViewListaCierresCaja;
import view.ViewListaClientes;
import view.ViewListaEmpleados;
import view.ViewListaFactura;
import view.ViewListaFacturasCompra;
import view.ViewListaMarca;
import view.ViewListaPagos;
import view.ViewListaPrecioProgramar;
import view.ViewListaProveedor;
import view.ViewListaRequisiciones;
import view.ViewListaUsuarios;
import view.ViewMenuPrincipal;
import view.ViewRequisicion;

public class CtlMenuPrincipal implements ActionListener {
	
	public ViewMenuPrincipal view;
	public Conexion conexion=null;
	
	
	
	public CtlMenuPrincipal(ViewMenuPrincipal view, Conexion conn){
		conexion=conn;
		this.view=view;
		
		view.getLblUserName().setText(conexion.getUsuarioLogin().getNombre());
		//view.setMaximumSize(maximumSize);
		
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String comando=e.getActionCommand();
		
		JDialog.setDefaultLookAndFeelDecorated(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		switch(comando){
		
			case "REQUISICION":
					ViewRequisicion viewRequi=new ViewRequisicion(view);
					CtlRequisicion ctlRequi=new CtlRequisicion(viewRequi,conexion);
					viewRequi.dispose();
					ctlRequi=null;
				break;
				
			case "REQUISICIONES":
				ViewListaRequisiciones viewRequiLista=new ViewListaRequisiciones(view);
				CtlRequisicionesLista ctlRequiLista=new CtlRequisicionesLista(viewRequiLista,conexion);
				viewRequiLista.dispose();
				ctlRequiLista=null;
			break;
	
			case "CERRARFACTURACION":
				try {
					//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Cierre_Caja_Saint_Paul.jasper",1 );
					AbstractJasperReports.createReport(conexion.getPoolConexion().getConnection(), 4, 0);
					
					//this.view.setModal(false);
					//AbstractJasperReports.imprimierFactura();
					AbstractJasperReports.showViewer(this.view);
					
					CierreCajaDao cierre=new CierreCajaDao(conexion);
					
					if(cierre.registrarCierre()){
						JOptionPane.showMessageDialog(view, "Se creo el cierre de caja");
					}
					
				} catch (SQLException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
				break;
			case "PROVEEDORES":
				ViewListaProveedor viewListaProveedor=new ViewListaProveedor(view);
				CtlProveedorLista ctlProveedor=new CtlProveedorLista(viewListaProveedor,conexion);
				viewListaProveedor.dispose();
				ctlProveedor=null;
				break;
			case "ARTICULOS":
				ViewListaArticulo viewListaArticulo=new ViewListaArticulo(view);
				CtlArticuloLista ctlArticulo =new CtlArticuloLista(viewListaArticulo,conexion);
				viewListaArticulo.dispose();
				ctlArticulo=null;
				
				break;
			case "AGREGARCOMPRAS":
				ViewAgregarCompras viewAgregarCompras= new ViewAgregarCompras(this.view);
				CtlAgregarCompras ctlAgregarCompra=new CtlAgregarCompras(viewAgregarCompras,conexion);
				
				
				viewAgregarCompras.dispose();
				viewAgregarCompras=null;
				ctlAgregarCompra=null;
				break;
			case "FACTURAR":
				
				/*ViewListaFactura vistaFacturars=new ViewListaFactura(this.view);
				CtlFacturaLista ctlFacturas=new CtlFacturaLista(vistaFacturars,conexion );
				vistaFacturars.dispose();
				ctlFacturas=null;*/
			
				ViewFacturar vistaFacturar=new ViewFacturar(this.view);
				//vistaFacturar.pack();
				CtlFacturar ctlFacturar=new CtlFacturar(vistaFacturar,conexion );
				vistaFacturar.setVisible(true);
				
				
				
				
		
				
				break;
			case "MARCAS":
				//se crea la lista de marcas
				ViewListaMarca viewMarcas=new ViewListaMarca(this.view);
				
				// se crea el control para la view lista marcas
				CtlMarcaLista ctlMarca =new CtlMarcaLista(viewMarcas,conexion); 
				
				viewMarcas.dispose();
				ctlMarca=null;
				
				break;
			case "CLIENTES":
				ViewListaClientes viewClientes=new ViewListaClientes(view);
				CtlClienteLista  ctlClientes=new CtlClienteLista(viewClientes,conexion);
				viewClientes.dispose();
				ctlClientes=null;
				break;
			case "BUSCARFACTURAS":
				
				ViewFacturas viewBuscarFacturas = new ViewFacturas(this.view);
				CtlFacturas cltBuscarFacturas= new CtlFacturas(viewBuscarFacturas,conexion);
				viewBuscarFacturas.dispose();
				cltBuscarFacturas=null;
				break;
				
			case "LISTAFACTURASCOMPRA":
				ViewListaFacturasCompra viewFacturasCompra=new ViewListaFacturasCompra(this.view);
				CtlFacturasCompra ctlFacturasCompra=new CtlFacturasCompra(viewFacturasCompra,conexion);
				viewFacturasCompra.dispose();
				viewFacturasCompra=null;
				ctlFacturasCompra=null;
				break;
				
			case "PAGOCLIENTES":
				ViewCxCPagos viewPagoClientes=new ViewCxCPagos(view);
				CtlFacturaPagos ctlPagoCleintes=new CtlFacturaPagos(viewPagoClientes,conexion);
				viewPagoClientes.dispose();
				viewPagoClientes=null;
				ctlPagoCleintes=null;
				break;
			case "LISTAPAGOS":
				
				ViewListaPagos viewListaPagos=new ViewListaPagos(view);
				CtlPagoLista ctlPagoLista =new CtlPagoLista(viewListaPagos, conexion);
				viewListaPagos.dispose();
				viewListaPagos=null;
				ctlPagoLista=null;
				break;
			case "PROGRAMARPRECIOS":
				ViewListaPrecioProgramar viewProgramarPrecio=new ViewListaPrecioProgramar(view);
				CtlProgramarPrecios ctlProgramarPrecio=new CtlProgramarPrecios(viewProgramarPrecio, conexion);
				viewProgramarPrecio.dispose();
				viewProgramarPrecio=null;
				ctlProgramarPrecio=null;
				break;
				
			case "R_DEI":
				ViewFiltroReportDei viewFiltroDei=new ViewFiltroReportDei(view);
				CtlFiltroRepDei ctlFiltroDei=new CtlFiltroRepDei(viewFiltroDei,conexion);
				break;
			case "USUARIOS":
				
				ViewListaUsuarios viewListaUsuarios=new ViewListaUsuarios(view);
				CtlUsuariosLista ctlUsuarios=new CtlUsuariosLista(viewListaUsuarios,conexion);
				viewListaUsuarios.dispose();
				viewListaUsuarios=null;
				ctlUsuarios=null;
				/*ViewCrearUsuario viewCrearUsuario=new ViewCrearUsuario(view);
				CtlUsuario ctlUsuario=new CtlUsuario(viewCrearUsuario, conexion);
				viewCrearUsuario.setVisible(true);*/
				
				break;
			case "INVENTARIO":
				try {
					//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Cierre_Caja_Saint_Paul.jasper",1 );
					AbstractJasperReports.createReportInventario(conexion.getPoolConexion().getConnection(), conexion.getUsuarioLogin().getUser());
					
					//this.view.setModal(false);
					//AbstractJasperReports.imprimierFactura();
					AbstractJasperReports.showViewer(this.view);
					
					
				} catch (SQLException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
				break;
				
			case "CIERRES_CAJA":
				
				ViewListaCierresCaja viewCierres=new ViewListaCierresCaja(view);
				CtlCierresCajaLista ctlCierres=new CtlCierresCajaLista(viewCierres,conexion);
				
				viewCierres.dispose();
				viewCierres=null;
				ctlCierres=null;
				/*try {
					//AbstractJasperReports.createReportFactura( conexion.getPoolConexion().getConnection(), "Cierre_Caja_Saint_Paul.jasper",1 );
					AbstractJasperReports.createReportCierresCaja(conexion.getPoolConexion().getConnection(), conexion.getUsuarioLogin().getUser());
					
					//this.view.setModal(false);
					//AbstractJasperReports.imprimierFactura();
					AbstractJasperReports.showViewer(this.view);
					
					
				} catch (SQLException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}*/
				break;
				
			case "EMPLEADOS":
				
				ViewListaEmpleados viewEmpleados=new ViewListaEmpleados(view);
				CtlEmpleadosLista ctlListaEmpleados=new CtlEmpleadosLista(viewEmpleados,conexion);
				
				viewEmpleados.dispose();
				viewEmpleados=null;
				ctlListaEmpleados=null;
				
				
					/*ViewCrearEmpleado viewCrearEmpleado=new ViewCrearEmpleado(view);
					CtlEmpleado ctlCrearEmpleado=new CtlEmpleado(viewCrearEmpleado, conexion);
					ctlCrearEmpleado.agregarEmpleado();
					viewCrearEmpleado.dispose();
					viewCrearEmpleado=null;
					ctlCrearEmpleado=null;*/
				break;
				
		}
		
	}

}
