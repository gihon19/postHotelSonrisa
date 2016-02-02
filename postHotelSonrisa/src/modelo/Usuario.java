package modelo;

public class Usuario extends Persona {
	private int codigo;
	private String user="";
	private String userOld="";
	private String permiso="";
	private String pwd="";
	private int tipo_permiso;
	
	public void setCodigo(int c){
		codigo=c;
	}
	public int getCodigo(){
		return codigo;
	}
	public void setTipoPermiso(int tp){
		tipo_permiso=tp;
	}
	public int getTipoPermiso(){
		return tipo_permiso;
	}
	
	public void setUser(String u){
		user=u;
	}
	public String getUser(){
		return user;
	}
	public void setUserOld(String u){
		userOld=u;
	}
	public String getUserOld(){
		return userOld;
	}
	
	public void setPermiso(String p){
		permiso=p;
	}
	public String getPermiso(){
		return permiso;
	}
	
	
	public void setPwd(String p){
		pwd=p;
	}
	public String getPwd(){
		return pwd;
	}
	
	@Override
	public String toString(){
		return "Codigo: "
				+this.codigo+ ","
						+ "User: "
						+ this.user+", "
							+ super.toString()+ ","
								+ "Tipo de permiso: "
								+ this.tipo_permiso+ ", "
										+ "Permiso: "
										+ this.permiso+", "
										+"user old: "
										+this.userOld;
						
	}
			

}
