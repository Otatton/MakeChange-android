package e.otatt.cs3270a5_tatton;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeActions extends Fragment {
    private View view;
    private ChangeGameState listener;

    private TextView textView;

    interface ChangeGameState{
        void startNew();
    }

    public ChangeActions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_actions, container, false);

        textView = (TextView)view.findViewById(R.id.correctChangeCount);

        Button btnNew = (Button)view.findViewById(R.id.btnNewAmount);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.startNew();
            }
        });

        Button btnReset = (Button)view.findViewById(R.id.btnStartOver);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.startNew();
                resetCount();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ChangeGameState) context;
    }

    public void increaseCount(){
        int i = Integer.valueOf(textView.getText().toString());
//        Log.d("test", "win counts: " + i);
        i = i + 1;
        Log.d("test", "win counts: " + i);
        textView.setText(String.valueOf(i));
    }

    public void resetCount(){
        textView.setText("0");
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putString("win count", textView.getText().toString());
        spEditor.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        textView.setText(sp.getString("win count", "0"));
    }
}
