package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ButtonGroup;
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

import controlador.CtlCierresCajaLista;
import view.botones.BotonAgregar;
import view.botones.BotonBuscar;
import view.botones.BotonEliminar;
import view.botones.BotonImprimirSmall;
import view.botones.BotonLimpiar;
import view.rendes.PanelPadre;
import view.rendes.RenderizadorTablaFactura;
import view.rendes.RenderizadorTablaFacturaCompra;
import view.rendes.TablaRenderizadorProveedor;
import view.tablemodel.TmCierres;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;

public class ViewListaCierresCaja extends JDialog {
	
	protected BorderLayout miEsquema;
	protected GridLayout miEsquemaTabla;
	
	protected JPanel panelAccion;
	protected JPanel panelSuperior;
	protected JPanel panelBusqueda;
	
	
	protected BotonAgregar btnAgregar;
	protected BotonEliminar btnEliminar;
	protected JButton btnLimpiar;
	private ButtonGroup grupoOpciones; // grupo de botones que contiene los botones de opci�n
	private JRadioButton rdbtnTodos;
	protected BotonBuscar btnBuscar;
	
	
	private JTable tablaCierres;
	private TmCierres modelo;
	private JLabel lblA;
	private JDateChooser dCBuscar1;
	private JDateChooser dCBuscar2;
	private JRadioButton rdbtnFechas;

	public ViewListaCierresCaja(Window view) {
		// TODO Auto-generated constructor stub
		super(view,"Cierres de caja",Dialog.ModalityType.DOCUMENT_MODAL);
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
		btnAgregar.setEnabled(false);
		panelAccion.add(btnAgregar);
       
		btnEliminar = new BotonEliminar();
        btnEliminar.setEnabled(false);
        panelAccion.add(btnEliminar);
        
        btnLimpiar = new BotonImprimirSmall();
        //btnLimpiar.setIcon(new ImageIcon(ViewListaMarca.class.getResource("/View/imagen/clear.png"))); // NOI18N
        panelAccion.add(btnLimpiar);
        
      //configuracion del panel busqueda
        grupoOpciones = new ButtonGroup(); // crea ButtonGroup
        rdbtnTodos = new JRadioButton("Todos");
		rdbtnTodos.setSelected(true);
		panelBusqueda.add(rdbtnTodos);
		grupoOpciones.add(rdbtnTodos);
		
		rdbtnFechas = new JRadioButton("Por fecha");
		panelBusqueda.add(rdbtnFechas);
		grupoOpciones.add(rdbtnFechas);
		
		dCBuscar1 = new JDateChooser();
		dCBuscar1.setSize(new Dimension (100,20));
		dCBuscar1.setPreferredSize(new Dimension (160,27));
		panelBusqueda.add(dCBuscar1);
		
		lblA = new JLabel("A");
		panelBusqueda.add(lblA);
		
		dCBuscar2 = new JDateChooser();
		dCBuscar2.setPreferredSize(new Dimension (160,27));
		panelBusqueda.add(dCBuscar2);
				
		btnBuscar=new BotonBuscar();
		panelBusqueda.add(btnBuscar);
		
		//tabla y sus componentes
		modelo=new TmCierres();
		tablaCierres=new JTable();
		tablaCierres.setModel(modelo);
		RenderizadorTablaFacturaCompra renderizador = new RenderizadorTablaFacturaCompra();
		tablaCierres.setDefaultRenderer(String.class, renderizador);
		
		
		tablaCierres.getColumnModel().getColumn(0).setPreferredWidth(100);     //Tama�o de las columnas de las tablas
		tablaCierres.getColumnModel().getColumn(1).setPreferredWidth(100);	//
		tablaCierres.getColumnModel().getColumn(2).setPreferredWidth(100);	//
		tablaCierres.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(tablaCierres);
		
		scrollPane.setBackground(PanelPadre.color1);
		//scrollPane.setBounds(36, 97, 742, 136);
		
		//configuracion de los paneles
		panelSuperior.add(panelAccion);
		panelSuperior.add(panelBusqueda);
		getContentPane().add(panelSuperior, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setSize(900,591);
		
		this.btnEliminar.setEnabled(false);
		//se hace visible
		//setVisible(true);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	
	}
	
	public TmCierres getModelo(){
		return this.modelo;
	}
	public JTable getTabla(){
		return this.tablaCierres;
	}
	public JDateChooser getBuscar1(){
		return this.dCBuscar1;
	}
	public JDateChooser getBuscar2(){
		return this.dCBuscar2;
	}
	
	public JRadioButton getRdbtnFechas(){
		return this.rdbtnFechas;
	}
	public JRadioButton getRdbtnTodos(){
		return this.rdbtnTodos;
	}
	
	public void conectarCtl(CtlCierresCajaLista c){
		btnAgregar.addActionListener(c);
		btnAgregar.setActionCommand("INSERTAR");
		
		btnEliminar.addActionListener(c);
		btnEliminar.setActionCommand("ELIMINAR");
		
		btnLimpiar.addActionListener(c);
		btnLimpiar.setActionCommand("IMPRIMIR");
		
		btnBuscar.addActionListener(c);
		btnBuscar.setActionCommand("BUSCAR");
		
		
		
		tablaCierres.addMouseListener(c);
		tablaCierres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}

}
