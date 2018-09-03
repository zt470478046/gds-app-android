package app.gds.one.utils.okhttp.get;


import java.util.Map;

import app.gds.one.utils.okhttp.OkHttpRequest;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/11/13.
 * 公司一直用的post 暂时没有封装 如果需要 请参考 洪洋的开源项目 做一些修改即可
 */

public class GetRequest extends OkHttpRequest {

    protected GetRequest(String url, Map<String, String> params, Map<String, String> headers) {
        super(url, params, headers);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    @Override
    protected Request buildRequest(Request.Builder builder, RequestBody requestBody) {
        return builder.get().build();
    }
}
