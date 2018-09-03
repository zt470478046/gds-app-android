package app.gds.one.utils.okhttp.get;

import java.util.Map;

import app.gds.one.utils.okhttp.RequestBuilder;
import app.gds.one.utils.okhttp.RequestCall;


/**
 * Created by Administrator on 2017/11/13.
 */

public class GetBuilder extends RequestBuilder {
    @Override
    public RequestCall build() {
        return new GetRequest(url, params, headers).build();
    }

    private String appendParams(String url, Map<String, String> params) {
        //TODO
        return null;
    }

    @Override
    public GetBuilder url(String url) {
        this.url = url;
        return this;
    }


    @Override
    public GetBuilder addParams(String key, String val) {
        //TODO
        return this;
    }

    @Override
    public GetBuilder addHeader(String key, String val) {
        //TODO
        return this;
    }
}
