package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import view.botones.BotonLimpiar;
import view.rendes.PanelPadre;
import view.tablemodel.TablaModeloCliente;
import controlador.CtlClienteBuscar;
import controlador.CtlClienteLista;

public class ViewListaClientes extends JDialog {
	
	protected BorderLayout miEsquema;
	protected GridLayout miEsquemaTabla;
	
	protected JPanel panelAccion;
	protected JPanel panelSuperior;
	protected JPanel panelBusqueda;
	
	
	protected BotonAgregar btnAgregar;
	protected BotonEliminar btnEliminar;
	protected JButton btnLimpiar;
	
	
	private JRadioButton rdbtnId;
	private JRadioButton rdbtnNombre;
	private JRadioButton rdbtnRtn;
	private ButtonGroup grupoOpciones; // grupo de botones que contiene los botones de opción
	private JRadioButton rdbtnTodos;
	protected BotonBuscar btnBuscar;
	protected JTextField txtBuscar;
	
	private JTable tablaClientes;
	private TablaModeloCliente modelo;
	
	
	
	public ViewListaClientes(Window view) {
		setTitle("Clientes");
		this.setLocationRelativeTo(view);
		this.setModal(true);
		
		miEsquema=new BorderLayout();
		this.setTitle("Articulos");
		getContentPane().setLayout(miEsquema);
		getContentPane().setBackground(PanelPadre.color1);
		
		//creacion de los paneles
		panelAccion=new PanelPadre();
		panelBusqueda=new PanelPadre();
		panelSuperior=new PanelPadre();
		
		panelAccion.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Acciones de registro", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBusqueda.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Busqueda de registros", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		//agregar componentes al panel acciones
		btnAgregar = new BotonAgregar();
		btnAgregar.setMnemonic('r');
		panelAccion.add(btnAgregar);
       
		btnEliminar = new BotonEliminar();
        btnEliminar.setEnabled(false);
        panelAccion.add(btnEliminar);
        
        btnLimpiar = new BotonLimpiar();
        //btnLimpiar.setIcon(new ImageIcon(ViewListaClientes.class.getResource("/View/imagen/clear.png"))); // NOI18N
        panelAccion.add(btnLimpiar);
        
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
		
		rdbtnNombre = new JRadioButton("Nombre",false);
		panelBusqueda.add(rdbtnNombre);
		grupoOpciones.add(rdbtnNombre);
		
		rdbtnRtn = new JRadioButton("RTN",false);
		panelBusqueda.add(rdbtnRtn);
		grupoOpciones.add(rdbtnRtn);
		
		//elementos del panel buscar
		txtBuscar=new JTextField(10);
		panelBusqueda.add(txtBuscar);
				
		btnBuscar=new BotonBuscar();
		panelBusqueda.add(btnBuscar);
		
		 //tabla y sus componentes
		modelo=new TablaModeloCliente();
		tablaClientes=new JTable();
		tablaClientes.setBackground(PanelPadre.color1);
		tablaClientes.setModel(modelo);
		//TablaRenderizadorProveedor renderizador = new TablaRenderizadorProveedor();
		//tablaClientes.setDefaultRenderer(String.class, renderizador);
		
		tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(5);     //Tamaño de las columnas de las tablas
		tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(200);	//
		tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(100);	//
		tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(10);	//
		
		JScrollPane scrollPane = new JScrollPane(tablaClientes);
		scrollPane.setBounds(36, 97, 742, 136);
		scrollPane.setBackground(PanelPadre.color1);
		
		//configuracion de los paneles
		panelSuperior.add(panelAccion);
		panelSuperior.add(panelBusqueda);
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.setSize(701,500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
	}
	
	public TablaModeloCliente getModelo(){
		return modelo;
	}
	public JTable getTablaClientes(){
		return tablaClientes;
	}
	public JRadioButton getRdbtnId(){
		return rdbtnId;
	}
	public JTextField getTxtBuscar(){
		return txtBuscar;
	}
	public JRadioButton getRdbtnNombre(){
		return rdbtnNombre;
	}
	public JRadioButton getRdbtnRtn(){
		return  rdbtnRtn;
		
	}
	public JRadioButton getRdbtnTodos(){
		return rdbtnTodos;
		
	}
	
	public void conectarControladorBuscar(CtlClienteBuscar c){
		btnAgregar.addActionListener(c);
		btnAgregar.setActionCommand("NUEVO");
		
		rdbtnId.addActionListener(c);
		//rdbtnId.getActionCommand();
		rdbtnId.setActionCommand("ID");
		
		rdbtnNombre.addActionListener(c);
		rdbtnNombre.setActionCommand("ARTICULO");
		
		rdbtnRtn.addActionListener(c);
		rdbtnRtn.setActionCommand("MARCA");
		
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand("BUSCAR");
		
		txtBuscar.addActionListener(c);
		txtBuscar.setActionCommand("BUSCAR");
		
		tablaClientes.addMouseListener(c);
		tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	public void conectarControlador(CtlClienteLista c){
		btnAgregar.addActionListener(c);
		btnAgregar.setActionCommand("NUEVO");
		
		rdbtnId.addActionListener(c);
		//rdbtnId.getActionCommand();
		rdbtnId.setActionCommand("ID");
		
		rdbtnNombre.addActionListener(c);
		rdbtnNombre.setActionCommand("ARTICULO");
		
		rdbtnRtn.addActionListener(c);
		rdbtnRtn.setActionCommand("MARCA");
		
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand("BUSCAR");
		
		txtBuscar.addActionListener(c);
		txtBuscar.setActionCommand("BUSCAR");
		
		tablaClientes.addMouseListener(c);
		tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

}
