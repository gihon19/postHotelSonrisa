package view;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;







import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;


import javax.swing.ListSelectionModel;

import controlador.CtlAgregarCompras;
















import com.toedter.calendar.JDateChooser;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;


import javax.swing.SwingConstants;

import view.botones.BotonActualizar;
import view.botones.BotonCancelar;
import view.botones.BotonGuardar;
import view.tablemodel.CbxTmDepartamento;
import view.tablemodel.DmtFacturaProveedores;

import javax.swing.JComboBox;


public class ViewAgregarCompras extends JDialog {
	/**
	 * Vista para ingresar las facturasd el la compra
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtIdProveedor;
	private JTextField txtNombreproveedor;
	protected JPanel panelProveedor;
	private JTextField txtTelefonoProveedor;
	
	private JTable tablaArticulos;
	private DmtFacturaProveedores modelo;
	private JTextField txtNofactura;
	private ButtonGroup grupoOpciones;
	private JRadioButton rdbtnCredito;
	private JRadioButton rdbtnContado;
	
	
	private BotonCancelar btnCancelar;
	private BotonGuardar btnGuardar;
	private BotonActualizar btnActualizar;
	
	private JDateChooser dateCompra;
	private JDateChooser dateVencFactura;
	private JTextField txtTotalimpuesto;
	private JTextField txtTotal;
	private JLabel lblFechaVencimiento;
	private JTextField txtSubtotal;
	
	private CbxTmDepartamento modeloCbx;
	private JComboBox cbxDepart;
	
	
	public ViewAgregarCompras(Window view) {
		super(view,"Registrar Compras",Dialog.ModalityType.DOCUMENT_MODAL);
		getContentPane().setLayout(null);
		modeloCbx=new CbxTmDepartamento();//comentar para ver en forma de diseño
		
		panelProveedor=new JPanel();
		panelProveedor.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Proveedor", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelProveedor.setBounds(10, 82, 764, 78);
		panelProveedor.setLayout(null);
		
		//JPanel panelProveedor = new JPanel();
		
		//
		
		JLabel lblIdProveedor = new JLabel("Id");
		lblIdProveedor.setBounds(10, 21, 72, 14);
		panelProveedor.add(lblIdProveedor);
		
		txtIdProveedor = new JTextField();
		txtIdProveedor.setBounds(10, 36, 104, 31);
		txtIdProveedor.setToolTipText("Id Proveedor");
		panelProveedor.add(txtIdProveedor);
		txtIdProveedor.setColumns(10);
		
		JLabel lblNombreProveedor = new JLabel("Nombre");
		lblNombreProveedor.setBounds(144, 21, 104, 14);
		panelProveedor.add(lblNombreProveedor);
		
		txtNombreproveedor = new JTextField();
		txtNombreproveedor.setEditable(false);
		txtNombreproveedor.setBounds(144, 36, 276, 31);
		panelProveedor.add(txtNombreproveedor);
		txtNombreproveedor.setColumns(10);
		
		getContentPane().add(panelProveedor);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(455, 21, 58, 14);
		panelProveedor.add(lblTelefono);
		
		txtTelefonoProveedor = new JTextField();
		txtTelefonoProveedor.setEditable(false);
		txtTelefonoProveedor.setBounds(455, 36, 276, 31);
		panelProveedor.add(txtTelefonoProveedor);
		txtTelefonoProveedor.setColumns(10);
		
		JPanel panelInfoCompra = new JPanel();
		panelInfoCompra.setBounds(10, 11, 764, 72);
		panelInfoCompra.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "------------------", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(panelInfoCompra);
		panelInfoCompra.setLayout(null);
		
		JLabel lblFecha = new JLabel("Fecha Factura");
		lblFecha.setBounds(10, 11, 80, 14);
		panelInfoCompra.add(lblFecha);
		
		dateCompra = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		dateCompra.setBounds(10, 30, 134, 31);
	
		panelInfoCompra.add(dateCompra);
		dateCompra.setDateFormatString("dd-MM-yyyy");
		//dateCompra.setDate(Date.);
		
		JLabel lblNoFactura = new JLabel("No Factura");
		lblNoFactura.setBounds(149, 11, 62, 14);
		panelInfoCompra.add(lblNoFactura);
		
		txtNofactura = new JTextField();
		txtNofactura.setBounds(149, 30, 102, 31);
		panelInfoCompra.add(txtNofactura);
		txtNofactura.setColumns(10);
		
		grupoOpciones = new ButtonGroup();
		rdbtnCredito = new JRadioButton("Credito");
		rdbtnCredito.setBounds(326, 34, 72, 23);
		grupoOpciones.add(rdbtnCredito);
		panelInfoCompra.add(rdbtnCredito);
		
		rdbtnContado = new JRadioButton("Contado");
		rdbtnContado.setSelected(true);
		rdbtnContado.setBounds(256, 34, 80, 23);
		grupoOpciones.add(rdbtnContado);
		panelInfoCompra.add(rdbtnContado);
		
		lblFechaVencimiento = new JLabel("Vencimiento");
		lblFechaVencimiento.setBounds(405, 11, 72, 14);
		panelInfoCompra.add(lblFechaVencimiento);
		
		dateVencFactura = new JDateChooser();
		dateVencFactura.setDateFormatString("dd-MM-yyyy");
		dateVencFactura.setBounds(405, 30, 129, 31);
		dateVencFactura.setEnabled(false);
		panelInfoCompra.add(dateVencFactura);
		
		JLabel lblDepartementoDeLa = new JLabel("Departemento de la compra");
		lblDepartementoDeLa.setBounds(544, 11, 168, 14);
		panelInfoCompra.add(lblDepartementoDeLa);
		
		cbxDepart = new JComboBox();
		cbxDepart.setModel(modeloCbx);
		cbxDepart.setBounds(544, 30, 210, 31);
		panelInfoCompra.add(cbxDepart);
		
		//botones
		btnGuardar = new BotonGuardar();
		btnGuardar.setSize(128, 72);
		btnGuardar.setLocation(32, 479);
		//tnCancelar.setLocation(42, 175);
		getContentPane().add(btnGuardar);
		btnActualizar=new BotonActualizar();
		btnActualizar.setLocation(32, 506);
		getContentPane().add(btnActualizar);
		btnActualizar.setVisible(false);
		
		btnCancelar = new BotonCancelar();
		btnCancelar.setSize(128, 72);
		
		//btnCancelar.setBounds(212, 175, 135, 39);
		btnCancelar.setLocation(270, 479);
		getContentPane().add(btnCancelar);
		
		
		//tabla de registro de los proveedores
		tablaArticulos=new JTable();
		modelo = new DmtFacturaProveedores();//se crea el modelo de los datos de la tabla
		tablaArticulos.setModel(modelo);
		tablaArticulos.getColumnModel().getColumn(0).setPreferredWidth(140);     //Tamaño de las columnas de las tablas
		tablaArticulos.getColumnModel().getColumn(1).setPreferredWidth(200);	//
		tablaArticulos.getColumnModel().getColumn(2).setPreferredWidth(90);	//
		tablaArticulos.getColumnModel().getColumn(3).setPreferredWidth(90);	//
		tablaArticulos.getColumnModel().getColumn(4).setPreferredWidth(90);	//
		tablaArticulos.getColumnModel().getColumn(5).setPreferredWidth(90);	//
		//tablaArticulos.getColumnModel().getColumn(6).setPreferredWidth(100);	//
		//Estitlo para la tabla		
		//TablaRenderizadorProveedor renderizador = new TablaRenderizadorProveedor();
		//tablaArticulos.setDefaultRenderer(String.class, renderizador);
		JScrollPane scrollPane = new JScrollPane(tablaArticulos);
		scrollPane.setBounds(10, 171, 764, 250);
		getContentPane().add(scrollPane);
		
		txtTotalimpuesto = new JTextField();
		txtTotalimpuesto.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalimpuesto.setEditable(false);
		txtTotalimpuesto.setBounds(580, 466, 177, 34);
		getContentPane().add(txtTotalimpuesto);
		txtTotalimpuesto.setColumns(10);
		
		txtTotal = new JTextField();
		txtTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotal.setEditable(false);
		txtTotal.setBounds(580, 510, 177, 34);
		getContentPane().add(txtTotal);
		txtTotal.setColumns(10);
		
		JLabel lblTotalImpuesto = new JLabel("Total Impuesto");
		lblTotalImpuesto.setBounds(486, 476, 86, 14);
		getContentPane().add(lblTotalImpuesto);
		
		JLabel lblTotalFactura = new JLabel("Total Factura");
		lblTotalFactura.setBounds(486, 520, 84, 14);
		getContentPane().add(lblTotalFactura);
		
		JLabel lblSubtotal = new JLabel("SubTotal");
		lblSubtotal.setBounds(486, 435, 84, 14);
		getContentPane().add(lblSubtotal);
		
		txtSubtotal = new JTextField();
		txtSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSubtotal.setEditable(false);
		txtSubtotal.setBounds(580, 424, 177, 36);
		getContentPane().add(txtSubtotal);
		txtSubtotal.setColumns(10);
		
		
		
		///DetalleFacturaProveedor uno= new DetalleFacturaProveedor();
		
		//modelo.agregarDetalle(uno);
		this.setSize(800, 600);
		
		//centrar la ventana en la pantalla
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
	
		

		
		
	}
	public JComboBox getCbxDepart(){
		return cbxDepart;
	}
	public CbxTmDepartamento getModeloCbx(){
		return modeloCbx;
	}
	public JTextField getTxtNofactura(){
		return txtNofactura;
	}
	public JTextField getTxtSubtotal(){
		return txtSubtotal;
	}
	
	public JTextField getTxtTotal(){
		return txtTotal;
	}
	public JTextField getTxtTotalimpuesto(){
		return  txtTotalimpuesto;
	}
	public JDateChooser getDateCompra(){
		return dateCompra;
	}
	public JDateChooser getDateVencFactura(){
		return dateVencFactura;
	}
	public JTextField gettxtTelefonoProveedor(){
		return txtTelefonoProveedor;
	}
	public JTextField getTxtNombreproveedor(){
		return txtNombreproveedor;
	}
	public JTextField getTxtIdProveedor(){
		return txtIdProveedor;
	}
	public DmtFacturaProveedores getModelo(){
		return modelo;
	}
	public JTable getTablaArticulos(){
		return tablaArticulos;
	}
	public void conectarControlador(CtlAgregarCompras c){
		
		
		this.txtIdProveedor.addActionListener(c);
		this.txtIdProveedor.setActionCommand("BUSCARPROVEEDOR");
		txtIdProveedor.addKeyListener(c);
		
		
		this.btnGuardar.addActionListener(c);
		this.btnGuardar.setActionCommand("GUARDARCOMPRA");
		btnGuardar.addKeyListener(c);
		

		this.rdbtnContado.addActionListener(c);
		this.rdbtnContado.setActionCommand("CONTADO");
		rdbtnContado.addKeyListener(c);
		
		
		this.rdbtnCredito.addActionListener(c);
		this.rdbtnCredito.setActionCommand("CREDITO");
		rdbtnCredito.addKeyListener(c);
		
		this.btnCancelar.addActionListener(c);
		this.btnCancelar.setActionCommand("CANCELAR");
		btnCancelar.addKeyListener(c);
		
		this.addWindowListener(c);
		
		txtTelefonoProveedor.addKeyListener(c);
		
		tablaArticulos.addKeyListener(c);
		txtNofactura.addKeyListener(c);
		dateCompra.getDateEditor().getUiComponent().addKeyListener(c);
		
		dateVencFactura.addKeyListener(c);
		dateVencFactura.getDateEditor().getUiComponent().addKeyListener(c);
		txtTotalimpuesto.addKeyListener(c);
		txtTotal.addKeyListener(c);
		txtSubtotal.addKeyListener(c);
	
		/*rdbtnTodos.addItemListener(c);
		
		
		rdbtnId.addActionListener(c);
		rdbtnId.addItemListener(c);
		//rdbtnId.getActionCommand();
		rdbtnId.setActionCommand("ID");
		
		rdbtnObservacion.addActionListener(c);
		rdbtnObservacion.addItemListener(c);
		rdbtnObservacion.setActionCommand("OBSERVACION");
		
		rdbtnMarca.addActionListener(c);
		rdbtnMarca.addItemListener(c);
		rdbtnMarca.setActionCommand("MARCA");
		
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand("BUSCAR");
		
		 btnAgregar.addActionListener(c);
		 btnAgregar.setActionCommand("INSERTAR");
		 
		 btnEliminar.addActionListener(c);
		 btnEliminar.setActionCommand("ELIMINAR");
		 
		 btnLimpiar.addActionListener(c);
		 btnLimpiar.setActionCommand("LIMPIAR");*/
		 
		 tablaArticulos.addMouseListener(c);
		// tablaArticulos.getModel().addTableModelListener(c);
		/* tablaArticulos.getModel().addTableModelListener(new TableModelListener() {

				@Override
				public void tableChanged(TableModelEvent e) {
					// TODO Auto-generated method stub
					JOptionPane.showMessageDialog(null, "Se modifico el dato en la celda "+e.getColumn()+", "+e.getFirstRow());
				}
	        });*/
		 modelo.addTableModelListener(c);
		 //tablaArticulos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaArticulos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		 tablaArticulos.setColumnSelectionAllowed(true);
		 tablaArticulos.setRowSelectionAllowed(true);
	}
}
