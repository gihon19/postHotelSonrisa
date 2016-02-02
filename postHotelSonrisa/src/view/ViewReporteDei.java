package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modelo.AbstractJasperReports;
import modelo.Conexion;

import com.toedter.calendar.JDateChooser;

public class ViewReporteDei  extends JFrame{
	
	private JDesktopPane elEscritorio;
	private  ViewFiltroReportDei panel;
	private Conexion conexion;
	

	public ViewReporteDei(Conexion conn) {
		// TODO Auto-generated constructor stub
		super( "Reporte de la DEI" );
		
		conexion=conn;
		elEscritorio = new JDesktopPane(); // crea el panel de escritorio
		getContentPane().add( elEscritorio ); // agrega el panel de escritorio al marco
		JInternalFrame marco = new JInternalFrame("Filtros", true, false, false, true );
		marco.setBounds(240, 50, 111, 74);
		
		
		//elEscritorio.add(panel2);
		//panel = new ViewFiltroReportDei(); // crea nuevo panel
		
		CtlReporteDei ctl =new CtlReporteDei();
		//panel.btnBuscar.addActionListener(ctl);
		marco.getContentPane().add( panel, BorderLayout.CENTER ); // agrega el panel
		//marco.getContentPane().setLayout(null);
		marco.pack(); // establece marco interno al tamaño del contenido
		
		
		elEscritorio.add( marco ); // adjunta marco interno
		//elEscritorio.add( marco2 ); // adjunta marco interno
		marco.setVisible( true ); // muestra marco interno
		//marco2.setVisible( true ); // muestra marco interno
	}
	public void resfresh(){
		this.revalidate();
		this.repaint();
	}
	
	class CtlReporteDei implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateInicio = sdf.format(panel.dateFechaInicio.getDate());
			//elEscritorio.getc
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateFinal = sdf.format(panel.dateFechaFinal.getDate());
			
			
			JOptionPane.showMessageDialog(null, dateInicio+" Hizo clik en el boton "+dateFinal);*/
			
			AbstractJasperReports.loadFileReport();
			
			try {
				//panel2.setVisible(false);
				//panel2.d
				//elEscritorio.setVisible(false);
				AbstractJasperReports.createReportDei(conexion.getPoolConexion().getConnection(),1, 1,  "DAvid");
				//elEscritorio.add(AbstractJasperReports.getPanelReport());
				//panel2=AbstractJasperReports.getPanelReport();
				JInternalFrame marco = new JInternalFrame("Filtros", true, true, true, true );
				//getContentPane().add(AbstractJasperReports.getPanelReport());
				//elEscritorio.add(AbstractJasperReports.getPanelReport());
				JPanel panel2=new JPanel();
				panel2.setSize(800, 600);
				panel2.add(AbstractJasperReports.getPanelReport());
				
				marco.getContentPane().add(panel2, BorderLayout.WEST ); // agrega el panel
				marco.setSize(900,750);
				marco.pack(); // establece marco interno al tamaño del contenido
				elEscritorio.add( marco ); // adjunta marco interno
				marco.setVisible( true ); // muestra marco interno
			} catch (SQLException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
		}
		
	}
	public static void main( String args[] )
	 {
		Conexion conexion=new Conexion();
		//JOptionPane.showMessageDialog(null, dateInicio+" Hizo clik en el boton "+dateFinal);
		
		/*AbstractJasperReports.loadFileReport();
		
		try {
			AbstractJasperReports.createReportDei(conexion.getPoolConexion().getConnection(), "2015-10-02", "2015-10-16",  "DAvid");
			
		} catch (SQLException ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}*/
		
		
		
		
		ViewReporteDei marcoEscritorio = new ViewReporteDei(conexion);
		marcoEscritorio.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		marcoEscritorio.setSize( 600, 480 ); // establece el tamaño del marco
		marcoEscritorio.setVisible( true ); // muestra el marco
	} // fin de main
	
	
	/*class MiJPanel extends JPanel
	{
		
		private JDateChooser dateInicio;
		private JDateChooser dateFinal;
	 
	public MiJPanel()
	 {
		dateInicio = new JDateChooser();
		dateInicio.setDateFormatString("dd-MM-yyyy");
		getContentPane().setLayout(null);
		
		add(dateInicio);
		
		
		dateFinal = new JDateChooser();
		dateFinal.setDateFormatString("dd-MM-yyyy");
		
		add(dateFinal);
	
	} // fin del constructor de MiJPanel
	
	
	
	} // fin de la clase MiJPanel*/

}
