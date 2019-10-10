package and.sample.deckbuilder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import and.sample.deckbuilder.data.Deck;
import and.sample.deckbuilder.data.Unit;
import and.sample.deckbuilder.data.UnitDTO;

public class DataHandler {
    private String serverName;
    private String localName;


    public DataHandler(String serverName, String localName) {
        this.serverName = serverName;
        this.localName = localName;
    }

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    public void initUserData(final Context context, final DataCallback dataCallback) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String deckKey = snapshot.getKey();
                    if (!deckKey.equals(localName)) {
                        continue;
                    }
                    Log.d("data_handle", "exist: " + deckKey);
                    break;
                } // for end

                dataCallback.onCallback();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.child(serverName).addListenerForSingleValueEvent(valueEventListener);
    }


    public void removeDeck(String deckKey, DatabaseReference.CompletionListener completionListener) {
        databaseReference.child(serverName).child(localName).child("deck_list").child(deckKey).removeValue(completionListener);
    }

    public void updateDeck(Deck deck) {
        databaseReference.child(serverName).child(localName).child("deck_list").child(deck.key).removeValue();
        databaseReference.child(serverName).child(localName).child("deck_list").child(deck.key).child("name").setValue(deck.name);
        for (int i = 0; i < deck.units.size(); i++) {
            UnitDTO unitDTO = new UnitDTO(deck.units.get(i).body, deck.units.get(i).armor, deck.units.get(i).weapon, deck.units.get(i).name);
            databaseReference.child(serverName).child(localName).child("deck_list").child(deck.key).child("units").push().setValue(unitDTO);
        }
    }

    public void saveDeck(Deck deck) {
        String key = databaseReference.child(serverName).child(localName).child("deck_list").push().getKey();
        databaseReference.child(serverName).child(localName).child("deck_list").child(key).child("name").setValue(deck.name);
        for (int i = 0; i < deck.units.size(); i++) {
            UnitDTO unitDTO = new UnitDTO(deck.units.get(i).body, deck.units.get(i).armor, deck.units.get(i).weapon, deck.units.get(i).name);
            databaseReference.child(serverName).child(localName).child("deck_list").child(key).child("units").push().setValue(unitDTO);
        }
    }


    public void loadDeck(final ArrayList<Deck> deckList, final Context context, final DataCallback dataCallback) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                deckList.clear();
                for (DataSnapshot deck : dataSnapshot.getChildren()) {
                    String deckKey = deck.getKey();
                    Log.d("data_handle", "deck.getValue: " + deck.getValue());
                    // key

                    try {
                        String json = deck.getValue() + "";
                        JSONObject jsonObject = new JSONObject(json);
                        String deckName = jsonObject.getString("name");
                        Log.d("data_handle", "names: " + jsonObject.names());
                        // name

                        String unitName = jsonObject.getString("units");
                        JSONObject jo = new JSONObject(unitName);
                        Log.d("data_handle", "jo.names: " + jo.names());
                        ArrayList<Unit> units = new ArrayList<>();
                        JSONArray jsonArray = jo.names();
                        ArrayList<String> keys = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            keys.add(jsonArray.getString(i));
                        }
                        Collections.sort(keys);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String key = keys.get(i);
                            UnitDTO unitDTO = deck.child("units").child(key).getValue(UnitDTO.class);
                            Log.d("data_handle", "unitDTO: " + unitDTO.unitName + "  wp: " + unitDTO.weapon);
                            Unit unit = new Unit(unitDTO, context);
                            units.add(unit);
                        }
                        // units


                        Deck temp_deck = new Deck();
                        temp_deck.key = deckKey;
                        temp_deck.name = deckName;
                        temp_deck.units = units;
                        for (int i = 0; i < temp_deck.units.size(); i++) {
                            temp_deck.troopsSize += temp_deck.units.get(i).size;
                        }
                        deckList.add(temp_deck);
                    } catch (JSONException e) {
                        Log.d("data_handle", "JSON ERR");
                    }
                } // for end

                dataCallback.onCallback();
            } // data change end

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.child(serverName).child(localName).child("deck_list").addListenerForSingleValueEvent(valueEventListener);
    }



}
