package com.strap.sdk.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author marcellebonterre
 */
public class Resource {

    private static final Gson JSON = new Gson();

    public String name;
    public String token;
    public String method;
    public String uri;
    public String description;
    public ArrayList<String> required;
    public ArrayList<String> optional;

    public String setToken(String token) {
        this.token = token;
        return this.token;
    }

    public String setMethod(String method) {
        this.method = method;
        return this.method;
    }

    /**
     *
     * @param method
     * @param params
     * @return
     */
    public StrapResponse call(String method, Map<String, String> params) {
        StrapResponse rv = new StrapResponse();
        Map<String, String> reqParams = new HashMap<>();

        // move url params from params object to url string
        StrapResponse route = replaceUrlParams(this.uri, params);
        if (!"".equals(route.error)) {
            rv = route;
            return rv;
        }

        route = paramsToQueryString(route,params);
        reqParams.put("route", route.body);
        
        if (!"GET".equals(this.method)) {
            // create request body for non-GET requests
            Type resourceMapType = new TypeToken< Map<String, String>>() {
            }.getType();
            String body = JSON.toJson(params, resourceMapType);
            reqParams.put("body", body);
        }

        switch (method) {
            case "GET":
                rv.body = httpGet(reqParams);
                break;
            case "PUT":
                rv.body = httpPut(reqParams);
                break;
            case "POST":
                rv.body = httpPost(reqParams);
                break;
            case "DELETE":
                rv.body = httpDelete(reqParams);
                break;
        }
        return rv;
    }

    private StrapResponse paramsToQueryString(StrapResponse url, Map<String, String> params) {
        StrapResponse route = url;

        if ("GET".equals(this.method)) {
            // get list of allowed, optional parameters
            List<String> allowed = new ArrayList<>();
            for (String param : this.optional) {
                if (params.get(param) != null) {
                    allowed.add(param + "=" + encodeString(params.get(param)));
                }
            }
            
            // convert allowed, optional parameters to querystring
            if (!allowed.isEmpty()) {
                for (int j = 0, len = allowed.size(); j < len; j++) {
                    route.body += (j == 0 ? "?" : "&") + allowed.get(j);
                }

            }
        }
        // return route with querystring
        return route;

    }

    private StrapResponse replaceUrlParams(String route, Map<String, String> params) {
        StrapResponse rv = new StrapResponse();

        String regex = "\\{(\\S+?)\\}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(route);

        StringBuffer strBuf = new StringBuffer();

        int i = 1;
        while (m.find()) {
            String UrlParam = m.group(i);

            if (params.get(UrlParam) != null) {
                String UrlParamVal = params.get(UrlParam);
                m.appendReplacement(strBuf, UrlParamVal);
                params.remove(UrlParam);
                i++;
            } else {
                if (!"GET".equals(this.method)) {
                    rv.error = "Missing parameter: " + UrlParam;
                    return rv;
                } else {
//                    GET calls may omit url params
                    m.appendReplacement(strBuf, "");
                }
            }
        }
        m.appendTail(strBuf);
        route = strBuf.toString();
        rv.body = route;
        return rv;
    }

    private static String encodeString(String name) throws NullPointerException {
        String tmp = null;

        if (name == null) {
            return null;
        }

        try {
            tmp = java.net.URLEncoder.encode(name, "utf-8");
        } catch (Exception e) {
        }

        if (tmp == null) {
            throw new NullPointerException();
        }

        return tmp;
    }

    private String httpGet(Map<String, String> params) {
        return HttpRequest
                .get(params.get("route"))
                .header("X-Auth-Token", this.token)
                .body();
    }

    private String httpPut(Map<String, String> params) {
        return HttpRequest
                .put(this.uri)
                .header("X-Auth-Token", this.token)
                .send(params.get("body"))
                .body();
    }

    private String httpPost(Map<String, String> params) {
        return HttpRequest
                .post(this.uri)
                .header("X-Auth-Token", this.token)
                .send(params.get("body"))
                .body();
    }

    private String httpDelete(Map<String, String> params) {
        return HttpRequest
                .delete(params.get("route"))
                .header("X-Auth-Token", this.token)
                .body();
    }

}