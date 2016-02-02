package modelo.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Cliente;
import modelo.Conexion;
import modelo.CuentaPorCobrar;
import modelo.Factura;
import modelo.ReciboPago;

public class CuentaPorCobrarDao {
	
	private Conexion conexion;
	private PreparedStatement insertar=null;
	private PreparedStatement seleccionar=null;
	private PreparedStatement buscar=null;
	private PreparedStatement actualizar=null;
	private PreparedStatement eliminar=null;

	public CuentaPorCobrarDao(Conexion conn) {
		conexion=conn;
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<      se obtiene el ultimo registro        >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public CuentaPorCobrar getSaldoCliente(Cliente myCliente){
		CuentaPorCobrar un=new CuentaPorCobrar();
		
		Connection con = null;
        
    	//String sql="select * from cierre where usuario = ?";
    	
    	String sql2="SELECT * FROM cuentas_por_cobrar WHERE cuentas_por_cobrar.codigo_cliente = ? ORDER BY cuentas_por_cobrar.codigo_reguistro DESC LIMIT 1";
ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			seleccionar = con.prepareStatement(sql2);
			
			seleccionar.setInt(1, myCliente.getId());
			res = seleccionar.executeQuery();
			while(res.next()){
				
				existe=true;
				un.setNoReguistro(res.getInt("codigo_reguistro"));
				un.setCliente(myCliente);
				un.setDescripcion(res.getString("descripcion"));
				un.setFecha(res.getString("fecha"));
				un.setCredito(res.getBigDecimal("credito"));
				un.setDebito(res.getBigDecimal("debito"));
				un.setSaldo(res.getBigDecimal("saldo"));
			
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionar != null)seleccionar.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		return un;
	}
	
	
	
	public boolean reguistrarDebito(ReciboPago myRecibo) {
		// TODO Auto-generated method stub
		CuentaPorCobrar aRegistrar=new CuentaPorCobrar();
		CuentaPorCobrar ultima=this.getSaldoCliente(myRecibo.getCliente());
		
		
		aRegistrar.setCliente(myRecibo.getCliente());
		aRegistrar.setDescripcion(myRecibo.getConcepto());
		//aRegistrar.setCredito(myFactura.getTotal());
		aRegistrar.setDebito(myRecibo.getTotal());//.setCredito(myFactura.getTotal());
		BigDecimal newSaldo=ultima.getSaldo().subtract(myRecibo.getTotal());//.add(myFactura.getTotal());
		aRegistrar.setSaldo(newSaldo);
		
		//JOptionPane.showConfirmDialog(null, myCliente);
				int resultado=0;
				ResultSet rs=null;
				Connection con = null;
				
				try 
				{
					con = conexion.getPoolConexion().getConnection();
					
					insertar=con.prepareStatement( "INSERT INTO cuentas_por_cobrar(fecha,codigo_cliente,descripcion,debito,saldo) VALUES (now(),?,?,?,?)");
					insertar.setInt(1, aRegistrar.getCliente().getId());
					insertar.setString(2, aRegistrar.getDescripcion());
					insertar.setBigDecimal(3, aRegistrar.getDebito());
					insertar.setBigDecimal(4, aRegistrar.getSaldo());
					resultado=insertar.executeUpdate();
					/*insertarNuevaCliente.setString( 1, myCliente.getNombre() );
					insertarNuevaCliente.setString( 2, myCliente.getDereccion() );
					insertarNuevaCliente.setString( 3, myCliente.getTelefono());
					insertarNuevaCliente.setString(4, myCliente.getCelular());
					insertarNuevaCliente.setString(5, myCliente.getRtn());
					
					
					
					rs=insertarNuevaCliente.getGeneratedKeys(); //obtengo las ultimas llaves generadas
					while(rs.next()){
						this.setIdClienteRegistrado(rs.getInt(1));
					}
					*/
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
						 if(insertar != null)insertar.close();
			              if(con != null) con.close();
					} // fin de try
					catch ( SQLException excepcionSql )
					{
						excepcionSql.printStackTrace();
						//conexion.desconectar();
					} // fin de catch
				} // fin de finally
		
		//return false;
	}
	
	public boolean reguistrarCredito(Factura myFactura) {
		// TODO Auto-generated method stub
		CuentaPorCobrar aRegistrar=new CuentaPorCobrar();
		CuentaPorCobrar ultima=this.getSaldoCliente(myFactura.getCliente());
		
		
		aRegistrar.setCliente(myFactura.getCliente());
		aRegistrar.setDescripcion("venta segun factura no. "+myFactura.getIdFactura());
		aRegistrar.setCredito(myFactura.getTotal());
		BigDecimal newSaldo=ultima.getSaldo().add(myFactura.getTotal());
		aRegistrar.setSaldo(newSaldo);
		
		//JOptionPane.showConfirmDialog(null, myCliente);
				int resultado=0;
				ResultSet rs=null;
				Connection con = null;
				
				try 
				{
					con = conexion.getPoolConexion().getConnection();
					
					insertar=con.prepareStatement( "INSERT INTO cuentas_por_cobrar(fecha,codigo_cliente,descripcion,credito,saldo) VALUES (now(),?,?,?,?)");
					insertar.setInt(1, aRegistrar.getCliente().getId());
					insertar.setString(2, aRegistrar.getDescripcion());
					insertar.setBigDecimal(3, aRegistrar.getCredito());
					insertar.setBigDecimal(4, aRegistrar.getSaldo());
					resultado=insertar.executeUpdate();
					
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
						 if(insertar != null)insertar.close();
			              if(con != null) con.close();
					} // fin de try
					catch ( SQLException excepcionSql )
					{
						excepcionSql.printStackTrace();
						//conexion.desconectar();
					} // fin de catch
				} // fin de finally
		
		//return false;
	}

}
