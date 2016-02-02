package view;

import java.awt.Window;

import view.tablemodel.TmDevoluciones;

public class ViewPrueba extends ViewFacturar {
	
	private TmDevoluciones modeloTabla;

	public ViewPrueba(Window view) {
		super(view);
		modeloTabla=new TmDevoluciones();
		// TODO Auto-generated constructor stub
		super.panelBuscar.setVisible(false);
		tableDetalle.setModel(modeloTabla);
	}

	

}
