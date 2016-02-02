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
import view.botones.BotonCobrarSmall;
import view.botones.BotonEliminar;
import view.rendes.RenderizadorTablaFacturas;
import view.tablemodel.TablaModeloFacturas;
import controlador.CtlArticuloLista;
import controlador.CtlFacturaLista;

public class ViewListaFactura extends JDialog {
	protected BorderLayout miEsquema;
	protected GridLayout miEsquemaTabla;
	
	protected JPanel panelAccion;
	protected JPanel panelSuperior;
	protected JPanel panelBusqueda;
	
	
	protected BotonAgregar btnAgregar;
	protected BotonEliminar btnEliminar;
	protected BotonCobrarSmall btnCobrar;
	
	
	private JRadioButton rdbtnId;
	private JRadioButton rdbtnArticulo;
	private JRadioButton rdbtnMarca;
	private ButtonGroup grupoOpciones; // grupo de botones que contiene los botones de opción
	private JRadioButton rdbtnTodos;
	protected BotonBuscar btnBuscar;
	protected JTextField txtBuscar;
	
	
	
	private JTable tablaFacturas;
	private TablaModeloFacturas modelo;
	
	public ViewListaFactura(Window view){
		
		miEsquema=new BorderLayout();
		this.setTitle("Creando Facturas");
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
        btnEliminar.setEnabled(false);
        panelAccion.add(btnEliminar);
        
        btnCobrar = new BotonCobrarSmall();
        btnCobrar.setEnabled(false);
        //btnLimpiar.setIcon(new ImageIcon("recursos/clear.png")); // NOI18N
        panelAccion.add(btnCobrar);
        
    
        
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
		
		rdbtnArticulo = new JRadioButton("Articulo",false);
		panelBusqueda.add(rdbtnArticulo);
		grupoOpciones.add(rdbtnArticulo);
		
		rdbtnMarca = new JRadioButton("Marca",false);
		panelBusqueda.add(rdbtnMarca);
		grupoOpciones.add(rdbtnMarca);
		
		//elementos del panel buscar
		txtBuscar=new JTextField(10);
		panelBusqueda.add(txtBuscar);
				
		btnBuscar=new BotonBuscar();
		panelBusqueda.add(btnBuscar);
		panelBusqueda.setVisible(false);
        //tabla y sus componentes
		modelo=new TablaModeloFacturas();
		tablaFacturas=new JTable();
		tablaFacturas.setModel(modelo);
		RenderizadorTablaFacturas renderizador = new RenderizadorTablaFacturas();
		tablaFacturas.setDefaultRenderer(String.class, renderizador);
		
		tablaFacturas.getColumnModel().getColumn(0).setPreferredWidth(100);     //Tamaño de las columnas de las tablas
		tablaFacturas.getColumnModel().getColumn(1).setPreferredWidth(300);	//de las columnas
		tablaFacturas.getColumnModel().getColumn(2).setPreferredWidth(70);	//en la tabla
		tablaFacturas.getColumnModel().getColumn(2).setPreferredWidth(70);	//
		
		
		JScrollPane scrollPane = new JScrollPane(tablaFacturas);
		scrollPane.setBounds(36, 97, 742, 136);
		
		
		//configuracion de los paneles
		panelSuperior.add(panelAccion);
		panelSuperior.add(panelBusqueda);
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize(710,600);
		
		//se hace visible
		//setVisible(true);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		
	}
	
public void conectarControlador(CtlFacturaLista c){
		
		
		rdbtnId.addActionListener(c);
		//rdbtnId.getActionCommand();
		rdbtnId.setActionCommand("ID");
		
		rdbtnArticulo.addActionListener(c);
		rdbtnArticulo.setActionCommand("ARTICULO");
		
		rdbtnMarca.addActionListener(c);
		rdbtnMarca.setActionCommand("MARCA");
		
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand("BUSCAR");
		
		 btnAgregar.addActionListener(c);
		 btnAgregar.setActionCommand("INSERTAR");
		 
		 btnEliminar.addActionListener(c);
		 btnEliminar.setActionCommand("ELIMINAR");
		 
		 btnCobrar.addActionListener(c);
		 btnCobrar.setActionCommand("COBRAR");
		 
		 txtBuscar.addActionListener(c);
		 txtBuscar.setActionCommand("BUSCAR");
		 
		 tablaFacturas.addMouseListener(c);
		 tablaFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public JTable getTablaFacturas(){
		return tablaFacturas;
	}
	public TablaModeloFacturas getModelo(){
		return modelo;
	}
	public JButton getBtnEliminar(){
		return btnEliminar;
	}
	public JRadioButton getRdbtnId(){
		return rdbtnId;
	}
	public JTextField getTxtBuscar(){
		return txtBuscar;
	}
	public BotonCobrarSmall getBtnCobrar(){
		return btnCobrar;
	}
	public JRadioButton getRdbtnArticulo(){
		return rdbtnArticulo;
	}
	public JRadioButton getRdbtnMarca(){
		return  rdbtnMarca;
		
	}
	public JRadioButton getRdbtnTodos(){
		return rdbtnTodos;
		
	}
	public BotonAgregar getBtnAgregar(){
		return btnAgregar;
	}

}
