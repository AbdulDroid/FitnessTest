package fitness.com.fitnesstest;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements FetchCountries.LoadCompleteListener{

    private RecyclerView countriesRecycler;
    private ProgressBar progress;
    private CountriesRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        countriesRecycler = findViewById(R.id.countries_list);
        progress = findViewById(R.id.progress);
        setupRecyclerView();
        FetchCountries fetch = new FetchCountries(this);
        fetch.execute();
    }


    private void setupRecyclerView() {
        countriesRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        countriesRecycler.setHasFixedSize(true);
        adapter = new CountriesRecyclerViewAdapter(new ArrayList<Country>());
        countriesRecycler.setAdapter(adapter);
    }

    @Override
    public void onStartLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadComplete(ArrayList<Country> countries) {
        adapter.setItems(countries);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(ListActivity.this, "An Error occurred",
                        Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        });
    }
}
