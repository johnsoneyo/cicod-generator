/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crowninteractive.cicodgenerator.reader;

import com.crowninteractive.cicod_generator.CicodDao;
import com.crowninteractive.cicodgenerator.model.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author johnson3yo
 */
@Component
public class CustomerReader implements ItemReader<Customer> {

    @Autowired
    CicodDao cdao;

    private List<Customer> cList;
    private int cIndex;

    @PostConstruct
    public void d() {
        System.out.println("Load customers");
        cList = cdao.getCustomers();
    }

    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        Customer cus = null;
        if (cIndex < cList.size()) {
            cus = cList.get(cIndex);
            cIndex++;
        }
        return cus;
    }

}
