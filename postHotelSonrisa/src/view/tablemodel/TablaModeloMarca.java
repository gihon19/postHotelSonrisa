package view.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.Marca;

public class TablaModeloMarca extends AbstractTableModel {
	
	private String []columnNames={"Id","Marca","Observacion"};
	private List<Marca> marcas = new ArrayList<Marca>();
	
	public void agregarMarca(Marca marca) {
		marcas.add(marca);
        fireTableDataChanged();
    }
	
	public Marca getMarca(int index){
		
		return marcas.get(index);
		
	}
	
	public void cambiarMarca(int index,Marca marca){
		marcas.set(index, marca);
	}
 
    public void eliminarMarca(int rowIndex) {
    	marcas.remove(rowIndex);
        fireTableDataChanged();
    }
     
    public void limpiarMarcas() {
    	marcas.clear();
        fireTableDataChanged();
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return marcas.size();
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
            return marcas.get(rowIndex).getId();
        case 1:
            return marcas.get(rowIndex).getMarca();
        case 2:
            return marcas.get(rowIndex).getObservacion();
        
        default:
            return null;
		}
	}
	
	@Override
    public Class getColumnClass(int columnIndex) {
		//        return getValueAt(0, columnIndex).getClass();
        return String.class;
        
        
        /*switch (columnIndex) {
        case 0:
            return Integer.class;
        case 1:
            return String.class;
        case 2:
        	return String.class;
        
        default:
            return null;
            }*/
    }
	
	@Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
	
	 @Override
	    public void setValueAt(Object value, int rowIndex, int columnIndex) {
	        Marca marca = marcas.get(rowIndex);
	        switch (columnIndex) {
	            case 0:
	            	marca.setId((Integer) value);
	            case 1:
	            	marca.setMarca((String) value);
	            case 2:
	            	marca.setObservacion((String) value);
	    
	        }
	        fireTableCellUpdated(rowIndex, columnIndex);
	    }

}
