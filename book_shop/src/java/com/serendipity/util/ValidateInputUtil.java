/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serendipity.util;

/**
 *
 * @author vladimir
 */
public class ValidateInputUtil {
    
       /**
     * Determine if string is an integer value
     * @param str
     * @return 
     */
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}
