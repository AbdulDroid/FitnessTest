package fitness.com.fitnesstest;


import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fitness.com.fitnesstest.utils.NewSwipeHelper;
import fitness.com.fitnesstest.utils.RecyclerViewTouchItemHelper;

public class ListActivity extends AppCompatActivity implements FetchCountries.LoadCompleteListener/*,
        RecyclerViewTouchItemHelper.RecyclerItemTouchHelperListener*/{

    private RecyclerView countriesRecycler;
    private ProgressBar progress;
    private CoordinatorLayout coordinatorLayout;
    private ArrayList<Country> countries;
    private CountriesRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        countriesRecycler = findViewById(R.id.countries_list);
        progress = findViewById(R.id.progress);
        coordinatorLayout = findViewById(R.id.coordinator);
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

        new NewSwipeHelper(this, countriesRecycler) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder,
                                                  List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new UnderlayButton(
                        "",
                        R.drawable.ic_delete,
                        Color.parseColor("#9400D3"),
                        new NewSwipeHelper.UnderlayButtonClickListener() {

                            @Override
                            public void onClick(int pos) {
                                adapter.removeItem(pos);
                            }
                        }));
            }
        };
        /*ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerViewTouchItemHelper(
                0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(countriesRecycler);*/
    }

    @Override
    public void onStartLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadComplete(ArrayList<Country> countries) {
        this.countries = countries;
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

    /*@Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CountriesRecyclerViewAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = countries.get(viewHolder.getAdapterPosition()).name;

            // backup of removed item for undo purpose
            final Country deletedItem = countries.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from country list",
                            Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }*/
}
