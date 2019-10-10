package and.sample.deckbuilder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import and.sample.deckbuilder.adapter.ExAdapter;
import and.sample.deckbuilder.adapter.SimpleTextAdapter;
import and.sample.deckbuilder.data.Armor;
import and.sample.deckbuilder.data.Body;
import and.sample.deckbuilder.data.Deck;
import and.sample.deckbuilder.data.Unit;
import and.sample.deckbuilder.data.UnitLayout;
import and.sample.deckbuilder.data.Weapon;

public class DeckOpen extends Activity {
    int currentDeckSize = 0;
    int maxDeckSize;

    EditText editText;
    TextView text_size, text_count;
    Button btn_add, btn_complete, btn_delete;
    ExpandableListView listView;
    ExAdapter exAdapter;

    int position;
    String deckName, deckKey;
    ArrayList<Deck> deckList;
    Unit unit;
    UnitLayout unitLayout;
    ArrayList<Unit> units;
    ArrayList<String> unitName = new ArrayList<>();

    RecyclerView recyclerView;
    SimpleTextAdapter adapter;

    String serverName, localName;
    DataHandler dataHandler;

    boolean isBodySelected, isArmorSelected, isWeaponSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_open);


        listView = findViewById(R.id.expandableListView);
        exAdapter = new ExAdapter();
        listView.setAdapter(exAdapter);
        initData();

        unitLayout = findViewById(R.id.unitLayout);
        unit = new Unit();
        units = new ArrayList<>();


        text_size = findViewById(R.id.textView_size);
        text_count = findViewById(R.id.textView_count);


        serverName = getString(R.string.app_name);
        SharedPreferences pref = getSharedPreferences("common", MODE_PRIVATE);
        localName = pref.getString("localName", null);
        dataHandler = new DataHandler(serverName, localName);


        if (false) { // version test
            maxDeckSize = getResources().getInteger(R.integer.max_deck_size);
        } else {
            maxDeckSize = getResources().getInteger(R.integer.max_deck_size_tutorial);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        deckList = GlobalData.getInstance().getDeckList();

        position = getIntent().getIntExtra("position", -1);
        if (position > 0) {
            deckName = deckList.get(position - 1).name;
            deckKey = deckList.get(position - 1).key;
            units = deckList.get(position - 1).units;
        }


        if (TextUtils.isEmpty(deckName)) {
            deckName = "NewDeck";
        }


        editText = findViewById(R.id.editText_name);
        editText.setText(deckName);
        /* empty space issue */


        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new SimpleTextAdapter(unitName);
        adapter.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(DeckOpen.this, PopupActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("unit", units.get(position));

                startActivityForResult(intent, 1);
            }
        });
        recyclerView.setAdapter(adapter);
        refreshView();


        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(isBodySelected && isArmorSelected && isWeaponSelected)) {
                    Toast.makeText(DeckOpen.this, "병사, 갑옷, 무기를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (currentDeckSize + unit.size > maxDeckSize) {
                    Toast.makeText(DeckOpen.this, "최대 규모 초과. 현재: " + currentDeckSize, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (unit.getWeight() > unit.getStrength()) {
                    Toast.makeText(DeckOpen.this, "무게가 근력을 초과합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((units.size() >= getResources().getInteger(R.integer.max_unit_count))) {
                    Toast.makeText(DeckOpen.this, "병사가 최대치 입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentDeckSize += unit.size;
                Unit tempUnit = new Unit(unit.body, unit.armor, unit.weapon);
                tempUnit.name = unit.body.getName();
                units.add(tempUnit);

                refreshView();
                Toast.makeText(DeckOpen.this, "추가되었습니다. 현재규모: " + currentDeckSize, Toast.LENGTH_SHORT).show();
            }
        });

        btn_complete = findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int unitCount = units.size();
                if (unitCount == 0) {
                    Toast.makeText(DeckOpen.this, "병사를 추가하세요.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                deckName = editText.getText() + "";
                Deck deck = new Deck(deckName, currentDeckSize, units);
                deck.key = deckKey;
                if (position > 0) {
                    dataHandler.updateDeck(deck);
                } else {
                    dataHandler.saveDeck(deck);
                }
                finish();
            }
        });


        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    dataHandler.removeDeck(deckKey, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            finish();
                        }
                    });
                } else {
                    finish();
                }
            }
        });


        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                switch (groupPosition) {
                    case 0:
                        isBodySelected = true;
                        unit.setBody((Body) exAdapter.getChild(groupPosition, childPosition));
                        break;
                    case 1:
                        isArmorSelected = true;
                        unit.setArmor((Armor) exAdapter.getChild(groupPosition, childPosition));
                        break;
                    case 2:
                        isWeaponSelected = true;
                        unit.setWeapon((Weapon) exAdapter.getChild(groupPosition, childPosition));
                        break;
                }
                unit.setData();
                unitLayout.setData(unit);
                return false;
            }
        });

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
    } // end resume


    @Override
    public void onBackPressed() {
        if (position == 0) {
            finish();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(DeckOpen.this);
        builder.setTitle("변경이 취소됩니다.").setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            int position = data.getIntExtra("position", -1);
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                if (position != -1) {
                    units.get(position).setName(name);
                }
                refreshView();
            }
            if (resultCode == RESULT_FIRST_USER) {
                units.remove(position);
                refreshView();
            }
        }
    }

    void refreshView() {
        unitName.clear();
        for (int i = 0; i < units.size(); i++) {
            unitName.add((i + 1) + ". " + units.get(i).name);
        }
        adapter.notifyDataSetChanged();


        currentDeckSize = 0;
        for (int i = 0; i < units.size(); i++) {
            units.get(i).setData();
            currentDeckSize += units.get(i).size;
        }
        String size = "[" + currentDeckSize + "/" + maxDeckSize + "]";
        Log.d("deck_open", "size: " + size); // 정상
        text_size.setText(size);
        String count = "[" + units.size() + "/" + getResources().getInteger(R.integer.max_unit_count) + "]";
        text_count.setText(count);

    }


    private void initData() {
        for (int j = 0; j < getResources().getInteger(R.integer.number_of_body); j++) {
            exAdapter.add("body", j + 1, DeckOpen.this);
        }

        for (int j = 0; j < getResources().getInteger(R.integer.number_of_armor); j++) {
            exAdapter.add("armor", j + 1, DeckOpen.this);
        }

        for (int j = 0; j < getResources().getInteger(R.integer.number_of_weapon); j++) {
            exAdapter.add("weapon", j + 1, DeckOpen.this);
        }
    }


}