package and.sample.deckbuilder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import and.sample.deckbuilder.data.Deck;

public class DeckEdit extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Deck> deckList = new ArrayList<>();

    String serverName;
    String localName;
    DataHandler dataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_edit);
    }

    @Override
    protected void onResume() {
        super.onResume();

        listView = findViewById(R.id.list_deck);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);

        serverName = getString(R.string.app_name);
        SharedPreferences pref = getSharedPreferences("common", MODE_PRIVATE);
        localName = pref.getString("localName", null);
        dataHandler = new DataHandler(serverName, localName);


        GlobalData.getInstance().progressON(DeckEdit.this, "Loading");
        dataHandler.loadDeck(deckList, DeckEdit.this, new DataCallback() {
            @Override
            public void onCallback() {
                arrayAdapter.clear();
                arrayAdapter.add("새 덱 만들기");
                for (int i = 0; i < deckList.size(); i++) {
                    arrayAdapter.add(deckList.get(i).name);
                }

                GlobalData.getInstance().setDeckList(deckList);
                GlobalData.getInstance().progressOFF();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DeckEdit.this, DeckOpen.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }

}
