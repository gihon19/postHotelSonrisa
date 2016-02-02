package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.CodBarra;
import modelo.Conexion;

public class CodBarraDao {
	Conexion conexion=null;
	private PreparedStatement insertarNuevoCodBarras=null;
	private PreparedStatement eliminarCodsBarra = null;
	private PreparedStatement buscarCodigos=null;
	private PreparedStatement eliminarCodsPorCodBarra=null;
	public CodBarraDao(Conexion conn){
		conexion=conn;
		/*try{
			insertarNuevoCodBarras=conexion.getConnection().prepareStatement( "INSERT INTO codigos_articulos(codigo_articulo,codigo_barra) VALUES (?,?)");
			eliminarCodsBarra=conexion.getConnection().prepareStatement("DELETE FROM codigos_articulos WHERE codigo_articulo = ?");
			eliminarCodsPorCodBarra=conexion.getConnection().prepareStatement("DELETE FROM codigos_articulos WHERE codigo_barra = ? and codigo_articulo= ?");
			buscarCodigos=conexion.getConnection().prepareStatement("SELECT codigo_articulo,codigo_barra FROM codigos_articulos where codigo_articulo =  ?");
			
		}catch ( SQLException excepcionSql )
		{
			excepcionSql.printStackTrace();
			System.exit( 1 );
		} // fin de catch*/
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< selecciona de la Bd todas las MArcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<CodBarra> getCodsArticulo(int codArticulo){
		List<CodBarra> cods=new ArrayList<CodBarra>();
		ResultSet res=null;
		Connection conn=null;
		
		boolean existe=false;
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarCodigos=conn.prepareStatement("SELECT codigo_articulo,codigo_barra FROM codigos_articulos where codigo_articulo =  ?");
			
			buscarCodigos.setInt(1, codArticulo);
			res = buscarCodigos.executeQuery();
			while(res.next()){
				CodBarra unCod=new CodBarra();
				existe=true;

				unCod.setCodArticulo(res.getInt("codigo_articulo"));
				unCod.setCodigoBarra(res.getString("codigo_barra"));
				
				cods.add(unCod);
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
					if(res != null) res.close();
	                if(buscarCodigos != null)buscarCodigos.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
				} // fin de catch
		} // fin de finally
		
			if (existe) {
				return cods;
			}
			else return null;
			
		}
	
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eliminar de articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarCodigo(int idArticulo){
		int resultado=0;
		
		Connection conn=null;
		
		try {
				conn=conexion.getPoolConexion().getConnection();
				eliminarCodsBarra=conn.prepareStatement("DELETE FROM codigos_articulos WHERE codigo_articulo = ?");
				
				eliminarCodsBarra.setInt( 1, idArticulo );
				resultado=eliminarCodsBarra.executeUpdate();
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		finally
		{
			try{
					
	                if(eliminarCodsBarra != null)eliminarCodsBarra.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
				} // fin de catch
		} // fin de finally
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eliminar de articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarCodigo(CodBarra cod){
		int resultado=0;
		Connection conn=null;
		try {
				conn=conexion.getPoolConexion().getConnection();
				eliminarCodsPorCodBarra=conn.prepareStatement("DELETE FROM codigos_articulos WHERE codigo_barra = ? and codigo_articulo= ?");
			
				eliminarCodsPorCodBarra.setString( 1, cod.getCodigoBarra() );
				eliminarCodsPorCodBarra.setInt( 2, cod.getCodArticulo() );
				
				
				resultado=eliminarCodsPorCodBarra.executeUpdate();
		
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		finally
		{
			try{
					
	                if(eliminarCodsPorCodBarra != null)eliminarCodsPorCodBarra.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
				} // fin de catch
		} // fin de finally
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eliminar de codigos de barra>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarCodsBarra(List<CodBarra> codsBarras){
		
		Connection conn=null;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			insertarNuevoCodBarras=conn.prepareStatement( "INSERT INTO codigos_articulos(codigo_articulo,codigo_barra) VALUES (?,?)");
			for(int x=0;x<codsBarras.size();x++){
				insertarNuevoCodBarras.setInt(1, codsBarras.get(x).getCodArticulo());
				insertarNuevoCodBarras.setString(2,codsBarras.get(x).getCodigoBarra() );
				insertarNuevoCodBarras.executeUpdate();
				
			}
			return true;
		}catch (SQLException e) {
				e.printStackTrace();
				//conexion.desconectar();
	            return false;
			}
		finally
		{
			try{
					
	                if(insertarNuevoCodBarras != null)insertarNuevoCodBarras.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
				} // fin de catch
		} // fin de finally
		
			
	

	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para registrar de codigos de barra>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarCodsBarra(CodBarra codBarra){
		Connection conn=null;
		try{
				conn=conexion.getPoolConexion().getConnection();
				insertarNuevoCodBarras=conn.prepareStatement( "INSERT INTO codigos_articulos(codigo_articulo,codigo_barra) VALUES (?,?)");
			
				insertarNuevoCodBarras.setInt(1, codBarra.getCodArticulo());
				insertarNuevoCodBarras.setString(2,codBarra.getCodigoBarra() );
				insertarNuevoCodBarras.executeUpdate();
				
			
			return true;
		}catch (SQLException e) {
				e.printStackTrace();
				//conexion.desconectar();
	            return false;
			}
		finally
		{
			try{
					
	                if(insertarNuevoCodBarras != null)insertarNuevoCodBarras.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
				} // fin de catch
		} // fin de finally
		
			
	

	}
}
