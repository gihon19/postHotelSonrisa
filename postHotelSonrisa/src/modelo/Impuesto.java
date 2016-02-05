package modelo;

public class Impuesto {
	private int  id;
	private String  porcentaje;
	private boolean agregado=false;
	
	public Impuesto(){
		
	}
	public void setAccion(boolean add){
		agregado=add;
	}
	public boolean getAccion(){
		return agregado;
	}
	public Impuesto(int  i,String  p){
		id=i;
		porcentaje=p;
	}
	
	public void setId(int  i){
		id=i;
	}
	public int  getId(){
		return id;
	}
	
	public void setPorcentaje(String  p){
		porcentaje=p;
	}
	public String  getPorcentaje(){
		return porcentaje;
	}
	
	@Override
	public String toString(){
		return porcentaje;
	}

}
