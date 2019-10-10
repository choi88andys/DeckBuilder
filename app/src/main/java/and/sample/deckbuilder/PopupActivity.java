package and.sample.deckbuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import and.sample.deckbuilder.data.Unit;
import and.sample.deckbuilder.data.UnitLayout;


public class PopupActivity extends Activity {
    Unit unit;
    UnitLayout unitLayout;

    Button btn_ok, btn_edit, btn_delete;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        unitLayout = findViewById(R.id.unitLayout);
        Intent intent = getIntent();
        unit = (Unit) intent.getSerializableExtra("unit");
        position = intent.getIntExtra("position", -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout();


        btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 전달하기
                Intent intent = new Intent();

                intent.putExtra("position", position);
                intent.putExtra("name", unit.name + "");

                setResult(RESULT_OK, intent);
                finish();
            }
        });


        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(PopupActivity.this);
                AlertDialog.Builder alert = new AlertDialog.Builder(PopupActivity.this);
                alert.setView(editText);
                alert.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = editText.getText() + "";
                        if (!nameCheck(name)) {
                            Toast.makeText(PopupActivity.this, "공백이 존재합니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        unit.setName(name);
                        refreshLayout();
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }
        });


        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("position", position);

                setResult(RESULT_FIRST_USER, intent);
                finish();
            }
        });
    }


    public boolean spaceCheck(String spaceCheck) {
        for (int i = 0; i < spaceCheck.length(); i++) {
            if (spaceCheck.charAt(i) == ' ')
                return true;
        }
        return false;
    }


    boolean nameCheck(String name) {
        boolean result = true;

        if (TextUtils.isEmpty(name)) {
            result = false;
        }
        if (spaceCheck(name)) {
            result = false;
        }


        return result;
    }


    void refreshLayout() {
        unit.setData();
        unitLayout.setData(unit);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }


}
