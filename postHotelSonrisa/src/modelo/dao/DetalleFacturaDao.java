package modelo.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Articulo;
import modelo.Conexion;
import modelo.DetalleFactura;
import modelo.DetalleFacturaProveedor;
import modelo.Inventario;
import modelo.Kardex;

public class DetalleFacturaDao {
	
	private PreparedStatement agregarDetalle=null;
	private PreparedStatement actualizarDetalle=null;
	private PreparedStatement detallesFacturaPendiente=null;
	private Conexion conexion=null;
	private PreparedStatement elimiarTem = null;
	private InventarioDao inventarioDao;
	private KardexDao kardexDao;
	private ArticuloDao articuloDao=null;
	
	public DetalleFacturaDao(Conexion conn){
		conexion=conn;
		inventarioDao=new InventarioDao(conexion);
		kardexDao=new KardexDao(conexion);
		articuloDao=new ArticuloDao(conexion);
		/*try {
			agregarDetalle=conexion.getConnection().prepareStatement( "INSERT INTO detalle_factura_compra(numero_compra,codigo_articulo,precio,cantidad,impuesto,subtotal) VALUES (?,?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar detalles de facturas temp>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean agregarDetalleTemp(DetalleFactura detalle, int idFactura) {
		boolean resultado=false;
		
		String sql="INSERT INTO detalle_factura_temp("
				+ "numero_factura,"
				+ "codigo_articulo,"
				+ "precio,"
				+ "cantidad,"
				+ "impuesto,"
				+ "subtotal,"
				+ "descuento,"
				+ "total"
				+ ") VALUES (?,?,?,?,?,?,?,?)";
		Connection conn=null;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			agregarDetalle=conn.prepareStatement( sql);
			
			agregarDetalle.setInt(1, idFactura);
			agregarDetalle.setInt(2, detalle.getArticulo().getId());
			agregarDetalle.setDouble(3, detalle.getArticulo().getPrecioVenta());
			agregarDetalle.setBigDecimal(4, detalle.getCantidad());
			agregarDetalle.setBigDecimal(5, detalle.getImpuesto());
			agregarDetalle.setBigDecimal(6, detalle.getSubTotal());
			agregarDetalle.setBigDecimal(7, detalle.getDescuentoItem());
			agregarDetalle.setBigDecimal(8, detalle.getTotal());
			agregarDetalle.executeUpdate();
				
			resultado=true;
		}catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
			resultado= false;
		}
		finally
		{
			try{
				
				//if(res != null) res.close();
                if(agregarDetalle != null)agregarDetalle.close();
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
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar detalles de facturas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean agregarDetalleRequi(DetalleFacturaProveedor detalle, int idRequi) {
		boolean resultado=false;
		
		String sql="INSERT INTO detalle_requisicion("
				+ "codigo_requisicion,"
				+ "codigo_articulo,"
				+ "precio_unidad,"
				+ "cantidad,"
				+ "total"
				+ ") VALUES (?,?,?,?,?)";
		Connection conn=null;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			agregarDetalle=conn.prepareStatement( sql);
			
			agregarDetalle.setInt(1, idRequi);
			agregarDetalle.setInt(2, detalle.getArticulo().getId());
			agregarDetalle.setBigDecimal(3, detalle.getPrecioCompra());
			agregarDetalle.setBigDecimal(4, detalle.getCantidad());
			agregarDetalle.setBigDecimal(5, detalle.getTotal());
			agregarDetalle.executeUpdate();
			
			resultado=true;
		}catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
			resultado= false;
		}
		finally
		{
			try{
				
				//if(res != null) res.close();
                if(agregarDetalle != null)agregarDetalle.close();
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
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar detalles de facturas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean agregarDetalle(DetalleFactura detalle, int idFactura) {
		boolean resultado=false;
		
		String sql="INSERT INTO detalle_factura("
				+ "numero_factura,"
				+ "codigo_articulo,"
				+ "precio,"
				+ "cantidad,"
				+ "impuesto,"
				+ "subtotal,"
				+ "descuento,"
				+ "total"
				+ ") VALUES (?,?,?,?,?,?,?,?)";
		Connection conn=null;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			agregarDetalle=conn.prepareStatement( sql);
			
			agregarDetalle.setInt(1, idFactura);
			agregarDetalle.setInt(2, detalle.getArticulo().getId());
			agregarDetalle.setDouble(3, detalle.getArticulo().getPrecioVenta());
			agregarDetalle.setBigDecimal(4, detalle.getCantidad());
			agregarDetalle.setBigDecimal(5, detalle.getImpuesto());
			agregarDetalle.setBigDecimal(6, detalle.getSubTotal());
			agregarDetalle.setBigDecimal(7, detalle.getDescuentoItem());
			agregarDetalle.setBigDecimal(8, detalle.getTotal());
			agregarDetalle.executeUpdate();
			
			
			/*if(detalle.getArticulo().getTipoArticulo()!=2){
				Inventario inventario=new Inventario();
				
				//se consigue el inventario del articulo
				inventario=inventarioDao.buscarArticulo(detalle.getArticulo().getId());
				
				//se verifica que exite el articulo en el inventario
				if(inventario!=null){
					//se agrega al inventario la nueva cantidad
					inventario.decrementarExistencia(detalle.getCantidad().setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
					//se actualizar el articulo del inventario
					inventarioDao.actualizarInventario(inventario);
				}else{//sino esta el inventario el articulo, se debe crear el inventario
					
					//para implementar varias bodegas hay que cambiar el codigo de la bodega
					Inventario inventario1=new Inventario();
					inventario1.getBodega().setId(1);
					inventario1.setArticulo(detalle.getArticulo());
					inventario1.setExistencia(detalle.getCantidad().doubleValue()*-1);
					inventarioDao.agregarInventario(inventario1);				
				}
				
				
				//se crea y guarda la nuevo movimiento del kardex
				Kardex myKardex =new Kardex();
				
				myKardex.setArticulo(detalle.getArticulo());
				myKardex.setSalida(detalle.getCantidad().doubleValue());
				
				//hay que cambiar para implementar multiples bodegas
				myKardex.getBodega().setId(1);
				myKardex.setNoDocumento(""+idFactura);
				
				kardexDao.agregarEntrada(myKardex);
			
			}*/
			resultado=true;
		}catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
			resultado= false;
		}
		finally
		{
			try{
				
				//if(res != null) res.close();
                if(agregarDetalle != null)agregarDetalle.close();
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

	public List<DetalleFactura> detallesFacturaPendiente(int idFactura) {
		List<DetalleFactura> detalles=new ArrayList<DetalleFactura>();
		

        Connection con = null;
  
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			detallesFacturaPendiente = con.prepareStatement("SELECT * FROM detalle_factura_temp where numero_factura=?;");
			detallesFacturaPendiente.setInt(1, idFactura);
			
			res = detallesFacturaPendiente.executeQuery();
			while(res.next()){
				DetalleFactura unDetalle=new DetalleFactura();
				existe=true;
				//se consigue el articulo del detalle
				Articulo articuloDetalle=articuloDao.buscarArticulo(res.getInt("codigo_articulo"));
				articuloDetalle.setPrecioVenta(res.getDouble("precio"));//se estable el precio del articulo
				unDetalle.setListArticulos(articuloDetalle);//se agrega el articulo al 
				unDetalle.setCantidad(res.getBigDecimal("cantidad"));
				unDetalle.setImpuesto(res.getBigDecimal("impuesto"));
				unDetalle.setSubTotal(res.getBigDecimal("subtotal"));
				unDetalle.setDescuentoItem(res.getBigDecimal("descuento"));
				unDetalle.setTotal(res.getBigDecimal("total"));
				
				
				
				
				detalles.add(unDetalle);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(detallesFacturaPendiente != null)detallesFacturaPendiente.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return detalles;
			}
			else return null;
	}

	public boolean eliminarTem(Integer idFactura) {
		
		int resultado=0;
		Connection conn=null;
		try {
			conn=conexion.getPoolConexion().getConnection();
			elimiarTem=conn.prepareStatement("DELETE FROM detalle_factura_temp WHERE numero_factura = ?");
			elimiarTem.setInt( 1, idFactura );
			resultado=elimiarTem.executeUpdate();
			
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

	public List<DetalleFactura> getDetallesFactura(int idFactura) {
		List<DetalleFactura> detalles=new ArrayList<DetalleFactura>();
		

        Connection con = null;
  
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			detallesFacturaPendiente = con.prepareStatement("SELECT * FROM v_detalle_factura where numero_factura_detalle=?;");
			detallesFacturaPendiente.setInt(1, idFactura);
			
			res = detallesFacturaPendiente.executeQuery();
			while(res.next()){
				DetalleFactura unDetalle=new DetalleFactura();
				existe=true;
				//se consigue el articulo del detalle
				Articulo articuloDetalle= new Articulo();//articuloDao.buscarArticulo(res.getInt("codigo_articulo"));
				articuloDetalle.setId(res.getInt("codigo_articulo"));
				articuloDetalle.setArticulo(res.getString("articulo"));
				articuloDetalle.setPrecioVenta(res.getDouble("precio_detalle"));//se estable el precio del articulo
				unDetalle.setListArticulos(articuloDetalle);//se agrega el articulo al 
				unDetalle.setCantidad(res.getBigDecimal("cantidad_detalle"));
				unDetalle.setImpuesto(res.getBigDecimal("impuesto_detalle"));
				unDetalle.setSubTotal(res.getBigDecimal("subtotal_detalle"));
				unDetalle.setDescuentoItem(res.getBigDecimal("descuento_detalle"));
				unDetalle.setTotal(res.getBigDecimal("total_detalle"));
				
				
				
				
				detalles.add(unDetalle);
			 }
					
			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error, no se conecto");
					System.out.println(e);
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(detallesFacturaPendiente != null)detallesFacturaPendiente.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		
			if (existe) {
				return detalles;
			}
			else return null;
	}

	

}
