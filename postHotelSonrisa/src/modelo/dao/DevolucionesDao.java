package modelo.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.Conexion;
import modelo.DetalleFactura;
import modelo.Inventario;
import modelo.Kardex;

public class DevolucionesDao {
	
	private PreparedStatement agregarDetalle=null;
	private PreparedStatement actualizarDetalle=null;
	private PreparedStatement detallesFacturaPendiente=null;
	private Conexion conexion=null;
	private PreparedStatement elimiarTem = null;
	private InventarioDao inventarioDao;
	private KardexDao kardexDao;
	private ArticuloDao articuloDao=null;

	public DevolucionesDao(Conexion conn) {
		// TODO Auto-generated constructor stub
		conexion=conn;
		inventarioDao=new InventarioDao(conexion);
		kardexDao=new KardexDao(conexion);
		articuloDao=new ArticuloDao(conexion);
	}
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar detalles de facturas>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean agregarDetalle(DetalleFactura detalle, int idFactura) {
		boolean resultado=false;
		
		String sql="INSERT INTO detalle_devoluciones("
				+ "numero_factura,"
				+ "codigo_articulo,"
				+ "precio,"
				+ "cantidad,"
				+ "impuesto,"
				+ "subtotal,"
				+ "descuento,"
				+ "total,"
				+ "fecha"
				+ ") VALUES (?,?,?,?,?,?,?,?,now())";
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
			
			
			if(detalle.getArticulo().getTipoArticulo()!=2){
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
			
			}
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

}
