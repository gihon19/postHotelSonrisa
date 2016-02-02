package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.Conexion;

public class ClienteDao {
	
	private int idClienteRegistrado;
	
	private Connection conexionBD=null;
	private Conexion conexion;
	private PreparedStatement insertarNuevaCliente=null;
	private PreparedStatement seleccionarTodasLosClientes=null;
	private PreparedStatement buscarClienteID=null;
	private PreparedStatement buscarClienteNombre=null;
	private PreparedStatement actualizarCliente=null;
	private PreparedStatement eliminarCliente=null;
	
	
	public ClienteDao(Conexion conn){
		conexion=conn;
		
	}
	
	public void setIdClienteRegistrado(int i){
		idClienteRegistrado=i;
	}
	public int getIdClienteRegistrado(){
		return idClienteRegistrado;
	} 
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los clientes>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Cliente> todoClientes(){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection conn = null;
        
        
        //Statement stmt = null;
       	List<Cliente> clientes=new ArrayList<Cliente>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			conn = conexion.getPoolConexion().getConnection();
			
			seleccionarTodasLosClientes = conn.prepareStatement("SELECT * FROM v_clientes;");
			
			res = seleccionarTodasLosClientes.executeQuery();
			while(res.next()){
				Cliente unCliente=new Cliente();
				existe=true;
				unCliente.setId(res.getInt("codigo_cliente"));
				unCliente.setNombre(res.getString("nombre_cliente"));
				unCliente.setDireccion(res.getString("direccion"));
				unCliente.setTelefono(res.getString("telefono"));
				unCliente.setCelular(res.getString("movil"));
				unCliente.setRtn(res.getString("rtn"));
				unCliente.setLimiteCredito(res.getBigDecimal("limite_credito"));
				unCliente.setSaldoCuenta(res.getBigDecimal("saldo"));
				
				clientes.add(unCliente);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarTodasLosClientes != null)seleccionarTodasLosClientes.close();
                if(conn != null) conn.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//Sconexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return clientes;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca los cliente  por rtn>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Cliente> buscarClienteRtn(String busqueda){
		List<Cliente> clientes=new ArrayList<Cliente>();
		
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarClienteNombre=conn.prepareStatement("SELECT * FROM v_clientes where rtn LIKE ? ;");
		
			buscarClienteNombre.setString(1, "%" + busqueda + "%");
			res = buscarClienteNombre.executeQuery();
			//System.out.println(buscarProveedorNombre);
			while(res.next()){
				Cliente unCliente=new Cliente();
				existe=true;
				unCliente.setId(res.getInt("codigo_cliente"));
				unCliente.setNombre(res.getString("nombre_cliente"));
				unCliente.setDireccion(res.getString("direccion"));
				unCliente.setTelefono(res.getString("telefono"));
				unCliente.setCelular(res.getString("movil"));
				unCliente.setRtn(res.getString("rtn"));	
				unCliente.setLimiteCredito(res.getBigDecimal("limite_credito"));
				unCliente.setSaldoCuenta(res.getBigDecimal("saldo"));
				
				clientes.add(unCliente);
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}finally
			{
				try{
					if(res!=null)res.close();
					if(conn!=null)conn.close();
					if(buscarClienteNombre!=null)buscarClienteNombre.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return clientes;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para busca los cliente  por nombre>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Cliente> buscarCliente(String busqueda){
		List<Cliente> clientes=new ArrayList<Cliente>();
		
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarClienteNombre=conn.prepareStatement("SELECT * FROM v_clientes where nombre_cliente LIKE ? ;");
		
			buscarClienteNombre.setString(1, "%" + busqueda + "%");
			res = buscarClienteNombre.executeQuery();
			//System.out.println(buscarProveedorNombre);
			while(res.next()){
				Cliente unCliente=new Cliente();
				existe=true;
				unCliente.setId(res.getInt("codigo_cliente"));
				unCliente.setNombre(res.getString("nombre_cliente"));
				unCliente.setDireccion(res.getString("direccion"));
				unCliente.setTelefono(res.getString("telefono"));
				unCliente.setCelular(res.getString("movil"));
				unCliente.setRtn(res.getString("rtn"));	
				unCliente.setLimiteCredito(res.getBigDecimal("limite_credito"));
				unCliente.setSaldoCuenta(res.getBigDecimal("saldo"));
				
				clientes.add(unCliente);
			 }
					
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}finally
			{
				try{
					if(res!=null)res.close();
					if(conn!=null)conn.close();
					if(buscarClienteNombre!=null)buscarClienteNombre.close();
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
			} // fin de finally
		
			if (existe) {
				return clientes;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para buscar clientes por id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Cliente buscarCliente(int id){
		Cliente myCliente=new Cliente();
		//se crear un referencia al pool de conexiones
		
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
       
		
		ResultSet res=null;
		
		boolean existe=false;
		
		
		try {
			con = conexion.getPoolConexion().getConnection();
			
			buscarClienteID=con.prepareStatement("SELECT * FROM v_clientes where codigo_cliente=?");
			
			buscarClienteID.setInt(1, id);
			res=buscarClienteID.executeQuery();
			while(res.next()){
				myCliente.setId(res.getInt("codigo_cliente"));
				myCliente.setNombre(res.getString("nombre_cliente"));
				myCliente.setTelefono(res.getString("telefono"));
				myCliente.setCelular(res.getString("movil"));
				myCliente.setRtn(res.getString("rtn"));
				myCliente.setLimiteCredito(res.getBigDecimal("limite_credito"));
				myCliente.setSaldoCuenta(res.getBigDecimal("saldo"));
				existe=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			
			if(res != null) res.close();
            if(buscarClienteID != null)buscarClienteID.close();
            if(con != null) con.close();
            
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();

			} // fin de catch
		
		if(existe){
				return myCliente;
		}
		else
			return null;
		
	
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para eliminar un cliente>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean eliminarCliente(int id){
		int resultado=0;
		Connection conn=null;
		
		try {
				conn=conexion.getPoolConexion().getConnection();
				
				eliminarCliente=conn.prepareStatement("DELETE FROM cliente WHERE codigo_cliente = ?");
				
				eliminarCliente.setInt( 1, id );
				resultado=eliminarCliente.executeUpdate();
				
				return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		finally
		{
			try{
				if(eliminarCliente != null)eliminarCliente.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
			excepcionSql.printStackTrace();
			//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Actualizar cliente>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean actualizarCliente(Cliente cliente){
		int resultado;
		
		Connection conn=null;
		
		try {
			conn=conexion.getPoolConexion().getConnection();
			actualizarCliente=conn.prepareStatement("UPDATE cliente SET nombre_cliente = ?, direccion = ? ,telefono = ?, movil=?, rtn=? WHERE codigo_cliente = ?");
			actualizarCliente.setString(1,cliente.getNombre());
			actualizarCliente.setString(2, cliente.getDereccion());
			actualizarCliente.setString(3, cliente.getTelefono());
			actualizarCliente.setString(4, cliente.getCelular());
			actualizarCliente.setString(5,cliente.getRtn());
			actualizarCliente.setInt(6,cliente.getId());
			
			resultado=actualizarCliente.executeUpdate();
			//JOptionPane.showMessageDialog(null, a+","+resultado );
			
			
			return true;
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
        }
		finally
		{
			try{
				
				if(actualizarCliente != null)actualizarCliente.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
			excepcionSql.printStackTrace();
			//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar Articulo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarCliente(Cliente myCliente)
	{
		//JOptionPane.showConfirmDialog(null, myCliente);
		int resultado=0;
		ResultSet rs=null;
		Connection con = null;
		
		try 
		{
			con = conexion.getPoolConexion().getConnection();
			
			insertarNuevaCliente=con.prepareStatement( "INSERT INTO cliente(nombre_cliente,direccion,telefono,movil,rtn) VALUES (?,?,?,?,?)");
			
			insertarNuevaCliente.setString( 1, myCliente.getNombre() );
			insertarNuevaCliente.setString( 2, myCliente.getDereccion() );
			insertarNuevaCliente.setString( 3, myCliente.getTelefono());
			insertarNuevaCliente.setString(4, myCliente.getCelular());
			insertarNuevaCliente.setString(5, myCliente.getRtn());
			
			resultado=insertarNuevaCliente.executeUpdate();
			
			rs=insertarNuevaCliente.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				this.setIdClienteRegistrado(rs.getInt(1));
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
				 if(insertarNuevaCliente != null)insertarNuevaCliente.close();
	              if(con != null) con.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	
	public void desconectarBD(){
		 try {
			 if(buscarClienteID != null)buscarClienteID.close();
			 if(conexionBD != null) conexionBD.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
