package modelo.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.Conexion;
import modelo.Factura;
import modelo.NumberToLetterConverter;

public class FacturaDao {
	
	private PreparedStatement getFecha=null;
	private Connection conexionBD=null;
	private Conexion conexion;
	private PreparedStatement agregarFactura=null;
	private PreparedStatement seleccionarFacturasPendientes=null;
	private PreparedStatement seleccionarFacturas=null;
	private PreparedStatement elimiarTem = null;
	private PreparedStatement actualizarTem = null;
	private PreparedStatement actualizarFactura = null;
	
	private DetalleFacturaDao detallesDao=null;
	private ClienteDao myClienteDao=null;
	private CuentaPorCobrarDao myCuentaCobrarDao=null;
	
	private Integer idFacturaGuardada=null;
	
	
	public FacturaDao(Conexion conn){
		//Class(Conexion);
		conexion =conn;
		detallesDao=new DetalleFacturaDao(conexion);
		myClienteDao=new ClienteDao(conexion);
		myCuentaCobrarDao=new CuentaPorCobrarDao(conexion);
		/*try {
			conexionBD=conn.getPoolConexion().getConnection();
			getFecha=conexionBD.prepareStatement("SELECT DATE_FORMAT(now(), '%d/%m/%Y') as fecha;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar facturas temporal>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarFacturaTemp(Factura myFactura){
		boolean resultado=false;
		ResultSet rs=null;
		
		Connection conn=null;
		//int idFactura=0;
		
		String sql= "INSERT INTO encabezado_factura_temp("
				+ "fecha,"
				+ "subtotal,"
				+ "impuesto,"
				+ "total,"
				+ "codigo_cliente,"
				+ "descuento,"
				+ "estado_factura,"
				+ "tipo_factura,"
				+ "usuario)"
				+ " VALUES (now(),?,?,?,?,?,?,?,?)";
		
		try 
		{
			conn=conexion.getPoolConexion().getConnection();
			agregarFactura=conn.prepareStatement(sql);
			agregarFactura.setBigDecimal(1,myFactura.getSubTotal() );
			agregarFactura.setBigDecimal(2, myFactura.getTotalImpuesto());
			agregarFactura.setBigDecimal(3, myFactura.getTotal());
			agregarFactura.setInt(4, myFactura.getCliente().getId());
			agregarFactura.setBigDecimal(5, myFactura.getTotalDescuento());
			agregarFactura.setString(6, "ACT");
			agregarFactura.setInt(7, myFactura.getTipoFactura());
			agregarFactura.setString(8, conexion.getUsuarioLogin().getUser());
			
			
			
			agregarFactura.executeUpdate();//se guarda el encabezado de la factura
			rs=agregarFactura.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				//idFactura=rs.getInt(1);
				idFacturaGuardada=rs.getInt(1);
				
			}
			
			//se guardan los detalles de la fatura
			for(int x=0;x<myFactura.getDetalles().size();x++){
				
				if(myFactura.getDetalles().get(x).getArticulo().getId()!=-1)
					detallesDao.agregarDetalleTemp(myFactura.getDetalles().get(x), idFacturaGuardada);
			}
			
			resultado= true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
			resultado= false;
		}
		finally
		{
			try{
				if(rs != null) rs.close();
	            if(agregarFactura != null)agregarFactura.close();
	            if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
		
		
		
		return resultado;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar facturas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarFactura(Factura myFactura){
		boolean resultado=false;
		ResultSet rs=null;
		
		Connection conn=null;
		//int idFactura=0;
		
		String sql= "INSERT INTO encabezado_factura("
				+ "fecha,"
				+ "subtotal,"
				+ "impuesto,"
				+ "total,"
				+ "codigo_cliente,"
				+ "descuento,"
				+ "estado_factura,"
				+ "tipo_factura,"
				+ "tipo_pago,"
				+ "observacion,"
				+ "isv18,"
				+ "pago,"
				+ "usuario,"
				+ "total_letras,"
				+ "codigo_vendedor,"
				+ "estado_pago)"
				+ " VALUES (now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try 
		{
			
			String nombreCliente=myFactura.getCliente().getNombre();//"Consumidor final";
			
			//si el cliente en escrito por el bombero
			if(myFactura.getCliente().getId()<0){
				myClienteDao.registrarCliente(myFactura.getCliente());
				myFactura.getCliente().setId(myClienteDao.getIdClienteRegistrado());
			}
			conn=conexion.getPoolConexion().getConnection();
			agregarFactura=conn.prepareStatement(sql);
			agregarFactura.setBigDecimal(1,myFactura.getSubTotal() );
			agregarFactura.setBigDecimal(2, myFactura.getTotalImpuesto());
			agregarFactura.setBigDecimal(3, myFactura.getTotal());
			agregarFactura.setInt(4, myFactura.getCliente().getId());
			agregarFactura.setBigDecimal(5, myFactura.getTotalDescuento());
			agregarFactura.setString(6, "ACT");
			agregarFactura.setInt(7, myFactura.getTipoFactura());
			agregarFactura.setInt(8, myFactura.getTipoPago());
			agregarFactura.setString(9, myFactura.getObservacion());
			agregarFactura.setBigDecimal(10, myFactura.getTotalImpuesto18());
			agregarFactura.setBigDecimal(11, myFactura.getPago());
			agregarFactura.setString(12, conexion.getUsuarioLogin().getUser());
			agregarFactura.setString(13, NumberToLetterConverter.convertNumberToLetter(myFactura.getTotal().setScale(0, BigDecimal.ROUND_HALF_EVEN).doubleValue()));
			agregarFactura.setInt(14, myFactura.getVendedor().getCodigo());
			//agregarFactura.setInt(15, myFactura.getCodigo());
			agregarFactura.setInt(15,myFactura.getEstadoPago());
			
			
			
			
			agregarFactura.executeUpdate();//se guarda el encabezado de la factura
			rs=agregarFactura.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				//idFactura=rs.getInt(1);
				idFacturaGuardada=rs.getInt(1);
				myFactura.setIdFactura(idFacturaGuardada);
				
			}
			
			//se guardan los detalles de la fatura
			for(int x=0;x<myFactura.getDetalles().size();x++){
				
				if(myFactura.getDetalles().get(x).getArticulo().getId()!=-1)
					detallesDao.agregarDetalle(myFactura.getDetalles().get(x), idFacturaGuardada);
			}
			
			
			if(myFactura.getTipoFactura()==2){
				boolean resultado2=this.myCuentaCobrarDao.reguistrarCredito(myFactura);
			}
			
			resultado= true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
			resultado= false;
		}
		finally
		{
			try{
				if(rs != null) rs.close();
	            if(agregarFactura != null)agregarFactura.close();
	            if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
		
		
		
		return resultado;
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar las facturas por id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public Factura facturasPorId(int id){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="SELECT "
				+ "encabezado_factura.numero_factura, "
				+ "DATE_FORMAT(encabezado_factura.fecha, '%d/%m/%Y') as fecha,"
				+ " encabezado_factura.subtotal, "
				+ "encabezado_factura.impuesto, "
				+ "encabezado_factura.total, "
				+ "encabezado_factura.codigo_cliente,"
				+ "encabezado_factura.codigo, "
				+ "encabezado_factura.estado_factura, "
				+ "encabezado_factura.isv18, "
				+ "encabezado_factura.tipo_factura, "
				+ "encabezado_factura.descuento,"
				+ "encabezado_factura.pago, "
				+ "encabezado_factura.usuario,"
				+ "encabezado_factura.estado_factura, "
				+ "encabezado_factura.agrega_kardex "
				+ "FROM encabezado_factura where numero_factura=?";
        //Statement stmt = null;
    	Factura unaFactura=new Factura();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarFacturas = con.prepareStatement(sql);
			seleccionarFacturas.setInt(1, id);
			
			res = seleccionarFacturas.executeQuery();
			while(res.next()){
				
				existe=true;
				unaFactura.setIdFactura(res.getInt("numero_factura"));
				Cliente unCliente=myClienteDao.buscarCliente(res.getInt("codigo_cliente"));
				
				unaFactura.setCliente(unCliente);
				
				unaFactura.setFecha(res.getString("fecha"));
				unaFactura.setSubTotal(res.getBigDecimal("subtotal"));
				unaFactura.setTotalImpuesto(res.getBigDecimal("impuesto"));
				unaFactura.setTotal(res.getBigDecimal("total"));
				//unaFactura.setEstado(res.getInt("estado_factura"));
				unaFactura.setTotalDescuento(res.getBigDecimal("descuento"));
				
				unaFactura.setEstado(res.getString("estado_factura"));
				unaFactura.setTipoFactura(res.getInt("tipo_factura"));
				unaFactura.setAgregadoAkardex(res.getInt("agrega_kardex"));
				
				unaFactura.setDetalles(detallesDao.getDetallesFactura(res.getInt("numero_factura")));
				
				
			
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarFacturas != null)seleccionarFacturas.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return unaFactura;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los articulos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Factura> todasfacturas(int limInf,int limSupe){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="SELECT "
				+ "encabezado_factura.numero_factura, "
				+ "DATE_FORMAT(encabezado_factura.fecha, '%d/%m/%Y') as fecha,"
				+ " encabezado_factura.subtotal, "
				+ "encabezado_factura.impuesto, "
				+ "encabezado_factura.total, "
				+ "encabezado_factura.codigo_cliente,"
				+ "encabezado_factura.codigo, "
				+ "encabezado_factura.estado_factura, "
				+ "encabezado_factura.isv18, "
				+ "encabezado_factura.tipo_factura, "
				+ "encabezado_factura.descuento,"
				+ "encabezado_factura.pago, "
				+ "encabezado_factura.usuario,"
				+ "encabezado_factura.estado_factura, "
				+ "encabezado_factura.agrega_kardex "
				+ " FROM encabezado_factura ORDER BY encabezado_factura.numero_factura DESC";
    	//String sql="select * from v_encabezado_factura ORDER BY numero_factura DESC LIMIT ?, ?";
        //Statement stmt = null;
       	List<Factura> facturas=new ArrayList<Factura>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			
			seleccionarFacturas = con.prepareStatement(sql);
			//seleccionarFacturas.setInt(1, limInf);
			//seleccionarFacturas.setInt(2, limSupe);
			res = seleccionarFacturas.executeQuery();
			while(res.next()){
				Factura unaFactura=new Factura();
				existe=true;
				unaFactura.setIdFactura(res.getInt("numero_factura"));
				Cliente unCliente=myClienteDao.buscarCliente(res.getInt("codigo_cliente"));
				
				unaFactura.setCliente(unCliente);
				
				unaFactura.setFecha(res.getString("fecha"));
				unaFactura.setSubTotal(res.getBigDecimal("subtotal"));
				unaFactura.setTotalImpuesto(res.getBigDecimal("impuesto"));
				unaFactura.setTotal(res.getBigDecimal("total"));
				//unaFactura.setEstado(res.getInt("estado_factura"));
				unaFactura.setTotalDescuento(res.getBigDecimal("descuento"));
				
				unaFactura.setEstado(res.getString("estado_factura"));
				unaFactura.setTipoFactura(res.getInt("tipo_factura"));
				unaFactura.setAgregadoAkardex(res.getInt("agrega_kardex"));
				
				//unaFactura.setDetalles(detallesDao.getDetallesFactura(res.getInt("numero_factura")));
				
				
				facturas.add(unaFactura);
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarFacturas != null)seleccionarFacturas.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return facturas;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para seleccionar todos los articulos>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Factura> facturasEnProceso(){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="SELECT "
				+ "encabezado_factura_temp.numero_factura, "
				+ "DATE_FORMAT(encabezado_factura_temp.fecha, '%d/%m/%Y') as fecha,"
				+ " encabezado_factura_temp.subtotal, "
				+ "encabezado_factura_temp.impuesto, "
				+ "encabezado_factura_temp.total, "
				+ "encabezado_factura_temp.codigo_cliente,"
				+ "encabezado_factura_temp.codigo, "
				+ "encabezado_factura_temp.estado_factura, "
				+ "encabezado_factura_temp.isv18, "
				+ "encabezado_factura_temp.tipo_factura, "
				+ "encabezado_factura_temp.descuento,"
				+ "encabezado_factura_temp.pago, "
				+ "encabezado_factura_temp.usuario "
				+ "FROM encabezado_factura_temp";
        //Statement stmt = null;
       	List<Factura> facturas=new ArrayList<Factura>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarFacturasPendientes = con.prepareStatement(sql);
			
			res = seleccionarFacturasPendientes.executeQuery();
			while(res.next()){
				Factura unaFactura=new Factura();
				existe=true;
				unaFactura.setIdFactura(res.getInt("numero_factura"));
				Cliente unCliente=myClienteDao.buscarCliente(res.getInt("codigo_cliente"));
				
				unaFactura.setCliente(unCliente);
				
				unaFactura.setFecha(res.getString("fecha"));
				unaFactura.setSubTotal(res.getBigDecimal("subtotal"));
				unaFactura.setTotalImpuesto(res.getBigDecimal("impuesto"));
				unaFactura.setTotal(res.getBigDecimal("total"));
				//unaFactura.setEstado(res.getInt("estado_factura"));
				unaFactura.setTotalDescuento(res.getBigDecimal("descuento"));
				unaFactura.setTipoFactura(res.getInt("tipo_factura"));
				
				unaFactura.setDetalles(detallesDao.detallesFacturaPendiente(unaFactura.getIdFactura()));
				
				
				facturas.add(unaFactura);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarFacturasPendientes != null)seleccionarFacturasPendientes.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return facturas;
			}
			else return null;
		
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para desconectar las conexiones>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public void desconectarBD(){
		try {
			if(getFecha != null)getFecha.close();
			if(conexionBD != null) conexionBD.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer getIdFacturaGuardada() {
		// TODO Auto-generated method stub
		return idFacturaGuardada;
	}
	
	
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Eliminar los proveedores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean EliminarTemp(Integer IdFactura) {
		
		int resultado=0;
		Connection conn=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			elimiarTem=conn.prepareStatement("DELETE FROM encabezado_factura_temp WHERE numero_factura = ?");
			elimiarTem.setInt( 1, IdFactura );
			resultado=elimiarTem.executeUpdate();
			
			this.detallesDao.eliminarTem(IdFactura);
			return true;
			
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
		finally
		{
			try{
				//if(res != null) res.close();
                if(elimiarTem != null)elimiarTem.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
	}

	public boolean actualizarFacturaTemp(Factura f) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		Connection conn=null;
		String sql="UPDATE encabezado_factura_temp "
				+ "SET fecha = now(),"
				+ " subtotal = ? , "
				+ "impuesto = ?, "
				+ "total=?, "
				+ "codigo_cliente=?,"
				
				+ "estado_factura=?,"
				+ "descuento=?,"
				+ "tipo_factura=?,"
				+ "usuario=?"
				+ " WHERE numero_factura = ?";
		try {
			conn=conexion.getPoolConexion().getConnection();
			actualizarTem=conn.prepareStatement(sql);
			
			//JOptionPane.showMessageDialog(null, f);
			actualizarTem.setBigDecimal(1,f.getSubTotal());
			actualizarTem.setBigDecimal(2,f.getTotalImpuesto());
			actualizarTem.setBigDecimal(3,f.getTotal());
			actualizarTem.setInt(4, f.getCliente().getId());
			actualizarTem.setString(5, "ACT");
			
			actualizarTem.setBigDecimal(6, f.getTotalDescuento());
			
			actualizarTem.setInt(7, f.getTipoFactura());
			actualizarTem.setString(8, conexion.getUsuarioLogin().getUser());
			actualizarTem.setInt(9, f.getIdFactura());
			actualizarTem.executeUpdate();
			
			detallesDao.eliminarTem(f.getIdFactura());
			//se guardan los detalles de la fatura
			for(int x=0;x<f.getDetalles().size();x++){
				
				if(f.getDetalles().get(x).getArticulo().getId()!=-1)
					detallesDao.agregarDetalleTemp(f.getDetalles().get(x),f.getIdFactura());
			}
			
			resultado= true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultado=false;
		}
		finally
		{
			try{
				//if(res != null) res.close();
                if(actualizarTem != null)actualizarTem.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		return resultado;
	}

	public List<Factura> facturasPorFechas(String fecha1, String fecha2) {
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="SELECT "
				+ "encabezado_factura.numero_factura, "
				+ "DATE_FORMAT(encabezado_factura.fecha, '%d/%m/%Y') as fecha,"
				+ " encabezado_factura.subtotal, "
				+ "encabezado_factura.impuesto, "
				+ "encabezado_factura.total, "
				+ "encabezado_factura.codigo_cliente,"
				+ "encabezado_factura.codigo, "
				+ "encabezado_factura.estado_factura, "
				+ "encabezado_factura.isv18, "
				+ "encabezado_factura.tipo_factura, "
				+ "encabezado_factura.descuento,"
				+ "encabezado_factura.pago, "
				+ "encabezado_factura.usuario,"
				+ "encabezado_factura.estado_factura, "
				+ "encabezado_factura.agrega_kardex "
				+ "FROM encabezado_factura where DATE_FORMAT(encabezado_factura.fecha, '%d/%m/%Y')>=? and DATE_FORMAT(encabezado_factura.fecha, '%d/%m/%Y')<=?";
        //Statement stmt = null;
       	List<Factura> facturas=new ArrayList<Factura>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarFacturas = con.prepareStatement(sql);
			
			seleccionarFacturas.setString(1, fecha1);
			seleccionarFacturas.setString(2, fecha2);
			
			res = seleccionarFacturas.executeQuery();
			while(res.next()){
				Factura unaFactura=new Factura();
				existe=true;
				unaFactura.setIdFactura(res.getInt("numero_factura"));
				Cliente unCliente=myClienteDao.buscarCliente(res.getInt("codigo_cliente"));
				
				unaFactura.setCliente(unCliente);
				
				unaFactura.setFecha(res.getString("fecha"));
				unaFactura.setSubTotal(res.getBigDecimal("subtotal"));
				unaFactura.setTotalImpuesto(res.getBigDecimal("impuesto"));
				unaFactura.setTotal(res.getBigDecimal("total"));
				//unaFactura.setEstado(res.getInt("estado_factura"));
				unaFactura.setTotalDescuento(res.getBigDecimal("descuento"));
				
				unaFactura.setEstado(res.getString("estado_factura"));
				unaFactura.setTipoFactura(res.getInt("tipo_factura"));
				unaFactura.setAgregadoAkardex(res.getInt("agrega_kardex"));
				
				unaFactura.setDetalles(detallesDao.getDetallesFactura(res.getInt("numero_factura")));
				
				
				facturas.add(unaFactura);
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarFacturas != null)seleccionarFacturas.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return facturas;
			}
			else return null;
		
	}

	public boolean anularFactura(Factura f) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		Connection conn=null;
		String sql="UPDATE encabezado_factura SET "
				
				
				+ "estado_factura=?"
				
				+ " WHERE numero_factura = ?";
		try {
			conn=conexion.getPoolConexion().getConnection();
			
			actualizarFactura=conn.prepareStatement(sql);
			
			
			actualizarFactura.setString(1, "NULA");
			
			actualizarFactura.setInt(2, f.getIdFactura());
			actualizarFactura.executeUpdate();
						
			
			resultado= true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultado=false;
		}
		finally
		{
			try{
				
				//if(res != null) res.close();
                if(actualizarFactura != null)actualizarFactura.close();
                if(conn != null) conn.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		return resultado;
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para Eliminar los proveedores>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<Factura> sinPagarCliente(Cliente myCliente) {
        Connection con = null;
        
    	String sql="SELECT "
				+ "encabezado_factura.numero_factura, "
				+ "DATE_FORMAT(encabezado_factura.fecha, '%d/%m/%Y') as fecha,"
				+ " encabezado_factura.subtotal, "
				+ "encabezado_factura.impuesto, "
				+ "encabezado_factura.total, "
				+ "encabezado_factura.codigo_cliente,"
				+ "encabezado_factura.codigo, "
				+ "encabezado_factura.estado_factura, "
				+ "encabezado_factura.isv18, "
				+ "encabezado_factura.tipo_factura, "
				+ "encabezado_factura.descuento,"
				+ "encabezado_factura.pago, "
				+ "encabezado_factura.usuario,"
				+ "encabezado_factura.estado_factura, "
				+ "encabezado_factura.agrega_kardex "
				+ " FROM encabezado_factura "
				+ "where encabezado_factura.codigo_cliente=? "
				+ " and "
				+ " encabezado_factura.estado_factura = 'ACT' "
				+ " and "
				+ " encabezado_factura.estado_pago = 0;";
        //Statement stmt = null;
       	List<Factura> facturas=new ArrayList<Factura>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarFacturas = con.prepareStatement(sql);
			
			seleccionarFacturas.setInt(1, myCliente.getId());
			
			res = seleccionarFacturas.executeQuery();
			while(res.next()){
				Factura unaFactura=new Factura();
				existe=true;
				unaFactura.setIdFactura(res.getInt("numero_factura"));
				Cliente unCliente=myClienteDao.buscarCliente(res.getInt("codigo_cliente"));
				
				unaFactura.setCliente(unCliente);
				
				unaFactura.setFecha(res.getString("fecha"));
				unaFactura.setSubTotal(res.getBigDecimal("subtotal"));
				unaFactura.setTotalImpuesto(res.getBigDecimal("impuesto"));
				unaFactura.setTotal(res.getBigDecimal("total"));
				//unaFactura.setEstado(res.getInt("estado_factura"));
				unaFactura.setTotalDescuento(res.getBigDecimal("descuento"));
				
				unaFactura.setEstado(res.getString("estado_factura"));
				unaFactura.setTipoFactura(res.getInt("tipo_factura"));
				unaFactura.setAgregadoAkardex(res.getInt("agrega_kardex"));
				
				unaFactura.setDetalles(detallesDao.getDetallesFactura(res.getInt("numero_factura")));
				
				
				facturas.add(unaFactura);
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarFacturas != null)seleccionarFacturas.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return facturas;
			}
			else return null;}

	public boolean cambiarEstadoPago(Integer idFactura) {
		boolean resultado=false;
		Connection conn=null;
		String sql="UPDATE encabezado_factura SET "
				
				
				+ "estado_pago=?"
				
				+ " WHERE numero_factura = ?";
		try {
			conn=conexion.getPoolConexion().getConnection();
			
			actualizarFactura=conn.prepareStatement(sql);
			
			
			actualizarFactura.setInt(1, 1);
			
			actualizarFactura.setInt(2, idFactura);
			actualizarFactura.executeUpdate();
						
			
			resultado= true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultado=false;
		}finally
		{
			try{
				
				//if(res != null) res.close();
                if(actualizarFactura != null)actualizarFactura.close();
                if(conn != null) conn.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		return resultado;
	}

	

}
