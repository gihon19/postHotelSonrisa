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
import modelo.DetalleFacturaProveedor;
import modelo.Inventario;
import modelo.Kardex;

public class DetalleFacturaProveedorDao {
	private Conexion conexion;
	private PreparedStatement agregarDetalle=null;
	private InventarioDao inventarioDao;
	private KardexDao kardexDao;
	private ArticuloDao articuloDao=null;
	private PreparedStatement detallesFactura=null;
	public DetalleFacturaProveedorDao(Conexion conn){
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
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para agreagar detalle>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public boolean agregarDetalle(DetalleFacturaProveedor detalle,int noCompra){
		boolean resultado=false;
		Connection conn=null;
		
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			agregarDetalle=conn.prepareStatement( "INSERT INTO detalle_factura_compra(numero_compra,codigo_articulo,precio,cantidad,impuesto,subtotal) VALUES (?,?,?,?,?,?)");
			agregarDetalle.setInt(1, noCompra);
			agregarDetalle.setInt(2, detalle.getArticulo().getId());
			agregarDetalle.setBigDecimal(3, detalle.getPrecioCompra());
			agregarDetalle.setBigDecimal(4, detalle.getCantidad());
			agregarDetalle.setBigDecimal(5, detalle.getImpuesto());
			agregarDetalle.setBigDecimal(6, detalle.getTotal());
			agregarDetalle.executeUpdate();
			
			
			
			Inventario inventario=new Inventario();
			//se consigue el inventario del articulo
			inventario=inventarioDao.buscarArticulo(detalle.getArticulo().getId());
			
			//se verifica que exite el articulo en el inventario
			if(inventario!=null){
				//se agrega al inventario la nueva cantidad
				inventario.incremetarExistencia(detalle.getCantidad().setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
				//se actualizar el articulo del inventario
				inventarioDao.actualizarInventario(inventario);
			}
			else{//sino esta el inventario el articulo, se debe crear el inventario
				
				//para implementar varias bodegas hay que cambiar el codigo de la bodega
				Inventario inventario1=new Inventario();
				inventario1.getBodega().setId(1);
				inventario1.setArticulo(detalle.getArticulo());
				inventario1.setExistencia(detalle.getCantidad().setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
				inventarioDao.agregarInventario(inventario1);				
			}
			
			//se crea y guarda la nuevo movimiento del kardex
			Kardex myKardex =new Kardex();
			
			myKardex.setArticulo(detalle.getArticulo());
			myKardex.setEntrada(detalle.getCantidad().setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
			
			//hay que cambiar para implementar multiples bodegas
			myKardex.getBodega().setId(1);
			myKardex.setNoDocumento(""+noCompra);
			
			kardexDao.agregarEntrada(myKardex);
			
			
			resultado=true;
		}catch (SQLException e) {
			e.printStackTrace();
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
					//Sconexion.desconectar();
				} // fin de catch
		} // fin de finally
		return resultado;
	}
	
	
	/*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Metodo para cargar los detalles>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
	public List<DetalleFacturaProveedor> getDetallesFactura(int idCompra) {
		
		
		List<DetalleFacturaProveedor> detalles=new ArrayList<DetalleFacturaProveedor>();
		

        Connection con = null;
  
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			detallesFactura = con.prepareStatement("SELECT * FROM detalle_factura_compra where numero_compra=?;");
			detallesFactura.setInt(1, idCompra);
			
			res = detallesFactura.executeQuery();
			while(res.next()){
				DetalleFacturaProveedor unDetalle=new DetalleFacturaProveedor();
				existe=true;
				//se consigue el articulo del detalle
				Articulo articuloDetalle=articuloDao.buscarArticulo(res.getInt("codigo_articulo"));
				articuloDetalle.setPrecioVenta(res.getDouble("precio"));//se estable el precio del articulo
				unDetalle.setListArticulos(articuloDetalle);//se agrega el articulo al 
				unDetalle.setCantidad(res.getBigDecimal("cantidad"));
				unDetalle.setImpuesto(res.getBigDecimal("impuesto"));
				//unDetalle.setSubTotal(res.getDouble("subtotal"));
				unDetalle.setTotal(res.getBigDecimal("subtotal"));
				
				
				
				
				detalles.add(unDetalle);
			 }
					
			} catch (SQLException e) {
					//JOptionPane.showMessageDialog(null, "Error, no se conecto");
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(detallesFactura != null)detallesFactura.close();
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
