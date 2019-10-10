package and.sample.deckbuilder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import and.sample.deckbuilder.data.Deck;
import and.sample.deckbuilder.data.Unit;
import and.sample.deckbuilder.data.UnitListAdapter;

public class DeckMain extends AppCompatActivity {
    Button btn_select, btn_edit;
    ListView listView;
    UnitListAdapter unitListAdapter;

    ArrayList<Deck> deckList;
    ArrayList<Unit> units = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        deckList = GlobalData.getInstance().getDeckList();

        listView = findViewById(R.id.listView_unit);
        unitListAdapter = new UnitListAdapter();
        listView.setAdapter(unitListAdapter);


        btn_select = findViewById(R.id.btn_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeckMain.this);

                String title = "덱 선택";
                final String[] items = getItems();
                if (items.length == 0) {
                    title = "생성된 덱이 없습니다.";
                }
                builder.setTitle(title);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        units = deckList.get(which).units;
                        unitListAdapter.clear();
                        for (int i = 0; i < units.size(); i++) {
                            unitListAdapter.addItem(units.get(i));
                            unitListAdapter.notifyDataSetChanged();
                        }
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeckMain.this, DeckEdit.class);
                startActivity(intent);
            }
        });
    } // end resume



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeckMain.this);
        builder.setMessage("Quit?");
        builder.setTitle("Quit").setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog ad = builder.create();
        ad.show();
    }


    public String[] getItems() {
        String[] items = new String[deckList.size()];

        for (int i = 0; i < deckList.size(); i++) {
            items[i] = deckList.get(i).name;
        }

        return items;
    }
}
