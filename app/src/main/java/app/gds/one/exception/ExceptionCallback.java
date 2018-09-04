package app.gds.one.exception;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by gerry on 2018/9/4.
 */

public interface ExceptionCallback {
    public abstract void onIOException(IOException e);
    public abstract void onJSONException(JSONException e);
}
