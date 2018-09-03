package app.gds.one.data;

/**
 * Created by gerry on 2018/8/30.
 * 实现请求接口数据
 */

public class HttpRequerstDataSource implements DataSource {

    private static HttpRequerstDataSource INSTANCE;

    public static HttpRequerstDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HttpRequerstDataSource();
        }
        return INSTANCE;
    }

    public HttpRequerstDataSource() {
        super();
    }


}
