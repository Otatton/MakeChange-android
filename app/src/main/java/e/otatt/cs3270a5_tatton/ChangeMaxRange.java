package e.otatt.cs3270a5_tatton;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeMaxRange extends Fragment {
    private View view;
    private ChangeMax listener;
    private EditText editText;
    private Button save;
    private int max;


    interface ChangeMax{
        void updateMax(int maxAmount);
    }


    public ChangeMaxRange() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_max_range, container, false);

        editText = (EditText) view.findViewById(R.id.edtMaxRange);
        save = (Button) view.findViewById(R.id.btnSave);


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ChangeMax) context;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        int temp = convertInt(editText);
        if (temp == 0){
            temp = 100;
        }
        spEditor.putInt("changeMax", temp);
        spEditor.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
//        editText.addTextChangedListener(textWatcher);
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        max = sp.getInt("changeMax", 100);
        editText.setText(String.valueOf(max));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sp.edit();
                int temp = convertInt(editText);
                if (temp == 0){
                    temp = 100;
                }
                spEditor.putInt("changeMax", temp);
                spEditor.commit();
                listener.updateMax(temp);
            }
        });

    }

    private int convertInt(EditText a){
        if(a.getText().toString().equals("")){
            return 0;
        }
        else{
            return Integer.valueOf(a.getText().toString());
        }
    }
}
