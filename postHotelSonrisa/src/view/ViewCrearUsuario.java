package view;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.SpringLayout;

import controlador.CtlUsuario;
import view.botones.BotonActualizar;
import view.botones.BotonCancelar;
import view.botones.BotonGuardar;
import view.rendes.PanelPadre;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class ViewCrearUsuario extends JDialog {
	private JTextField txtUsuario;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JPasswordField pwdPwd;
	private JPasswordField pwdRePwd;
	private BotonGuardar btnGuardar;
	private JRadioButton rdbtnCajero;
	private JRadioButton rdbtnAdministrador;
	private BotonCancelar btnCancelar;
	private ButtonGroup grupoOpciones;
	private BotonActualizar btnActualizar;
	
	
	public ViewCrearUsuario(Window view) {
		
		
		this.setTitle("Crear Usuario");
		this.setLocationRelativeTo(view);
		this.setModal(true);
		
		this.setResizable(false);
		
		grupoOpciones = new ButtonGroup();
		this.setSize(423, 537);
		getContentPane().setLayout(null);
		
		getContentPane().setBackground(PanelPadre.color1);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(20, 9, 43, 15);
		getContentPane().add(lblUsuario);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(20, 234, 55, 15);
		getContentPane().add(lblPassword);
		
		JLabel lblRepetirPassword = new JLabel("Repetir Password");
		lblRepetirPassword.setBounds(20, 309, 101, 15);
		getContentPane().add(lblRepetirPassword);
		
		JLabel lblNombres = new JLabel("Nombre");
		lblNombres.setBounds(20, 84, 47, 15);
		getContentPane().add(lblNombres);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(20, 108, 379, 42);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(20, 159, 47, 15);
		getContentPane().add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(20, 183, 379, 42);
		getContentPane().add(txtApellido);
		txtApellido.setColumns(10);
		
		pwdPwd = new JPasswordField();
		pwdPwd.setBounds(20, 258, 379, 42);
		getContentPane().add(pwdPwd);
		
		pwdRePwd = new JPasswordField();
		pwdRePwd.setBounds(20, 333, 379, 42);
		getContentPane().add(pwdRePwd);
		
		rdbtnAdministrador = new JRadioButton("Administrador");
		rdbtnAdministrador.setBounds(155, 384, 104, 18);
		grupoOpciones.add(rdbtnAdministrador);
		getContentPane().add(rdbtnAdministrador);
		
		rdbtnCajero = new JRadioButton("Cajero");
		rdbtnCajero.setSelected(true);
		this.grupoOpciones.add(rdbtnCajero);
		rdbtnCajero.setBounds(20, 384, 60, 18);
		getContentPane().add(rdbtnCajero);
		
		btnGuardar = new BotonGuardar();
		btnGuardar.setLocation(39, 429);
		getContentPane().add(btnGuardar);
		
		btnCancelar = new BotonCancelar();
		btnCancelar.setLocation(231, 431);
		getContentPane().add(btnCancelar);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(20, 33, 379, 42);
		getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);
		
		btnActualizar=new BotonActualizar();
		btnActualizar.setLocation(39, 429);
		getContentPane().add(btnActualizar);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	public BotonActualizar getBtnActualizar(){
		return btnActualizar;
	}
	public BotonGuardar getBtnGuardar(){
		return btnGuardar;
	}
	public JRadioButton getRdbtnAdministrador(){
		return rdbtnAdministrador;
	}
	public JRadioButton getRdbtnCajero(){
		return rdbtnCajero;
	}
	public JTextField getTxtUser(){
		return txtUsuario;
	}
	public JTextField getTxtNombre(){
		return txtNombre;
	}
	public JTextField getTxtApellido(){
		return txtApellido;
	}
	public JPasswordField getPwd(){
		return pwdPwd;
	}
	public JPasswordField getRePwd(){
		return pwdRePwd;
	}
	
	public void conectarCtl(CtlUsuario c){
		btnGuardar.addActionListener(c);
		btnGuardar.setActionCommand("GUARDAR");
		
		btnCancelar.addActionListener(c);
		btnCancelar.setActionCommand("CANCELAR");
		
		btnActualizar.addActionListener(c);
		btnActualizar.setActionCommand("ACTUALIZAR");
		
		
	}
}
