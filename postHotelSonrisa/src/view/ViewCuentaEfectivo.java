package view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;

import view.botones.BotonAgregar;
import view.botones.BotonBuscar;
import view.botones.BotonBuscar1;
import view.botones.BotonBuscarClientes;
import view.botones.BotonCancelar;
import view.botones.BotonCierreCaja;
import view.botones.BotonCobrar;
import view.botones.BotonGuardar;
import view.rendes.PanelPadre;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controlador.CtlContarEfectivo;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewCuentaEfectivo extends JDialog {
	private JTextField txtUno;
	private JTextField txtDos;
	private JTextField txtCinco;
	private JTextField txtDiez;
	private JTextField txtVeinte;
	private JTextField txtCincuenta;
	private JTextField txtCien;
	private JTextField txtQuinientos;
	
	private JPanel panelAcciones;
	
	private ButtonGroup grupoOpciones;
	private BotonCancelar btnCerrar;
	private BotonGuardar btnGuardar;
	private BotonBuscar1 btnMostrar;

	public ViewCuentaEfectivo(Window v) {
		// TODO Auto-generated constructor stub
		
		super(v,"Contar Efectivo",Dialog.ModalityType.DOCUMENT_MODAL);
		getContentPane().setLayout(null);
		getContentPane().setBackground(PanelPadre.color1);
		
		panelAcciones=new PanelPadre();
		panelAcciones.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Opciones", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAcciones.setBounds(10, 11, 102, 227);
		panelAcciones.setLayout(null);
		//panelAcciones.setVisible(false);
		
		
		//this.setTitle("Facturar");
		getContentPane().add(panelAcciones);
		//btnBuscar.getInputMap().put(KeyStroke.getKeyStroke("F1"), sumar());
		
		btnGuardar = new BotonGuardar();
		btnGuardar.setText("F1 Guardar");
		btnGuardar.setSize(80, 70);
		//btnCierre.setText("Guardar");
		btnGuardar.setHorizontalAlignment(SwingConstants.LEFT);
		btnGuardar.setLocation(11, 14);
		//btnCliente.setBounds(10, 19, 158, 80);
		panelAcciones.add(btnGuardar);
		
		btnMostrar = new BotonBuscar1();
		btnMostrar.setText("F2 Mostrar");
		btnMostrar.setHorizontalAlignment(SwingConstants.LEFT);
		btnMostrar.setBounds(11, 82, 80, 70);
		
		panelAcciones.add(btnMostrar);
		
		btnCerrar = new BotonCancelar();
		btnCerrar.setHorizontalAlignment(SwingConstants.LEFT);
		btnCerrar.setText("Esc Cerrar");
		btnCerrar.setBounds(11, 150, 80, 70);
		panelAcciones.add(btnCerrar);
		
		txtUno = new JTextField();
		txtUno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDos.requestFocusInWindow();
			}
		});
		txtUno.setBounds(173, 24, 171, 30);
		getContentPane().add(txtUno);
		txtUno.setColumns(10);
		

		
		JLabel lblLempiras = new JLabel("1");
		lblLempiras.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLempiras.setBounds(140, 24, 25, 30);
		getContentPane().add(lblLempiras);
		
		txtDos = new JTextField();
		txtDos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCinco.requestFocusInWindow();
			}
		});
		txtDos.setBounds(175, 78, 171, 30);
		getContentPane().add(txtDos);
		txtDos.setColumns(10);
		
		txtCinco = new JTextField();
		txtCinco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDiez.requestFocusInWindow();
			}
		});
		txtCinco.setBounds(173, 132, 171, 30);
		getContentPane().add(txtCinco);
		txtCinco.setColumns(10);
		
		txtDiez = new JTextField();
		txtDiez.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtVeinte.requestFocusInWindow();
			}
		});
		txtDiez.setBounds(173, 186, 171, 30);
		getContentPane().add(txtDiez);
		txtDiez.setColumns(10);
		
		txtVeinte = new JTextField();
		txtVeinte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCincuenta.requestFocusInWindow();
			}
		});
		txtVeinte.setBounds(385, 24, 171, 30);
		getContentPane().add(txtVeinte);
		txtVeinte.setColumns(10);
		
		txtCincuenta = new JTextField();
		txtCincuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCien.requestFocusInWindow();
			}
		});
		txtCincuenta.setBounds(383, 78, 171, 30);
		getContentPane().add(txtCincuenta);
		txtCincuenta.setColumns(10);
		
		txtCien = new JTextField();
		txtCien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtQuinientos.requestFocusInWindow();
			}
		});
		txtCien.setBounds(385, 132, 171, 30);
		getContentPane().add(txtCien);
		txtCien.setColumns(10);
		
		txtQuinientos = new JTextField();
		txtQuinientos.setBounds(385, 186, 171, 30);
		getContentPane().add(txtQuinientos);
		txtQuinientos.setColumns(10);
		
		JLabel lblDos = new JLabel("2");
		lblDos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDos.setBounds(141, 78, 25, 30);
		getContentPane().add(lblDos);
		
		JLabel lblCinco = new JLabel("5");
		lblCinco.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCinco.setBounds(140, 132, 25, 30);
		getContentPane().add(lblCinco);
		
		JLabel lblDiez = new JLabel("10");
		lblDiez.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiez.setBounds(140, 186, 25, 30);
		getContentPane().add(lblDiez);
		
		JLabel lblVeinte = new JLabel("20");
		lblVeinte.setBounds(352, 24, 35, 30);
		getContentPane().add(lblVeinte);
		
		JLabel lblCincuenta = new JLabel("50");
		lblCincuenta.setBounds(355, 78, 32, 30);
		getContentPane().add(lblCincuenta);
		
		JLabel lblCien = new JLabel("100");
		lblCien.setBounds(352, 132, 35, 30);
		getContentPane().add(lblCien);
		
		JLabel lblQuinientos = new JLabel("500");
		lblQuinientos.setBounds(352, 186, 35, 30);
		getContentPane().add(lblQuinientos);
		this.setResizable(false);
		setSize(582,264);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		txtUno.requestFocusInWindow();
		
	}
	public JTextField getTxtUno(){
		return txtUno;
	}
	public JTextField getTxtDos(){
		return txtDos;
	}
	public JTextField getTxtCinco(){
		return txtCinco;
	}
	public JTextField getTxtDiez(){
		return txtDiez;
	}
	public JTextField getTxtVeinte(){
		return txtVeinte;
	}
	public JTextField getTxtCincuenta(){
		return txtCincuenta;
	}
	public JTextField getTxtCien(){
		return txtCien;
	}
	public JTextField getTxtQuiniento(){
		return txtQuinientos;
	}
	public void conectarControlador(CtlContarEfectivo c){
		
		btnGuardar.addActionListener(c);
		btnGuardar.setActionCommand("GUARDAR");
		btnGuardar.addKeyListener(c);
		
		btnMostrar.addActionListener(c);
		btnMostrar.setActionCommand("MOSTRAR");
		btnMostrar.addKeyListener(c);
		
		btnCerrar.addActionListener(c);
		btnCerrar.setActionCommand("SALIR");
		btnCerrar.addKeyListener(c);
		
		txtUno.addKeyListener(c);
		txtDos.addKeyListener(c);
		txtCinco.addKeyListener(c);
		txtDiez.addKeyListener(c);
		txtVeinte.addKeyListener(c);
		txtCincuenta.addKeyListener(c);
		txtCien.addKeyListener(c);
		txtQuinientos.addKeyListener(c);
		
	}
}
