package modelo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.Proveedor;

public class ProveedorDao {
	private int idProveedorRegistrado;
	public Conexion conexion;
	private Proveedor myProveedor;
	
	private PreparedStatement insertarNuevoProveedor = null;
	private PreparedStatement seleccionarTodasLosProveedores = null;
	private PreparedStatement elimiarProveedor = null;
	private PreparedStatement actualizarProveedor = null;
	private PreparedStatement buscarProveedor = null;
	private PreparedStatement buscarProveedorNombre = null;
	private PreparedStatement buscarProveedorDireccion = null;
	
	public ProveedorDao(Conexion conn){
		
		conexion=conn;
		myProveedor=new Proveedor();
		/*try {
			
			//conexion= new Conexion();
			
		
		
			//seleccionarTodasLosProveedores = conexion.getConnection().prepareStatement( "SELECT * FROM proveedor;");
			//insertarNuevoProveedor=conexion.getConnection().prepareStatement( "INSERT INTO proveedor(nombre_proveedor,telefono,celular,direccion) VALUES (?,?,?,?)");
			//elimiarProveedor=conexion.getConnection().prepareStatement("DELETE FROM proveedor WHERE codigo_proveedor = ?");
			//buscarProveedor=conexion.getConnection().prepareStatement("SELECT codigo_proveedor,nombre_proveedor,telefono,celular,direccion FROM proveedor where codigo_proveedor =  ?");
			//buscarProveedorNombre=conexion.getConnection().prepareStatement("SELECT * FROM proveedor where nombre_proveedor LIKE ? ;");
			//buscarProveedorDireccion=conexion.getConnection().prepareStatement("SELECT * FROM proveedor where direccion LIKE ? ;");
			//actualizarProveedor=conexion.getConnection().prepareStatement("UPDATE proveedor SET nombre_proveedor = ?, telefono =?, celular = ?, direccion = ?  WHERE codigo_proveedor = ?");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/*/
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agregar un proveedores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarProveedor(Proveedor myProveedor)
	{
		
		int resultado=0;
		ResultSet res=null;
		Connection conn=null;
		try 
		{
			conn=conexion.getPoolConexion().getConnection();
			insertarNuevoProveedor=conn.prepareStatement( "INSERT INTO proveedor(nombre_proveedor,telefono,celular,direccion) VALUES (?,?,?,?)");
			insertarNuevoProveedor.setString( 1, myProveedor.getNombre() );
			insertarNuevoProveedor.setString( 2, myProveedor.getTelefono() );
			insertarNuevoProveedor.setString( 3, myProveedor.getCelular() );
			insertarNuevoProveedor.setString( 4, myProveedor.getDireccion() );
			
			resultado=insertarNuevoProveedor.executeUpdate();
			res=insertarNuevoProveedor.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(res.next()){
				this.setIdProveedorRegistrado(res.getInt(1));
				//int clave=rs.getInt(1);
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
                if(insertarNuevoProveedor != null)insertarNuevoProveedor.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para el atributo de ID>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public int getIdProveedorRegistrado(){
		return idProveedorRegistrado;
	}
	
	public void setIdProveedorRegistrado(int id){
		idProveedorRegistrado=id;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Actualizar los proveedores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean actualizarProveedor(Proveedor myProveedor){
		
		Connection conn=null;
		
		
		try {
				conn=conexion.getPoolConexion().getConnection();
				actualizarProveedor=conn.prepareStatement("UPDATE proveedor SET nombre_proveedor = ?, telefono =?, celular = ?, direccion = ?  WHERE codigo_proveedor = ?");
				actualizarProveedor.setString(1, myProveedor.getNombre());
				actualizarProveedor.setString(2, myProveedor.getTelefono());
				actualizarProveedor.setString(3, myProveedor.getCelular());
				actualizarProveedor.setString(4, myProveedor.getDireccion());
				actualizarProveedor.setInt(5, myProveedor.getId());
				
				actualizarProveedor.executeUpdate();
				/*Statement estatuto = conexion.getConnection().createStatement();
				estatuto.executeUpdate("UPDATE proveedor SET nombre_proveedor ='"+myProveedor.getNombre()+"', telefono ='"
					+myProveedor.getTelefono()+"', celular = '"+myProveedor.getCelular()+"', direccion = '"+myProveedor.getDireccion()+"'  WHERE codigo_proveedor ="+myProveedor.getId());
				estatuto.close();
				conexion.desconectar();*/
				return true;
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
            
			
		}
		finally
		{
			try{
				//if(res != null) res.close();
                if(actualizarProveedor != null)actualizarProveedor.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
	}


	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Eliminar los proveedores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarProveedor(int id){
		
		int resultado=0;
		Connection conn=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			elimiarProveedor=conn.prepareStatement("DELETE FROM proveedor WHERE codigo_proveedor = ?");
				elimiarProveedor.setInt( 1, id );
				resultado=elimiarProveedor.executeUpdate();
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		finally
		{
			try{
				//if(res != null) res.close();
                if(elimiarProveedor != null)elimiarProveedor.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar proveedor por ID>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Proveedor buscarPro(int i){
		//Conexion conex= new Conexion();
		Proveedor unoPro=new Proveedor();
		ResultSet res=null;
		boolean existe=false;
		
		Connection conn=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarProveedor=conn.prepareStatement("SELECT codigo_proveedor,nombre_proveedor,telefono,celular,direccion FROM proveedor where codigo_proveedor =  ?");
			buscarProveedor.setInt(1, i);
			res = buscarProveedor.executeQuery();
			while(res.next()){
				existe=true;
				unoPro.setId(Integer.parseInt(res.getString("codigo_proveedor")));
				unoPro.setNombre(res.getString("nombre_proveedor"));
				unoPro.setTelefono(res.getString("telefono"));
				unoPro.setCelular(res.getString("celular"));
				unoPro.setDireccion(res.getString("direccion"));
				
			 }
					
					
			} catch (SQLException e) {
					//JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
			finally
			{
				try{
					if(res != null) res.close();
	                if(buscarProveedor != null)buscarProveedor.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
				//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return unoPro;
			}
			else return null;
		
		
		
	}

	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca los proveedores por nombre>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Proveedor> buscarProveedorNombre(String busqueda){
		List<Proveedor> proveedores=new ArrayList<Proveedor>();
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		try {
			
			conn=conexion.getPoolConexion().getConnection();
			buscarProveedorNombre=conn.prepareStatement("SELECT * FROM proveedor where nombre_proveedor LIKE ? ;");
			buscarProveedorNombre.setString(1, "%" + busqueda + "%");
			res = buscarProveedorNombre.executeQuery();
			//System.out.println(buscarProveedorNombre);
			while(res.next()){
				Proveedor unoPro=new Proveedor();
				existe=true;
				unoPro.setId(Integer.parseInt(res.getString("codigo_proveedor")));
				unoPro.setNombre(res.getString("nombre_proveedor"));
				unoPro.setTelefono(res.getString("telefono"));
				unoPro.setCelular(res.getString("celular"));
				unoPro.setDireccion(res.getString("direccion"));
				proveedores.add(unoPro);
				//System.out.println(unoPro);
			 }
					
					
			} catch (SQLException e) {
					//JOptionPane.showMessageDialog(null, "Error, no se conecto");
				e.printStackTrace();
			}finally
			{
				try{
					if(res != null) res.close();
	                if(buscarProveedorNombre != null)buscarProveedorNombre.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return proveedores;
			}
			else return null;
		
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar los proveedores por direccion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Proveedor> buscarProveedorDireccion(String busqueda){
		List<Proveedor> proveedores=new ArrayList<Proveedor>();
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		try {
			
			conn=conexion.getPoolConexion().getConnection();
			buscarProveedorDireccion=conn.prepareStatement("SELECT * FROM proveedor where direccion LIKE ? ;");
			
		
			buscarProveedorDireccion.setString(1, "%" + busqueda + "%");
			res = buscarProveedorDireccion.executeQuery();
			while(res.next()){
				Proveedor unoPro=new Proveedor();
				existe=true;
				unoPro.setId(Integer.parseInt(res.getString("codigo_proveedor")));
				unoPro.setNombre(res.getString("nombre_proveedor"));
				unoPro.setTelefono(res.getString("telefono"));
				unoPro.setCelular(res.getString("celular"));
				unoPro.setDireccion(res.getString("direccion"));
				proveedores.add(unoPro);
				//System.out.println(unoPro);
			 }
					
					
			} catch (SQLException e) {
				e.printStackTrace();
			}finally
			{
				try{
					if(res != null) res.close();
	                if(buscarProveedorDireccion != null)buscarProveedorDireccion.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return proveedores;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca todos los proveedores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/	
	public List<Proveedor> todoProveedor(){
		List<Proveedor> proveedores=new ArrayList<Proveedor>();
		
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		
		try {
			
			conn=conexion.getPoolConexion().getConnection();
			seleccionarTodasLosProveedores = conn.prepareStatement( "SELECT * FROM proveedor;");
			res = seleccionarTodasLosProveedores.executeQuery();
			while(res.next()){
				Proveedor unoPro=new Proveedor();
				existe=true;
				unoPro.setId(Integer.parseInt(res.getString("codigo_proveedor")));
				unoPro.setNombre(res.getString("nombre_proveedor"));
				unoPro.setTelefono(res.getString("telefono"));
				unoPro.setCelular(res.getString("celular"));
				unoPro.setDireccion(res.getString("direccion"));
				proveedores.add(unoPro);
			 }					
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				try{
					if(res != null) res.close();
	                if(seleccionarTodasLosProveedores != null)seleccionarTodasLosProveedores.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return proveedores;
			}
			else return null;
		
		
		//return  proveedores;
	}
	
	public ProveedorDao getProveedorDao(){
		return this;
	}
	
/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<cierra la conexi�n a la base de datos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	/*public void close()
		{
			conexion.desconectar();
		} //// fin del m�todo close*/

}
