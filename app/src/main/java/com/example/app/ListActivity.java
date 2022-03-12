package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.example.app.adapter.StarAdapter;
import com.example.app.beans.Star;
import com.example.app.service.StarService;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    private StarService service;
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private static final String TAG = "StarAdapter";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list);
            stars = new ArrayList<>();
            service = StarService.getInstance();
            init();
            recyclerView = findViewById(R.id.recycle_view);
            starAdapter = new StarAdapter(this, service.findAll());
            recyclerView.setAdapter(starAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        public void init(){
            service.create(new Star("Peaky Blinders", "https://i.pinimg.com/474x/32/4f/10/324f10c88729964f48ce5f874569ed2a.jpg", 3.5f));
            service.create(new Star("Lucifer", "https://pbs.twimg.com/media/Ey7Z3zuWUAoYgbl.jpg", 3));
            service.create(new Star("La Casa de Papel", "https://fr.web.img6.acsta.net/pictures/19/07/22/10/12/3891122.jpg", 5));
            service.create(new Star("The Witcher ", "https://sm.ign.com/ign_fr/screenshot/default/d-zs52owsauofg7_jtbw.jpg", 1));
            service.create(new Star("Maid","https://freakingeek.com/wp-content/uploads/2021/09/Maid-Banniere-800x445.jpg",3));
            service.create(new Star("Narcos","https://fr.web.img6.acsta.net/pictures/15/07/29/14/33/086520.jpg",1));
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);


        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null) {

                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder.from(this).setType(mimeType).setChooserTitle("Stars").setText(txt).startChooser();
        }
        return super.onOptionsItemSelected(item);
    }
}