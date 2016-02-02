package modelo;

import java.math.BigDecimal;

public class DetalleFactura {
	private Articulo articulo=new Articulo();;
	private BigDecimal cantidad=new BigDecimal(1);
	private BigDecimal impuesto=new BigDecimal(0.0);
	private BigDecimal total=new BigDecimal(0.0);
	private BigDecimal subTotal=new BigDecimal(0.0);
	private BigDecimal descuentoItem=new BigDecimal(0.0);
	private int descuento=0;
	private int idFactura=1;
	private boolean accion=false;
	
	public void setAccion(boolean d){
		accion=d;
	}
	public boolean getAccion(){
		return accion;
	}
	
	public BigDecimal getDescuentoItem(){
		return descuentoItem;
	}
	public void setDescuentoItem(BigDecimal d){
		descuentoItem=d;
	}
	
	public BigDecimal getSubTotal(){
		return subTotal;
	}
	public void setSubTotal(BigDecimal s){
		subTotal=s;
	}
	public void setDescuento(int d){
		descuento=d;
	}
	public int getDescuento(){
		return descuento;
	}
	public void setIdFactura(int id){
		idFactura=id;
	}
	public int getIdFactura(){
		return idFactura;
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
