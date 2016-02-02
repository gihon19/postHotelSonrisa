package view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import view.botones.BotonCancelar;
import view.botones.BotonCobrar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewCambio extends JDialog  {
	
	private JTextField txtEfectivo;
	private JTextField txtCambio;
	//private final ToggleGroup grupo;
	private ButtonGroup grupoOpciones;
	private JPanel panel;

	public ViewCambio(Window v) {
		
		super(v,"Cambio",Dialog.ModalityType.DOCUMENT_MODAL);
		//setUndecorated(true);
		Font myFont=new Font("OCR A Extended", Font.PLAIN, 45);
		 grupoOpciones = new ButtonGroup(); // crea ButtonGroup//para el grupo de la forma de pago
		 //Color color1 =Color.decode("#0009999");
		 Color color1 =Color.decode("#d4f4ff");
		 this.getContentPane().setBackground(color1);
		 
		 
		
		this.setPreferredSize(new Dimension(420, 250));
		//this.setResizable(false);
		//setUndecorated(true);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(35, 21, 353, 161);
		panel.setBackground(color1);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblPagaCon = new JLabel("Paga con:");
		lblPagaCon.setFont(new Font("Georgia", Font.BOLD, 13));
		lblPagaCon.setForeground(Color.BLACK);
		lblPagaCon.setBounds(6, 6, 77, 14);
		panel.add(lblPagaCon);
		
		txtEfectivo = new JTextField();
		txtEfectivo.setEditable(false);
		txtEfectivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				setVisible(false);
			}
		});
		txtEfectivo.setBounds(6, 26, 341, 54);
		txtEfectivo.setFont(myFont);
		panel.add(txtEfectivo);
		txtEfectivo.setColumns(10);
		
		JLabel lblCambio = new JLabel("Cambio:");
		lblCambio.setFont(new Font("Georgia", Font.BOLD, 13));
		lblCambio.setForeground(Color.BLACK);
		lblCambio.setBounds(6, 92, 77, 14);
		panel.add(lblCambio);
		
		txtCambio = new JTextField();
		txtCambio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		txtCambio.setEditable(false);
		txtCambio.setFont(myFont);
		txtCambio.setBounds(6, 106, 341, 49);
		panel.add(txtCambio);
		txtCambio.setColumns(10);
		
		this.setSize(420, 246);
		
		//this.setResizable(false);
		//centrar la ventana en la pantalla
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.pack();
	}
	public JTextField getTxtCambio(){
		return txtCambio;
	}
	public JTextField getTxtEfectivo(){
		return txtEfectivo;
	}

}
