/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.strap.sdk.java;

/**
 *
 * @author marcellebonterre
 */
public class StrapReport {
   public StrapReportModel data;
   public String error;
   
    public StrapReport(StrapReportModel data) {
        this.data = data;
        this.error = "";

    }

    public StrapReport(StrapReportModel data, String error) {
        this.data = data;
        this.error = error;
    }
}
