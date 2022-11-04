/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.dao;

import com.inventory.database.ConnectionFactory;
import com.inventory.dto.SupplierDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/***
 * Refactoring name: PULL UP METHOD
 * To remove duplication of code for the method buildTableModel() in both classes UserDAO.java and SupplierDAO.java,
 * Pull up method refactoring is performed and method is pulled from both classes and is kept in the new class BuildTableModel.java class
 * The class BuildTableModel.java is then extended to two classes UserDAO.java and SupplierDAO.java.
 */
public class SupplierDAO extends BuildTableModel{
    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;

    public SupplierDAO() {
        try {
            System.out.println("sdao1");
            con = new ConnectionFactory().getConnection();
            System.out.println("sdao1.1");
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addSupplierDAO(SupplierDTO supplierdto) {
        try{
                String query = "SELECT * FROM suppliers WHERE fullname='"+supplierdto.getFullName()+"' AND locations='"+supplierdto.getLocation()+"' AND phone='"+supplierdto.getPhone()+"'";
                rs=stmt.executeQuery(query);
                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"Same Supplier has already been added!");
                }else{
                    addFunction(supplierdto);
                }
        }catch(Exception e){
                e.printStackTrace();
        }            
    }//end of method addSupplierDAO
    
    public void addFunction(SupplierDTO supplierdto){
                try{
                    String supplierCode = null;
                    String oldSupplierCode = null;
                    String query1="SELECT * FROM suppliers";
                    rs=stmt.executeQuery(query1);
                    if(!rs.next()){
                        supplierCode="sup"+"1"; 
                    }
                    else{
                        String query2="SELECT * FROM suppliers ORDER by s_id DESC";
                        rs=stmt.executeQuery(query2);
                        if(rs.next()){
                            oldSupplierCode=rs.getString("suppliercode");
                            Integer scode=Integer.parseInt(oldSupplierCode.substring(3));
                            scode++;    
                            supplierCode="sup"+scode;
                        }
                    }
                   /* String q = "INSERT INTO suppliers VALUES(null,?,?,?,?)";
                    pstmt = (PreparedStatement) con.prepareStatement(q);
                    pstmt.setString(1, supplierCode);
                    pstmt.setString(2, supplierdto.getFullName());
                    pstmt.setString(3, supplierdto.getLocation());
                    pstmt.setString(4, supplierdto.getPhone());*/
                    String insertCustomers = "INSERT INTO suppliers(s_id,fullname, locations, phone) VALUES('"+supplierCode+"','"+ supplierdto.getFullName()+"','"+ supplierdto.getLocation()+"','"+supplierdto.getPhone()+"')";
                           pstmt = (PreparedStatement) con.prepareStatement(insertCustomers);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Inserted Successfully! Now add some products..");
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
    
    public void editSupplierDAO(SupplierDTO supplierdto) {
            try {
                String query = "UPDATE suppliers SET s_id=?,fullname=?,locations=?,phone=? WHERE suppliercode=?";
                pstmt = (PreparedStatement) con.prepareStatement(query);
                pstmt.setString(1, supplierdto.getSupplierCode());
                pstmt.setString(2, supplierdto.getFullName());
                pstmt.setString(3, supplierdto.getLocation());
                pstmt.setString(4, supplierdto.getPhone());
                pstmt.setString(5, supplierdto.getSupplierCode());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated Successfully");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Nothing updated! Click the table data first!");
            }
    }//end of method editCustomerDTO

    public void deleteSupplierDAO(String value){
        try{
            String query="delete from suppliers where s_id=?";
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted..");
        }catch(SQLException  e){
            
        }
    }

    public ResultSet getQueryResult() {
        try {
            String query = "SELECT s_id AS SupplierCode, fullname AS Name, locations as Address, phone AS Phone FROM suppliers";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult
    
 /*   
    public ResultSet getCreditSuppliersQueryResult() {
        try {
            String query = "SELECT * FROM suppliers WHERE credit>0";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getDebitSuppliersQueryResult() {
        try {
            String query = "SELECT * FROM suppliers WHERE credit=0";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
   */ 
    public ResultSet getSearchSuppliersQueryResult(String searchTxt) {
        try {
            String query = "SELECT s_id AS SupplierCode, fullname AS Name, locations as Address, phone AS Phone FROM suppliers WHERE fullname LIKE '%"+searchTxt+"%' OR locations LIKE '%"+searchTxt+"%' OR s_id LIKE '%"+searchTxt+"%' OR phone LIKE '%"+searchTxt+"%'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

}
