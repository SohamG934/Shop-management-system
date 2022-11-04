/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.dao;

import com.inventory.database.ConnectionFactory;
import com.inventory.dto.ProductDTO;
import com.inventory.ui.CurrentStocks;
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
public class ProductDAO {
    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs1=null;
    Statement stmt1=null;
    ResultSet rs = null;

    /***
     * Refactoring name: EXTRACT CLASS
     * Extract class refactoring is implemented to remove multiple responsibilities
     * checkStock() was present in this class which is moved to a new class Stocks.java
     * Here object of new class is created and method checkStock() of Stocks.java class is called with this object.
     * This improves cohesiveness.
     */
    Stocks stocks = null;


    public ProductDAO() {
        try {
            System.out.println("pdao1");
            con = new ConnectionFactory().getConnection();
            System.out.println("pdao1.1");
            stmt = con.createStatement();
            stmt1=con.createStatement();
            stocks = new Stocks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet getSuppliersInfo(){
        try{
            String query="SELECT * FROM suppliers";
            rs=stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getCustomersInfo(){
        try{
            String query="SELECT * FROM buyers";
            rs=stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getProductInfo(){
        try{
            String query="SELECT * FROM currentstocks";
            rs=stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getProductsName(){
        try{
            String query="SELECT * FROM product";
            rs=stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public Double getProductCostPrice(String productCodeTxt){
        Double costPrice = null;
        try{
            String query="SELECT costprice FROM product WHERE p_id='"+productCodeTxt+"'";
            rs=stmt.executeQuery(query);
            if(rs.next()){
                costPrice=rs.getDouble("costprice");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return costPrice;
    }
    
    public Double getProductSellingPrice(String productCodeTxt){
        Double sellingPrice = null;
        try{
            String query="SELECT sellingprice FROM product WHERE p_id='"+productCodeTxt+"'";
            rs=stmt.executeQuery(query);
            if(rs.next()){
                sellingPrice=rs.getDouble("sellingprice");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return sellingPrice;
    }
    
    
    
    String supplierCode;
    public String getSupplierCode(String suppliersName){
        try{
            String query="SELECT s_id FROM suppliers WHERE fullname='"+suppliersName+"'";
            rs=stmt.executeQuery(query);
            while(rs.next()){
                supplierCode=rs.getString("s_id");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return supplierCode;
    }
    //getProductCode
    
    String productCode;
    public String getProductCode(String productsName){
        try{
            String query="SELECT p_id FROM product WHERE productname='"+productsName+"'";
            rs=stmt.executeQuery(query);
            while(rs.next()){
                productCode=rs.getString("p_id");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return productCode;
    }
    
    String customerCode;
    public String getCustomerCode(String customersName){
        try{
            String query="SELECT c_id FROM buyers WHERE fullname='"+customersName+"'";
            rs=stmt.executeQuery(query);
            while(rs.next()){
                customerCode=rs.getString("c_id");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return customerCode;
    }
    
    public void addProductDAO(ProductDTO productdto) {
         try{
                String query = "SELECT * FROM product WHERE productname='"+productdto.getProductName()+"' AND costprice='"+productdto.getCostPrice()+"' AND sellingprice='"+productdto.getSellingPrice()+"' AND brand='"+productdto.getBrand()+"'";
                rs=stmt.executeQuery(query);
                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"Same Product has already been added!");
                }else{
                    addFunction(productdto);
                }
        }catch(Exception e){
                e.printStackTrace();
        }
            
    }//end of method addUserDTO
    
    public void addFunction(ProductDTO productdto){
        try {
            String productCode = null;
            String oldProductCode = null;
            String query1="SELECT * FROM product";
            rs=stmt.executeQuery(query1);
            if(!rs.next()){
                    productCode="prod"+"1"; 
                    }
                    else{
                        String query2="SELECT * FROM product ORDER by p_id DESC";
                        rs=stmt.executeQuery(query2);
                        if(rs.next()){
                            oldProductCode=rs.getString("p_id");
                            Integer pcode=Integer.parseInt(oldProductCode.substring(4));
                            pcode++;    
                            productCode="prod"+pcode;
                        }
                    }
                   // String q = "INSERT INTO product VALUES(null,?,?,?,?,?)";
                    /*pstmt = (PreparedStatement) con.prepareStatement(q);
                    pstmt.setString(1, productCode);
                    pstmt.setString(2, productdto.getProductName());
                    pstmt.setDouble(3, productdto.getCostPrice());
                    pstmt.setDouble(4, productdto.getSellingPrice());
                    pstmt.setString(5, productdto.getBrand());*/
                    String insertCustomers = "INSERT INTO product(p_id,productname,costprice, sellingprice, brand) VALUES('"+productCode+"','"+ productdto.getProductName()+"','"+ productdto.getCostPrice()+"','"+productdto.getSellingPrice()+"','"+ productdto.getBrand()+"')";
                           pstmt = (PreparedStatement) con.prepareStatement(insertCustomers);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Inserted Successfully! Now you can purchase the product..");
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

//    addPurchaseDAO
     public void addPurchaseDAO(ProductDTO productdto){

        try {
                   /* String q = "INSERT INTO purchaseinfo VALUES(null,?,?,?,?,?)";
                    pstmt = (PreparedStatement) con.prepareStatement(q);
                    pstmt.setString(1, productdto.getSupplierCode());
                    pstmt.setString(2, productdto.getProductCode());
                    pstmt.setString(3, productdto.getDate());
                    pstmt.setInt(4, productdto.getQuantity());
                    pstmt.setDouble(5, productdto.getTotalCost());*/
                    String insertCustomers = "INSERT INTO purchaseinfo(s_id,p_id, dates, quantity) VALUES('"+productdto.getSupplierCode()+"','"+productdto.getProductCode()+"','"+ productdto.getDate()+"','"+ productdto.getQuantity()+"')";
                           pstmt = (PreparedStatement) con.prepareStatement(insertCustomers);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Inserted Successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        
            String productCode=productdto.getProductCode();
            if(stocks.checkStock(productCode, stmt)==true){
                try {
                    String q = "UPDATE currentstocks SET quantity=quantity+? WHERE p_id=?";
                    pstmt = (PreparedStatement) con.prepareStatement(q);
                    pstmt.setDouble(1, productdto.getQuantity());
                    pstmt.setString(2, productdto.getProductCode());

                    pstmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(stocks.checkStock(productCode, stmt)==false){
                try{
                    String q = "INSERT INTO currentstocks VALUES(?,?)";
                    pstmt = (PreparedStatement) con.prepareStatement(q);

                    pstmt.setString(1, productdto.getProductCode());
                    pstmt.setInt(2, productdto.getQuantity());
                    pstmt.executeUpdate();
                }catch(Exception e){
                     e.printStackTrace();   
                }
            }

         /***
          * Refactoring name: MOVE METHOD
          * Move method refactoring is implemented to improve cohesion and reduce coupling
          * deleteStock() was present in this class which is moved to other class Stocks.java
          */
            stocks.deleteStock(stmt);
     }
    
    public void editProductDAO(ProductDTO productdto) {
        try {
                String query = "UPDATE product SET productname=?,costprice=?,sellingprice=?,brand=? WHERE p_id=?";
                pstmt = (PreparedStatement) con.prepareStatement(query);
                pstmt.setString(1, productdto.getProductName());
                pstmt.setDouble(2, productdto.getCostPrice());
                pstmt.setDouble(3, productdto.getSellingPrice());
                pstmt.setString(4, productdto.getBrand());
                pstmt.setString(5, productdto.getProductCode());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated Successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }  
       
    }//end of method editUserDTO
    
   
    /*
    public void editStock1(ProductDTO productdto,int quantity,String pCode){
        String productCode=productdto.getProductCode();
                try {
                    if(productdto.getQuantity()>quantity){
                         String q = "UPDATE currentstocks SET productname=?,quantity=quantity+? WHERE productcode=?";
                         pstmt = (PreparedStatement) con.prepareStatement(q);
                         pstmt.setString(1, productdto.getProductName());
                         int n=productdto.getQuantity()-quantity;
                         pstmt.setDouble(2, n);
                         pstmt.setString(3, productdto.getProductCode());
                         pstmt.executeUpdate();
                    }else if(productdto.getQuantity()<quantity){
                         String q = "UPDATE currentstocks SET productname=?,quantity=quantity-? WHERE productcode=?";
                         pstmt = (PreparedStatement) con.prepareStatement(q);
                         pstmt.setString(1, productdto.getProductName());
                         int n=quantity-productdto.getQuantity();
                         pstmt.setDouble(2, n);
                         pstmt.setString(3, productdto.getProductCode());
                         pstmt.executeUpdate();
                    }else{
                        String q = "UPDATE currentstocks SET productname=?,quantity=? WHERE productcode=?";
                        pstmt = (PreparedStatement) con.prepareStatement(q);
                        pstmt.setString(1, productdto.getProductName());
                        pstmt.setDouble(2, productdto.getQuantity());
                        pstmt.setString(3, productdto.getProductCode());
                        pstmt.executeUpdate();
                    }   
                } catch (Exception e) {
                    e.printStackTrace();
                }            
    }
    
    public void editStock2(ProductDTO productdto,int quantity,String pCode){
        String productCode=productdto.getProductCode();
        if(checkStock(productCode)==true){
            try{
                String q = "UPDATE currentstocks SET productname=?,quantity=quantity+? WHERE productcode=?";
                pstmt = (PreparedStatement) con.prepareStatement(q);
                pstmt.setString(1, productdto.getProductName());
                pstmt.setInt(2, productdto.getQuantity());
                pstmt.setString(3, productdto.getProductCode());
                pstmt.executeUpdate();

                String q2 = "UPDATE currentstocks SET quantity=quantity-? WHERE productcode=?";
                pstmt = (PreparedStatement) con.prepareStatement(q2);
                pstmt.setInt(1, productdto.getQuantity());
                pstmt.setString(2, pCode);
                pstmt.executeUpdate();
            }catch(Exception e){
                 e.printStackTrace();   
            }
        }else if(checkStock(productCode)==false){
            try{
                String q = "INSERT INTO currentstocks VALUES(?,?,?)";
                pstmt = (PreparedStatement) con.prepareStatement(q);
                pstmt.setString(1, productdto.getProductCode());
                pstmt.setString(2, productdto.getProductName());
                pstmt.setInt(3, productdto.getQuantity());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null,productdto.getProductCode()+" "+productdto.getProductName());
                
                String q2 = "UPDATE currentstocks SET quantity=quantity-? WHERE productcode=?";
                pstmt = (PreparedStatement) con.prepareStatement(q2);
                pstmt.setInt(1, productdto.getQuantity());
                pstmt.setString(2, pCode);
                pstmt.executeUpdate();

            }catch(Exception e){
                 e.printStackTrace();   
            }
         }
         
    }
    */
    
    public void editStock(String val,int q){
        try{
            String query="SELECT * FROM currentstocks WHERE p_id = '"+val+"'";
            rs=stmt.executeQuery(query);
            if(rs.next()){
                String qry = "UPDATE currentstocks SET quantity=quantity-? WHERE p_id=?";
                pstmt = (PreparedStatement) con.prepareStatement(qry);
                pstmt.setDouble(1, q);
                pstmt.setString(2, val);
                pstmt.executeUpdate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void editSoldStock(String val,int q){
        try{
            String query="SELECT * FROM currentstocks WHERE p_id = '"+val+"'";
            rs=stmt.executeQuery(query);
            if(rs.next()){
                String qry = "UPDATE currentstocks SET quantity=quantity+? WHERE p_id=?";
                pstmt = (PreparedStatement) con.prepareStatement(qry);
                pstmt.setDouble(1, q);
                pstmt.setString(2, val);
                pstmt.executeUpdate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteProductDAO(String value){
        try{
            String query="delete from product where p_id=?";
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted..");
        }catch(SQLException  e){
            e.printStackTrace();
        }
        stocks.deleteStock(stmt);
    }
    
    
    public void deletePurchaseDAO(String value){
        try{
            String query="delete from purchaseinfo where purchaseid=?";
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted..");
        }catch(SQLException  e){
            e.printStackTrace();
        }
        stocks.deleteStock(stmt);
    }
    
    public void deleteSalesDAO(String value){
        try{
            String query="delete from salesreport where salesid=?";
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted..");
        }catch(SQLException  e){
            e.printStackTrace();
        }
        stocks.deleteStock(stmt);
    }
    
    public void sellProductDAO(ProductDTO productDTO,String username){
        int quantity=0;
        String sellDate=productDTO.getSellDate();
        String productCode=productDTO.getProductCode();
        String customersCode=productDTO.getCustomerCode();
        Double sellingPrice=productDTO.getSellingPrice();
        Double totalRevenue=productDTO.getTotalRevenue();
        int qty=productDTO.getQuantity();
        try{
            String query="SELECT * FROM currentstocks WHERE productcode='"+productDTO.getProductCode()+"'";
            rs=stmt.executeQuery(query);
            while(rs.next()){
                productCode=rs.getString("productcode");
                quantity=rs.getInt("quantity");
            }
            if(productDTO.getQuantity()>quantity){
                JOptionPane.showMessageDialog(null,"Quantity Insufficient");
            }else if(productDTO.getQuantity()<=0){
                JOptionPane.showMessageDialog(null,"Invalid Quantity");
            }else{
                try{
                    String q="UPDATE currentstocks SET quantity=quantity-'"+productDTO.getQuantity()+"' WHERE productcode='"+productDTO.getProductCode()+"'";
                    String qry="INSERT INTO salesreport(date,productcode,customercode,quantity,revenue,soldby) VALUES('"+sellDate+"','"+productCode+"','"+customersCode+"','"+qty+"','"+totalRevenue+"','"+username+"')";
                    stmt.executeUpdate(q);
                    stmt.executeUpdate(qry);
                    JOptionPane.showMessageDialog(null,"SUCCESSFULLY SOLD");
                 }catch(Exception e){
                    e.printStackTrace();
                 }
             }
         }catch(Exception e){
                e.printStackTrace();
         } 
    }

    public ResultSet getQueryResult() {
        try {
            String query = "SELECT p_id,productname,costprice,sellingprice,brand FROM product ORDER BY p_id";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult
   
    public ResultSet getPurchaseResult() {
        try {
            String query = "SELECT purchaseid,purchaseinfo.p_id,purchaseinfo.productname,purchaseinfo.quantity,totalcost FROM purchaseinfo INNER JOIN product ON product.p_id=purchaseinfo.p_id ORDER BY purchaseid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult
    
    public ResultSet getQueryResultOfCurrentStocks() {
        try {
            String query = "SELECT product.p_id,product.productname,product.quantity,product.costprice,product.sellingprice FROM product ";//INNER JOIN product ON currentstocks.productcode=product.p_id";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult
    
    public ResultSet getSalesReportQueryResult() {
        try {
            String query = "SELECT salesid,product.p_id,productname,product.quantity,revenue,soldby FROM salesreport INNER JOIN product ON salesreport.productcode=product.p_id";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult
    
     public ResultSet getSearchProductsQueryResult(String searchTxt) {
        try {
            String query = "SELECT p_id,productname,costprice,sellingprice,brand FROM products WHERE productname LIKE '%"+searchTxt+"%' OR brand LIKE '%"+searchTxt+"%' OR p_id LIKE '%"+searchTxt+"%'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
     
     public ResultSet getSearchPurchaseQueryResult(String searchTxt) {
        try {
            String query = "SELECT purchaseid,purchaseinfo.productcode,productname,quantity,totalcost FROM purchaseinfo INNER JOIN products ON products.productcode=purchaseinfo.productcode WHERE purchaseinfo.productcode LIKE '%"+searchTxt+"%' OR productname LIKE '%"+searchTxt+"%' ORDER BY purchaseid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
     
   public ResultSet getSearchSalesQueryResult(String searchTxt) {
        try {
            String query = "SELECT salesid,salesreport.productcode,productname,quantity,revenue,soldby FROM salesreport INNER JOIN products ON product.p_id=salesreport.productcode INNER JOIN buyers ON customers.c_id=salesreport.customercode WHERE salesreport.productcode LIKE '%"+searchTxt+"%' OR productname LIKE '%"+searchTxt+"%' OR soldby LIKE '%"+searchTxt+"%' OR fullname LIKE '%"+searchTxt+"%' ORDER BY salesid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
     
    public ResultSet getProductName(String pcode){
        try {
            String query = "SELECT productname FROM product WHERE p_id='"+pcode+"'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public String getProductsSupplier(int id){
        String sup=null;
        try {
            String query = "SELECT fullname FROM suppliers INNER JOIN purchaseinfo ON suppliers.suppliercode=purchaseinfo.suppliercode WHERE purchaseid='"+id+"'";
            rs = stmt.executeQuery(query);
            if(rs.next()){
                sup=rs.getString("fullname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sup;
    }
    
    public String getProductsCustomer(int id){
        String cus=null;
        try {
            String query = "SELECT fullname FROM buyers INNER JOIN salesreport ON buyers.c_id=salesreport.customercode WHERE salesid='"+id+"'";
            rs = stmt.executeQuery(query);
            if(rs.next()){
                cus=rs.getString("fullname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cus;
    }
     
    
    public String getPurchasedDate(int pur){
        String p=null;
        try {
            String query = "SELECT date FROM purchaseinfo WHERE purchaseid='"+pur+"'";
            rs = stmt.executeQuery(query);
            if(rs.next()){
                p=rs.getString("date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
    
    public String getSoldDate(int salesid){
        String p=null;
        try {
            String query = "SELECT date FROM salesreport WHERE salesid='"+salesid+"'";
            rs = stmt.executeQuery(query);
            if(rs.next()){
                p=rs.getString("date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    /***
     * Refactoring name: EXTRACT METHOD
     * To remove multiple responsibilities: Extracted code block from buildTableModel() and,
     * Created two new methods getColumnNames() and tableModel() and,
     * Passed appropriate variables and return appropriate values
     */
    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        Vector<String> columnNames = getColumnNames(rs);
        Vector<Vector<Object>> data = tableModel(rs, columnNames);

        return new DefaultTableModel(data, columnNames);
    }//end of method DefaultTableModel


    public Vector<String> getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData(); //resultset ko metadata
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        return columnNames;
    }

    public Vector<Vector<Object>> tableModel(ResultSet rs, Vector<String> columnNames) throws SQLException {
        int columnCount = columnNames.size();
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return data;
    }
}
