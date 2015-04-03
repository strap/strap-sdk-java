package com.strap.sdk.java;

import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author marcellebonterre
 */
public class StrapSDK extends StrapSDKBase {

    public StrapSDK(String token) throws StrapResponseParseException  {
        super(token);
    }


    protected Map<String, String> resetCurrentPage(Map<String, String> params) {
        if (!params.containsKey("page")) {
            params.put("page", "1");
        }
        return params;
    }
    
    public ReportList activity(Map<String, String> params) throws StrapResourceNotFoundException, UnsupportedEncodingException, StrapMalformedUrlException  {
        PagedResponse res = super.call("activity", "GET", params);
        List<ReportModel> rs = jsonToReportList(res.getData());
        ReportList rv = new ReportList(this, "activity", params, res);
        return rv;
    }

    public Report report(Map<String, String> params) throws StrapMalformedUrlException, StrapResourceNotFoundException, UnsupportedEncodingException  {
        if (!params.containsKey("id")) {
            throw new StrapMalformedUrlException("No ID provided.");
        }
        PagedResponse res = super.call("report", "GET", params);
        Report rv = new Report(this,"report",params,res);
        return rv;

    }

    public ReportList today() throws StrapResourceNotFoundException, UnsupportedEncodingException, StrapMalformedUrlException  {
        Map<String, String> params = new HashMap<>();
        return this.today(params);
    }

    public ReportList today(Map<String, String> params) throws StrapResourceNotFoundException, UnsupportedEncodingException, StrapMalformedUrlException  {
        params = resetCurrentPage(params);
        PagedResponse res = super.call("today", "GET", params);
        ReportList rv = new ReportList(this, "today", params, res);
        return rv;
    }

    public ReportList week() throws StrapResourceNotFoundException, UnsupportedEncodingException, StrapMalformedUrlException  {
        Map<String, String> params = new HashMap<>();
        return this.week(params);
    }

    public ReportList week(Map<String, String> params) throws StrapResourceNotFoundException, UnsupportedEncodingException, StrapMalformedUrlException  {
        params = resetCurrentPage(params);
        PagedResponse res = super.call("week", "GET", params);
        ReportList rv = new ReportList(this, "week", params, res);
        return rv;
    }

    public ReportList month() throws StrapResourceNotFoundException, UnsupportedEncodingException, StrapMalformedUrlException  {
        Map<String, String> params = new HashMap<>();
        return this.month(params);
    }

    public ReportList month(Map<String, String> params) throws StrapResourceNotFoundException, UnsupportedEncodingException, StrapMalformedUrlException  {
        params = resetCurrentPage(params);
        PagedResponse res = super.call("month", "GET", params);
        ReportList rv = new ReportList(this, "month", params, res);
        return rv;
    }

    public UserList users() throws StrapResourceNotFoundException, UnsupportedEncodingException, StrapMalformedUrlException  {
        Map<String, String> params = new HashMap<>();
        return this.users(params);
    }

    public UserList users(Map<String, String> params) throws StrapResourceNotFoundException, UnsupportedEncodingException, StrapMalformedUrlException  {
        params = resetCurrentPage(params);
        PagedResponse res = super.call("users", "GET", params);
        UserList rv = new UserList(this, "month", params, res);
        return rv;
    }

    public Trigger trigger(Map<String, String> params) throws StrapResourceNotFoundException, UnsupportedEncodingException, StrapMalformedUrlException  {
        PagedResponse res = super.call("trigger", "GET", params);
        Trigger rv = new Trigger(this, "trigger", params, res);
        return rv;
    }

    protected ArrayList<ReportModel> jsonToReportList(String jsonStr) {
        Type reportType = new TypeToken<ArrayList<ReportModel>>() { }.getType();
        ArrayList<ReportModel> rv = super.JSON.fromJson(jsonStr, reportType);
        return rv;
    }
}
