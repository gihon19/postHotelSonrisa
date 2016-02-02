package view;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import controlador.CtlProveedorLista;
import modelo.Proveedor;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.table.TableColumnModel;
import javax.swing.JRadioButton;

import view.botones.BotonAgregar;
import view.botones.BotonBuscar;
import view.botones.BotonEliminar;
import view.botones.BotonLimpiar;
import view.rendes.PanelPadre;
import view.rendes.TablaRenderizadorProveedor;
import view.tablemodel.TablaModeloProveedor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewListaProveedor extends JDialog {
	
	public JTable tablaProvedores;
	public TablaModeloProveedor modelo;
	protected BorderLayout miEsquema;
	protected JTextField txtBuscar;
	
	protected BotonAgregar btnAgregar;
	protected BotonEliminar btnEliminar;
	protected JButton btnLimpiar;
	protected BotonBuscar btnBuscar;
	protected JPanel panelAccion;
	protected JPanel panelSuperior;
	protected JPanel panelBusqueda;
	protected GridLayout miEsquemaTabla;
	private JRadioButton rdbtnId;
	private JRadioButton rdbtnNombre;
	private JRadioButton rdbtnDireccion;
	private ButtonGroup grupoOpciones; // grupo de botones que contiene los botones de opción
	private JRadioButton rdbtnTodos;
	
	
	
	public ViewListaProveedor(Window view){
		//super("Proveedores");
		super(view,"Proveedores",Dialog.ModalityType.DOCUMENT_MODAL);
		//mi esquema layout
		miEsquema =new BorderLayout();
		//mi esquema grid
		miEsquemaTabla=new GridLayout(1,2);

		
		
		//panel de la ventana
		panelSuperior=new PanelPadre();
		panelAccion=new PanelPadre();
		panelBusqueda=new PanelPadre();
		panelAccion.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Acciones de registro", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelBusqueda.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Busqueda de registros", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		//tabla de registro de los proveedores
		tablaProvedores=new JTable();
		modelo = new TablaModeloProveedor();//se crea el modelo de los datos de la tabla
		tablaProvedores.setModel(modelo);
		tablaProvedores.setBackground(PanelPadre.color1);
		//Estitlo para la tabla		
		TablaRenderizadorProveedor renderizador = new TablaRenderizadorProveedor();
		tablaProvedores.setDefaultRenderer(String.class, renderizador);
		//tamaño de las columnas
		tablaProvedores.getColumnModel().getColumn(0).setPreferredWidth(5);
		tablaProvedores.getColumnModel().getColumn(1).setPreferredWidth(200);
		tablaProvedores.getColumnModel().getColumn(2).setPreferredWidth(50);
		tablaProvedores.getColumnModel().getColumn(3).setPreferredWidth(50);
		tablaProvedores.getColumnModel().getColumn(4).setPreferredWidth(100);
		
				
		
		//opciones de busquedas
		grupoOpciones = new ButtonGroup(); // crea ButtonGroup	// crea una relación lógica entre los objetos JRadioButton
		rdbtnTodos = new JRadioButton("Todos",true);
		panelBusqueda.add(rdbtnTodos);
		grupoOpciones.add(rdbtnTodos);
		
		
		rdbtnId = new JRadioButton("ID",false);
		panelBusqueda.add(rdbtnId);
		grupoOpciones.add(rdbtnId);
		
		rdbtnNombre = new JRadioButton("Nombre",false);
		panelBusqueda.add(rdbtnNombre);
		grupoOpciones.add(rdbtnNombre);
		
		rdbtnDireccion = new JRadioButton("Direccion",false);
		panelBusqueda.add(rdbtnDireccion);
		grupoOpciones.add(rdbtnDireccion);
		
		//elementos del panel buscar
		txtBuscar=new JTextField(10);
		panelBusqueda.add(txtBuscar);
		
		btnBuscar=new BotonBuscar();
		panelBusqueda.add(btnBuscar);
			
		//panel de acciones	
		btnAgregar = new BotonAgregar();
		panelAccion.add(btnAgregar);
       
		btnEliminar = new BotonEliminar();
        btnEliminar.setEnabled(false);
        panelAccion.add(btnEliminar);
        
        btnLimpiar = new BotonLimpiar();
        //btnLimpiar.setIcon(new ImageIcon(ViewListaProveedor.class.getResource("/View/imagen/clear.png"))); // NOI18N
        panelAccion.add(btnLimpiar);
		
		JScrollPane scrollPane = new JScrollPane(tablaProvedores);
		
		scrollPane.setBackground(PanelPadre.color1);
		//scrollPane.setBounds(36, 97, 742, 136);
		
		panelSuperior.add(panelAccion);
		panelSuperior.add(panelBusqueda);
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().setBackground(PanelPadre.color1);
		setSize(710,600);
		
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setResizable(false);
		//se hace visible
		//setVisible(true);

		
	}
	
	public JButton getBtnEliminar(){
		return btnEliminar;
	}
	public JRadioButton getRdbtnId(){
		return rdbtnId;
	}
	public JRadioButton getRdbtnNombre(){
		return rdbtnNombre;
	}
	public JRadioButton getRdbtnDireccion(){
		return rdbtnDireccion;
	}
	
	public JRadioButton getRdbtnTodos(){
		return rdbtnTodos;
	}
	public JTextField getTxtBuscar(){
		return txtBuscar;
	}
	
	public void conectarControlador(CtlProveedorLista c){
		
		rdbtnTodos.addItemListener(c);
		
		rdbtnId.addActionListener(c);
		rdbtnId.addItemListener(c);
		rdbtnId.setActionCommand("ID");
		
		rdbtnNombre.addActionListener(c);
		rdbtnNombre.addItemListener(c);
		rdbtnNombre.setActionCommand("NOMBRE");
		
		rdbtnDireccion.addActionListener(c);
		rdbtnDireccion.addItemListener(c);
		rdbtnDireccion.setActionCommand("DIRECCION");
		
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand("BUSCAR");
		
		 btnAgregar.addActionListener(c);
		 btnAgregar.setActionCommand("INSERTAR");
		 
		 btnEliminar.addActionListener(c);
		 btnEliminar.setActionCommand("ELIMINAR");
		 
		 btnLimpiar.addActionListener(c);
		 btnLimpiar.setActionCommand("LIMPIAR");
		 
		 tablaProvedores.addMouseListener(c);
		 tablaProvedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

}
