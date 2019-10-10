package and.sample.deckbuilder.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import and.sample.deckbuilder.R;

public class UnitListAdapter extends BaseAdapter {
    private ArrayList<Unit> listViewItemList = new ArrayList<>();

    public UnitListAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.unit_layout, parent, false);
        }

        ImageView imageView;
        TextView text_name, text_size, text_weight, text_strength;
        TextView text_attack, text_delay, text_swift;
        TextView text_health, text_defense;

        imageView = convertView.findViewById(R.id.unit_image);
        text_name = convertView.findViewById(R.id.text_name);
        text_size = convertView.findViewById(R.id.text_size);
        text_weight = convertView.findViewById(R.id.text_weight);
        text_strength = convertView.findViewById(R.id.text_strength);
        text_attack = convertView.findViewById(R.id.text_attack);
        text_delay = convertView.findViewById(R.id.text_delay);
        text_swift = convertView.findViewById(R.id.text_swift);
        text_health = convertView.findViewById(R.id.text_health);
        text_defense = convertView.findViewById(R.id.text_defense);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Unit unit = listViewItemList.get(position);
        unit.setData();

        String temp = unit.name + "";
        text_name.setText(temp);
        temp ="  규모:" + unit.size;
        text_size.setText(temp);
        text_weight.setText("  [무게:" + unit.weight + " /");
        text_strength.setText("근력:" + unit.strength + "]");
        text_attack.setText("  공격력:" + unit.attack);
        text_delay.setText("  딜레이:" + unit.delay);
        if (unit.swiftAttack) {
            text_swift.setText("  속공");
        } else {
            text_swift.setText("");
        }
        text_health.setText("  체력:" + unit.health);
        text_defense.setText("  방어:" + unit.defense + "%");

        return convertView;
    }


    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Unit unit) {
        listViewItemList.add(unit);
    }

    public void clear(){
        listViewItemList.clear();
    }

}
