/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class ConnectionFactory {

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    public ConnectionFactory(){
        try{
            //Class.forName("com.oracle.cj.jdbc.Driver");
            //con=DriverManager.getConnection("jdbc:oracle://localhost/1521user=scott&password=admin");
            //stmt=con.createStatement();
        con=DriverManager.getConnection("jdbc:oracle:thin:@LAPTOP-CR6FLS09:1521:orcl", "scott", "admin");
        System.out.println(" Database connected");
        stmt=con.createStatement();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Connection getConnection(){
        try{
          //  Class.forName("com.oracle.cj.jdbc.Driver");
           // con=DriverManager.getConnection("jdbc:oracle://localhost/1521user=scott&password=admin");
        Connection conn;
        Statement stmt1;
        ResultSet rs1;
        //load the driver
        System.out.println(" Registering Driver........");
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        System.out.println(" Driver registered");
        
        
        //connect to database
        System.out.println(" Connecting Database ");
        conn=DriverManager.getConnection("jdbc:oracle:thin:@LAPTOP-CR6FLS09:1521:orcl", "scott", "admin");
        System.out.println(" Database connected");
        stmt=conn.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }

    public boolean checkLogin(String username,String passwords, String user)
    {
        //JOptionPane.showMessageDialog(null, "2U="+username+" P= "+passwords+" Use="+user);
               //String query="SELECT * FROM users WHERE username='"+username+"' AND password='"+passwords+"'";
         //String qry= "select * from users where username like 'Soham' and passwords like 'admin'";
         String qry="select * from users where username like  '"+username+"' and password like '"+passwords+"'";
       //JOptionPane.showMessageDialog(null, "3Query="+qry);
       boolean op=false;
        try{
            rs=stmt.executeQuery(qry);
           // rs=stmt.executeQuery("select * from users where username like 'Soham' and passwords like 'admin'");
            /*JOptionPane.showMessageDialog(null, "4Rs"+rs.getString("username") );
            if(rs.getString("username")==username)
            {
                op= true;
            }
            else
                op=false;*/
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }
    
}
        
        
    

