package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Articulo;
import modelo.CodBarra;
import modelo.Conexion;
import modelo.PrecioArticulo;

public class PrecioArticuloDao {
	
	private PreparedStatement buscarPorArticulo=null;
	private PreparedStatement registrar=null;
	private PreparedStatement eleminar=null;
	private Conexion conexion=null;
	
	public PrecioArticuloDao(Conexion conn) {
		// TODO Auto-generated constructor stub
		conexion=conn;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los precios de un articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<PrecioArticulo> getTipoPrecios(){
		
		
		
        Connection con = null;
        
        
      
       	List<PrecioArticulo> precios=new ArrayList<PrecioArticulo>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			buscarPorArticulo = con.prepareStatement("SELECT * FROM precios ;");
			//buscarPorArticulo.setInt(1,id);
			res = buscarPorArticulo.executeQuery();
			while(res.next()){
				PrecioArticulo unPrecio=new PrecioArticulo();
				existe=true;
				
				//unPrecio.setCodigoArticulo(res.getInt("codigo_articulo"));
				unPrecio.setCodigoPrecio(res.getInt("codigo_precio"));
				unPrecio.setDecripcion(res.getString("descripcion"));
				//unPrecio.setPrecio(res.getBigDecimal("precio_articulo"));
				
				
				precios.add(unPrecio);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(buscarPorArticulo != null)buscarPorArticulo.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return precios;
			}
			else return null;
		
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los precios de un articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<PrecioArticulo> getPreciosArticulo(int id){
		
		
		
        Connection con = null;
        
        
      
       	List<PrecioArticulo> precios=new ArrayList<PrecioArticulo>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			buscarPorArticulo = con.prepareStatement("SELECT * FROM v_precios WHERE v_precios.codigo_articulo = ?;");
			buscarPorArticulo.setInt(1,id);
			res = buscarPorArticulo.executeQuery();
			while(res.next()){
				PrecioArticulo unPrecio=new PrecioArticulo();
				existe=true;
				
				unPrecio.setCodigoArticulo(res.getInt("codigo_articulo"));
				unPrecio.setCodigoPrecio(res.getInt("codigo_precio"));
				unPrecio.setDecripcion(res.getString("descripcion"));
				unPrecio.setPrecio(res.getBigDecimal("precio_articulo"));
				
				
				precios.add(unPrecio);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(buscarPorArticulo != null)buscarPorArticulo.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return precios;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eliminar de articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarPrecio(PrecioArticulo precios){
		int resultado=0;
		Connection conn=null;
		try {
				conn=conexion.getPoolConexion().getConnection();
				eleminar=conn.prepareStatement("DELETE FROM precios_articulos WHERE codigo_articulo = ? and codigo_precio= ?");
			
				eleminar.setInt( 1, precios.getCodigoArticulo());
				eleminar.setInt( 2, precios.getCodigoPrecio() );
				
				
				resultado=eleminar.executeUpdate();
		
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		finally
		{
			try{
					
	                if(eleminar != null)eleminar.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
				} // fin de catch
		} // fin de finally
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar Articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrar(PrecioArticulo precio)
	{
		
		int resultado=0;
		ResultSet rs=null;
		Connection con = null;
		
		try 
		{
			con = conexion.getPoolConexion().getConnection();
			
			registrar=con.prepareStatement( "INSERT INTO precios_articulos(codigo_articulo,precio_articulo,codigo_precio) VALUES (?,?,?)");
			
			registrar.setInt(1, precio.getCodigoArticulo());
			registrar.setFloat(2, precio.getPrecio().floatValue());
			registrar.setInt(3, precio.getCodigoPrecio());
			
			
			resultado=registrar.executeUpdate();
			
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
            return false;
		}
		finally
		{
			try{
				if(rs!=null)rs.close();
				 if(registrar != null)registrar.close();
	              if(con != null) con.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}

}
