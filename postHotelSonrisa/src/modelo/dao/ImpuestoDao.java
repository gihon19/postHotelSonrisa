package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Impuesto;

public class ImpuestoDao {
	
	private Conexion conexion;
	private PreparedStatement seleccionarTodasLosImpuesto = null;
	
	public ImpuestoDao(Conexion conn){
		conexion=conn;
		
		/*try{
			
			//conexion= new Conexion();
			seleccionarTodasLosImpuesto = conexion.getConnection().prepareStatement("SELECT codigo_impuesto,porcentaje FROM impuesto");
			insertarNuevaMarca=conexion.getConnection().prepareStatement( "INSERT INTO marcas(descripcion,observacion) VALUES (?,?)");
			actualizarMarca=conexion.getConnection().prepareStatement("UPDATE marcas SET descripcion = ?, observacion = ? WHERE codigo_marca = ?");
			eliminarMarca=conexion.getConnection().prepareStatement("DELETE FROM marcas WHERE codigo_marca = ?");
			buscarMarca=conexion.getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where codigo_marca =  ?");
			buscarMarcaObseracion=conexion.getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where observacion LIKE ? ;");
			buscarMarcaNombre=conexion.getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where descripcion LIKE ? ;");
		}
		catch ( SQLException excepcionSql )
		{
			excepcionSql.printStackTrace();
			System.exit( 1 );
		} // fin de catch*/
	}
	
	
/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< selecciona de la Bd todas las MArcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Vector<Impuesto> todoImpuesto(){
		
		//List<Impuesto> impuestos=new ArrayList<Impuesto>();
		Vector<Impuesto> impuestos=new Vector<Impuesto>();
		ResultSet res=null;
		Connection conn=null;
		//DefaultComboBoxModel modelImpuesto = new DefaultComboBoxModel();
		boolean existe=false;
		try {
			conn=conexion.getPoolConexion().getConnection();
			seleccionarTodasLosImpuesto = conn.prepareStatement("SELECT codigo_impuesto,porcentaje FROM impuesto");
			
			res = seleccionarTodasLosImpuesto.executeQuery();
			
			while(res.next()){
				Impuesto unaImpuesto=new Impuesto();
				existe=true;
				unaImpuesto.setId(Integer.parseInt(res.getString("codigo_impuesto")));
				unaImpuesto.setPorcentaje(res.getString("porcentaje"));
				impuestos.add(unaImpuesto);
				
				//modelImpuesto.addElement(new Impuesto((String)res.getString("codigo_impuesto"), (String)res.getString("porcentaje")));
			 }
			//res.close();
			//conexion.desconectar();
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				if(res != null) res.close();
                if(seleccionarTodasLosImpuesto != null)seleccionarTodasLosImpuesto.close();
                if(conn != null) conn.close();
                
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
			if (existe) {
				return impuestos;
			}
			else return null;
			
		}

}
