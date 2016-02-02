package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Marca;

public class MarcaDao {
	
	private int idMarcaRegistrada;
	private Conexion conexion;
	
	private PreparedStatement seleccionarTodasLasMarcas = null;
	private PreparedStatement seleccionarPersonasPorApellido = null;
	private PreparedStatement insertarNuevaMarca = null;
	private PreparedStatement actualizarMarca = null;
	private PreparedStatement eliminarMarca = null;
	private PreparedStatement buscarMarca = null;
	private PreparedStatement buscarMarcaObseracion=null;
	private PreparedStatement buscarMarcaNombre=null;
	
	
	public MarcaDao(Conexion conn){
		conexion=conn;
		/*try{
			
			//conexion= new Conexion();
			//seleccionarTodasLasMarcas = conexion.getPoolConexion().getConnection().prepareStatement("SELECT * FROM marcas");
			//insertarNuevaMarca=conexion.getPoolConexion().getConnection().prepareStatement( "INSERT INTO marcas(descripcion,observacion) VALUES (?,?)");
			//actualizarMarca=conexion.getPoolConexion().getConnection().prepareStatement("UPDATE marcas SET descripcion = ?, observacion = ? WHERE codigo_marca = ?");
			//eliminarMarca=conexion.getPoolConexion().getConnection().prepareStatement("DELETE FROM marcas WHERE codigo_marca = ?");
			//buscarMarca=conexion.getPoolConexion().getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where codigo_marca =  ?");
			//buscarMarcaObseracion=conexion.getPoolConexion().getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where observacion LIKE ? ;");
			///buscarMarcaNombre=conexion.getPoolConexion().getConnection().prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where descripcion LIKE ? ;");
		}
		catch ( SQLException excepcionSql )
		{
			excepcionSql.printStackTrace();
			System.exit( 1 );
		} // fin de catch*/
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eleminar marcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarMarca(int id){
		int resultado=0;
		Connection conn=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			eliminarMarca=conn.prepareStatement("DELETE FROM marcas WHERE codigo_marca = ?");
			eliminarMarca.setInt( 1, id );
			resultado=eliminarMarca.executeUpdate();
			return true;
			
		} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
		}
		finally
		{
			try{
				
				//if(res != null) res.close();
                if(eliminarMarca != null)eliminarMarca.close();
                if(conn != null) conn.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//Sconexion.desconectar();
				} // fin de catch
		} // fin de finally
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Actualizar los marcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean actualizarMarca(Marca myMarca){
		
		Connection conn=null;
		
		
		try {
				conn=conexion.getPoolConexion().getConnection();
				actualizarMarca=conn.prepareStatement("UPDATE marcas SET descripcion = ?, observacion = ? WHERE codigo_marca = ?");
				actualizarMarca.setString(1, myMarca.getMarca());
				actualizarMarca.setString(2, myMarca.getObservacion());
				
				actualizarMarca.setInt(3, myMarca.getId());
				
				
				actualizarMarca.executeUpdate();
				return true;
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
            
			
		}
		finally
		{
			try{
				
				//if(res != null) res.close();
                if(actualizarMarca != null)actualizarMarca.close();
                if(conn != null) conn.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//Sconexion.desconectar();
				} // fin de catch
		} // fin de finally
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< selecciona de la Bd todas las MArcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Marca> todoMarcas(){
		
		List<Marca> marcas=new ArrayList<Marca>();
		
		ResultSet res=null;
		
		Connection conn=null;
		
		boolean existe=false;
		
		try {
			
			
			conn=conexion.getPoolConexion().getConnection();
			seleccionarTodasLasMarcas = conn.prepareStatement("SELECT * FROM marcas");
			res = seleccionarTodasLasMarcas.executeQuery();
			while(res.next()){
				Marca unaMarca=new Marca();
				existe=true;
				unaMarca.setId(Integer.parseInt(res.getString("codigo_marca")));
				unaMarca.setMarca(res.getString("descripcion"));
				unaMarca.setObservacion(res.getString("observacion"));
				marcas.add(unaMarca);
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
                if(seleccionarTodasLasMarcas != null)seleccionarTodasLasMarcas.close();
                if(conn != null) conn.close();
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
			if (existe) {
				return marcas;
			}
			else return null;
			
		}
	
	
	public void setIdMarcaRegistrada(int i){
		idMarcaRegistrada=i;
	}
	public int getIdMarcaRegistrada(){
		return idMarcaRegistrada;
	} 
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar Marcas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarMarca(Marca myMarca)
	{
		
		int resultado=0;
		ResultSet res=null;
		Connection conn=null;
		
		try 
		{
			conn=conexion.getPoolConexion().getConnection();
			insertarNuevaMarca=conn.prepareStatement( "INSERT INTO marcas(descripcion,observacion) VALUES (?,?)");
			insertarNuevaMarca.setString( 1, myMarca.getMarca() );
			insertarNuevaMarca.setString( 2, myMarca.getObservacion() );
			
			resultado=insertarNuevaMarca.executeUpdate();
			
			res=insertarNuevaMarca.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(res.next()){
				this.setIdMarcaRegistrada(res.getInt(1));
			}
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
            return false;
		}
		finally
		{
			try{
				if(res != null) res.close();
                if(insertarNuevaMarca != null)insertarNuevaMarca.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar marca por ID>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Marca buscarMarca(int i){
		
		Marca unaMarca=new Marca();
		
		ResultSet res=null;
		
		boolean existe=false;
		
		Connection conn=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarMarca=conn.prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where codigo_marca =  ?");
			buscarMarca.setInt(1, i);
			res = buscarMarca.executeQuery();
			while(res.next()){
				existe=true;
				unaMarca.setId(Integer.parseInt(res.getString("codigo_marca")));
				unaMarca.setMarca(res.getString("descripcion"));
				unaMarca.setObservacion(res.getString("observacion"));
				
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
			finally
			{
				try{
					if(res != null) res.close();
	                if(buscarMarca != null)buscarMarca.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return unaMarca;
			}
			else return null;
		
		
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca los marcas por observacion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Marca> buscarMarcasObservacion(String busqueda){
		List<Marca> marcas=new ArrayList<Marca>();
		ResultSet res=null;
		 
		boolean existe=false;
		
		Connection conn=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarMarcaNombre=conn.prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where descripcion LIKE ? ;");
			buscarMarcaNombre.setString(1, "%" + busqueda + "%");
			res = buscarMarcaNombre.executeQuery();
			//System.out.println(buscarProveedorNombre);
			while(res.next()){
				Marca unaMarca=new Marca();
				existe=true;
				unaMarca.setId(Integer.parseInt(res.getString("codigo_marca")));
				unaMarca.setMarca(res.getString("descripcion"));
				unaMarca.setObservacion(res.getString("observacion"));
				marcas.add(unaMarca);
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}finally
			{
				try{
					if(res != null) res.close();
	                if(buscarMarcaNombre != null)buscarMarcaNombre.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return marcas;
			}
			else return null;
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca los marcas por observacion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Marca> buscarMarcas(String busqueda){
		List<Marca> marcas=new ArrayList<Marca>();
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		try {
			
			conn=conexion.getPoolConexion().getConnection();
			buscarMarcaObseracion=conn.prepareStatement("SELECT codigo_marca,descripcion,observacion FROM marcas where observacion LIKE ? ;");
			buscarMarcaObseracion.setString(1, "%" + busqueda + "%");
			res = buscarMarcaObseracion.executeQuery();
			//System.out.println(buscarProveedorNombre);
			while(res.next()){
				Marca unaMarca=new Marca();
				existe=true;
				unaMarca.setId(Integer.parseInt(res.getString("codigo_marca")));
				unaMarca.setMarca(res.getString("descripcion"));
				unaMarca.setObservacion(res.getString("observacion"));
				marcas.add(unaMarca);
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}finally
			{
				try{
					if(res != null) res.close();
	                if(buscarMarcaObseracion != null)buscarMarcaObseracion.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return marcas;
			}
			else return null;
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<cierra la conexión a la base de datos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public void close()
		{
			if(seleccionarTodasLasMarcas!=null)
				try {
					seleccionarTodasLasMarcas.close();
					if(insertarNuevaMarca!=null)insertarNuevaMarca.close();
					if(actualizarMarca!=null)actualizarMarca.close();
					if(eliminarMarca!=null)eliminarMarca.close();
					if(buscarMarca!=null)buscarMarca.close();
					if(buscarMarcaObseracion!=null)buscarMarcaObseracion.close();
					if(buscarMarcaNombre!=null)buscarMarcaNombre.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			//conexion.desconectar();
		} //// fin del método close
	

}
