package modelo;

import java.math.BigDecimal;




public class DetalleFacturaProveedor {
	private Articulo articulo;
	private BigDecimal cantidad=new BigDecimal(1);
	private BigDecimal impuesto=new BigDecimal(0.0);
	private BigDecimal total=new BigDecimal(0.0);
	private BigDecimal precioCompra=new BigDecimal(0.0);
	
	

	
	public DetalleFacturaProveedor(){
		articulo=new Articulo();
	}
	public DetalleFacturaProveedor(Articulo a,double c,double i,double t){
		
	}
	
	public void setPrecioCompra(BigDecimal p){
		precioCompra=p;
	}
	public BigDecimal getPrecioCompra(){
		return precioCompra;
	}
	public void setListArticulos(Articulo a){
		articulo=a;
	}
	public Articulo getArticulo(){
		return articulo;
	}
	
	public void setCantidad(BigDecimal c){
		cantidad=c;
	}
	public BigDecimal getCantidad(){
		return cantidad;
	}
	
	public void setImpuesto(BigDecimal i){
		impuesto=i;
	}
	public BigDecimal getImpuesto(){
		return impuesto;
	}
	
	public void setTotal(BigDecimal t){
		total=t;
	}
	public BigDecimal getTotal(){
		return total;
	}
}
