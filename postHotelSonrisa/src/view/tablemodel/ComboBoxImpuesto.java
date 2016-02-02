package view.tablemodel;


import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import modelo.Impuesto;

public class ComboBoxImpuesto extends DefaultComboBoxModel {
	
	private Vector<Impuesto> impuestos;

	@Override
	public int getSize() {
		  return impuestos.size();
		 }
	
	@Override
	public Object getElementAt(int index) {
		  return impuestos.get(index);
		 }
	
	public void setLista(Vector<Impuesto> im){
		impuestos=im;
	}
	
	public ComboBoxImpuesto(){
		
	}
	
	public int buscarImpuesto(Impuesto m){
		int index=-1;
		
		for(int c=0;c<impuestos.size();c++){
			
			if(impuestos.get(c).getId()==m.getId()){
				
				index=c;
			}
		}
		
		return index;
	}
	
	
	
	
	
	
	/*public ComboBoxImpuesto(Conexion conn) {
		conexion=conn;
		impuestos = new Vector<Impuesto>();
		  try {
			 // conexion= new Conexion();
		   refrescarDatos();
		  } catch (SQLException e) {
		   System.err.println(e);
		  }
		 }*/
	
	
	
	
	/*private void refrescarDatos() throws SQLException {
		 
		  Statement st = null;
		  ResultSet rs = null;
		  try {
		   
		   st = conexion.getConnection().createStatement();
		   rs = st.executeQuery("SELECT codigo_impuesto,porcentaje FROM impuesto");
		 
		   while (rs.next()) {
		    Impuesto dep = new Impuesto();
		    dep.setId(rs.getInt("codigo_impuesto"));
		    dep.setPorcentaje(rs.getString("porcentaje"));
		    impuestos.add(dep);
		   }
		  } catch (SQLException e) {
		   System.err.println(e);
		  } finally {
		   if (rs != null) {
		    rs.close();
		   }
		 
		   if (st != null) {
		    st.close();
		   }
		 
		   //if (conexion != null) {
		  //  conexion.desconectar();
		  // }
		  }
		 }*/
	
	
	
	
}
