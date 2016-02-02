package modelo;

public class Marca {
	private int id;
	private String marca;
	private String observacion;
	
	public Marca(){
		
	}
	public void setId(int i){
		id=i;
	}
	public int getId(){
		return id;
	}
	public void setMarca(String m){
		marca=m;
	}
	public String getMarca(){
		return marca;
	}
	public void setObservacion(String o){
		observacion=o;
	}
	public String getObservacion(){
		return observacion;
	}
	
	@Override
	public String toString(){
		return "Codigo Marca: "+getId()+", Marca: "+getMarca()+", Observacion: "+getObservacion();
	}

}
