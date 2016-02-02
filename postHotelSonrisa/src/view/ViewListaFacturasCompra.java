package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import view.botones.BotonAgregar;
import view.botones.BotonBuscar;
import view.botones.BotonEliminar;
import view.botones.BotonImprimirSmall;
import view.rendes.RenderizadorTablaFacturaCompra;
import view.tablemodel.TablaModeloFacturasCompra;
import controlador.CtlFacturas;
import controlador.CtlFacturasCompra;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewListaFacturasCompra extends JDialog {
	
	protected BorderLayout miEsquema;
	protected GridLayout miEsquemaTabla;
	
	protected JPanel panelAccion;
	protected JPanel panelSuperior;
	protected JPanel panelBusqueda;
	
	
	protected BotonAgregar btnAgregar;
	protected BotonEliminar btnEliminar;
	protected BotonImprimirSmall btnImprimir;
	
	
	private JRadioButton rdbtnId;
	private JRadioButton rdbtnFecha;
	private ButtonGroup grupoOpciones; // grupo de botones que contiene los botones de opción
	private JRadioButton rdbtnTodos;
	protected BotonBuscar btnBuscar;
	protected JTextField txtBuscar1;
	
	
	
	private JTable tablaFacturas;
	private TablaModeloFacturasCompra modelo;
	private JTextField txtBuscar2;

	
	
	
	public ViewListaFacturasCompra(JFrame view) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		
		
		miEsquema=new BorderLayout();
		this.setTitle("Facturas compras");
		this.setLocationRelativeTo(view);
		this.setModal(true);
		getContentPane().setLayout(miEsquema);
	
	
	
		//creacion de los paneles
		panelAccion=new JPanel();
		panelBusqueda=new JPanel();
		panelSuperior=new JPanel();
		
		panelAccion.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Acciones de registro", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBusqueda.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Busqueda de registros", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		//agregar componentes al panel acciones
		btnAgregar = new BotonAgregar();
		btnAgregar.setEnabled(false);
		btnAgregar.setMnemonic('r');
		panelAccion.add(btnAgregar);
	   
		btnEliminar = new BotonEliminar();
		btnEliminar.setToolTipText("Anular Facturas");
		btnEliminar.setEnabled(false);
	    panelAccion.add(btnEliminar);
	    
	    btnImprimir = new BotonImprimirSmall();
	    btnImprimir.setEnabled(false);
	    //btnLimpiar.setIcon(new ImageIcon("recursos/clear.png")); // NOI18N
	    panelAccion.add(btnImprimir);
	    //panelAccion.setVisible(false);	
	    
	    //configuracion del panel busqueda
	    grupoOpciones = new ButtonGroup(); // crea ButtonGroup
	    rdbtnTodos = new JRadioButton("Todos");
		rdbtnTodos.setSelected(true);
		panelBusqueda.add(rdbtnTodos);
		grupoOpciones.add(rdbtnTodos);
	
		//opciones de busquedas
		rdbtnId = new JRadioButton("ID",false);
		panelBusqueda.add(rdbtnId);
		grupoOpciones.add(rdbtnId);
		
		rdbtnFecha = new JRadioButton("Fecha",false);
		panelBusqueda.add(rdbtnFecha);
		grupoOpciones.add(rdbtnFecha);
		
		//elementos del panel buscar
		txtBuscar1=new JTextField(10);
		panelBusqueda.add(txtBuscar1);
		
		txtBuscar2 = new JTextField();
		txtBuscar2.setEditable(false);
		panelBusqueda.add(txtBuscar2);
		txtBuscar2.setColumns(10);
				
		btnBuscar=new BotonBuscar();
		panelBusqueda.add(btnBuscar);
	    
	    //tabla y sus componentes
		modelo=new TablaModeloFacturasCompra();
		tablaFacturas=new JTable();
		tablaFacturas.setModel(modelo);
		RenderizadorTablaFacturaCompra renderizador = new RenderizadorTablaFacturaCompra();
		tablaFacturas.setDefaultRenderer(String.class, renderizador);
		
		tablaFacturas.getColumnModel().getColumn(0).setPreferredWidth(60);     //Tamaño de las columnas de las tablas
		tablaFacturas.getColumnModel().getColumn(1).setPreferredWidth(80);	//de las columnas
		tablaFacturas.getColumnModel().getColumn(2).setPreferredWidth(70);	//en la tabla
		tablaFacturas.getColumnModel().getColumn(3).setPreferredWidth(280);	//
		
		
		JScrollPane scrollPane = new JScrollPane(tablaFacturas);
		scrollPane.setBounds(36, 97, 742, 136);
	
	
		//configuracion de los paneles
		panelSuperior.add(panelAccion);
		panelSuperior.add(panelBusqueda);
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize(744,600);
	
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
public void conectarControlador(CtlFacturasCompra c){
		
		rdbtnTodos.addActionListener(c);
		rdbtnTodos.setActionCommand("TODAS");
		
		rdbtnId.addActionListener(c);
		//rdbtnId.getActionCommand();
		rdbtnId.setActionCommand("ID");
		
		rdbtnFecha.addActionListener(c);
		rdbtnFecha.setActionCommand("FECHA");
		
		
		
		
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand("BUSCAR");
		
		 btnAgregar.addActionListener(c);
		 btnAgregar.setActionCommand("INSERTAR");
		 
		 btnEliminar.addActionListener(c);
		 btnEliminar.setActionCommand("ANULARFACTURA");
		 
		 btnImprimir.addActionListener(c);
		 btnImprimir.setActionCommand("IMPRIMIR");
		 
		 txtBuscar1.addActionListener(c);
		 txtBuscar1.setActionCommand("BUSCAR");
		 
		 tablaFacturas.addMouseListener(c);
		 tablaFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public JTable getTablaFacturas(){
		return tablaFacturas;
	}
	public TablaModeloFacturasCompra getModelo(){
		return modelo;
	}
	public JButton getBtnEliminar(){
		return btnEliminar;
	}
	public JRadioButton getRdbtnId(){
		return rdbtnId;
	}
	public JTextField getTxtBuscar1(){
		return txtBuscar1;
	}
	public JTextField getTxtBuscar2(){
		return txtBuscar2;
	}
	public BotonImprimirSmall getBtnImprimir(){
		return btnImprimir;
	}
	public JRadioButton getRdbtnFecha(){
		return rdbtnFecha;
	}
	public JRadioButton getRdbtnTodos(){
		return rdbtnTodos;
		
	}
	public BotonAgregar getBtnAgregar(){
		return btnAgregar;
	}


}
