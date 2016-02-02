package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Articulo;
import modelo.Conexion;
import modelo.Factura;
import modelo.Usuario;

/**

* Esta clase representa a un empleado de la empresa

* @author: jdmayorga

* @version: 16/07/2015

* @see <a href = "https://www.github.com/jdmayorga" /> direccion del creador </a>

*/
public class UsuarioDao {
	private int idRegistrado=-1;
	
	//private PreparedStatement getFecha=null;
	//private Connection conexionBD=null;
	private Conexion conexion;
	//private PreparedStatement agregarFactura=null;
	//private PreparedStatement seleccionarFacturasPendientes=null;
	//private PreparedStatement seleccionarFacturas=null;
	//private PreparedStatement elimiarTem = null;
	private PreparedStatement comprobarAdmin = null;
	private PreparedStatement nuevo = null;
	private PreparedStatement actualizar = null;
	private PreparedStatement consulta = null;
	
	
	//private DetalleFacturaDao detallesDao=null;
	
	public UsuarioDao(Conexion conn){
		conexion =conn;
	}
	
	public boolean comprobarAdmin(String pwd){
		
		boolean resultado=false;

		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="SELECT * FROM usuario where permiso='administrador' and clave=?";
        //Statement stmt = null;
       	List<Factura> facturas=new ArrayList<Factura>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			comprobarAdmin = con.prepareStatement(sql);
			comprobarAdmin.setString(1, pwd);
			
			res = comprobarAdmin.executeQuery();
			while(res.next()){
				resultado=true;
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
				resultado=false;
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(comprobarAdmin != null)comprobarAdmin.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
		
	
		
		return resultado;
	}
	
	public void setIdRegistrado(int i){
		idRegistrado=i;
	}
	public int getIdRegistrado(){
		return idRegistrado;
	} 
	
	/**

     * Metodo para conseguir todos los usuarios del sistema
     * 

     * @return lista de usuarios del sistema

     */
	
	public List<Usuario> todos(){
		
		List<Usuario> usuarios =new ArrayList<Usuario>();
		
		ResultSet res=null;
		
		Connection conn=null;
		
		boolean existe=false;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			consulta=conn.prepareStatement("SELECT * FROM usuario");
			res = consulta.executeQuery();
			while(res.next()){
				existe=true;
				Usuario un=new Usuario();
				un.setUser(res.getString("usuario"));
				un.setNombre(res.getString("nombre_completo"));
				un.setPwd(res.getString("clave"));
				un.setTipoPermiso(res.getInt("tipo_permiso"));
				un.setPermiso(res.getString("permiso"));
				usuarios.add(un);
				
				
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error, no se conecto");
			System.out.println(e);
	}
	finally
	{
		try{
			if(res != null) res.close();
	        if(consulta != null)consulta.close();
	        if(conn != null) conn.close();
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
	} // fin de finally
		
		
		if (existe) {
			return usuarios;
		}
		else return null;
		
	}
	
	/**

     * metodo para guardar el usuario
     * 
     * @param usuario que se guardara

     * @return falso o true segun el estado del accion

     */
	public boolean registrar(Usuario myUsuario)
	{
		
		int resultado=0;
		ResultSet rs=null;
		Connection con = null;
		
		try 
		{
			con = conexion.getPoolConexion().getConnection();
			
			nuevo=con.prepareStatement( "INSERT INTO usuario(usuario,nombre_completo,clave,permiso,tipo_permiso) VALUES (?,?,?,?,?)");
			
			nuevo.setString( 1, myUsuario.getUser() );
			nuevo.setString( 2, myUsuario.getNombre()+" "+myUsuario.getApellido() );
			nuevo.setString( 3, myUsuario.getPwd());
			nuevo.setString(4, myUsuario.getPermiso());
			nuevo.setInt(5,myUsuario.getTipoPermiso());
			
			resultado=nuevo.executeUpdate();
			
			rs=nuevo.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				this.setIdRegistrado(rs.getInt(1));
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
				if(rs!=null)rs.close();
				 if(nuevo != null)nuevo.close();
	              if(con != null) con.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	public boolean setLogin(Usuario user) {
		
		Usuario unUsuario=new Usuario();
		ResultSet res=null;
		PreparedStatement buscarUser=null;
		Connection conn=null;
		boolean existe=false;
		
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarUser=conn.prepareStatement("SELECT usuario, nombre_completo, clave, permiso,tipo_permiso FROM usuario WHERE usuario = ? AND clave = ?");
			buscarUser.setString(1, user.getUser());
			buscarUser.setString(2, user.getPwd());
			res = buscarUser.executeQuery();
			while(res.next()){
				existe=true;
				unUsuario.setNombre(res.getString("nombre_completo"));
				unUsuario.setUser(res.getString("usuario"));
				unUsuario.setPwd(res.getString("clave"));
				unUsuario.setPermiso(res.getString("permiso"));
				unUsuario.setTipoPermiso(res.getInt("tipo_permiso"));
				
				
				
			 }
			conexion.setUsuarioLogin(unUsuario);		
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				try{
					if(res != null) res.close();
	                if(buscarUser != null)buscarUser.close();
	                if(conn != null) conn.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
				excepcionSql.printStackTrace();
			//	conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			
		
		return existe;
			
		
		
		
	}
	
	/**

     * metodo para eliminar un usuario
     * 

     * @return falso o true segun el estado del accion

     */

	public boolean eliminarUsuario(String user) {
		// TODO Auto-generated method stub
		int resultado=0;
		Connection conn=null;
		
		try {
				conn=conexion.getPoolConexion().getConnection();
				
				consulta=conn.prepareStatement("DELETE FROM usuario WHERE usuario = ?");
				
				consulta.setString( 1, user );
				resultado=consulta.executeUpdate();
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		finally
		{
			try{
				if(consulta != null)consulta.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
			excepcionSql.printStackTrace();
			//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}

	public boolean actualizar(Usuario myUsuario) {
		int resultado;
		Connection conn=null;
		
		try {
			conn=conexion.getPoolConexion().getConnection();
			actualizar=conn.prepareStatement("UPDATE usuario SET usuario = ?, nombre_completo = ?,clave=? ,permiso = ?, tipo_permiso=? WHERE usuario = ?");
			//nuevo=con.prepareStatement( "INSERT INTO usuario(usuario,nombre_completo,clave,permiso,tipo_permiso) VALUES (?,?,?,?,?)");
			actualizar.setString( 1, myUsuario.getUser() );
			actualizar.setString( 2, myUsuario.getNombre()+" "+myUsuario.getApellido() );
			actualizar.setString( 3, myUsuario.getPwd());
			actualizar.setString(4, myUsuario.getPermiso());
			actualizar.setInt(5,myUsuario.getTipoPermiso());
			actualizar.setString( 6, myUsuario.getUserOld() );
			resultado=actualizar.executeUpdate();
			return true;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
        }
		finally
		{
			try{
				
				if(actualizar != null)actualizar.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
			excepcionSql.printStackTrace();
			//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
	}

	public List<Usuario> porNombre(String busqueda) {
		
		List<Usuario> usuarios =new ArrayList<Usuario>();
		
		ResultSet res=null;
		
		Connection conn=null;
		
		boolean existe=false;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			consulta=conn.prepareStatement("SELECT * FROM usuario where nombre_completo LIKE ? ;" );
			consulta.setString(1, "%" + busqueda + "%");
			res = consulta.executeQuery();
			while(res.next()){
				existe=true;
				Usuario un=new Usuario();
				un.setUser(res.getString("usuario"));
				un.setNombre(res.getString("nombre_completo"));
				un.setPwd(res.getString("clave"));
				un.setTipoPermiso(res.getInt("tipo_permiso"));
				un.setPermiso(res.getString("permiso"));
				usuarios.add(un);
				
				
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error, no se conecto");
			System.out.println(e);
	}
	finally
	{
		try{
			if(res != null) res.close();
	        if(consulta != null)consulta.close();
	        if(conn != null) conn.close();
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
	} // fin de finally
		
		
		if (existe) {
			return usuarios;
		}
		else return null;
		
	}

	public List<Usuario> porUser(String busqueda) {
		
		List<Usuario> usuarios =new ArrayList<Usuario>();
		
		ResultSet res=null;
		
		Connection conn=null;
		
		boolean existe=false;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			consulta=conn.prepareStatement("SELECT * FROM usuario where usuario LIKE ? ;" );
			consulta.setString(1, "%" + busqueda + "%");
			res = consulta.executeQuery();
			while(res.next()){
				existe=true;
				Usuario un=new Usuario();
				un.setUser(res.getString("usuario"));
				un.setNombre(res.getString("nombre_completo"));
				un.setPwd(res.getString("clave"));
				un.setTipoPermiso(res.getInt("tipo_permiso"));
				un.setPermiso(res.getString("permiso"));
				usuarios.add(un);
				
				
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error, no se conecto");
			System.out.println(e);
	}
	finally
	{
		try{
			if(res != null) res.close();
	        if(consulta != null)consulta.close();
	        if(conn != null) conn.close();
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
	} // fin de finally
		
		
		if (existe) {
			return usuarios;
		}
		else return null;
		
	}

}
