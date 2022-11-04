/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.dao;

import com.inventory.database.ConnectionFactory;
import com.inventory.dto.CustomerDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class CustomerDAO {
    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    public CustomerDAO(){
        try {
            System.out.println("cdao1");
            con = new ConnectionFactory().getConnection();
            System.out.println("cdao1.1");
            stmt = con.createStatement();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => findCustomersByNameLocationAndPhone
     */
     public void addCustomerDAO(CustomerDTO customerdto) {
        try{
                String findCustomersByNameLocationAndPhone = "SELECT * FROM buyers WHERE fullname='"+customerdto.getFullName()+"' AND locations='"+customerdto.getLocation()+"' AND phone='"+customerdto.getPhone()+"'";
                rs=stmt.executeQuery(findCustomersByNameLocationAndPhone);
                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"Same Customer has already been added!");
                }else{
                    addFunction(customerdto);
                }
        }catch(Exception e){
                e.printStackTrace();
        }           
    }//end of method addCustomerDTO

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from
     * query1 => getAllCustomers
     * query2 => getAllCustomersInDescOrder
     * q => insertCustomers
     */
     public void addFunction(CustomerDTO customerdto){
         try {
                        String customerCode = "";
                        String oldCustomerCode = "";
                        String getAllCustomers="SELECT * FROM buyers";
                        rs=stmt.executeQuery(getAllCustomers);
                        if(!rs.next()){
                            customerCode="cus"+"1"; 
                        }
                        else{
                            String getAllCustomersInDescOrder="SELECT * FROM buyers ORDER by c_id DESC";
                            rs=stmt.executeQuery(getAllCustomersInDescOrder);
                            if(rs.next()){
                                oldCustomerCode=rs.getString("c_id");
                                 //JOptionPane.showMessageDialog(null,oldCustomerCode);
                                Integer scode=Integer.parseInt(oldCustomerCode.substring(3));
                                scode++;    
                                customerCode="cus"+scode;
                              //  JOptionPane.showMessageDialog(null,customerCode);
                            }
                        }
                           /* String insertCustomers = "INSERT INTO buyers(fullname, locations, phone) VALUES(?,?,?)";
                            pstmt = (PreparedStatement) con.prepareStatement(insertCustomers);
                            pstmt.setString(1, customerCode);
                            pstmt.setString(1, customerdto.getFullName());
                            pstmt.setString(2, customerdto.getLocation());
                            pstmt.setString(3, customerdto.getPhone());
                            JOptionPane.showMessageDialog(null,pstmt.toString());
                            pstmt.executeUpdate();*/
                           String insertCustomers = "INSERT INTO buyers(c_id,fullname, locations, phone) VALUES('"+customerCode+"','"+ customerdto.getFullName()+"','"+ customerdto.getLocation()+"','"+customerdto.getPhone()+"')";
                           pstmt = (PreparedStatement) con.prepareStatement(insertCustomers);
                          // JOptionPane.showMessageDialog(null,insertCustomers);
                           pstmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Inserted Successfully");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
     }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => updateCustomerDetails
     */
    public void editCustomerDAO(CustomerDTO customerdto){
          try {
                        String updateCustomerDetails = "UPDATE buyers SET fullname=?,locations=?,phone=? WHERE c_id=?";
                        pstmt = (PreparedStatement) con.prepareStatement(updateCustomerDetails);
                        pstmt.setString(1, customerdto.getFullName());
                        pstmt.setString(2, customerdto.getLocation());
                        pstmt.setString(3, customerdto.getPhone());
                        pstmt.setString(4, customerdto.getCustomerCode());
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Updated Successfully!"); 
                    
            } catch (Exception e) {
                e.printStackTrace();
            } 
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => deleteCustomerByCode
     */
    public void deleteCustomerDAO(String value){
        try{
            System.out.println(value);
            String deleteCustomerByCode="delete from buyers where c_id=?";
            pstmt=con.prepareStatement(deleteCustomerByCode);
            pstmt.setString(1,value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted..");
        }catch(SQLException  e){
            e.printStackTrace();
        }
    }


    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomersDetails
     */
    public ResultSet getQueryResult() {
        try {
            String getCustomersDetails = "SELECT c_id AS CustomerCode, fullname AS Name, locations AS Location, phone AS Phone FROM buyers";
            rs = stmt.executeQuery(getCustomersDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomersWithCredit
     */
    public ResultSet getCreditCustomersQueryResult() {
        try {
            String getCustomersWithCredit = "SELECT * FROM buyers WHERE credit>0";
            rs = stmt.executeQuery(getCustomersWithCredit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomersWithDebit
     */
    public ResultSet getDebitCustomersQueryResult() {
        try {
            String getCustomersWithDebit = "SELECT * FROM buyers WHERE credit=0";
            rs = stmt.executeQuery(getCustomersWithDebit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomers
     */
    public ResultSet getSearchCustomersQueryResult(String searchTxt) {
        try {
            String getCustomers = "SELECT * FROM buyers WHERE fullname LIKE '%"+searchTxt+"%' OR locations LIKE '%"+searchTxt+"%' OR c_id LIKE '%"+searchTxt+"%' OR phone LIKE '%"+searchTxt+"%'";
            rs = stmt.executeQuery(getCustomers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomersByCode
     */
    public ResultSet getCustomersName(String customerCode){
        try{
            String getCustomersByCode="SELECT * FROM buyers WHERE c_id='"+customerCode+"'";
            rs=stmt.executeQuery(getCustomersByCode);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomersByProductCode
     */
    public ResultSet getProductsName(String productCode){
        try{
            String getCustomersByProductCode="SELECT productname, currentstocks.quantity FROM product INNER JOIN currentstocks ON product.p_id=currentstocks.p_id WHERE currentstocks.p_id='"+productCode+"'";
            rs=stmt.executeQuery(getCustomersByProductCode);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    //start of method DefaultTableModel
    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData(); //resultset ko metadata
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }//end of method DefaultTableModel
}
