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
import modelo.FacturaCompra;
import modelo.Proveedor;

public class FacturaCompraDao {
	private Conexion conexion=null;
	private PreparedStatement agregarFactura=null;
	private DetalleFacturaProveedorDao detallesDao;
	private PreparedStatement seleccionarFacturas=null;
	private ProveedorDao myProveedorDao=null;
	private PreparedStatement actualizarFactura = null;
	private KardexDao kardexDao=null;
	
	public FacturaCompraDao(Conexion conn){
		conexion=conn;
		detallesDao=new DetalleFacturaProveedorDao(conexion);
		myProveedorDao=new ProveedorDao(conexion);
		kardexDao=new KardexDao(conexion);
		/*try {
			agregarFactura=conexion.getConnection().prepareStatement( "INSERT INTO encabezado_factura_compra(fecha,subtotal,impuesto,total,codigo_cliente,no_factura_compra,tipo_factura,fecha_vencimiento,estado_factura,fecha_ingreso) VALUES (?,?,?,?,?,?,?,?,?,now())");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar facturas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean registrarFactura(FacturaCompra fac)
	{
		
		boolean resultado;
		ResultSet res=null;
		Connection conn=null;
		int idFactura=0;
		
		try 
		{
			conn=conexion.getPoolConexion().getConnection();
			agregarFactura=conn.prepareStatement( "INSERT INTO encabezado_factura_compra(fecha,subtotal,impuesto,total,codigo_proveedor,no_factura_compra,tipo_factura,fecha_vencimiento,estado_factura,fecha_ingreso) VALUES (?,?,?,?,?,?,?,?,?,now())");
			agregarFactura.setString(1, fac.getFechaCompra());
			agregarFactura.setBigDecimal(2, fac.getSubTotal());
			agregarFactura.setBigDecimal(3, fac.getTotalImpuesto());
			agregarFactura.setBigDecimal(4, fac.getTotal());
			agregarFactura.setInt(5, fac.getProveedor().getId());
			agregarFactura.setString(6, fac.getIdFactura());
			agregarFactura.setInt(7, fac.getTipoFactura());
			agregarFactura.setString(8, fac.getFechaVencimento());
			agregarFactura.setString(9, "ACT");
			
			/*if(fac.getTipoFactura()==1){
				//uno para factura pagada
				agregarFactura.setInt(9, 1);
			}
			if (fac.getTipoFactura()==2){
				//dos para factura pendiente de pago
				agregarFactura.setInt(9, 2);
			}*/
			
			agregarFactura.executeUpdate();
			res=agregarFactura.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(res.next()){
				idFactura=res.getInt(1);
			}
			
			//JOptionPane.showMessageDialog(null,""+idFactura);
			for(int x=0;x<fac.getDetalles().size();x++){
				//solo los articulos validos que no tengan id nulos
				if(fac.getDetalles().get(x).getArticulo().getId()>0){
					boolean resul=detallesDao.agregarDetalle(fac.getDetalles().get(x), idFactura);
					
					//si el detalle se agrego correctamente
					if(resul){
						//se comprueba que exista el kardex para el detalle
						if(!kardexDao.comprobarKardex(fac.getDetalles().get(x).getArticulo().getId(), fac.getDepartamento().getId())){
							
							//se registra el nuevo kardex
							int codigoKardex=kardexDao.registrarKardex(fac.getDetalles().get(x).getArticulo().getId(), fac.getDepartamento().getId());
							
							//comprabamos si se creo el kardex correctamente
							if(codigoKardex>0){
								//se agrega el movimiento
								int idMov=kardexDao.agregarDetalle(codigoKardex,fac.getIdFactura());
								
								//comprobamos que se creo el detalle
								if(idMov>0){
									kardexDao.agregarMovimiento(idMov,fac.getDetalles().get(x).getCantidad(),fac.getDetalles().get(x).getPrecioCompra());
								}
							}
						}
					}
				}
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
				if(res != null) res.close();
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
	
	public String getFechaSistema(){
		String fecha="";
		
		Statement instrucions=null;
		Connection conn=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			instrucions = conn.createStatement();
			ResultSet res=null;
			//res=instrucions.executeQuery(" SELECT DATE_FORMAT(now(), '%d/%m/%Y %h:%i:%s %p') as fecha;");
			res=instrucions.executeQuery(" SELECT DATE_FORMAT(now(), '%d/%m/%Y') as fecha;");
			while(res.next()){
				fecha=res.getString("fecha");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try{
				//if(res != null) res.close();
                if(instrucions != null)instrucions.close();
                if(conn != null) conn.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
		
		
		
		
		return fecha;
	}

	public List<FacturaCompra> todasfacturas() {
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="SELECT "+
						" encabezado_factura_compra.numero_compra,"+
						"DATE_FORMAT(encabezado_factura_compra.fecha, '%d/%m/%Y') as fecha,"+
						"encabezado_factura_compra.subtotal,"+
						"encabezado_factura_compra.impuesto,"+
						"encabezado_factura_compra.total,"+
						"encabezado_factura_compra.codigo_proveedor,"+
						"encabezado_factura_compra.estado_factura,"+
						"encabezado_factura_compra.usuario,"+
						"encabezado_factura_compra.no_factura_compra,"+
						"encabezado_factura_compra.fecha_ingreso,"+
						"encabezado_factura_compra.tipo_factura,"+
						"encabezado_factura_compra.fecha_vencimiento,"+
						"encabezado_factura_compra.isv18,"+
						"encabezado_factura_compra.agrega_kardex "+
					" FROM "+
						" encabezado_factura_compra "+
						" ORDER BY "+
						" encabezado_factura_compra.numero_compra DESC";
        //Statement stmt = null;
       	List<FacturaCompra> facturas=new ArrayList<FacturaCompra>();
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			seleccionarFacturas = con.prepareStatement(sql);
			
			res = seleccionarFacturas.executeQuery();
			while(res.next()){
				FacturaCompra unaFactura=new FacturaCompra();
				existe=true;
				unaFactura.setNoCompra(res.getInt("numero_compra"));
				unaFactura.setFechaCompra(res.getString("fecha"));
				
				unaFactura.setIdFactura(res.getString("no_factura_compra"));
				Proveedor unProveedor=myProveedorDao.buscarPro(res.getInt("codigo_proveedor"));           // .buscarCliente(res.getInt("codigo_cliente"));
				
				unaFactura.setProveedor(unProveedor);
				
				
				unaFactura.setSubTotal(res.getBigDecimal("subtotal"));
				unaFactura.setTotalImpuesto(res.getBigDecimal("impuesto"));
				unaFactura.setTotal(res.getBigDecimal("total"));
				//unaFactura.setEstado(res.getInt("estado_factura"));
				//unaFactura.setTotalDescuento(res.getDouble("descuento"));
				
				unaFactura.setEstado(res.getString("estado_factura"));
				unaFactura.setTipoFactura(res.getInt("tipo_factura"));
				unaFactura.setAgregadoAkardex(res.getInt("agrega_kardex"));
				
				unaFactura.setDetalles(detallesDao.getDetallesFactura(res.getInt("numero_compra")));
				
				
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

	public boolean anularFactura(FacturaCompra f) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		Connection conn=null;
		String sql="UPDATE encabezado_factura_compra SET "
				
				
				+ "estado_factura=?"
				
				+ " WHERE numero_compra = ?";
		try {
			conn=conexion.getPoolConexion().getConnection();
			
			actualizarFactura=conn.prepareStatement(sql);
			
			
			actualizarFactura.setString(1, "NULA");
			
			actualizarFactura.setInt(2, f.getNoCompra());
			actualizarFactura.executeUpdate();
						
			
			resultado= true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultado=false;
		}
		return resultado;
	}

	public FacturaCompra facturasPorId(int id) {
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
        String sql="SELECT "+
				" encabezado_factura_compra.numero_compra,"+
				"DATE_FORMAT(encabezado_factura_compra.fecha, '%d/%m/%Y') as fecha,"+
				"encabezado_factura_compra.subtotal,"+
				"encabezado_factura_compra.impuesto,"+
				"encabezado_factura_compra.total,"+
				"encabezado_factura_compra.codigo_proveedor,"+
				"encabezado_factura_compra.estado_factura,"+
				"encabezado_factura_compra.usuario,"+
				"encabezado_factura_compra.no_factura_compra,"+
				"encabezado_factura_compra.fecha_ingreso,"+
				"encabezado_factura_compra.tipo_factura,"+
				"encabezado_factura_compra.fecha_vencimiento,"+
				"encabezado_factura_compra.isv18,"+
				"encabezado_factura_compra.agrega_kardex "+
			" FROM "+
				" encabezado_factura_compra "+
				" WHERE "+
				"encabezado_factura_compra.numero_compra = ? ";
        //Statement stmt = null;
    	FacturaCompra unaFactura=new FacturaCompra();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			seleccionarFacturas = con.prepareStatement(sql);
			seleccionarFacturas.setInt(1, id);
			
			res = seleccionarFacturas.executeQuery();
			while(res.next()){
				
				existe=true;
				unaFactura.setNoCompra(res.getInt("numero_compra"));
				unaFactura.setFechaCompra(res.getString("fecha"));
				
				unaFactura.setIdFactura(res.getString("no_factura_compra"));
				Proveedor unProveedor=myProveedorDao.buscarPro(res.getInt("codigo_proveedor"));           // .buscarCliente(res.getInt("codigo_cliente"));
				
				unaFactura.setProveedor(unProveedor);
				
				
				unaFactura.setSubTotal(res.getBigDecimal("subtotal"));
				unaFactura.setTotalImpuesto(res.getBigDecimal("impuesto"));
				unaFactura.setTotal(res.getBigDecimal("total"));
				//unaFactura.setEstado(res.getInt("estado_factura"));
				//unaFactura.setTotalDescuento(res.getDouble("descuento"));
				
				unaFactura.setEstado(res.getString("estado_factura"));
				unaFactura.setTipoFactura(res.getInt("tipo_factura"));
				unaFactura.setAgregadoAkardex(res.getInt("agrega_kardex"));
				
				unaFactura.setDetalles(detallesDao.getDetallesFactura(res.getInt("numero_compra")));
				
				
			
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

	public List<FacturaCompra> facturasPorFechas(String fecha1, String fecha2) {
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	String sql="SELECT "+
				" encabezado_factura_compra.numero_compra,"+
				"DATE_FORMAT(encabezado_factura_compra.fecha, '%d/%m/%Y') as fecha,"+
				"encabezado_factura_compra.subtotal,"+
				"encabezado_factura_compra.impuesto,"+
				"encabezado_factura_compra.total,"+
				"encabezado_factura_compra.codigo_proveedor,"+
				"encabezado_factura_compra.estado_factura,"+
				"encabezado_factura_compra.usuario,"+
				"encabezado_factura_compra.no_factura_compra,"+
				"encabezado_factura_compra.fecha_ingreso,"+
				"encabezado_factura_compra.tipo_factura,"+
				"encabezado_factura_compra.fecha_vencimiento,"+
				"encabezado_factura_compra.isv18,"+
				"encabezado_factura_compra.agrega_kardex "+
				" FROM encabezado_factura_compra "
				+ "where DATE_FORMAT(encabezado_factura_compra.fecha, '%d/%m/%Y')>=? and DATE_FORMAT(encabezado_factura_compra.fecha, '%d/%m/%Y')<=?";
        //Statement stmt = null;
    	List<FacturaCompra> facturas=new ArrayList<FacturaCompra>();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = Conexion.getPoolConexion().getConnection();
			
			seleccionarFacturas = con.prepareStatement(sql);
			
			seleccionarFacturas.setString(1, fecha1);
			seleccionarFacturas.setString(2, fecha2);
			
			res = seleccionarFacturas.executeQuery();
			while(res.next()){
				FacturaCompra unaFactura=new FacturaCompra();
				existe=true;
				unaFactura.setNoCompra(res.getInt("numero_compra"));
				unaFactura.setFechaCompra(res.getString("fecha"));
				
				unaFactura.setIdFactura(res.getString("no_factura_compra"));
				Proveedor unProveedor=myProveedorDao.buscarPro(res.getInt("codigo_proveedor"));           // .buscarCliente(res.getInt("codigo_cliente"));
				
				unaFactura.setProveedor(unProveedor);
				
				
				unaFactura.setSubTotal(res.getBigDecimal("subtotal"));
				unaFactura.setTotalImpuesto(res.getBigDecimal("impuesto"));
				unaFactura.setTotal(res.getBigDecimal("total"));
				//unaFactura.setEstado(res.getInt("estado_factura"));
				//unaFactura.setTotalDescuento(res.getDouble("descuento"));
				
				unaFactura.setEstado(res.getString("estado_factura"));
				unaFactura.setTipoFactura(res.getInt("tipo_factura"));
				unaFactura.setAgregadoAkardex(res.getInt("agrega_kardex"));
				
				unaFactura.setDetalles(detallesDao.getDetallesFactura(res.getInt("numero_compra")));
				
				
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

}
