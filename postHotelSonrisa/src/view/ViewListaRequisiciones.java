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

import controlador.CtlRequisicionesLista;
import view.botones.BotonAgregar;
import view.botones.BotonBuscar;
import view.botones.BotonEliminar;
import view.botones.BotonImprimirSmall;
import view.botones.BotonLimpiar;
import view.rendes.TablaRenderizadorProveedor;
import view.tablemodel.TmRequisicionesEncabezados;

public class ViewListaRequisiciones extends JDialog {
	public JTable tabla;
	public TmRequisicionesEncabezados modelo;
		
	protected BorderLayout miEsquema;
	protected JTextField txtBuscar;
	
	protected BotonAgregar btnAgregar;
	protected BotonEliminar btnEliminar;
	protected BotonImprimirSmall btnImprimir;
	protected BotonBuscar btnBuscar;
	protected JPanel panelAccion;
	protected JPanel panelSuperior;
	protected JPanel panelBusqueda;
	protected GridLayout miEsquemaTabla;
	
	private JRadioButton rdbtnId;
	private JRadioButton rdbtnFecha;
	private ButtonGroup grupoOpciones; // grupo de botones que contiene los botones de opción
	private JRadioButton rdbtnTodos;
	protected JTextField txtBuscar1;
	private JTextField txtBuscar2;

	public ViewListaRequisiciones(Window view) {
		//super("Proveedores");
				super(view,"Requisiciones",Dialog.ModalityType.DOCUMENT_MODAL);
				//mi esquema layout
				miEsquema =new BorderLayout();
				//mi esquema grid
				miEsquemaTabla=new GridLayout(1,2);

				
				
				//panel de la ventana
				panelSuperior=new JPanel();
				panelAccion=new JPanel();
				panelBusqueda=new JPanel();
				panelAccion.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Acciones de registro", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
				panelBusqueda.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Busqueda de registros", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
				
				//tabla de registro de los proveedores
				tabla=new JTable();
				modelo = new TmRequisicionesEncabezados();//se crea el modelo de los datos de la tabla
				tabla.setModel(modelo);
				//Estitlo para la tabla		
				TablaRenderizadorProveedor renderizador = new TablaRenderizadorProveedor();
				tabla.setDefaultRenderer(String.class, renderizador);
				//tamaño de las columnas
				tabla.getColumnModel().getColumn(0).setPreferredWidth(50);     //Tamaño de las columnas de las tablas
				tabla.getColumnModel().getColumn(1).setPreferredWidth(50);	//de las columnas
				tabla.getColumnModel().getColumn(2).setPreferredWidth(100);	//en la tabla
				tabla.getColumnModel().getColumn(3).setPreferredWidth(100);	//
				tabla.getColumnModel().getColumn(4).setPreferredWidth(50);	//
				tabla.getColumnModel().getColumn(5).setPreferredWidth(50);	//
				
						
				
				//opciones de busquedas
				grupoOpciones = new ButtonGroup(); // crea ButtonGroup	// crea una relación lógica entre los objetos JRadioButton
				rdbtnTodos = new JRadioButton("Todos",true);
				panelBusqueda.add(rdbtnTodos);
				grupoOpciones.add(rdbtnTodos);
				
				
				rdbtnId = new JRadioButton("Codigo",false);
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
				
				/*/elementos del panel buscar
				txtBuscar=new JTextField(10);
				panelBusqueda.add(txtBuscar);
				
				/*btnBuscar=new BotonBuscar();dsfa
				panelBusqueda.add(btnBuscar);*/
					
				//panel de acciones	
				btnAgregar = new BotonAgregar();
				panelAccion.add(btnAgregar);
		       
				btnEliminar = new BotonEliminar();
		        btnEliminar.setEnabled(false);
		        panelAccion.add(btnEliminar);
		        
		        btnImprimir = new BotonImprimirSmall();
		        //btnLimpiar.setIcon(new ImageIcon(ViewListaProveedor.class.getResource("/View/imagen/clear.png"))); // NOI18N
		        panelAccion.add(btnImprimir);
				
				JScrollPane scrollPane = new JScrollPane(tabla);
				//scrollPane.setBounds(36, 97, 742, 136);
				
				panelSuperior.add(panelAccion);
				panelSuperior.add(panelBusqueda);
				getContentPane().add(panelSuperior, BorderLayout.NORTH);
				getContentPane().add(scrollPane, BorderLayout.CENTER);
				setSize(792,600);
				
				
				
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
				//this.setResizable(false);
	}

	public TmRequisicionesEncabezados getModelo() {
		// TODO Auto-generated method stub
		return modelo;
	}
	
	public JTable getTabla(){
		return tabla;
	}
	public void conectarCtl(CtlRequisicionesLista c){
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
		 
		 tabla.addMouseListener(c);
		 tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
