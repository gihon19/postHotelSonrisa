package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
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

import controlador.CtlUsuariosLista;
import view.botones.BotonAgregar;
import view.botones.BotonBuscar;
import view.botones.BotonEliminar;
import view.botones.BotonLimpiar;
import view.rendes.PanelPadre;
import view.rendes.TablaRenderizadorProveedor;
import view.tablemodel.TablaModeloMarca;
import view.tablemodel.TmUsuarios;

public class ViewListaUsuarios extends JDialog {
	
	protected BorderLayout miEsquema;
	protected GridLayout miEsquemaTabla;
	
	protected JPanel panelAccion;
	protected JPanel panelSuperior;
	protected JPanel panelBusqueda;
	
	
	protected BotonAgregar btnAgregar;
	protected BotonEliminar btnEliminar;
	protected JButton btnLimpiar;
	
	
	private JRadioButton rdbtnUser;
	private JRadioButton rdbtnNombre;
	private ButtonGroup grupoOpciones; // grupo de botones que contiene los botones de opci�n
	private JRadioButton rdbtnTodos;
	protected BotonBuscar btnBuscar;
	protected JTextField txtBuscar;
	
	
	private JTable tablaUsuarios;
	private TmUsuarios modelo;
	/**
	 * @wbp.parser.constructor
	 */
	public ViewListaUsuarios(JDialog view){
		super(view,"Buscar Usuarios",Dialog.ModalityType.DOCUMENT_MODAL);
		Init();
		btnAgregar.setEnabled(false);
		btnLimpiar.setEnabled(false);
		
	}
	public ViewListaUsuarios(JFrame view){
		super(view,"Usuarios",Dialog.ModalityType.DOCUMENT_MODAL);
		Init();
		
	}
	
	
	public void Init() {
		
		//super("Marcas");
		//super(null,"Marcas",Dialog.ModalityType.DOCUMENT_MODAL);
		miEsquema=new BorderLayout();
		getContentPane().setLayout(miEsquema);
		
		//creacion de los paneles
		panelAccion=new PanelPadre();
		panelBusqueda=new PanelPadre();
		panelSuperior=new PanelPadre();
		
		panelAccion.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Acciones de registro", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBusqueda.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Busqueda de registros", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		//agregar componentes al panel acciones
		btnAgregar = new BotonAgregar();
		panelAccion.add(btnAgregar);
       
		btnEliminar = new BotonEliminar();
        btnEliminar.setEnabled(false);
        panelAccion.add(btnEliminar);
        
        btnLimpiar = new BotonLimpiar();
        //btnLimpiar.setIcon(new ImageIcon(ViewListaMarca.class.getResource("/View/imagen/clear.png"))); // NOI18N
        panelAccion.add(btnLimpiar);
        
      //configuracion del panel busqueda
        grupoOpciones = new ButtonGroup(); // crea ButtonGroup
        rdbtnTodos = new JRadioButton("Todos");
		rdbtnTodos.setSelected(true);
		panelBusqueda.add(rdbtnTodos);
		grupoOpciones.add(rdbtnTodos);
		
		//opciones de busquedas
		rdbtnUser = new JRadioButton("User",false);
		panelBusqueda.add(rdbtnUser);
		grupoOpciones.add(rdbtnUser);
		
		rdbtnNombre = new JRadioButton("Nombre",false);
		panelBusqueda.add(rdbtnNombre);
		grupoOpciones.add(rdbtnNombre);
		
		
		//elementos del panel buscar
		txtBuscar=new JTextField(10);
		panelBusqueda.add(txtBuscar);
				
		btnBuscar=new BotonBuscar();
		panelBusqueda.add(btnBuscar);
		
		//tabla y sus componentes
		modelo=new TmUsuarios();
		tablaUsuarios=new JTable();
		tablaUsuarios.setModel(modelo);
		TablaRenderizadorProveedor renderizador = new TablaRenderizadorProveedor();
		tablaUsuarios.setDefaultRenderer(String.class, renderizador);
		
		tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(5);     //Tama�o de las columnas de las tablas
		tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(200);	//
		tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(100);	//
		tablaUsuarios.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
		
		scrollPane.setBackground(PanelPadre.color1);
		//scrollPane.setBounds(36, 97, 742, 136);
		
		//configuracion de los paneles
		panelSuperior.add(panelAccion);
		panelSuperior.add(panelBusqueda);
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize(718,591);
		
		this.btnEliminar.setEnabled(false);
		//se hace visible
		//setVisible(true);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	
	}
	
	public TmUsuarios getModelo(){
		return modelo;
	}
	public JTable getTabla(){
		return tablaUsuarios;
	}
	public JRadioButton getRdbtnNombre(){
		return rdbtnNombre;
	}
	public JRadioButton getRdbtnUser(){
		return rdbtnUser;
	}
	public JRadioButton getRdbtnTodos(){
		return rdbtnTodos;
	}
	public JTextField getTxtBuscar(){
		return this.txtBuscar;
	}
	
	public void conectarCtl(CtlUsuariosLista c){
		btnAgregar.addActionListener(c);
		btnAgregar.setActionCommand("INSERTAR");
		
		btnEliminar.addActionListener(c);
		btnEliminar.setActionCommand("ELIMINAR");
		
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand("BUSCAR");
		
		txtBuscar.addActionListener(c);
		txtBuscar.setActionCommand("BUSCAR");
		
		
		tablaUsuarios.addMouseListener(c);
		tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}
	public BotonEliminar getBtnEliminar() {
		// TODO Auto-generated method stub
		return btnEliminar;
	}

}
