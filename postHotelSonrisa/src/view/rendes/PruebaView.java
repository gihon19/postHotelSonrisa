package view.rendes;

import view.ViewMenuPrincipal;
import controlador.CtlArticuloLista;
import controlador.CtlMenuPrincipal;
import controlador.CtlProveedorLista;
import modelo.Conexion;

public class PruebaView {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Conexion conexion= new Conexion();
		ViewMenuPrincipal principal=new ViewMenuPrincipal();
		//CtlMenuPrincipal ctl=new CtlMenuPrincipal(principal,conexion);
		//principal.conectarControlador(ctl);
		
		/*ViewAgregarCompras view= new ViewAgregarCompras();
		view.setVisible(true);*/
		
		
		
		/*ViewListaArticulo viewListaProveedor=new VciewListaArticulo();
		CtlArticuloLista ctl=new CtlArticuloLista(viewListaProveedor);
		
		
		
		
		
		hoy si papa si que funcion perfecto
		viewListaProveedor.conectarControlador(ctl);
		*/
				
	}

}
