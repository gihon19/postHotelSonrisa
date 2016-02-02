package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import modelo.Departamento;
import modelo.Conexion;

public class DepartamentoDao {
	
	private Conexion conexion=null;
	private PreparedStatement buscarBodega=null;
	private PreparedStatement todos=null;
	public DepartamentoDao(Conexion conn){
		conexion=conn;
		/*try {
			buscarBodega=conexion.getConnection().prepareStatement("SELECT * FROM bodega where codigo_bodega=?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	
	
//	*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< todos los departamentos *////////////////
	public Vector<Departamento> todosExecto(int id){
		Vector<Departamento> depats=new Vector<Departamento>();
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		try {
			conn=conexion.getPoolConexion().getConnection();
			todos=conn.prepareStatement("SELECT * FROM bodega where bodega.codigo_bodega<>?");
			
			todos.setInt(1, id);
			
			
			
			res=todos.executeQuery();
			while(res.next()){
				Departamento unDept=new Departamento();
				existe=true;
				unDept.setId(res.getInt("codigo_bodega"));
				unDept.setDescripcion(res.getString("descripcion_bodega"));
				depats.add(unDept);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try{
				if(res!=null)res.close();
				if(todos!=null)todos.close();
				if(conn!=null)conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
			} // fin de catch
		} // fin de finally
		
		if(existe)
			return depats;
		else
			return null;
			
	}
	
//	*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< todos los departamentos *////////////////
	public Vector<Departamento> todos(){
		Vector<Departamento> depats=new Vector<Departamento>();
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		try {
			conn=conexion.getPoolConexion().getConnection();
			todos=conn.prepareStatement("SELECT * FROM bodega");
			
			
			
			res=todos.executeQuery();
			while(res.next()){
				Departamento unDept=new Departamento();
				existe=true;
				unDept.setId(res.getInt("codigo_bodega"));
				unDept.setDescripcion(res.getString("descripcion_bodega"));
				depats.add(unDept);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try{
				if(res!=null)res.close();
				if(todos!=null)todos.close();
				if(conn!=null)conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
			} // fin de catch
		} // fin de finally
		
		if(existe)
			return depats;
		else
			return null;
			
	}
	
	public Departamento buscarBodega(int id){
		Departamento myBodega=new Departamento();
		ResultSet res=null;
		Connection conn=null;
		boolean existe=false;
		try {
			conn=conexion.getPoolConexion().getConnection();
			buscarBodega=conn.prepareStatement("SELECT * FROM bodega where codigo_bodega=?");
			
			
			buscarBodega.setInt(1, id);
			res=buscarBodega.executeQuery();
			while(res.next()){
				existe=true;
				myBodega.setId(res.getInt("codigo_bodega"));
				myBodega.setDescripcion(res.getString("descripcion_bodega"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try{
				if(res!=null)res.close();
				if(buscarBodega!=null)buscarBodega.close();
				if(conn!=null)conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
			} // fin de catch
		} // fin de finally
		
		if(existe)
			return myBodega;
		else
			return null;
			
	}

}
