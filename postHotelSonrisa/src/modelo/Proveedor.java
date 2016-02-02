package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Proveedor extends Persona {
	private int id;
	private String celular;
	//Conexion conex= new Conexion();
	
	//contructor vacio
	public Proveedor(){
		
	}
	
	//contructor con todos los valores del proveedor
	public Proveedor(int i,String n,String t,String c,String d){
		nombre=n;
		id=i;
		celular=t;
		telefono=t;
		direccion=d;
	}
		
	public void setId(int i){
		id=i;
	}
	public int getId(){
		return id;
	}
	
	
	public void setCelular(String c){
		
		celular=c;
	}
	public String getCelular(){
		return celular;
	}
	
		
	@Override
	public String toString(){
		return "Id:"+id+", Nombre:"+nombre+", Telefono:"+telefono+", Celular:"+celular+"\n Direccion:"+direccion;
	}
	
	
}
