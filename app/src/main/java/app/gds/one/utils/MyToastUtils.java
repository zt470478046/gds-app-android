package app.gds.one.utils;

import android.content.Context;
import android.widget.Toast;

public class MyToastUtils {
    public static void setToast(Context context,String str){
        Toast toast = new Toast(context);
        toast.makeText(context,str,Toast.LENGTH_SHORT);
    }
}
