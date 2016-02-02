package view;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import view.botones.BotonActualizar;
import view.botones.BotonCancelar;
import view.botones.BotonGuardar;
import controlador.CtlMarca;
import modelo.Marca;

public class ViewCrearMarca extends JDialog {
	private JTextField txtMarca;
	private JLabel lblMarca;
	
	private JTextArea txtAreaObservacion;
	private JLabel lblObservacion;
	
	private BotonCancelar btnCancelar;
	private BotonActualizar btnActualizar;
	private BotonGuardar btnGuardar;
	private Marca myMarca;
	
	
	public ViewCrearMarca(Marca m,ViewListaMarca view){
		this(view);
		myMarca=m;
		cargarDatos();
		btnGuardar.setVisible(false);
		btnActualizar.setVisible(true);
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ViewCrearMarca(ViewListaMarca view) {
		super(view,"Agregar marcas",Dialog.ModalityType.DOCUMENT_MODAL);
		setResizable(false);
		getContentPane().setLayout(null);
		this.setFont(new Font("Verdana", Font.PLAIN, 12));
	
		
		lblMarca = new JLabel("Marca");
		lblMarca.setBounds(22, 12, 37, 14);
		getContentPane().add(lblMarca);
		
		txtMarca = new JTextField();
		txtMarca.setBounds(22, 38, 260, 32);
		getContentPane().add(txtMarca);
		txtMarca.setColumns(10);
		
		lblObservacion = new JLabel("Observacion");
		lblObservacion.setBounds(22, 82, 90, 14);
		getContentPane().add(lblObservacion);
		
		txtAreaObservacion = new JTextArea();
		txtAreaObservacion.setBounds(22, 108, 260, 130);
		getContentPane().add(txtAreaObservacion);
		
		// botones de accion
		
		btnCancelar = new BotonCancelar();
		btnCancelar.setSize(128, 78);
		btnCancelar.setLocation(154, 277);
		getContentPane().add(btnCancelar);
		
		btnActualizar=new BotonActualizar();
		btnActualizar.setSize(128, 78);
		btnActualizar.setLocation(22, 277);
		getContentPane().add(btnActualizar);
		btnActualizar.setVisible(false);
		
		btnGuardar = new BotonGuardar();	
		btnGuardar.setLocation(22, 277);
		getContentPane().add(btnGuardar);
		
		this.setSize(309, 395);
		
		//centrar la ventana en la pantalla
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
	}
	public Marca getMarca(){
		myMarca.setMarca(txtMarca.getText());
		myMarca.setObservacion(txtAreaObservacion.getText());
		return myMarca;
	}
	public BotonActualizar getBtnActualizar(){
		return btnActualizar;
	}
	public JTextField getTxtMarca(){
		return txtMarca;
	}
	public JTextArea getTxtObservacion(){
		return txtAreaObservacion;
	}
	
	public void conectarControlador(CtlMarca c){
		btnGuardar.addActionListener(c);
		btnGuardar.setActionCommand("GUARDAR");
		
		btnActualizar.addActionListener(c);
		btnActualizar.setActionCommand("ACTUALIZAR");
	}
	
	private void cargarDatos(){
		
		txtAreaObservacion.setText(myMarca.getObservacion());
		txtMarca.setText(myMarca.getMarca());
		
	}
}
