package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import view.botones.BotonEliminar;
import view.botones.BotonLimpiar;
import view.rendes.PanelPadre;
import view.rendes.TablaRenderizadorProveedor;
import view.tablemodel.TablaModeloMarca;
import controlador.CtlMarcaBuscar;
import controlador.CtlMarcaLista;

public class ViewListaMarca extends JDialog {
	
	protected BorderLayout miEsquema;
	protected GridLayout miEsquemaTabla;
	
	protected JPanel panelAccion;
	protected JPanel panelSuperior;
	protected JPanel panelBusqueda;
	
	
	protected BotonAgregar btnAgregar;
	protected BotonEliminar btnEliminar;
	protected JButton btnLimpiar;
	
	
	private JRadioButton rdbtnId;
	private JRadioButton rdbtnObservacion;
	private JRadioButton rdbtnMarca;
	private ButtonGroup grupoOpciones; // grupo de botones que contiene los botones de opci�n
	private JRadioButton rdbtnTodos;
	protected BotonBuscar btnBuscar;
	protected JTextField txtBuscar;
	
	
	private JTable tablaMarca;
	private TablaModeloMarca modelo;
	/**
	 * @wbp.parser.constructor
	 */
	public ViewListaMarca(JDialog view){
		super(view,"Buscar Marcas",Dialog.ModalityType.DOCUMENT_MODAL);
		Init();
		btnAgregar.setEnabled(false);
		btnLimpiar.setEnabled(false);
		
	}
	public ViewListaMarca(JFrame view){
		super(view,"Marcas",Dialog.ModalityType.DOCUMENT_MODAL);
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
		rdbtnId = new JRadioButton("ID",false);
		panelBusqueda.add(rdbtnId);
		grupoOpciones.add(rdbtnId);
		
		rdbtnMarca = new JRadioButton("Marca",false);
		panelBusqueda.add(rdbtnMarca);
		grupoOpciones.add(rdbtnMarca);
		
		rdbtnObservacion = new JRadioButton("Observacion",false);
		panelBusqueda.add(rdbtnObservacion);
		grupoOpciones.add(rdbtnObservacion);
		
		//elementos del panel buscar
		txtBuscar=new JTextField(10);
		panelBusqueda.add(txtBuscar);
				
		btnBuscar=new BotonBuscar();
		panelBusqueda.add(btnBuscar);
		
		//tabla y sus componentes
		modelo=new TablaModeloMarca();
		tablaMarca=new JTable();
		tablaMarca.setModel(modelo);
		TablaRenderizadorProveedor renderizador = new TablaRenderizadorProveedor();
		tablaMarca.setDefaultRenderer(String.class, renderizador);
		
		tablaMarca.getColumnModel().getColumn(0).setPreferredWidth(5);     //Tama�o de las columnas de las tablas
		tablaMarca.getColumnModel().getColumn(1).setPreferredWidth(200);	//
		tablaMarca.getColumnModel().getColumn(2).setPreferredWidth(100);	//
		tablaMarca.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(tablaMarca);
		scrollPane.setBackground(PanelPadre.color1);
		//scrollPane.setBounds(36, 97, 742, 136);
		
		//configuracion de los paneles
		panelSuperior.add(panelAccion);
		panelSuperior.add(panelBusqueda);
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize(718,591);
		
		//se hace visible
		//setVisible(true);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	
	}
public JButton getBtnAgregar(){
	return btnAgregar;
}
public JButton getBtnLimpiar(){
	return btnLimpiar;
}
public JTable getTablaMarca(){
	return tablaMarca;
}
public TablaModeloMarca getModelo(){
	return modelo;
}
public JButton getBtnEliminar(){
	return btnEliminar;
}
public JTextField getTxtBuscar(){
	return txtBuscar;
}
public JRadioButton getRdbtnId(){
	return rdbtnId;
}
public JRadioButton getRdbtnObservacion(){
	return rdbtnObservacion;
}
public JRadioButton getRdbtnMarca(){
	return rdbtnMarca;
}
public JRadioButton getRdbtnTodos(){
	return rdbtnTodos;
}
public JPanel getPanelAccion(){
	return panelAccion;
}
public void conectarControlador(CtlMarcaLista c){
	
	
	
		rdbtnTodos.addItemListener(c);
		
		
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
		 btnLimpiar.setActionCommand("LIMPIAR");
		 
		 tablaMarca.addMouseListener(c);
		 tablaMarca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 this.addWindowListener(c);
	}

public void conectarControladorBusqueda(CtlMarcaBuscar c){
	rdbtnTodos.addItemListener(c);
	
	
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
	 btnLimpiar.setActionCommand("LIMPIAR");
	 
	 tablaMarca.addMouseListener(c);
	 tablaMarca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}

}
