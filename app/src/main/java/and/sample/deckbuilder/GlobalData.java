package and.sample.deckbuilder;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;

import and.sample.deckbuilder.data.Deck;


// ref. github.com/ParkSangGwon/GifProgressSample
public class GlobalData extends Application {
    private static GlobalData globalData;

    public static GlobalData getInstance() {
        return globalData;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        globalData = this;
    }

    AppCompatDialog progressDialog;
    private ArrayList<Deck> deckList;
    //private PartsList partsList;


    public void progressON(Activity activity, String message) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {
            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();
        }

        TextView tv_progress_message = progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }
    }

    public void progressSET(String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        TextView tv_progress_message = progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }
    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    public ArrayList<Deck> getDeckList() {
        return deckList;
    }

    public void setDeckList(ArrayList<Deck> deckList) {
        this.deckList = deckList;
    }

    /*
    public PartsList getPartsList() {
        return partsList;
    }
    public void setPartsList(PartsList partsList) {
        this.partsList = partsList;
    }
    */
}
