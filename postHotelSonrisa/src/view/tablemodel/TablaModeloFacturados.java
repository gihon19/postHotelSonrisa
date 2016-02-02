package view.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.Factura;

public class TablaModeloFacturados  extends AbstractTableModel {
	final private String []columnNames= {
			"No Factura","Fecha","Cliente", "Telefono", "Total","Estado"
		};
	private List<Factura> facturas=new ArrayList<Factura>();
	
	private int limiteInferior=0;
	private int limiteSuperior=30;
	private int noPagina=1;
	
	public int getLimiteInferior(){
		return limiteInferior;
	}
	public int getLimiteSuperior(){
		return limiteSuperior;
	}
	public int getNoPagina(){
		return noPagina;
	}
	
	public void netPag(){
		limiteInferior+=30;
		//limiteSuperior+=30;
		noPagina++;
	}
	public void lastPag(){
		//limiteSuperior-=30;
		limiteInferior-=30;
		noPagina--;
	}
	
	public Factura getFactura(int row){
		return facturas.get(row);
	}
	public void agregarFactura(Factura f){
		facturas.add(f);
		fireTableDataChanged();
	}
	@Override
	public String getColumnName(int columnIndex) {
	        return columnNames[columnIndex];
	        
	  }

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return facturas.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		switch (columnIndex) {
		case 0:
			return facturas.get(rowIndex).getIdFactura();
		case 1:
			return facturas.get(rowIndex).getFecha();
		case 2:
			return facturas.get(rowIndex).getCliente().getNombre();
		case 3:
			return facturas.get(rowIndex).getCliente().getTelefono();
		case 4:
			return facturas.get(rowIndex).getTotal();
		case 5:
			return facturas.get(rowIndex).getEstado();
		
		default:
				return null;
		}
	}
	@Override
    public Class getColumnClass(int columnIndex) {
		//        return getValueAt(0, columnIndex).getClass();
        return String.class;
    }
	public void limpiarFacturas() {
		// TODO Auto-generated method stub
		facturas.clear();
		fireTableDataChanged();
	}
	public void eliminarFactura(int row) {
		// TODO Auto-generated method stub
		facturas.remove(row);
		fireTableDataChanged();
	}

}
