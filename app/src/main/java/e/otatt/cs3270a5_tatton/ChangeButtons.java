package e.otatt.cs3270a5_tatton;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeButtons extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn50, btn20, btn10, btn05, btn01;
    private Button btnPenny, btnNickel, btnDime, btnQuarter, btn50cent;

    private SendUpdate listener;

    interface SendUpdate{
        void addToTotal(String amount);
    }

    public ChangeButtons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_buttons, container, false);
        //dollars
        btn01 = createButton(btn01, R.id.btnDollar1, "1.00");
        btn05 = createButton(btn05, R.id.btnDollar5, "5.00");
        btn10 = createButton(btn10, R.id.btnDollar10, "10.00");
        btn20 = createButton(btn20, R.id.btnDollar20, "20.00");
        btn50 = createButton(btn50, R.id.btnDollar50, "50.00");
        //cents
        btnPenny = createButton(btnPenny, R.id.btnPenny, "0.01");
        btnNickel = createButton(btnNickel, R.id.btnNickel, "0.05");
        btnDime = createButton(btnDime, R.id.btnDime, "0.10");
        btnQuarter = createButton(btnQuarter, R.id.btnQuarter, "0.25");
        btn50cent = createButton(btn50cent, R.id.btnCent50, "0.50");

        return view;
    }

    private Button createButton(Button btn, int id, String amt){
        btn = (Button) view.findViewById(id);
        btn.setTag(amt);
        btn.setOnClickListener(this);
        return btn;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (SendUpdate) context;
    }

    @Override
    public void onClick(View v) {
        String s = view.findViewById(v.getId()).getTag().toString();
        listener.addToTotal(s);
//        Log.d("button click", s);
    }
}
