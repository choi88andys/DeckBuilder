package and.sample.deckbuilder.data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import and.sample.deckbuilder.R;

public class UnitLayout extends FrameLayout {

    public UnitLayout(Context context) {
        super(context);
        init();
    }


    public UnitLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    ImageView imageView;
    TextView text_name, text_size, text_weight, text_strength;
    TextView text_attack, text_delay, text_swift;
    TextView text_health, text_defense;


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.unit_layout, this);

        imageView = findViewById(R.id.unit_image);

        text_name = findViewById(R.id.text_name);
        text_size = findViewById(R.id.text_size);
        text_weight = findViewById(R.id.text_weight);
        text_strength = findViewById(R.id.text_strength);

        text_attack = findViewById(R.id.text_attack);
        text_delay = findViewById(R.id.text_delay);
        text_swift = findViewById(R.id.text_swift);

        text_health = findViewById(R.id.text_health);
        text_defense = findViewById(R.id.text_defense);
    }

    public void setData(Unit unit) {
        //imageView.setImageResource();

        String temp = unit.name + "";
        text_name.setText(temp);
        text_size.setText("  규모:" + unit.size);
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


    }


}
