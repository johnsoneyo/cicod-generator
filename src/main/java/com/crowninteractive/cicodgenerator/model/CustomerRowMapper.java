/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crowninteractive.cicodgenerator.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author johnson3yo
 */
public class CustomerRowMapper implements RowMapper<Customer> {
    
    @Override
    public Customer mapRow(ResultSet rs, int i) throws SQLException {
        
        Customer c = new Customer();
        c.setAccountNumber(rs.getString("account_number"));
        c.setCustomerId(rs.getLong("customer_id"));
        c.setAccountStatus(rs.getString("account_status"));
        c.setCustomerStatus(rs.getString("customer_status"));
        c.setAccountType(rs.getString("account_type"));
        c.setAdc(rs.getString("adc"));
        c.setAddress1(rs.getString("address_1"));
        c.setAddress2(rs.getString("address_2"));
        c.setAddress3(rs.getString("address_3"));
        c.setBusinessDistrict(rs.getString("business_district"));
        c.setEmail(rs.getString("customer_email"));
        c.setName(rs.getString("name"));
        return c;
        
    }
    
}
