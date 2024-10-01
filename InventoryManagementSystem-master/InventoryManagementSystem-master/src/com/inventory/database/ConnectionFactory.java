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
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/ims?user=root&password=root");
            stmt=con.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/ims?user=root&password=root");
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
        
        
    

