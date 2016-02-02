package controlador;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.ViewFiltroReportDei;
import modelo.AbstractJasperReports;
import modelo.Conexion;

public class CtlFiltroRepDei implements ActionListener {
	
	private Conexion conexion;
	private ViewFiltroReportDei view;

	public CtlFiltroRepDei(ViewFiltroReportDei v,Conexion conn) {
		// TODO Auto-generated constructor stub
		conexion=conn;
		view =v;
		view.conectarCtl(this);
		view.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		
		switch(comando){
		case "GENERAR":

			// TODO Auto-generated method stub
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateInicio = sdf.format(panel.dateFechaInicio.getDate());
			//elEscritorio.getc
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateFinal = sdf.format(panel.dateFechaFinal.getDate());
			
			
			JOptionPane.showMessageDialog(null, dateInicio+" Hizo clik en el boton "+dateFinal);*/
			
			int mes=view.getMes().getMonth()+1;
			int anio=view.getAnio().getYear();
			
			//JOptionPane.showMessageDialog(null, mes+" Hizo clik en el boton "+anio);
			//AbstractJasperReports.loadFileReport();
			
			try {
				//panel2.setVisible(false);
				//panel2.d
				//elEscritorio.setVisible(false);
				AbstractJasperReports.createReportDei(conexion.getPoolConexion().getConnection(),mes, anio, conexion.getUsuarioLogin().getUser());
				
				AbstractJasperReports.showViewer(view);
			} catch (SQLException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
		
			break;
		}
	}

}
