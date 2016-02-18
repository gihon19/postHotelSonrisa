package view.tablemodel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelo.Impuesto;
import modelo.PrecioArticulo;

public class TmImpuestos extends AbstractTableModel {
	
	private String []columnNames={"Tipo Impuesto","Agregar?"};
	private List<Impuesto> impuestos = new ArrayList<Impuesto>();

	public TmImpuestos() {
		// TODO Auto-generated constructor stub
	}
	
	public void agregarImpuesto(Impuesto impuesto) {
		impuestos.add(impuesto);
        fireTableDataChanged();
    }
	public List<Impuesto> getImpuestos(){
		return impuestos;
	}
	public void eliminar(int rowIndex) {
    	impuestos.remove(rowIndex);
        fireTableDataChanged();
    }
     
    public void limpiar() {
    	impuestos.clear();
        fireTableDataChanged();
    }

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return impuestos.size();
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
            return impuestos.get(rowIndex).getPorcentaje();
        case 1:
        	return impuestos.get(rowIndex).getAccion();
        	
        default:
            return null;
		}
	}
	
	@Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
	
	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Impuesto imp = impuestos.get(rowIndex);
		
        switch (columnIndex) {
            case 0:
            	String v=(String) value;
            	imp.setPorcentaje((String) v);// .setId((Integer) value);
            	break;
            case 1:
            	boolean vv=(boolean) value;
            	impuestos.get(rowIndex).setAccion(vv);// Double.parseDouble(v));
            	fireTableCellUpdated(rowIndex, columnIndex);
        	break;
           
    
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
	 @Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		boolean resul=false;
		/*if(columnIndex==0)
			resul= true;*/
		
		if(columnIndex==1)
			resul=true;
		/*if(columnIndex==6)
			resul=true;*/
		
		
		
		
	
		
		
		return resul;
	}
	 
	 @Override
	    public Class getColumnClass(int columnIndex) {
			//        return getValueAt(0, columnIndex).getClass();
			switch (columnIndex) {
	        
	        case 1:
	            return Boolean.class;
	        
	        default:
	            return String.class;
			}
	    }

	public void selectImpuesto(List<Impuesto> imp) {
		// TODO Auto-generated method stub
		for(int x=0;x<imp.size();x++){
			for(int y=0;y<impuestos.size();y++){
				if(imp.get(x).getId()==impuestos.get(y).getId()){
					impuestos.get(y).setAccion(true);
				}
			}
			
		}
		fireTableDataChanged();
		
	}

}
