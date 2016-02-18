

package modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.apache.commons.dbcp.BasicDataSource;



/**
 * Clase que permite conectar con la base de datos
 * @author jdmayorga
 *
 */
public class Conexion {
	
	
		/*static String bd = "miswendy_admin_tools";
	   static String login = "miswendy_pos";
	   static String password = "bTp.rxN-*~Z4";
	   static String server = "108.167.189.55";
	   static String url = "jdbc:mysql://108.167.189.55:3306/"+bd;*/
	
	
	//10.10.10.8:3306
		
	  /* static String bd = "jdmayorg_admin_tools";
	   static String login = "jdmayorg_user";
	   static String password = "Admin321?";
	   static String url = "jdbc:mysql://108.179.234.89:3306/"+bd;*/
	   
		private  BasicDataSource basicDataSource;
	   private DataSource dataSource;
	   
	   static String bd = "admin_tools";
	   static String login = "root";
	   //static String password = "TOSHIBA";
	   static String password = "jdmm123.";
	   static String url = "jdbc:mysql://localhost:3306/"+bd;
	   
	   
	/*  static String bd = "admin_tools";
	   static String login = "root";
	   static String password = "texaco123.";
	   static String url = "jdbc:mysql://10.10.10.8:3306/"+bd;*/
	   
	   
	   static String driver="com.mysql.jdbc.Driver";
	   private Usuario usuarioLogin=null;

   Connection conn = null;
   
   private static DataSource poolConexiones=null;
   
   

   /** Constructor de DbConnection */
   public Conexion() {
	   
	   poolConexiones=Conexion.setDataSource("mysql");
	   
	   /*BasicDataSource basicDataSource = new BasicDataSource();
	   
	   basicDataSource.setDriverClassName(driver);
	   basicDataSource.setUsername(login);
	   basicDataSource.setPassword(password);
	   basicDataSource.setUrl(url);
	   basicDataSource.setMaxActive(50);
	  
	   
	  // basicDataSource.setValidationQuery("select 1");

	    dataSource = basicDataSource;
	   
	   try {

		   conn=basicDataSource.getConnection();// dataSource.getConnection();

           if(conn!=null){

               //JOptionPane.showMessageDialog(null, "Conectado");
               System.out.println("Conecci�n a base de datos "+bd+" OK");

           }

       } catch (SQLException e) {

           System.out.println(e);

       }/*finally{


               try {

            	   conn.close();

               } catch (SQLException ex) {

                   System.out.println(ex);

               }


       }
	   
	   
	   
	   
     /* try{
         //obtenemos el driver de para mysql
         Class.forName(driver);
         //obtenemos la conexi�n
         conn = DriverManager.getConnection(url,login,password);

         if (conn!=null){
            System.out.println("Conecci�n a base de datos "+bd+" OK");
         }
      }
      catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }catch(Exception e){
         System.out.println(e);
      }*/
	  // conn.p
   }
   public Usuario getUsuarioLogin(){
	   return usuarioLogin;
   }
   public void setUsuarioLogin(Usuario u){
	   usuarioLogin=u;
	   
   }
  
   /**Permite retornar la conexi�n*/
   public Connection getConnection(){
	  
      return conn;
   }

  /* public void desconectar(){
      //conn = null;
      try {
		conn.close();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }*/
   
   public static DataSource getPoolConexion(){
	   return poolConexiones;
   }
   
   public static DataSource setDataSource(String dbType){
       //Properties props = new Properties();
       //FileInputStream fis = null;
       BasicDataSource ds = new BasicDataSource();
        
       /*try {
          fis = new FileInputStream( "db.config");
    	   //Conexion.class.getResource("/View/imagen/actualizar.png");
    	   //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	   //fis = ClassLoader.class.getResourceAsStream("/path/to/your/xml");
           props.load(fis);
       }catch(IOException e){
           e.printStackTrace();
           return null;
       }*/
       if("mysql".equals(dbType)){
           ds.setDriverClassName(driver);
           ds.setUrl(url);
           ds.setUsername(login);
           ds.setPassword(password);
          // ds.setMinIdle(20);
           ds.setMaxActive(10);
           ds.setMaxIdle(5);
           ds.setMinIdle(3);
           ds.setInitialSize(2);
           
           
           
           
           
           /*ds.setDriverClassName(props.getProperty("MYSQL_DB_DRIVER_CLASS"));
           ds.setUrl(props.getProperty("MYSQL_DB_URL"));
           ds.setUsername(props.getProperty("MYSQL_DB_USERNAME"));
           ds.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
          // ds.setMinIdle(20);
           ds.setMaxActive(10);
           ds.setMaxIdle(5);
           ds.setMinIdle(3);
           ds.setInitialSize(2);*/
       }/*else if("oracle".equals(dbType)){
           ds.setDriverClassName(props.getProperty("ORACLE_DB_DRIVER_CLASS"));
           ds.setUrl(props.getProperty("ORACLE_DB_URL"));
           ds.setUsername(props.getProperty("ORACLE_DB_USERNAME"));
           ds.setPassword(props.getProperty("ORACLE_DB_PASSWORD"));
       }*/else{
           return null;
       }
        
       return ds;
   }

}