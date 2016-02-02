package modelo.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.CierreCaja;
import modelo.Conexion;
import modelo.SalidaCaja;

public class SalidaCajaDao {

	private Conexion conexion=null;
	private PreparedStatement seleccionarCierre=null;
	private PreparedStatement registrarCierre=null;
	public int idUltimoRequistro=0;
	private int idRegistrado;
	
	public SalidaCajaDao(Conexion conn){
		//Class(Conexion);
		conexion =conn;
	}
	
	public boolean registrarCierre(){
		boolean resultado=false;
		 Connection con = null;
		 
		 ResultSet rs=null;
		 
		 //SE CONSIGUE EL ITEM PARA EL CIERRE DE CAJA
		 CierreCaja unCierre=this.getCierre();
		 String sql= "INSERT INTO cierre_caja("
					+ "fecha,"
					+ "factura_inicial,"
					+ "factura_final,"
					+ "efectivo,"
					+ "creditos,"
					+ "totalventa,"
					+ "tarjeta,"
					+ "usuario,"
					+ "isv15,"
					+ "isv18)"
					+ " VALUES (now(),?,?,?,?,?,?,?,?,?)";
			 if(unCierre!=null&&unCierre.getNoFacturaFinal()!=0){
				 try {
						con = conexion.getPoolConexion().getConnection();
						registrarCierre=
								con.prepareStatement(sql);
						
						registrarCierre.setInt(1,unCierre.getNoFacturaInicio() );
						registrarCierre.setInt(2,unCierre.getNoFacturaFinal() );
						registrarCierre.setBigDecimal(3, unCierre.getEfectivo());
						registrarCierre.setBigDecimal(4, unCierre.getCredito());
						registrarCierre.setBigDecimal(5, unCierre.getTotal());
						registrarCierre.setBigDecimal(6, unCierre.getTarjeta());
						registrarCierre.setString(7, unCierre.getUsuario());
						registrarCierre.setBigDecimal(8, unCierre.getIsv15());
						registrarCierre.setBigDecimal(9, unCierre.getIsv18());
						
						
						
						
						registrarCierre.executeUpdate();//se guarda el encabezado de la factura
						
						
						rs=registrarCierre.getGeneratedKeys(); //obtengo las ultimas llaves generadas
						while(rs.next()){
							this.idUltimoRequistro=rs.getInt(1);
							//this.setIdArticuloRegistrado(rs.getInt(1));
						}
						resultado=true;
						
				 }catch (SQLException e) {
						e.printStackTrace();
						resultado=false;
					}
				finally
				{
					try{
						
						if(rs != null) rs.close();
						if(registrarCierre != null)registrarCierre.close();
						if(con != null) con.close();
		             
						
						} // fin de try
						catch ( SQLException excepcionSql )
						{
							excepcionSql.printStackTrace();
							//conexion.desconectar();
						} // fin de catch
				} // fin de finally
		
		 }else{
			 resultado=false;
		 }
		
		return resultado;
	}
	public boolean actualizarCierre(){
		boolean resultado=false;
		 Connection con = null;
		 
		 ResultSet rs=null;
		 
		 //SE CONSIGUE EL ITEM PARA EL CIERRE DE CAJA
		 CierreCaja unCierre=this.getCierre(1);
		 String sql= "update cierre_caja set "
		 		+ "fecha=now(),"
		 		+ "factura_final=?,"
		 		+ "efectivo=?,"
		 		+ "creditos=?,"
		 		+ "tarjeta=?,"
		 		+ "isv15=?,"
		 		+ "isv18=?,"
		 		+ "total_isv15=?,"
		 		+ "total_isv18=?,"
		 		+ "total_excento=?,"
		 		+ "estado=?,"
		 		+ "total_efectivo=?,"
		 		+ "totalventa=? "
		 		+ "where "
		 		+ "idCierre=?";
		 		//+ "
			 if(unCierre!=null&&unCierre.getNoFacturaFinal()!=0){
				 try {
						con = conexion.getPoolConexion().getConnection();
						registrarCierre=
								con.prepareStatement(sql);
						
						registrarCierre.setInt(1,unCierre.getNoFacturaFinal() );
						registrarCierre.setDouble(2,unCierre.getEfectivo().setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
						registrarCierre.setBigDecimal(3, unCierre.getCredito().setScale(2, BigDecimal.ROUND_HALF_EVEN));
						registrarCierre.setBigDecimal(4, unCierre.getTarjeta().setScale(2, BigDecimal.ROUND_HALF_EVEN));
						
						registrarCierre.setBigDecimal(5, unCierre.getIsv15().setScale(2, BigDecimal.ROUND_HALF_EVEN));
						
						registrarCierre.setBigDecimal(6, unCierre.getIsv18().setScale(2, BigDecimal.ROUND_HALF_EVEN));
						
						registrarCierre.setBigDecimal(7, unCierre.getTotalIsv15().setScale(2, BigDecimal.ROUND_HALF_EVEN));
						registrarCierre.setBigDecimal(8, unCierre.getTotalIsv18().setScale(2, BigDecimal.ROUND_HALF_EVEN));
						
						registrarCierre.setBigDecimal(9, unCierre.getTotalExcento().setScale(2, BigDecimal.ROUND_HALF_EVEN));
						
						registrarCierre.setInt(10, 0);
						registrarCierre.setBigDecimal(11, unCierre.getTotalEfectivo().setScale(2, BigDecimal.ROUND_HALF_EVEN));
						registrarCierre.setBigDecimal(12, unCierre.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN));
						registrarCierre.setInt(13, unCierre.getId());
						
						
						
						
						
						registrarCierre.executeUpdate();//se guarda el encabezado de la factura
						
						
						rs=registrarCierre.getGeneratedKeys(); //obtengo las ultimas llaves generadas
						while(rs.next()){
							this.idUltimoRequistro=rs.getInt(1);
							//this.setIdArticuloRegistrado(rs.getInt(1));
						}
						this.idUltimoRequistro=unCierre.getId();
						resultado=true;
						
				 }catch (SQLException e) {
						e.printStackTrace();
						resultado=false;
					}
				finally
				{
					try{
						
						if(rs != null) rs.close();
						if(registrarCierre != null)registrarCierre.close();
						if(con != null) con.close();
		             
						
						} // fin de try
						catch ( SQLException excepcionSql )
						{
							excepcionSql.printStackTrace();
							//conexion.desconectar();
						} // fin de catch
				} // fin de finally
		
		 }else{
			 resultado=false;
		 }
		
		return resultado;
	}
	
	public boolean registrarCierre(CierreCaja unCierre){
		boolean resultado=false;
		 Connection con = null;
		 
		 ResultSet rs=null;
		 
		 
		 String sql= "INSERT INTO cierre_caja("
					+ "fecha,"
					+ "factura_inicial,"
					+ "factura_final,"
					+ "efectivo,"
					+ "creditos,"
					+ "totalventa,"
					+ "tarjeta,"
					+ "usuario,"
					+ "isv15,"
					+ "isv18,"
					+ "efectivo_inicial)"
					+ " VALUES (now(),?,?,?,?,?,?,?,?,?,?)";
			 if(unCierre!=null){
				 try {
						con = conexion.getPoolConexion().getConnection();
						registrarCierre=
								con.prepareStatement(sql);
						
						registrarCierre.setInt(1,unCierre.getNoFacturaFinal() );
						registrarCierre.setInt(2,0 );
						registrarCierre.setBigDecimal(3, unCierre.getEfectivo());
						registrarCierre.setBigDecimal(4, unCierre.getCredito());
						registrarCierre.setBigDecimal(5, unCierre.getTotal());
						registrarCierre.setBigDecimal(6, unCierre.getTarjeta());
						registrarCierre.setString(7, unCierre.getUsuario());
						registrarCierre.setBigDecimal(8, unCierre.getIsv15());
						registrarCierre.setBigDecimal(9, unCierre.getIsv18());
						registrarCierre.setBigDecimal(10,unCierre.getEfectivoInicial());
						
						
						
						
						registrarCierre.executeUpdate();//se guarda el encabezado de la factura
						
						
						rs=registrarCierre.getGeneratedKeys(); //obtengo las ultimas llaves generadas
						while(rs.next()){
							this.idUltimoRequistro=rs.getInt(1);
							//this.setIdArticuloRegistrado(rs.getInt(1));
						}
						resultado=true;
						
				 }catch (SQLException e) {
						e.printStackTrace();
						resultado=false;
					}
				finally
				{
					try{
						
						if(rs != null) rs.close();
						if(registrarCierre != null)registrarCierre.close();
						if(con != null) con.close();
		             
						
						} // fin de try
						catch ( SQLException excepcionSql )
						{
							excepcionSql.printStackTrace();
							//conexion.desconectar();
						} // fin de catch
				} // fin de finally
		
		 }else{
			 resultado=false;
		 }
		
		return resultado;
	}
	
	
	
	
	
public SalidaCaja getSalidaUltimoUser(){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        
    	//String sql="select * from cierre where usuario = ?";
    	
    	String sql2="SELECT * FROM salidas_caja WHERE salidas_caja.usuario=? ORDER BY salidas_caja.codigo_salida DESC LIMIT 1";
        //Statement stmt = null;
    	SalidaCaja unaSalida=new SalidaCaja();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarCierre = con.prepareStatement(sql2);
			
			seleccionarCierre.setString(1, conexion.getUsuarioLogin().getUser());
			res = seleccionarCierre.executeQuery();
			while(res.next()){
				
				existe=true;
				unaSalida.setCodigoSalida(res.getInt("codigo_salida"));
				unaSalida.setConcepto(res.getString("concepto"));
				unaSalida.setCantidad(res.getBigDecimal("cantidad"));
				unaSalida.setFecha(res.getString("fecha"));
				unaSalida.setUsuario(res.getString("usuario"));
			
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarCierre != null)seleccionarCierre.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		return unaSalida;
			/*if (existe) {
				return unaCierre;
			}
			else return null;*/
		
	}
	public CierreCaja getCierre(){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        //SE CONSIGUE EL ITEM PARA EL CIERRE DE CAJA
		 CierreCaja ultimoCierreUser=new CierreCaja();//this.getCierreUltimoUser();
        
    	String sql="select * from cierre where usuario = ?";
    	
    	String sql2=""
    			+ " select "
    			+ " date_format(now(),'%d/%m/%Y %h:%i:%s') AS `fecha`, "
    			+ "	("
    			+ "		select "
    			+ "			`encabezado_factura`.`numero_factura` "
    			+ "		from "
    			+ "			`encabezado_factura` "
    			+ "		WHERE "
    			+ "			encabezado_factura.usuario ='"
    			+ 		conexion.getUsuarioLogin().getUser()+"'"
    			+ "		order by "
    			+ "			`encabezado_factura`.`numero_factura` desc "
    			+ "		limit 1"
    			+ "	) AS `factura_ultima`,"
    			+ "	("
    			+ "		select "
    			+ "			sum("
    			+ "				`encabezado_factura`.`total` "
    			+ "			) AS `total_efectivo` "
    			+ "		from "
    			+ "			`encabezado_factura` "
    			+ "		where "
    			+ "			("
    			+ "				("
    			+ "					`encabezado_factura`.`tipo_factura` = 1"
    			+ "				) "
    			+ "				and "
    			+ "				("
    			+ "					`encabezado_factura`.`estado_factura` = 'ACT'"
    			+ "				) "
    			+ "				and "
    			+ "				("
    			+ "					`encabezado_factura`.`numero_factura` > "+ultimoCierreUser.getNoFacturaFinal()
    			+ "				) "
    			+ "				and "
    			+ "				("
    			+ "					`encabezado_factura`.`numero_factura` <= `factura_ultima`"
    			+ "				) "
    			+ "				and "
    			+ "				("
    			+ "					`encabezado_factura`.`tipo_pago` = 1"
    			+ "				)"
    			+ "				and "
    			+ "				( "
    			+ "					encabezado_factura.usuario ='"
    			+ 					conexion.getUsuarioLogin().getUser()+"'"
    			+ "				)"
    			+ "			)"
    			+ "	) AS `total_efectivo`,"
    			+ "	("
    			+ "		select "
    			+ "			sum("
    			+ "				`encabezado_factura`.`total` "
    			+ "			) AS `total_efectivo` "
    			+ "		from "
    			+ "			`encabezado_factura` "
    			+ "		where "
    			+ "			("
    			+ "				("
    			+ "					`encabezado_factura`.`tipo_factura` = 1"
    			+ "				) "
    			+ "				and ("
    			+ "					`encabezado_factura`.`estado_factura` = 'ACT'"
    			+ "				) "
    			+ "				and ("
    			+ "					`encabezado_factura`.`numero_factura` > "+ultimoCierreUser.getNoFacturaFinal()
    			+ "				) "
    			+ "				and ("
    			+ "					`encabezado_factura`.`numero_factura` <= `factura_ultima`"
    			+ "				) "
    			+ "				and ("
    			+ "					`encabezado_factura`.`tipo_pago` = 2"
    			+ "				) "
    			+ "				and("
    			+ "					encabezado_factura.usuario ='"
    			+ 				conexion.getUsuarioLogin().getUser()+"'"
    			+ "				)"
    			+ "			)"
    			+ "	) AS `total_tarjeta`,"
    			+ "	("
    			+ "		select "
    			+ "			sum("
    			+ "				`encabezado_factura`.`total` "
    			+ "			) AS `total_efectivo` "
    			+ "		from "
    			+ "			`encabezado_factura` "
    			+ "		where "
    			+ "			("
    			+ "				("
    			+ "					`encabezado_factura`.`tipo_factura` = 2"
    			+ "				) "
    			+ "				and ("
    			+ "					`encabezado_factura`.`estado_factura` = 'ACT'"
    			+ "				) "
    			+ "				and ("
    			+ "					`encabezado_factura`.`numero_factura` > "+ultimoCierreUser.getNoFacturaFinal()
    			+ "				) "
    			+ "				and ("
    			+ "					`encabezado_factura`.`numero_factura` <= `factura_ultima`"
    			+ "				) "
    			+ "				and( "
    			+ "					encabezado_factura.usuario = '"
    			+ 						conexion.getUsuarioLogin().getUser()+"'"
    			+ "				)"
    			+ "			)"
    			+ ") AS `total_credito`,"
    			+ "("
    			+ "		SELECT "
    			+ "			sum( "
				+ "				`encabezado_factura`.`impuesto` "
				+"			) AS `total_isv15` "
				+"		FROM "
				+"			`encabezado_factura` "
				+"		WHERE "
				+"			( "
				+"				( "
				+"					`encabezado_factura`.`tipo_factura` = 1 "
				+"				) "
				+"				AND ( "
				+"					`encabezado_factura`.`estado_factura` = 'ACT' "
				+"				) "
				+"				AND ( "
				+"					`encabezado_factura`.`numero_factura` > "+ultimoCierreUser.getNoFacturaFinal()
				+"				) "
				+"				AND ( "
				+"					`encabezado_factura`.`numero_factura` <= `factura_ultima` "
				+				")"
				+"				AND ( "
				+"					encabezado_factura.usuario = '"
				+ 					conexion.getUsuarioLogin().getUser()+"'"
				+"				) "
				+"			) "
				+") AS `total_isv15`, "
    			+ "("
    			+"		SELECT "
    			+"			sum( "
				+"				`encabezado_factura`.`isv18` "
				+"			) AS `total_isv18` "
				+"		FROM "
				+"			`encabezado_factura` "
				+"		WHERE  "
				+"		( "
				+"				( "
				+"					`encabezado_factura`.`tipo_factura` = 1 "
				+"				) "
				+"			AND ( "
				+"				`encabezado_factura`.`estado_factura` = 'ACT' "
				+"			) "
				+"			AND ( "
				+"				`encabezado_factura`.`numero_factura` > "+ultimoCierreUser.getNoFacturaFinal()
				+"			) "
				+"			AND ( "
				+"				`encabezado_factura`.`numero_factura` <= `factura_ultima` "
				+"			) "
				+"			AND ( "
				+"					encabezado_factura.usuario = '"
				+ 				conexion.getUsuarioLogin().getUser()+"'"
				+"			) "
				+"		) "
				+") AS `total_isv18`, "
    			+ "	("
    			+ "		select "
    			+ "			sum("
    			+ "				`encabezado_factura`.`total` "
    			+ "			) AS `total_efectivo` "
    			+ "			from "
    			+ "				`encabezado_factura` "
    			+ "			where "
    			+ "			("
    			+ "				("
    			+ "					`encabezado_factura`.`estado_factura` = 'ACT'"
    			+ "				) "
    			+ "				and ("
    			+ "					`encabezado_factura`.`numero_factura` > "+ultimoCierreUser.getNoFacturaFinal()
    			+ "				) "
    			+ "				and ("
    			+ "					`encabezado_factura`.`numero_factura` <= `factura_ultima`"
    			+ "				) "
    			+ "				and( "
    			+ "					encabezado_factura.usuario = '"
    			+ 					conexion.getUsuarioLogin().getUser()+"'"
    			+ "				)"
    			+ "			)"
    			+ "	) AS `total` "
    			+ "	from "
    			+ "		`encabezado_factura` "
    			+ "	where ((`encabezado_factura`.`numero_factura` > (select `cierre_caja`.`factura_final` from `cierre_caja` order by `cierre_caja`.`idCierre` desc limit 1)) and (`encabezado_factura`.`numero_factura` <= (select `encabezado_factura`.`numero_factura` from `encabezado_factura` order by `encabezado_factura`.`numero_factura` desc limit 1))) limit 1; ";
        //Statement stmt = null;
    	CierreCaja unaCierre=new CierreCaja();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarCierre = con.prepareStatement(sql2);
			
			//seleccionarCierre.setString(1, conexion.getUsuarioLogin().getUser());
			res = seleccionarCierre.executeQuery();
			while(res.next()){
				
				existe=true;
				
				unaCierre.setNoFacturaInicio(ultimoCierreUser.getNoFacturaFinal());
				unaCierre.setNoFacturaFinal(res.getInt("factura_ultima"));
				unaCierre.setEfectivo(res.getBigDecimal("total_efectivo"));
				unaCierre.setCredito(res.getBigDecimal("total_credito"));
				unaCierre.setTarjeta(res.getBigDecimal("total_tarjeta"));
				
				unaCierre.setIsv15(res.getBigDecimal("total_isv15"));
				unaCierre.setIsv18(res.getBigDecimal("total_isv18"));
				
				unaCierre.setTotal(res.getBigDecimal("total"));
				unaCierre.setUsuario(conexion.getUsuarioLogin().getUser());//.setTotal(res.getBigDecimal("total"));
			
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarCierre != null)seleccionarCierre.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		return unaCierre;
			/*if (existe) {
				return unaCierre;
			}
			else return null;*/
		
	}
	
public CierreCaja getCierre(int x){
		
		//se crear un referencia al pool de conexiones
		//DataSource ds = DBCPDataSourceFactory.getDataSource("mysql");
		
		
        Connection con = null;
        //SE CONSIGUE EL ITEM PARA EL CIERRE DE CAJA
		 CierreCaja ultimoCierreUser=new CierreCaja();//this.getCierreUltimoUser();
        
    	String sql="SELECT "
    				+ " ifnull(sum(v_cierre_cajeros.t_exectos),0)AS t_exectos,"
	    			+ "ifnull(sum(v_cierre_cajeros.total_isv15),0)AS total_isv15,"
	    			+ "ifnull(sum(v_cierre_cajeros.isv15),0)AS isv15,"
	    			+ "ifnull(sum(v_cierre_cajeros.total_isv18),0)AS total_isv18,"
	    			+ "ifnull(sum(v_cierre_cajeros.isv18),0)AS isv18,"
	    			+ "ifnull(sum(v_cierre_cajeros.total_credito),0)AS total_credito,"
	    			+ "sum("
	    			+ "v_cierre_cajeros.total_efectivo"
	    			+ ")AS total_efectivo,"
	    			+ " ifnull(sum(v_cierre_cajeros.total_tarjeta),0)AS total_tarjeta,"
	    			+ "max("
	    			+ "`v_cierre_cajeros`.`numero_factura`"
	    			+ ")AS `no_factura_final`,"
	    			+ "min("
	    			+ "`v_cierre_cajeros`.`numero_factura`"
	    			+ ")AS `no_factura_inicio`,"
	    			+ "sum(v_cierre_cajeros.total) as total"
    			+ " FROM "
    			+ "v_cierre_cajeros"
	    			+ " WHERE "
	    			+ " v_cierre_cajeros.usuario = ? "
	    			+ " AND v_cierre_cajeros.numero_factura > ?"
	    			+ " AND v_cierre_cajeros.numero_factura <=("
		    			+ " SELECT "
		    			+ " encabezado_factura.numero_factura"
		    			+ " FROM"
		    			+ " encabezado_factura "
		    			+ " WHERE "
		    			+ " encabezado_factura.usuario = ?"
		    			+ " ORDER BY"
		    			+ " encabezado_factura.numero_factura DESC"
		    			+ " LIMIT 1"
		    		+ ")";
    	
    	
        //Statement stmt = null;
    	CierreCaja unaCierre=new CierreCaja();
		
		ResultSet res=null;
		
		boolean existe=false;
		try {
			con = conexion.getPoolConexion().getConnection();
			
			
			seleccionarCierre = con.prepareStatement(sql);
			seleccionarCierre.setString(1,ultimoCierreUser.getUsuario() );
			
			seleccionarCierre.setInt(2, ultimoCierreUser.getNoFacturaInicio());
			seleccionarCierre.setString(3,ultimoCierreUser.getUsuario() );
			//seleccionarCierre.setString(1, conexion.getUsuarioLogin().getUser());
			res = seleccionarCierre.executeQuery();
			while(res.next()){
				
				existe=true;
				
				unaCierre.setId(ultimoCierreUser.getId());
				unaCierre.setNoFacturaInicio(ultimoCierreUser.getNoFacturaInicio());
				unaCierre.setNoFacturaFinal(res.getInt("no_factura_final"));
				unaCierre.setEfectivo(res.getBigDecimal("total_efectivo"));
				unaCierre.setCredito(res.getBigDecimal("total_credito"));
				unaCierre.setTarjeta(res.getBigDecimal("total_tarjeta"));
				unaCierre.setTotalExcento(res.getBigDecimal("t_exectos"));
				unaCierre.setTotalIsv15(res.getBigDecimal("total_isv15"));
				unaCierre.setTotalIsv18(res.getBigDecimal("total_isv18"));
				unaCierre.setNoFacturaFinal(res.getInt("no_factura_final"));
				
				unaCierre.setIsv15(res.getBigDecimal("isv15"));
				unaCierre.setIsv18(res.getBigDecimal("isv18"));
				
				BigDecimal t_efectivo=ultimoCierreUser.getEfectivoInicial().add(res.getBigDecimal("total_efectivo"));//se suma el efectivo inicial y la venta con efectivo
				
				unaCierre.setTotalEfectivo(t_efectivo);
				
				unaCierre.setTotal(res.getBigDecimal("total"));
				unaCierre.setUsuario(conexion.getUsuarioLogin().getUser());//.setTotal(res.getBigDecimal("total"));
			
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarCierre != null)seleccionarCierre.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
		
		return unaCierre;
			/*if (existe) {
				return unaCierre;
			}
			else return null;*/
		
	}

	public List<CierreCaja> todos() {
		List<CierreCaja> cierres =new ArrayList<CierreCaja>();
		
		ResultSet res=null;
		
		Connection conn=null;
		
		boolean existe=false;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			seleccionarCierre=conn.prepareStatement("SELECT * FROM v_cierre_caja ORDER BY idCierre Desc");
			res = seleccionarCierre.executeQuery();
			while(res.next()){
				existe=true;
				CierreCaja unaCierre=new CierreCaja();
				unaCierre.setFecha(res.getString("fecha"));
				unaCierre.setId(res.getInt("idCierre"));
				unaCierre.setNoFacturaInicio(res.getInt("factura_inicial"));
				unaCierre.setNoFacturaFinal(res.getInt("factura_final"));
				unaCierre.setEfectivo(res.getBigDecimal("efectivo"));
				unaCierre.setCredito(res.getBigDecimal("creditos"));
				unaCierre.setTarjeta(res.getBigDecimal("tarjeta"));
				
				unaCierre.setIsv15(res.getBigDecimal("isv15"));
				unaCierre.setIsv18(res.getBigDecimal("isv18"));
				
				unaCierre.setTotal(res.getBigDecimal("totalventa"));
				unaCierre.setUsuario(res.getString("usuario"));
				
				
				cierres.add(unaCierre);
				
				
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error, no se conecto");
			System.out.println(e);
	}
	finally
	{
		try{
			if(res != null) res.close();
	        if(seleccionarCierre != null)seleccionarCierre.close();
	        if(conn != null) conn.close();
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
	} // fin de finally
		
		
		if (existe) {
			return cierres;
		}
		else return null;
	}

	public List<CierreCaja> porFecha(String date, String date2) {
		List<CierreCaja> cierres =new ArrayList<CierreCaja>();
		
		ResultSet res=null;
		
		Connection conn=null;
		
		boolean existe=false;
		
		try{
			conn=conexion.getPoolConexion().getConnection();
			seleccionarCierre=conn.prepareStatement("SELECT * FROM v_cierre_caja where fecha2 BETWEEN ? and ?");
			seleccionarCierre.setString(1, date);
			seleccionarCierre.setString(2, date2);
			res = seleccionarCierre.executeQuery();
			while(res.next()){
				existe=true;
				CierreCaja unaCierre=new CierreCaja();
				unaCierre.setFecha(res.getString("fecha"));
				unaCierre.setId(res.getInt("idCierre"));
				unaCierre.setNoFacturaInicio(res.getInt("factura_inicial"));
				unaCierre.setNoFacturaFinal(res.getInt("factura_final"));
				unaCierre.setEfectivo(res.getBigDecimal("efectivo"));
				unaCierre.setCredito(res.getBigDecimal("creditos"));
				unaCierre.setTarjeta(res.getBigDecimal("tarjeta"));
				
				unaCierre.setIsv15(res.getBigDecimal("isv15"));
				unaCierre.setIsv18(res.getBigDecimal("isv18"));
				
				unaCierre.setTotal(res.getBigDecimal("totalventa"));
				unaCierre.setUsuario(res.getString("usuario"));
				
				
				cierres.add(unaCierre);
				
				
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error, no se conecto");
			System.out.println(e);
	}
	finally
	{
		try{
			if(res != null) res.close();
	        if(seleccionarCierre != null)seleccionarCierre.close();
	        if(conn != null) conn.close();
			
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
	} // fin de finally
		
		
		if (existe) {
			return cierres;
		}
		else return null;
	}

	public void calcularTotal(CierreCaja unaCierre) {
		// TODO Auto-generated method stub
		
		
		Connection con = null;
		ResultSet res=null;
		
		//se consiguie el ultima salida que realizo el usuario
		SalidaCaja ultimo=this.getSalidaUltimoUser();
		//se establece la ultima salida del usuaurio
		unaCierre.setNoSalidaFinal(ultimo.getCodigoSalida());
		
		
		
		
		
		String sql="SELECT ifnull(sum(salidas_caja.cantidad),0)AS cantidad FROM salidas_caja WHERE salidas_caja.codigo_salida >= ? AND salidas_caja.codigo_salida <= ? and salidas_caja.usuario=?";
		
		try {
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarCierre = con.prepareStatement(sql);
			
			seleccionarCierre.setInt(1, unaCierre.getNoSalidaInicial());
			seleccionarCierre.setInt(2, unaCierre.getNoSalidaFinal());
			seleccionarCierre.setString(3, unaCierre.getUsuario());
			
			
			//seleccionarCierre.setString(1, conexion.getUsuarioLogin().getUser());
			res = seleccionarCierre.executeQuery();
			while(res.next()){
				
				
				
				//si existe alguna salida se suma y se establecen en el cierre
				unaCierre.setTotalSalida(res.getBigDecimal("cantidad"));
				
	
			
			 }
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally
		{
			try{
				
				if(res != null) res.close();
                if(seleccionarCierre != null)seleccionarCierre.close();
                if(con != null) con.close();
                
				
				} // fin de try
				catch ( SQLException excepcionSql )
				{
					excepcionSql.printStackTrace();
					//conexion.desconectar();
				} // fin de catch
		} // fin de finally
	
		
	}

	public boolean registrarSalida(SalidaCaja mySalida) {
		// TODO Auto-generated method stub
		int resultado=0;
		ResultSet rs=null;
		Connection con = null;
	
		try 
		{
			con = conexion.getPoolConexion().getConnection();
			
			seleccionarCierre=con.prepareStatement( "INSERT INTO salidas_caja(concepto,cantidad,usuario,fecha) VALUES (?,?,?,now())");
			
			seleccionarCierre.setString( 1, mySalida.getConcepto());
			seleccionarCierre.setBigDecimal( 2, mySalida.getCantidad().setScale(2, BigDecimal.ROUND_HALF_EVEN) );
			seleccionarCierre.setString( 3, conexion.getUsuarioLogin().getUser());
			
			
			resultado=seleccionarCierre.executeUpdate();
			
			rs=seleccionarCierre.getGeneratedKeys(); //obtengo las ultimas llaves generadas
			while(rs.next()){
				this.setIdRegistrado(rs.getInt(1));
			}
			
			
			
			

			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//conexion.desconectar();
            return false;
		}
		finally
		{
			try{
				if(rs!=null)rs.close();
				 if(seleccionarCierre != null)seleccionarCierre.close();
	              if(con != null) con.close();
			} // fin de try
			catch ( SQLException excepcionSql )
			{
				excepcionSql.printStackTrace();
				//conexion.desconectar();
			} // fin de catch
		} // fin de finally
	}

	private void setIdRegistrado(int i) {
		// TODO Auto-generated method stub
		idRegistrado=i;
	}
	public int getIdRegistrado(){
		return idRegistrado;
	} 

}
