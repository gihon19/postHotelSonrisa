package view.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.Articulo;



public class TableModeloArticulo extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1222;
	private String []columnNames={"Id","Nombre","Marca","Impuesto","Precio Venta"};
	private List<Articulo> articulos = new ArrayList<Articulo>();
	
	
	public void agregarArticulo(Articulo articulo) {
		articulos.add(articulo);
        fireTableDataChanged();
    }
	
	public Articulo getArticulo(int index){
		//proveedores.
		return articulos.get(index);
		
	}
	
	public void cambiarArticulo(int index,Articulo articulo){
		articulos.set(index, articulo);
	}
 
    public void eliminarArticulos(int rowIndex) {
    	articulos.remove(rowIndex);
        fireTableDataChanged();
    }
     
    public void limpiarArticulos() {
    	articulos.clear();
        fireTableDataChanged();
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return articulos.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
        case 0:
            return articulos.get(rowIndex).getId();
        case 1:
            return articulos.get(rowIndex).getArticulo();
        case 2:
        	return articulos.get(rowIndex).getMarcaObj().getMarca();//articulos.get(rowIndex).getMarca();
        case 3:
            
            return articulos.get(rowIndex).getImpuestoObj().getPorcentaje();
        case 4:
        	 return articulos.get(rowIndex).getPrecioVenta();
        default:
            return null;
		}
	}
	
	
	 @Override
	    public void setValueAt(Object value, int rowIndex, int columnIndex) {
	        Articulo articulo = articulos.get(rowIndex);
	        switch (columnIndex) {
	            case 0:
	            	articulo.setId((Integer) value);
	            case 1:
	            	articulo.setArticulo((String) value);
	            case 2:
	            	//articulo.setMarca((String) value);
	            	articulo.getMarcaObj().setMarca((String) value);
	            case 3:
	            	articulo.getImpuestoObj().setPorcentaje((String) value);
	            case 4:
	            	articulo.setPrecioVenta((Double)value);
	            	//articulo.setImpuesto((Double) value);
	   ///º	º	º	1º	º	º	º	1	q1	1	Q	11 codigo de mi  niño jajajaj
	        }
	        fireTableCellUpdated(rowIndex, columnIndex);
	    }
	

	@Override
    public Class getColumnClass(int columnIndex) {
		//        return getValueAt(0, columnIndex).getClass();
        return String.class;
    }
	
	@Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
