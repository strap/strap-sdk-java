/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.strap.sdk.java;


/**
 *
 * @author marcellebonterre
 */
public class StrapSDK {

    private final ResourceManager serviceManager;
    
    public final StrapActivity activity;
    public final StrapReport report;
    public final StrapToday today;
    public final StrapUsers users;

    public StrapSDK(String token) {
        this.serviceManager = new ResourceManager(token);
        
        this.activity = new StrapActivity(this.serviceManager);
        this.report = new StrapReport(this.serviceManager);
        this.today = new StrapToday(this.serviceManager);
        this.users = new StrapUsers(this.serviceManager);
    }

}
