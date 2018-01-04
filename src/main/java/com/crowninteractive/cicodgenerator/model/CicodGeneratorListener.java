/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crowninteractive.cicodgenerator.model;

import java.util.List;
import org.springframework.batch.core.ItemWriteListener;

/**
 *
 * @author johnson3yo
 */

public class CicodGeneratorListener implements ItemWriteListener<Customer>{

    @Override
    public void beforeWrite(List<? extends Customer> list) {
       
    }

    @Override
    public void afterWrite(List<? extends Customer> list) {
        System.out.printf(" Migrating  customers  to subscriber table\n "+list);
    }

    @Override
    public void onWriteError(Exception excptn, List<? extends Customer> list) {
        excptn.printStackTrace();
    }

   
}
