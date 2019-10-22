package e.otatt.cs3270a5_tatton;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeResults extends Fragment {
    private View view;
    private EditText editText;
    private TextView txtTotal;
    private double target;
    private CountDownTimer timer;
    private ChangeResultsListener listener;

    interface ChangeResultsListener{
        void sendWin(int result);
    }


    public ChangeResults() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_results, container, false);

        txtTotal = (TextView) view.findViewById(R.id.txtChangeTotalSoFar);
        editText = (EditText) view.findViewById(R.id.editText);

        startTimer();

        return view;
    }

    public void setChangeTotal(String s){
        editText.setText(s);
    }

    public void setRandomChangeTotal(){
        setChangeTotal(getRandomTotal());
    }

    public String getRandomTotal(){
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        int max = sp.getInt("changeMax", 100);
        Random rand = new Random();
        int temp = rand.nextInt(max);
//        Log.d("count", "temp: " + temp);
        int i = rand.nextInt(100);
//        Log.d("count", "d: " + i);
//        Log.d("count", "total: " + temp + "." + i);
//        Log.d("count", "New temp: " + temp);

        return "" + temp + "." + i;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putString("target", editText.getText().toString());
        spEditor.putString("txtTotal", txtTotal.getText().toString());
        spEditor.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        String s = getRandomTotal();
        editText.setText(sp.getString("target", ""+ s));
        txtTotal.setText(sp.getString("txtTotal", "0"));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                double temp = Double.valueOf(s.toString());
                target = temp;
                startTimer();
            }
        });
    }

    public void startTimer(){
        final TextView textView = (TextView)view.findViewById(R.id.txtTimer);
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
        txtTotal.setText("0");
        timer = new CountDownTimer(30000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(""+millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                textView.setText("Done");
                double temp = Double.valueOf(txtTotal.getText().toString());
                double total = Double.valueOf(editText.getText().toString());
                if (temp == total){
                    listener.sendWin(0);

                }
                else if (temp > target){
                    listener.sendWin(1);
                }
                else{
                    listener.sendWin(2);
                }

            }
        }.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ChangeResultsListener) context;
    }

    public void setTxtTotal(String s){
        BigDecimal bigA = new BigDecimal(s);
//        Log.d("test", "BigA: " + bigA.toString());
        BigDecimal bigB = new BigDecimal(txtTotal.getText().toString());
//        Log.d("test", "BigB: " + bigB.toString());
        BigDecimal bigC = bigA.add(bigB);
//        Log.d("test", "BigC: " + bigC.toString());
//        Log.d("test", "BigB: " + bigB.toString());
        BigDecimal bigD = new BigDecimal(editText.getText().toString());

        txtTotal.setText(bigC.toString());

        if(bigC.compareTo(bigD) == 0) {

            if(timer != null) {
                timer.cancel();
                timer = null;
            }
            listener.sendWin(0);
        }
        if(bigC.compareTo(bigD) == 1){
            if(timer != null) {
                timer.cancel();
                timer = null;
            }
            listener.sendWin(1);
        }
    }


}
