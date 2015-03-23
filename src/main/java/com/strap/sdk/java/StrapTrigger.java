package com.strap.sdk.java;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author marcellebonterre
 */
public class StrapTrigger extends StrapPagedResponse {

    public String data;
    public String error;
    private StrapSDK strap;
    private String service;
    Map<String, String> params;

    public StrapTrigger(StrapSDK strap, String service, Map<String, String> params, String data, String error) {
        this.strap = strap;
        this.service = service;
        this.params = params;
        this.data = data;
        this.error = error;
    }

    public StrapTrigger(String data) {
        this.data = data;
        this.error = "";
    }

    public StrapTrigger(String data, String error) {
        this.data = data;
        this.error = error;
    }

    public ArrayList<StrapReportList> getAll() {
        ArrayList<StrapReportList> reports = new ArrayList<>();
        while (this.hasNext()) {
            reports.add(this.next());
        }
        return reports;
    }

    public boolean hasNext() {
        return (super.numPages > 0 && super.currentPage < super.numPages);
    }

    public StrapReportList next() {
        super.currentPage++;
        params.put("page", Integer.toString(super.currentPage));
        StrapResponse<String> res = strap.call(this.service, "GET", this.params);
        ArrayList<StrapReportModel> rs = strap.jsonToReportList(res.data);
        return new StrapReportList(this.strap, this.service, this.params, rs, res.error);
    }
}
