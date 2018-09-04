package app.gds.one.activity.pickcity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.gds.one.R;
import app.gds.one.adapter.PyAdapter;
import app.gds.one.adapter.viewholder.LetterHolder;
import app.gds.one.adapter.viewholder.VH;
import app.gds.one.base.BaseActivity;
import app.gds.one.data.PyEntity;
import app.gds.one.entity.Country;
import app.gds.one.utils.SideBar;
import butterknife.BindView;

/**
 * Created by gerry on 2018/9/4.
 */

public class PickActivity extends BaseActivity {
    @BindView(R.id.top_center_name)
    TextView topnmae;
    @BindView(R.id.rv_pick)
    RecyclerView rvPick;
    @BindView(R.id.side)
    SideBar side;
    @BindView(R.id.tv_letter)
    TextView tvLetter;

    @Override
    protected void loadData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void obtainData() {

    }

    private ArrayList<Country> selectedCountries = new ArrayList<>();
    private ArrayList<Country> allCountries = new ArrayList<>();

    @Override
    protected void initViews(Bundle savedInstanceState) {

        topnmae.setText(getResources().getString(R.string.country_select));

        List<Country> country = (List<Country>) getIntent().getSerializableExtra("country");

        Log.v("MAC", "城市：" + country.toString());
        allCountries.clear();
        allCountries.addAll(country);
        selectedCountries.clear();
        selectedCountries.addAll(allCountries);

        final CAdapter adapter = new CAdapter(selectedCountries);
        rvPick.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvPick.setLayoutManager(manager);
        rvPick.setAdapter(adapter);
        rvPick.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        side.addIndex("#", side.indexes.size());
        side.setOnLetterChangeListener(new SideBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                tvLetter.setVisibility(View.VISIBLE);
                tvLetter.setText(letter);
                int position = adapter.getLetterPosition(letter);
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }

            @Override
            public void onReset() {
                tvLetter.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_pick;
    }


    class CAdapter extends PyAdapter<RecyclerView.ViewHolder> {

        public CAdapter(List<? extends PyEntity> entities) {
            super(entities);
        }

        @Override
        public RecyclerView.ViewHolder onCreateLetterHolder(ViewGroup parent, int viewType) {
            return new LetterHolder(getLayoutInflater().inflate(R.layout.item_letter, parent, false));
        }

        @Override
        public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
            return new VH(getLayoutInflater().inflate(R.layout.item_country_large_padding, parent, false));
        }

        @Override
        public void onBindHolder(RecyclerView.ViewHolder holder, PyEntity entity, int position) {
            VH vh = (VH) holder;
            final Country country = (Country) entity;
            vh.tvName.setText(country.name);
            vh.tvCode.setText("+" + country.code);
            holder.itemView.setOnClickListener(v -> {
                Log.v("MAC","城市："+country.toJson().toString());
                Intent data = new Intent();
                data.putExtra("country", country.toJson());
                setResult(Activity.RESULT_OK, data);
                finish();
            });
        }

        @Override
        public void onBindLetterHolder(RecyclerView.ViewHolder holder, LetterEntity entity, int position) {
            ((LetterHolder) holder).textView.setText(entity.letter.toUpperCase());
        }
    }
}
