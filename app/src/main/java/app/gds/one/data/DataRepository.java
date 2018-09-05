package app.gds.one.data;

/**
 * Created by gerry on 2018/9/4.
 */

public class DataRepository  implements DataSource{

    private static DataRepository INSTANCE = null;
    private final DataSource mRemoteDataSource;
    private DataRepository(DataSource mRemoteDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
    }

    public static DataRepository getInstance(DataSource mRemoteDataSource) {
        if (INSTANCE == null) INSTANCE = new DataRepository(mRemoteDataSource);
        return INSTANCE;
    }
    @Override
    public void getCountry(DataCallback loadDataCallback) {
        mRemoteDataSource.getCountry(loadDataCallback);
    }

    @Override
    public void checkPhone(String country, String phone, DataCallback loadDataCallback) {
        mRemoteDataSource.checkPhone(country,phone,loadDataCallback);
    }

    @Override
    public void sendCode(int country, String telephone, DataCallback loadDataCallback) {
        mRemoteDataSource.sendCode(country,telephone,loadDataCallback);
    }

    @Override
    public void userRegist(int country, String phone, String code, DataCallback loadDataCallback) {
        mRemoteDataSource.userRegist(country,phone,code,loadDataCallback);
    }

    @Override
    public void setPassword(int country, String phone, String password, String token, DataCallback loadDataCallback) {
        mRemoteDataSource.setPassword(country,phone,password,token,loadDataCallback);
    }

    @Override
    public void codeVerify(int country, String phone, String code, DataCallback loadDataCallback) {
        mRemoteDataSource.codeVerify(country,phone,code,loadDataCallback);
    }

    @Override
    public void userLogin(int country, String phone, String password, DataCallback loadDataCallback) {
        mRemoteDataSource.userLogin(country,phone,password,loadDataCallback);
    }

}
