/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crowninteractive.cicod_generator;

import com.crowninteractive.cicodgenerator.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author johnson3yo
 */
@Component
public class CicodDao {

    @Autowired
    private DataSource dataSource;

    public void getCountOfCustomers() {

        Connection conn = null;
        int fetchSize = 0;
        try {
            conn = dataSource.getConnection();
            Statement s = conn.createStatement();
            ResultSet resultSet = s.executeQuery("select count(*) from customer");
            while (resultSet.next()) {
                fetchSize = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    public List<Customer> getCustomers() {

        List<Customer> clist = new ArrayList();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select * from customer");

            while (rs.next()) {
                Customer c = new Customer();
                c.setAccountNumber(rs.getString("account_number"));
                c.setCustomerId(rs.getLong("customer_id"));
                c.setCustomerStatus(rs.getString("customer_status"));
                c.setAccountType(rs.getString("account_type"));
                c.setTariff(rs.getString("tariff"));
                c.setPhoneNumber(rs.getString("phone_number"));
                c.setAdc(rs.getString("adc"));
                c.setAddress1(rs.getString("address_1"));
                c.setAddress2(rs.getString("address_2"));
                c.setAddress3(rs.getString("address_3"));
                c.setBusinessDistrict(rs.getString("business_district"));
                c.setEmail(rs.getString("email"));
                c.setName(rs.getString("name"));
                clist.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return clist;
    }

    public Customer createSubscriber(Customer cus) {

        Connection conn = null;
        String insert_to_sub = "insert into subscriber(address1,name,phone,email) values(?,?,?,?)";
        String insert_to_subprxrf = "insert into subscriber_product_xref(cicod_number,product_id,tariff,frequency) values(?,?,?,?)";
        String insert_to_cussub = "insert into  customer_subscriber_xref(customer_id,cicod_number) values (?,?)";

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps1 = conn.prepareStatement(insert_to_sub);
            PreparedStatement ps2 = conn.prepareStatement(insert_to_subprxrf);
            PreparedStatement ps3 = conn.prepareStatement(insert_to_cussub);
            Statement st = conn.createStatement();

            ps1.setString(1, cus.getAddress1());
            ps1.setString(2, cus.getName());
            ps1.setString(3, cus.getPhoneNumber());
            ps1.setString(4, cus.getEmail());
            ps1.executeUpdate();

            ResultSet rs = st.executeQuery("select distinct last_insert_id() as last_id from subscriber");

            int lastid = 0;
            while (rs.next()) {
                lastid = rs.getInt("last_id");
                ps2.setInt(1, lastid);
                int productId = getProduct(cus.getTariff());
                ps2.setInt(2, productId);
                ps2.setString(3, cus.getTariff());
                ps2.setInt(4, 0);
                ps2.executeUpdate();

                ps3.setLong(1, cus.getCustomerId());
                ps3.setInt(2, lastid);
                ps3.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return cus;
    }

    public Customer createAccount(Customer cus) {

        Connection conn = null;
        String insert_to_account = "insert into account"
                + "(billing_account,account_class,balance,account_status,modified_by,update_description) values(?,?,?,?,?,?)";
        String insert_to_cus_acct = "insert into customer_account(cicod_number,customer_id) values(?,?)";
        String insert_to_cus_tar = "insert into customer_product_tariff(cicod_number,product_id,tariff,active) values (?,?,?,?)";

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps1 = conn.prepareStatement(insert_to_account);
            PreparedStatement ps2 = conn.prepareStatement(insert_to_cus_acct);
            PreparedStatement ps3 = conn.prepareStatement(insert_to_cus_tar);
            Statement st = conn.createStatement();

            ps1.setString(1, cus.getAccountNumber());
            ps1.setString(2, cus.getAccountType());
            ps1.setDouble(3, 0.00);
            ps1.setString(4, "ACTIVE");
            ps1.setString(5, "system");
            ps1.setString(6, "New Cicod Number Generated");
            ps1.executeUpdate();

            ResultSet rs = st.executeQuery("select distinct last_insert_id() as last_id from account");

            int lastid = 0;
            while (rs.next()) {
                lastid = rs.getInt("last_id");
                
                ps2.setInt(1, lastid);
                ps2.setLong(2, cus.getCustomerId());
                ps2.executeUpdate();
                
                ps3.setInt(1, lastid);
                int productId = getProduct(cus.getTariff());
                ps3.setInt(2, productId);
                ps3.setString(3, cus.getTariff());
                ps3.setBoolean(4, Boolean.TRUE);
                ps3.executeUpdate();

               
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return cus;
    }

    private int getProduct(String tariff) {
        if (tariff == null) {
            return ProductListing.RESIDENTIAL;
        } else if (tariff.startsWith("R")) {
            return ProductListing.RESIDENTIAL;
        } else if (tariff.startsWith("C")) {
            return ProductListing.COMMERCIAL;
        } else if (tariff.startsWith("A")) {
            return ProductListing.GOVERNMENT;
        } else if (tariff.startsWith("D")) {
            return ProductListing.INDUSTRIAL;
        } else if (tariff.startsWith("S")) {
            return ProductListing.STREET_LIGHT;
        } //otherwise still set as a resident customer  
        else {
            return ProductListing.RESIDENTIAL;
        }
    }
}
