package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import view.tablemodel.DmtFacturaProveedores;





public class ViewIngresoArticulos extends JFrame {
	
	private JTable table;
	
	
	
	public ViewIngresoArticulos() {
		super("Ingreso de articulos");
		getContentPane().setLayout(null);
		
		
		
		String []titulosTablas= {
				"Id Articulo", "Nombre", "Cantidad", "Precio Unidad","Impuesto", "Total"
			};
		Object [][]data={
				{null, null, null, null,null, null},
				{null, null, null, null,null, null},
				{null, null, null, null,null, null},
				{null, null, null, null,null, "hola"},
			};
		DmtFacturaProveedores myDtm=new DmtFacturaProveedores();
		DefaultTableModel dtm= new DefaultTableModel(data, titulosTablas);
		table = new JTable(myDtm);
		table.setBounds(47, 95, 430, 81);
		//getContentPane().add(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(36, 97, 742, 136);
		getContentPane().add(scrollPane);
		
		 /*/Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane = new JScrollPane(table);
		//Agregamos el JScrollPane al contenedor
		getContentPane().add(scrollPane, BorderLayout.CENTER);*/
		
		setSize(804,401);
		
	}
}
