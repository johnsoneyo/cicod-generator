/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crowninteractive.cicodgenerator.writer;

import com.crowninteractive.cicod_generator.CicodDao;
import com.crowninteractive.cicodgenerator.model.Customer;
import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author johnson3yo
 */
@Component
public class CustomerWriter implements ItemWriter<Customer> {

    @Autowired
    private CicodDao cdao;

    @Override
    public void write(List<? extends Customer> list) throws Exception {
        System.out.println(">>>Writer Init >>>>>>>>>>>>>");
        for (Customer cus : list) {
            Customer c = cdao.createAccount(cus);
        }

    }

}
