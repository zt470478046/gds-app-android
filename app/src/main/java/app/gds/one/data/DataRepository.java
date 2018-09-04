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
}
