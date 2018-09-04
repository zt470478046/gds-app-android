package config;

import android.content.Context;

import app.gds.one.data.DataRepository;
import app.gds.one.data.HttpRequerstDataSource;


/**
 * Created by Administrator on 2017/9/25.
 */

public class Injection {
    public static DataRepository provideTasksRepository(Context context) {
        return DataRepository.getInstance(HttpRequerstDataSource.getInstance());
    }
}
