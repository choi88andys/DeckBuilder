package and.sample.deckbuilder;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

import and.sample.deckbuilder.data.Deck;


public class FirstState extends AppCompatActivity {
    String serverName, localName;
    DataHandler dataHandler;

    ArrayList<Deck> deckList = new ArrayList<>();
    boolean isDeckReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_state);

        SharedPreferences pref = getSharedPreferences("common", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("localName", "localNameDef");
        editor.apply();

        serverName = getString(R.string.app_name);
        localName = pref.getString("localName", null);
        dataHandler = new DataHandler(serverName, localName);

        GlobalData.getInstance().progressON(FirstState.this, "User Data");
        dataHandler.initUserData(FirstState.this, new DataCallback() {
            @Override
            public void onCallback() {
                GlobalData.getInstance().progressOFF();

                // Data Loading
                GlobalData.getInstance().progressON(FirstState.this, "Loading");
                dataHandler.loadDeck(deckList, FirstState.this, new DataCallback() {
                    @Override
                    public void onCallback() {
                        GlobalData.getInstance().setDeckList(deckList);
                        isDeckReady = true;
                        GlobalData.getInstance().progressOFF();

                        Intent intent = new Intent(FirstState.this, DeckMain.class);
                        startActivity(intent);

                    }
                });
            }
        });
    }

}