package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Articulo;
import modelo.Cliente;
import modelo.Conexion;
import modelo.Factura;
import modelo.Requisicion;

public class RequisicionDao {
	
	private Conexion conexion=null;
	private PreparedStatement getFecha=null;
	private PreparedStatement buscarArticulo=null;
	private PreparedStatement insertarNuevoMovimiento=null;
	private PreparedStatement actualizar=null;
	private PreparedStatement insertarNueva=null;
	private DetalleFacturaDao detallesDao=null;
	private PreparedStatement selectTodas=null;

	public RequisicionDao(Conexion conn) {
		conexion=conn;
		detallesDao=new DetalleFacturaDao(conexion);
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para conseguir la fecha>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public String getFechaSistema(){
		String fecha="";
		//DataSource ds=DBCPDataSourceFactory.getDataSource("mysql");
		Connection conn=null;
		ResultSet res=null;
		//Statement instrucions=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			getFecha = conn.prepareStatement("SELECT DATE_FORMAT(now(), '%d/%m/%Y') as fecha;");
			
			//res=getFecha.executeQuery(" SELECT DATE_FORMAT(now(), '%d/%m/%Y %h:%i:%s %p') as fecha;");
			res=getFecha.executeQuery();
			while(res.next()){
				fecha=res.getString("fecha");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				
				if(res != null) res.close();
	            if(getFecha != null)getFecha.close();
	            if(conn != null) conn.close();
	            
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
				} // fin de catch
		}
		
		
		return fecha;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar requisicion>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrar(Requisicion myRequisicion)
	{
		
		int resultado=0;
		ResultSet rs=null;
		Connection con = null;
		
		try 
		{
			con = conexion.getPoolConexion().getConnection();
			
			insertarNueva=con.prepareStatement( "INSERT INTO encabezado_requisicion(fecha,total,codigo_depart_origen,codigo_depart_destino,usuario,estado_requisicion) VALUES (now(),?,?,?,?,?)");
			
			insertarNueva.setBigDecimal( 1, myRequisicion.getTotal() );
			insertarNueva.setInt( 2, myRequisicion.getDepartamentoOrigen().getId());
			insertarNueva.setInt( 3, myRequisicion.getDepartamentoDestino().getId());
			insertarNueva.setString(4, conexion.getUsuarioLogin().getUser());
			insertarNueva.setString(5, "ACT");
			
			//myRequisicion.get
			resultado=insertarNueva.executeUpdate();
			
			rs=insertarNueva.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			
			int idRequiReguistrado=0;
			while(rs.next()){
				//this.setIdArticuloRegistrado(rs.getInt(1));
				idRequiReguistrado=rs.getInt(1);
			}
			
			//se guardan los detalles de la fatura
			for(int x=0;x<myRequisicion.getDetalles().size();x++){
				
				if(myRequisicion.getDetalles().get(x).getArticulo().getId()!=-1)
					detallesDao.agregarDetalleRequi(myRequisicion.getDetalles().get(x), idRequiReguistrado);
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
				 if(insertarNueva != null)insertarNueva.close();
	              if(con != null) con.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para extraer todas las requisiciones de un rango de fechas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
public List<Requisicion> requiPorFechas(String fecha1, String fecha2) {
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="SELECT *"
			   + "FROM v_requisiciones where "
			   + "DATE_FORMAT(v_requisiciones.fecha2, '%d/%m/%Y')>=? "
			   + "and "
			   + "DATE_FORMAT(v_requisiciones.fecha2, '%d/%m/%Y')<=?";
        //Statement stmt = null;
    	List<Requisicion> requisiciones=new ArrayList<Requisicion>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			selectTodas = con.prepareStatement(sql);
			
			selectTodas.setString(1, fecha1);
			selectTodas.setString(2, fecha2);
			
			res = selectTodas.executeQuery();
			while(res.next()){
				Requisicion una=new Requisicion();
				existe=true;
				
				
				una.setNoRequisicion(res.getInt("codigo_requisicion"));
				una.setFechaCompra(res.getString("fecha"));
				una.getDepartamentoOrigen().setDescripcion(res.getString("origen"));
				una.getDepartamentoDestino().setDescripcion(res.getString("destino"));
				una.setTotal(res.getBigDecimal("total"));
				una.setEstado(res.getString("estado_requisicion"));
				
				
				requisiciones.add(una);
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(selectTodas != null)selectTodas.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return requisiciones;
			}
			else return null;
		
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para extraer todas las requisiciones>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Requisicion> todas() {
		Connection con = null;
        
        
	      
       	List<Requisicion> requisiciones=new ArrayList<Requisicion>();
		
		ResultSet res=null;
		
		boolean existe=false;
		
		
		try {
			con = conexion.getPoolConexion().getConnection();
			
			selectTodas = con.prepareStatement("SELECT * FROM v_requisiciones;");
			
			res = selectTodas.executeQuery();
			while(res.next()){
				Requisicion una=new Requisicion();
				existe=true;
				
				
				una.setNoRequisicion(res.getInt("codigo_requisicion"));
				una.setFechaCompra(res.getString("fecha"));
				una.getDepartamentoOrigen().setDescripcion(res.getString("origen"));
				una.getDepartamentoDestino().setDescripcion(res.getString("destino"));
				una.setTotal(res.getBigDecimal("total"));
				una.setEstado(res.getString("estado_requisicion"));
				
				
							
				
				requisiciones.add(una);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(selectTodas != null)selectTodas.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
		if (existe) {
			return requisiciones;
		}
		else return null;
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para extraer una requi por id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Requisicion requiPorId(int id) {
		// TODO Auto-generated method stub
		Requisicion una=new Requisicion();
		Connection con = null;
		ResultSet res=null;
		
		boolean existe=false;
		
		try {
			con = conexion.getPoolConexion().getConnection();
			
			selectTodas = con.prepareStatement("SELECT * FROM v_requisiciones where v_requisiciones.codigo_requisicion=?;");
			selectTodas.setInt(1, id);
			
			res = selectTodas.executeQuery();
			while(res.next()){
				existe=true;
				una.setNoRequisicion(res.getInt("codigo_requisicion"));
				una.setFechaCompra(res.getString("fecha"));
				una.getDepartamentoOrigen().setDescripcion(res.getString("origen"));
				una.getDepartamentoDestino().setDescripcion(res.getString("destino"));
				una.setTotal(res.getBigDecimal("total"));
				una.setEstado(res.getString("estado_requisicion"));
				
				
				
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(selectTodas != null)selectTodas.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
		if (existe) {
			return una;
		}
		else return null;
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para anular requisiones>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean anularRequi(Requisicion r) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		Connection conn=null;
		String sql="UPDATE encabezado_requisicion SET "
				
				
				+ "estado_requisicion=?"
				
				+ " WHERE codigo_requisicion = ?";
		try {
			conn=conexion.getPoolConexion().getConnection();
			
			actualizar=conn.prepareStatement(sql);
			
			
			actualizar.setString(1, "NULA");
			
			actualizar.setInt(2, r.getNoRequisicion());
			actualizar.executeUpdate();
						
			
			resultado= true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultado=false;
		}
		return resultado;
	}

}
