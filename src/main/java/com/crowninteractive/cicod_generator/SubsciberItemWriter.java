/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crowninteractive.cicod_generator;

import java.util.List;
import org.springframework.batch.item.ItemWriter;

/**
 *
 * @author johnson3yo
 */
public class SubsciberItemWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {
        System.out.println(">>>>>Hooooo");
    }
    
}
