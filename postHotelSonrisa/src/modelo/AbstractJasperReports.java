package modelo;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JRException;


public abstract class AbstractJasperReports
{
	private static JasperReport	report;
	private static JasperPrint	reportFilled;
	private static JasperPrint	reportFilled2;
	private static JasperViewer	viewer;
	
	private static InputStream factura=null;
	private static InputStream facturaCompra=null;
	private static InputStream facturaReimpresion=null;
	private static InputStream cierreCaja=null;
	private static InputStream reciboPago=null;
	private static InputStream Dei=null;
	private static InputStream Devolucion=null;
	private static InputStream codigoBarra=null;
	private static InputStream inventario=null;
	private static InputStream cierresCaja=null;
	private static InputStream salidaCaja=null;
	private static InputStream kardex=null;
	
	private static JasperReport	reportFactura;
	private static JasperReport	reportFacturaCompra;
	private static JasperReport	reportFacturaReimpresion;
	private static JasperReport	reportFacturaCierreCaja;
	private static JasperReport	reportReciboPago;
	private static JasperReport	reportDei;
	private static JasperReport	reportDevolucion;
	private static JasperReport	reportCodigoBarra;
	private static JasperReport	reportInventario;
	private static JasperReport	reportCierresCaja;
	private static JasperReport	reportSalidaCaja;
	private static JasperReport	reportKardex;
	
	
	public static void loadFileReport(){
		
		factura=AbstractJasperReports.class.getResourceAsStream("/reportes/factura_texaco2.jasper");
		facturaCompra=AbstractJasperReports.class.getResourceAsStream("/reportes/Factura_Compra_Saint_Paul.jasper");
		facturaReimpresion=AbstractJasperReports.class.getResourceAsStream("/reportes/factura_texaco_reimpresion2.jasper");
		cierreCaja=AbstractJasperReports.class.getResourceAsStream("/reportes/cierre_caja.jasper");
		reciboPago=AbstractJasperReports.class.getResourceAsStream("/reportes/recibo_pago.jasper");
		Dei=AbstractJasperReports.class.getResourceAsStream("/reportes/ReporteDEI.jasper");
		Devolucion=AbstractJasperReports.class.getResourceAsStream("/reportes/devoluciones_texaco.jasper");
		codigoBarra=AbstractJasperReports.class.getResourceAsStream("/reportes/codigo_barra.jasper");
		inventario=AbstractJasperReports.class.getResourceAsStream("/reportes/ReporteExistencia.jasper");
		cierresCaja=AbstractJasperReports.class.getResourceAsStream("/reportes/cierres_caja_expreso.jasper");
		salidaCaja=AbstractJasperReports.class.getResourceAsStream("/reportes/salida_caja.jasper");
		kardex=AbstractJasperReports.class.getResourceAsStream("/reportes/ReporteKardex.jasper");
		
		
		try {
			reportFactura = (JasperReport) JRLoader.loadObject( factura );
			reportFacturaCompra = (JasperReport) JRLoader.loadObject( facturaCompra );
			reportFacturaReimpresion= (JasperReport) JRLoader.loadObject( facturaReimpresion );
			reportFacturaCierreCaja= (JasperReport) JRLoader.loadObject( cierreCaja );
			reportReciboPago= (JasperReport) JRLoader.loadObject( reciboPago );
			reportDei= (JasperReport) JRLoader.loadObject( Dei );
			reportDevolucion= (JasperReport) JRLoader.loadObject( Devolucion );
			reportCodigoBarra= (JasperReport) JRLoader.loadObject( codigoBarra );
			reportInventario= (JasperReport) JRLoader.loadObject( inventario );
			reportCierresCaja= (JasperReport) JRLoader.loadObject( cierresCaja );
			reportSalidaCaja= (JasperReport) JRLoader.loadObject( salidaCaja );
			reportKardex= (JasperReport) JRLoader.loadObject( kardex );
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createReportDei(Connection conn,int mes,int anio,String usuario){
		 Map parametros = new HashMap();
		 parametros.put("Mes",mes);
		 parametros.put("Anio",anio);
		 parametros.put("usuario",usuario);
		 
		 
		 try {
			reportFilled = JasperFillManager.fillReport( reportDei, parametros, conn );
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
				conn.close();
			} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			}
	}
	
	public static void createReportSalidaCaja(Connection conn,int codigo){
		 Map parametros = new HashMap();
		 parametros.put("codigo_salida",codigo);
		 
		 
		 
		 try {
			reportFilled = JasperFillManager.fillReport( reportSalidaCaja, parametros, conn );
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
				conn.close();
			} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			}
	}
	
	
	public static void createReportCodBarra(Connection conn,int id){
		 Map parametros = new HashMap();
		 parametros.put("id_articulo",id);
		 
		 
		 try {
			reportFilled = JasperFillManager.fillReport( reportCodigoBarra, parametros, conn );
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
				conn.close();
			} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			}
	}
	
	public static void createReportKardex(Connection conn,Integer idArticulo,Integer idBodega,String user){
		 Map parametros = new HashMap();
		 parametros.put("cod_articulo",idArticulo);
		 parametros.put("cod_bodega",idBodega);
		 parametros.put("usuario",user);
		 
		 
		 try {
			reportFilled = JasperFillManager.fillReport( reportKardex, parametros, conn );
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
				conn.close();
			} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			}
		
	}
	
	public static void createReport( Connection conn, int tipoReporte,Integer idFactura )
	{
		 Map parametros = new HashMap();
		 parametros.put("numero_factura",idFactura);
		 
		 
		try {
			
			if(tipoReporte==1){
				reportFilled = JasperFillManager.fillReport( reportFactura, parametros, conn );
			}
			if(tipoReporte==2){
				reportFilled = JasperFillManager.fillReport( reportFacturaCompra, parametros, conn );
			}
			if(tipoReporte==3){
				reportFilled = JasperFillManager.fillReport( reportFacturaReimpresion, parametros, conn );
			}
			if(tipoReporte==4){
				
				//se le pasa la direccion del sub informe de las salidas de caja
				InputStream subInputStream = AbstractJasperReports.class.getClass().getResourceAsStream("/reportes/cierre_salida.jasper");
				parametros.put("SUBREPORT_INPUT_STREAM", subInputStream);
				
				reportFilled = JasperFillManager.fillReport( reportFacturaCierreCaja, parametros, conn );
			}
			if(tipoReporte==5){
				reportFilled = JasperFillManager.fillReport( reportReciboPago, parametros, conn );
			}
			if(tipoReporte==6){
				reportFilled = JasperFillManager.fillReport( reportDevolucion, parametros, conn );
			}
			
			
			
			
			
			}
			catch( JRException ex ) {
				ex.printStackTrace();
			}
			try {
					conn.close();
				} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
				}
		
	}
	
	
	public static void createReportInventario(Connection conn,String user){
		 Map parametros = new HashMap();
		 parametros.put("usuario",user);
		 
		 
		 try {
			reportFilled = JasperFillManager.fillReport( reportInventario, parametros, conn );
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
				conn.close();
			} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			}
	}
	
	public static void createReportCierresCaja(Connection conn,String user){
		 Map parametros = new HashMap();
		 parametros.put("usuario",user);
		 
		 
		 try {
			reportFilled = JasperFillManager.fillReport( reportCierresCaja, parametros, conn );
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
				conn.close();
			} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			}
		
	}
	

	public static void createReportFactura( Connection conn, String path,Integer idFactura )
	{
		 Map parametros = new HashMap();
		 parametros.put("numero_factura",idFactura);
		 
		 
		 
		 
		 InputStream ticketReportStream=null;
		 
		 
		 
		 //ticketReportStream=JReportPrintService.class.getResourceAsStream("/com/floreantpos/jreports/TicketReceiptReport.jasper");
		    /*JasperReport ticketReport=(JasperReport)JRLoader.loadObject(ticketReportStream);
		    JasperPrint jasperPrint=JasperFillManager.fillReport(ticketReport,map,new JRTableModelDataSource(new TicketDataSource(ticket)));
		    JasperViewer.viewReport(jasperPrint,false);
		    JasperPrintManager.printReport(jasperPrint,false);*/
		// Connection conn=null;
		 
		/* try {
			conn=conexion.getPoolConexion().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 
		try {
			
			ticketReportStream=AbstractJasperReports.class.getResourceAsStream("/Reportes/"+path);
			//report = (JasperReport) JRLoader.loadObjectFromFile( path );
			report = (JasperReport) JRLoader.loadObject( ticketReportStream );
			reportFilled = JasperFillManager.fillReport( report, parametros, conn );
			
		}
		catch( JRException ex ) {
			ex.printStackTrace();
		}
		try {
				conn.close();
			} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
			}
		
	}
	public static void imprimierFactura(){
		try {
			JasperPrintManager.printReport(reportFilled, false);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void showViewer(Window view)
	{
		JDialog viewer2 = new JDialog(view,"Vista previa del reporte", Dialog.ModalityType.DOCUMENT_MODAL);
		viewer2.setSize(1000,750);
		viewer2.setLocationRelativeTo(null);
		
		
		JasperViewer viewer3 = new JasperViewer( reportFilled );
		//viewer.setAlwaysOnTop( true );
		//viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		/*viewer.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				viewer.setVisible(false);
				//viewer.dispose();
			}
		});*/
		//viewer2.setTitle("Factura");
		viewer2.getContentPane().add(viewer3.getContentPane());
		viewer2.setVisible( true );
		//viewer.setVisible( true );
		
		
	}
	public static Container getPanelReport(){
		JasperViewer viewer3 = new JasperViewer( reportFilled );
		return viewer3.getContentPane();
	}
	
	 public static void Imprimir2(){  
		 
		 
		 
		 
		 try {


		        //String report = JasperCompileManager.compileReportToFile(sourceFileName);


		        //JasperPrint jasperPrint = JasperFillManager.fillReport(report, para, ds);


		        PrinterJob printerJob = PrinterJob.getPrinterJob();


		        PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
		        printerJob.defaultPage(pageFormat);

		        int selectedService = 0;


		        AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName("\\\\TEXACO-PC\\EPSON L210 Series", null));
		        
		        //AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName("\\\\CONTABILIDAD-PC\\EPSON LX-300+ /II (Copiar 1)", null));


		        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, attributeSet);

		        try {
		            printerJob.setPrintService(printService[selectedService]);

		        } catch (Exception e) {

		            System.out.println(e);
		        }
		        JRPrintServiceExporter exporter;
		        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		        printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
		        printRequestAttributeSet.add(new Copies(1));

		        // these are deprecated
		        exporter = new JRPrintServiceExporter();
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, reportFilled);
		        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService[selectedService]);
		        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService[selectedService].getAttributes());
		        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
		        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
		        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
		        exporter.exportReport();

		    } catch (JRException e) {
		        e.printStackTrace();
		    }
		//}   
		         
		         
		         
		}
	 
	 public static void ImprimirCodigo(){  
		 
		 
		 
		 
		 
		 try {

			        PrinterJob printerJob = PrinterJob.getPrinterJob();
	
	
			        PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
			        printerJob.defaultPage(pageFormat);
	
			        int selectedService = 0;
	
	
			        //AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName("\\\\TEXACO-PC\\EPSON L210 Series", null));
			        
			        AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName("ZDesigner LP 2824 Plus (ZPL)", null));
	
	
			        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, attributeSet);
	
			        try {
			            printerJob.setPrintService(printService[selectedService]);
	
			        } catch (Exception e) {
	
			            System.out.println(e);
			        }
			        JRPrintServiceExporter exporter;
			        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
			        //printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
			        printRequestAttributeSet.add(new Copies(1));
	
			        // these are deprecated
			        exporter = new JRPrintServiceExporter();
			        exporter.setParameter(JRExporterParameter.JASPER_PRINT, reportFilled);
			        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService[selectedService]);
			        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService[selectedService].getAttributes());
			        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
			        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
			        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
			        exporter.exportReport();

		    } catch (JRException e) {
		        e.printStackTrace();
		   } 
		         
		         
		         
	}//fin del metodo

	public static void exportToPDF( String destination )
	{
		try { 
			JasperExportManager.exportReportToPdfFile( reportFilled, destination );
		}
		catch( JRException ex ) {
			ex.printStackTrace();
		}
	}
	
}
