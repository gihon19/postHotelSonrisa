package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JLabel;

import controlador.CtlMenuPrincipal;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;

public class ViewMenuPrincipal extends JFrame {
	private final JLabel usuario = new JLabel("Usuario:");
	
	private JMenuItem mntmCerrarFacturacion;
	private JMenuItem mntmProveedores;
	private JMenuItem mntmArticulos;
	private JMenuItem mntmMarcas;
	private JMenuItem mntmAgregarCompras;
	private JMenuItem mntmFacturar;
	private JMenuItem mntmClientes;
	private JMenuItem mntmBuscarFacturas;
	private JMenuItem mntmFacturasIngresadas;
	private JMenuItem mntmRequisicion;
	private JLabel lblUserName;
	private JMenu mnArchivo;
	private JMenuItem mntmUsuarios;
	private JMenuItem mntmSalir;
	private JMenu mnRequisiciones;
	private JMenuItem mntmVerRequi;
	
	private JMenuItem mntmPagosClientes;
	private JMenuItem mntmListaPagos;
	private JMenuItem mntmProgramarPrecios;
	private JMenu mnReportes;
	private JMenuItem mntmDeclaracionDei;

	private JMenuItem mntmInventario;

	private JMenuItem mntmCierresDeCaja;
	private JMenuItem mntmEmpleados;
	
	public ViewMenuPrincipal() {
		setTitle("AdminTools");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewMenuPrincipal.class.getResource("/view/recursos/logo-admin-tool1.png")));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		mntmUsuarios = new JMenuItem("Usuarios");
		mnArchivo.add(mntmUsuarios);
		
		mntmEmpleados = new JMenuItem("Empleados");
		mnArchivo.add(mntmEmpleados);
		
		mntmSalir = new JMenuItem("Salir");
		mnArchivo.add(mntmSalir);
		
		JMenu mnInventario = new JMenu("Inventario");
		menuBar.add(mnInventario);
		
		mntmProveedores = new JMenuItem("Proveedores");
		mnInventario.add(mntmProveedores);
		
		mntmArticulos = new JMenuItem("Articulos");
		mnInventario.add(mntmArticulos);
		
		mntmMarcas = new JMenuItem("Marcas");
		mnInventario.add(mntmMarcas);
		/*
		mntmProgramarPrecios = new JMenuItem("Programar Precios");
		mnInventario.add(mntmProgramarPrecios);*/
		
		mnRequisiciones = new JMenu("Requisiciones");
		mnInventario.add(mnRequisiciones);
		
		mntmRequisicion = new JMenuItem("Agregar");
		mnRequisiciones.add(mntmRequisicion);
		
		mntmVerRequi = new JMenuItem("Ver");
		mnRequisiciones.add(mntmVerRequi);
		
		JMenu mnFacturacion = new JMenu("Facturacion");
		menuBar.add(mnFacturacion);
		
		mntmFacturar = new JMenuItem("Facturar");
		mnFacturacion.add(mntmFacturar);
		
		mntmCerrarFacturacion = new JMenuItem("Cerrar Facturacion");
		mnFacturacion.add(mntmCerrarFacturacion);
		

		mntmClientes = new JMenuItem("Clientes");
		mnFacturacion.add(mntmClientes);
		
		mntmBuscarFacturas = new JMenuItem("Buscar Facturas");
		mnFacturacion.add(mntmBuscarFacturas);
		
		JMenu mnCompras = new JMenu("Compras");
		menuBar.add(mnCompras);
		
		mntmAgregarCompras = new JMenuItem("Agregar");
		mnCompras.add(mntmAgregarCompras);
		
		mntmFacturasIngresadas = new JMenuItem("Facturas Ingresadas");
		mnCompras.add(mntmFacturasIngresadas);
		
		JMenu mnCuentasPorCobrar = new JMenu("Cuentas por Cobrar");
		menuBar.add(mnCuentasPorCobrar);
		
		mntmListaPagos = new JMenuItem("Ver Pagos");
		mnCuentasPorCobrar.add(mntmListaPagos);
		
		mntmPagosClientes = new JMenuItem("Pagos Clientes");
		mnCuentasPorCobrar.add(mntmPagosClientes);
		
		mnReportes = new JMenu("Reportes");
		menuBar.add(mnReportes);
		
		mntmDeclaracionDei = new JMenuItem("Declaracion DEI");
		mnReportes.add(mntmDeclaracionDei);
		
		/*
		JMenu mnCuentasPorPagar = new JMenu("Cuentas Por Pagar");
		menuBar.add(mnCuentasPorPagar);*/
		
		mntmInventario = new JMenuItem("Inventario");
		mnReportes.add(mntmInventario);
		
		mntmCierresDeCaja = new JMenuItem("Cierres de caja");
		mnReportes.add(mntmCierresDeCaja);
		/*
		JMenuItem mntmFacturasPendientes = new JMenuItem("Facturas pendientes");
		mnCuentasPorPagar.add(mntmFacturasPendientes);
		*/
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de..");
		menuBar.add(mntmAcercaDe);
		setSize(1024,700);
		
		JPanel panel = new JPanel();
		//panel.setBackground(new Color(0, 191, 255));
		//panel.setBackground(new Color(119, 136, 153));
		panel.setSize(700, 100);
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.add(usuario);
		
		lblUserName = new JLabel("Unico");
		panel.add(lblUserName);
		
		JPanel panel_1 = new panelFondo();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void conectarControlador(CtlMenuPrincipal c){
		
		mntmProveedores.addActionListener(c);
		mntmProveedores.setActionCommand("PROVEEDORES");
		
		mntmArticulos.addActionListener(c);
		mntmArticulos.setActionCommand("ARTICULOS");
		
		mntmMarcas.addActionListener(c);
		mntmMarcas.setActionCommand("MARCAS");
		
		mntmAgregarCompras.addActionListener(c);
		mntmAgregarCompras.setActionCommand("AGREGARCOMPRAS");
		
		mntmFacturar.addActionListener(c);
		mntmFacturar.setActionCommand("FACTURAR");
		
		mntmClientes.addActionListener(c);
		mntmClientes.setActionCommand("CLIENTES");
		
		mntmBuscarFacturas.addActionListener(c);
		mntmBuscarFacturas.setActionCommand("BUSCARFACTURAS");
		
		mntmCerrarFacturacion.addActionListener(c);
		mntmCerrarFacturacion.setActionCommand("CERRARFACTURACION");
		
		
		mntmFacturasIngresadas.addActionListener(c);
		mntmFacturasIngresadas.setActionCommand("LISTAFACTURASCOMPRA");
		
		mntmRequisicion.addActionListener(c);
		mntmRequisicion.setActionCommand("REQUISICION");
		
		mntmUsuarios.addActionListener(c);
		mntmUsuarios.setActionCommand("USUARIOS");
		
		mntmVerRequi.addActionListener(c);
		mntmVerRequi.setActionCommand("REQUISICIONES");
		
		mntmPagosClientes.addActionListener(c);
		mntmPagosClientes.setActionCommand("PAGOCLIENTES");
		
		mntmListaPagos.addActionListener(c);
		mntmListaPagos.setActionCommand("LISTAPAGOS");
		
		/*
		mntmProgramarPrecios.addActionListener(c);
		mntmProgramarPrecios.setActionCommand("PROGRAMARPRECIOS");*/
		
		
		mntmDeclaracionDei.addActionListener(c);
		mntmDeclaracionDei.setActionCommand("R_DEI");
		
		mntmInventario.addActionListener(c);
		mntmInventario.setActionCommand("INVENTARIO");
		
		mntmCierresDeCaja.addActionListener(c);
		mntmCierresDeCaja.setActionCommand("CIERRES_CAJA");
		
		mntmEmpleados.addActionListener(c);
		mntmEmpleados.setActionCommand("EMPLEADOS");
		
	}
	public JLabel getLblUserName(){
		return lblUserName;
	}
	
	private class panelFondo extends JPanel{
		@Override
		   public void paintComponent(Graphics g){
		      Dimension tamanio = getSize();
		      ImageIcon imagenFondo = new ImageIcon(getClass().
		      getResource("/view/recursos/fondo-sistema.jpg"));
		      g.drawImage(imagenFondo.getImage(), 0, 0,
		      tamanio.width, tamanio.height, null);
		      setOpaque(false);
		      super.paintComponent(g);
		   }
	}

}
