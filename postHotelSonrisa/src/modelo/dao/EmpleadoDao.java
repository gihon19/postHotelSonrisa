package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import modelo.Articulo;
import modelo.Conexion;
import modelo.Empleado;
import modelo.Usuario;

public class EmpleadoDao {
	
	private PreparedStatement seleccionarTodos=null;
	private Conexion conexion=null;
	private PreparedStatement buscar=null;
	private PreparedStatement accion=null;
	private int idRegistrado;

	public EmpleadoDao(Conexion conn) {
		// TODO Auto-generated constructor stub
		conexion=conn;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los articulos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Vector<Empleado> todoEmpleadosVendedores(){
		
		
		
        Connection con = null;
        
        
      
        Vector<Empleado> vendedores=new Vector<Empleado>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarTodos = con.prepareStatement("SELECT * FROM v_empleados WHERE v_empleados.codigo_tipo_empleado = 1");
			
			res = seleccionarTodos.executeQuery();
			while(res.next()){
				Empleado unEmpleado=new Empleado();
				existe=true;
				unEmpleado.setCodigo(res.getInt("codigo_empleado"));
				unEmpleado.setNombre(res.getString("nombre"));
				unEmpleado.setApellido(res.getString("apellido"));
				/*unArticulo.setId(Integer.parseInt(res.getString("codigo_articulo")));
				unArticulo.setArticulo(res.getString("articulo"));
				unArticulo.getMarcaObj().setMarca(res.getString("marca"));
				unArticulo.getMarcaObj().setId(res.getInt("codigo_marca"));
				unArticulo.getImpuestoObj().setPorcentaje(res.getString("impuesto"));
				unArticulo.getImpuestoObj().setId(res.getInt("codigo_impuesto"));
				unArticulo.setPrecioVenta(res.getDouble("precio_articulo"));
				unArticulo.setTipoArticulo(res.getInt("tipo_articulo"));*/
				
				
				vendedores.add(unEmpleado);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarTodos != null)seleccionarTodos.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return vendedores;
			}
			else return null;
		
	}
	
	public Empleado buscarPorId(int id) {
		// TODO Auto-generated method stub
		
		Empleado myEmpleado=new Empleado();
		
		
		String sql="SELECT "
				+ "codigo_empleado, "
				+ "nombre,"
				+ " apellido, "
				+ "telefono, "
				+ "correo, "
				+ "direccion,"
				+ "sueldo_base, "
				+ "codigo_tipo_empleado "
				+ "FROM empleados where codigo_empleado=?";
		ResultSet res=null;
		boolean existe=false;
		Connection con = null;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			buscar = con.prepareStatement(sql);
			buscar.setInt(1, id);
			
			res = buscar.executeQuery();
			while(res.next()){
				
				existe=true;
				myEmpleado.setCodigo(res.getInt("codigo_empleado"));
				myEmpleado.setNombre(res.getString("nombre"));
				myEmpleado.setApellido(res.getString("apellido"));
				myEmpleado.setTelefono(res.getString("telefono"));
				myEmpleado.setCorreo(res.getString("correo"));
				myEmpleado.setDireccion(res.getString("direccion"));
				myEmpleado.setSueldoBase(res.getBigDecimal("sueldo_base"));
				
				
			
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(buscar != null)buscar.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return myEmpleado;
			}
			else return null;
	}
	public void setIdRegistrado(int i){
		idRegistrado=i;
	}
	public int getIdRegistrado(){
		return idRegistrado;
	} 

	public boolean registrar(Empleado myEmpleado) {
		// TODO Auto-generated method stub
		int resultado=0;
		ResultSet rs=null;
		Connection con = null;
		
		try 
		{
			con = conexion.getPoolConexion().getConnection();
			
			accion=con.prepareStatement( "INSERT INTO empleados(nombre,apellido,telefono,correo) VALUES (?,?,?,?)");
			
			accion.setString(1, myEmpleado.getNombre());
			accion.setString(2, myEmpleado.getNombre());
			accion.setString(3, myEmpleado.getTelefono());
			accion.setString(4, myEmpleado.getCorreo());
			resultado=accion.executeUpdate();
			
			rs=accion.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				this.setIdRegistrado(rs.getInt(1));
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
				if(rs!=null)rs.close();
				 if(accion != null)accion.close();
	              if(con != null) con.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}//fin de registrar

	public List<Empleado> todos() {
		List<Empleado> empleados =new ArrayList<Empleado>();
		
		ResultSet res=null;
		
		Connection conn=null;
		
		boolean existe=false;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			accion=conn.prepareStatement("SELECT * FROM Empleados");
			res = accion.executeQuery();
			while(res.next()){
				existe=true;
				Empleado un=new Empleado();
				un.setCodigo(res.getInt("codigo_empleado"));
				un.setNombre(res.getString("nombre"));
				un.setApellido(res.getString("apellido"));
				un.setTelefono(res.getString("telefono"));
				un.setCorreo(res.getString("correo"));
				empleados.add(un);
				
				
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error, no se conecto");
			System.out.println(e);
	}
	finally
	{
		try{
			if(res != null) res.close();
	        if(accion != null)accion.close();
	        if(conn != null) conn.close();
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
	} // fin de finally
		
		
		if (existe) {
			return empleados;
		}
		else return null;
	}

	public boolean actualizar(Empleado myEmpleado) {
		int resultado;
		Connection conn=null;
		
		try {
			conn=conexion.getPoolConexion().getConnection();
			accion=conn.prepareStatement("UPDATE empleados SET nombre = ?, apellido = ?,telefono=? ,correo = ? WHERE codigo_empleado = ?");
			//nuevo=con.prepareStatement( "INSERT INTO usuario(usuario,nombre_completo,clave,permiso,tipo_permiso) VALUES (?,?,?,?,?)");
			accion.setString( 1, myEmpleado.getNombre() );
			accion.setString( 2, myEmpleado.getApellido() );
			accion.setString( 3, myEmpleado.getTelefono());
			accion.setString(4, myEmpleado.getCorreo());
			accion.setInt(5,myEmpleado.getCodigo());
			resultado=accion.executeUpdate();
			return true;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
        }
		finally
		{
			try{
				
				if(accion != null)accion.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
			excepcionSql.printStackTrace();
			//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
	}

	public List<Empleado> porNombre(String busqueda) {
		List<Empleado> empleados =new ArrayList<Empleado>();
		
		ResultSet res=null;
		
		Connection conn=null;
		
		boolean existe=false;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			accion=conn.prepareStatement("SELECT * FROM Empleados where nombre LIKE ? ;");
			accion.setString(1, "%" + busqueda + "%");
			res = accion.executeQuery();
			while(res.next()){
				existe=true;
				Empleado un=new Empleado();
				un.setCodigo(res.getInt("codigo_empleado"));
				un.setNombre(res.getString("nombre"));
				un.setApellido(res.getString("apellido"));
				un.setTelefono(res.getString("telefono"));
				un.setCorreo(res.getString("correo"));
				empleados.add(un);
				
				
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error, no se conecto");
			System.out.println(e);
	}
	finally
	{
		try{
			if(res != null) res.close();
	        if(accion != null)accion.close();
	        if(conn != null) conn.close();
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
	} // fin de finally
		
		
		if (existe) {
			return empleados;
		}
		else return null;
	}

	public List<Empleado> porApellido(String busqueda) {
		List<Empleado> empleados =new ArrayList<Empleado>();
		
		ResultSet res=null;
		
		Connection conn=null;
		
		boolean existe=false;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			accion=conn.prepareStatement("SELECT * FROM Empleados where apellido LIKE ? ;");
			accion.setString(1, "%" + busqueda + "%");
			res = accion.executeQuery();
			while(res.next()){
				existe=true;
				Empleado un=new Empleado();
				un.setCodigo(res.getInt("codigo_empleado"));
				un.setNombre(res.getString("nombre"));
				un.setApellido(res.getString("apellido"));
				un.setTelefono(res.getString("telefono"));
				un.setCorreo(res.getString("correo"));
				empleados.add(un);
				
				
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error, no se conecto");
			System.out.println(e);
	}
	finally
	{
		try{
			if(res != null) res.close();
	        if(accion != null)accion.close();
	        if(conn != null) conn.close();
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
	} // fin de finally
		
		
		if (existe) {
			return empleados;
		}
		else return null;
	}

}
