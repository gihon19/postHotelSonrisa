package view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controlador.CtlCobro;
import controlador.CtlFacturaPagos;
import view.botones.BotonBuscarClientes;
import view.botones.BotonCancelar;
import view.botones.BotonCobrar;
import view.rendes.PanelPadre;
import view.rendes.RenderizadorTablaFactura;
import view.tablemodel.TmFacturasPago;

public class ViewCobro extends JDialog {
	private TmFacturasPago modelo;
	private JPanel panelAcciones;
	private JPanel panelDatosFactura;
	private JLabel lblCodigoCliente;
	private JTextField txtIdcliente;
	private JTextField txtNombrecliente;
	
	private ButtonGroup grupoOpciones;
	private BotonCancelar btnCerrar;
	private BotonBuscarClientes btnCliente;
	private BotonCobrar btnCobrar;
	private JLabel lblNombreCliente;
	private JTextField txtLimitecredito;
	private JTextField txtSaldocliente;
	
	
	private JTextField txtTotal;
	private JLabel lblTotal;

	public ViewCobro(Window view) {
		
		
		this.setTitle("Cobro");
		this.setLocationRelativeTo(view);
		this.setModal(true);
		
		//setIconImage(Toolkit.getDefaultToolkit().getImage(ViewFacturar.class.getResource("/view/recursos/logo-admin-tool1.png")));
		getContentPane().setBackground(PanelPadre.color1);
		
		getContentPane().setLayout(null);
		
		panelAcciones=new PanelPadre();
		panelAcciones.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Opciones", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAcciones.setBounds(20, 11, 117, 276);
		panelAcciones.setLayout(null);
		//panelAcciones.setVisible(false);
		
		
		//this.setTitle("Facturar");
		getContentPane().add(panelAcciones);
		//btnBuscar.getInputMap().put(KeyStroke.getKeyStroke("F1"), sumar());
		
		btnCliente = new BotonBuscarClientes();
		btnCliente.setSize(92, 70);
		btnCliente.setText("F3 Clientes");
		btnCliente.setHorizontalAlignment(SwingConstants.LEFT);
		btnCliente.setLocation(10, 19);
		//btnCliente.setBounds(10, 19, 158, 80);
		panelAcciones.add(btnCliente);
		
		btnCobrar = new BotonCobrar();
		btnCobrar.setText("F2 Cobrar");
		btnCobrar.setHorizontalAlignment(SwingConstants.LEFT);
		btnCobrar.setBounds(10, 102, 92, 70);
		
		panelAcciones.add(btnCobrar);
		
		btnCerrar = new BotonCancelar();
		btnCerrar.setHorizontalAlignment(SwingConstants.LEFT);
		btnCerrar.setText("Esc Cerrar");
		btnCerrar.setBounds(10, 185, 92, 70);
		panelAcciones.add(btnCerrar);
		
		
		
		
		panelDatosFactura=new PanelPadre();
		//panelDatosFactura.setBackground(Color.WHITE);
		panelDatosFactura.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Datos Generales", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDatosFactura.setBounds(161, 11, 482, 165);
		panelDatosFactura.setLayout(null);
		
		getContentPane().add(panelDatosFactura);
		
		lblCodigoCliente = new JLabel("Id Cliente");
		lblCodigoCliente.setBounds(20, 23, 61, 14);
		panelDatosFactura.add(lblCodigoCliente);
		
		Font myFont=new Font("OCR A Extended", Font.PLAIN, 45);
		
		txtIdcliente = new JTextField();
		txtIdcliente.setBounds(20, 44, 197, 29);
		panelDatosFactura.add(txtIdcliente);
		txtIdcliente.setColumns(10);
		
		txtNombrecliente = new JTextField();
		txtNombrecliente.setToolTipText("Nombre Cliente");
		txtNombrecliente.setBounds(243, 44, 214, 29);
		panelDatosFactura.add(txtNombrecliente);
		txtNombrecliente.setColumns(10);
		
		
		lblNombreCliente = new JLabel("Nombre Cliente");
		lblNombreCliente.setBounds(243, 23, 104, 14);
		panelDatosFactura.add(lblNombreCliente);
		
		JLabel lblSaldoCliente = new JLabel("Saldo Cliente");
		lblSaldoCliente.setBounds(243, 87, 86, 14);
		panelDatosFactura.add(lblSaldoCliente);
		
		JLabel lblLimiteDeCredito = new JLabel("Limite de credito");
		lblLimiteDeCredito.setBounds(20, 87, 125, 14);
		panelDatosFactura.add(lblLimiteDeCredito);
		
		txtLimitecredito = new JTextField();
		txtLimitecredito.setBounds(20, 113, 197, 29);
		panelDatosFactura.add(txtLimitecredito);
		txtLimitecredito.setColumns(10);
		
		txtSaldocliente = new JTextField();
		txtSaldocliente.setBounds(243, 113, 214, 29);
		panelDatosFactura.add(txtSaldocliente);
		txtSaldocliente.setColumns(10);
		modelo=new TmFacturasPago();
		
		
		
		
				
		
		txtTotal = new JTextField();
		//txtTotal.setForeground(Color.RED);
		txtTotal.setHorizontalAlignment(SwingConstants.LEFT);
		txtTotal.setFont(myFont);
		txtTotal.setText("00");
		txtTotal.setBounds(161, 229, 480, 58);
		getContentPane().add(txtTotal);
		txtTotal.setColumns(10);
		
		lblTotal = new JLabel("Pago");
		lblTotal.setBounds(161, 203, 46, 14);
		getContentPane().add(lblTotal);
		
		setSize(660, 330);
		
		//this.setPreferredSize(new Dimension(660, 330));
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setPreferredSize(new Dimension(660, 330));
		this.pack();
	}
	
	public JButton getBtnBuscarCliente(){
		return btnCliente;
	}
	public JButton getBtnCobrar(){
		return btnCobrar;
	}
	public JButton getBtnCerrar(){
		return btnCerrar;
	}
	public JTextField getTxtTotal(){
		return txtTotal;
	}
	public JTextField getTxtNombrecliente(){
		return txtNombrecliente;
	}
	public JTextField getTxtIdcliente(){
		return txtIdcliente;
	}
	
	public JTextField getTxtLimiteCredito(){
		return this.txtLimitecredito;
	}
	public JTextField getTxtSaldo(){
		return this.txtSaldocliente;
	}
	public void conectarContralador(CtlCobro c){

		
		txtIdcliente.addActionListener(c);
		txtIdcliente.setActionCommand("BUSCARCLIENTE");
		
		
		txtIdcliente.addKeyListener(c);
		txtNombrecliente.addKeyListener(c);
		
		this.btnCerrar.addKeyListener(c);
		this.btnCerrar.addActionListener(c);
		this.btnCerrar.setActionCommand("CERRAR");
		
		this.btnCliente.addKeyListener(c);
		this.btnCliente.addActionListener(c);
		this.btnCliente.setActionCommand("BUSCARCLIENTES");
		
		this.btnCobrar.addKeyListener(c);
		this.btnCobrar.addActionListener(c);
		this.btnCobrar.setActionCommand("COBRAR");
		
		txtTotal.addKeyListener(c);
		
		
	}

}
