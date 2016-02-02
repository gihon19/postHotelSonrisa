package modelo;

import java.math.BigDecimal;

public class SalidaCaja {
	
	private int codigoSalida=-1;
	private String concepto="";
	private BigDecimal cantidad= new BigDecimal(0.00); 
	private String fecha="";
	private String usuario="";
	
	
	public void setCodigoSalida(int c){
		codigoSalida=c;
	}
	public int getCodigoSalida(){
		return codigoSalida;
	}
	
	public void setConcepto(String c){
		concepto=c;
	}
	public String getConcepto(){
		return concepto;
	}
	
	public void setCantidad(BigDecimal c){
		cantidad=c;
	}
	public BigDecimal getCantidad(){
		return cantidad;
	}
	
	public void setFecha(String f){
		fecha=f;
	}
	public String getFecha(){
		return fecha;
	}
	
	public void setUsuario(String u){
		usuario=u;
	}
	public String getUsuario(){
		return usuario;
	}
	
	@Override
	public String toString(){
		return "Codigo="
				+ this.codigoSalida+", Concepto="
						+ this.concepto+", Cantidad="
								+ this.cantidad+", Fecha="
										+ this.fecha+", Usuario="
												+ this.usuario;
	}

	public SalidaCaja() {
		// TODO Auto-generated constructor stub
	}

}
