/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Michael Schroeder <michael.schroeder.1@hs-osnabrueck.de>
 */
public class GlobalFunc {

    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy");
        return df.format(date).toString();
    }
}
