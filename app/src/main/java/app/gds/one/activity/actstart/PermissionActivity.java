package app.gds.one.activity.actstart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import app.gds.one.R;
import app.gds.one.base.BaseActivity;
import butterknife.BindView;

public class PermissionActivity extends BaseActivity {
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.ibMessage)
    ImageButton ibMessage;
    @BindView(R.id.llTitle)
    LinearLayout llTitle;
    @BindView(R.id.wb)
    WebView wb;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PermissionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    protected ViewGroup getEmptyView() {
        return null;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initWeb();
    }

    private void initWeb() {
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setSupportZoom(false);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setDefaultFontSize(16);
        wb.setWebViewClient(new WebViewClient());
        wb.loadUrl("file:///android_asset/phone_permission.html");
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        if (!isSetTitle) {
            immersionBar.setTitleBar(this, llTitle);
            isSetTitle = true;
        }
    }
}
