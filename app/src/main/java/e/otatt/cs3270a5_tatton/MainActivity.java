package e.otatt.cs3270a5_tatton;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements ChangeMaxRange.ChangeMax, ChangeButtons.SendUpdate,
        ChangeActions.ChangeGameState, ChangeResults.ChangeResultsListener{

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragments(new ChangeResults(), R.id.changeResultsFragmentContainer, "CR");
        loadFragments(new ChangeButtons(), R.id.changeButtonsFragmentContainer, "CB");
        loadFragments(new ChangeActions(), R.id.changeActionsFragmentContainer, "CA");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void loadFragments(Fragment f, int fID, String tag){
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(fID, f, tag).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
       getMenuInflater().inflate(R.menu.main,menu);
       return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSetChangeMax:
                menuClicked(1);
                return true;
            case R.id.menuResetCorrectCount:
                menuClicked(2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void menuClicked (int i){
        if (i == 1){
            loadFragments(new ChangeMaxRange(), R.id.changeResultsFragmentContainer, "CM");
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentByTag("CB"))
                    .hide(fragmentManager.findFragmentByTag("CA"))
                    .commit();
        }
        if (i == 2){
            resetCount();
            Toast toast = Toast.makeText(this, "Counts Reset ", Toast.LENGTH_LONG);
            toast.show();
        }

    }



    @Override
    public void addToTotal(String amount) {
        ChangeResults cr = (ChangeResults)fragmentManager.findFragmentByTag("CR");
        cr.setTxtTotal(amount);
    }

    @Override
    public void updateMax(int maxAmount) {
        loadFragments(new ChangeResults(), R.id.changeResultsFragmentContainer, "CR");
        fragmentManager.beginTransaction()
                .show(fragmentManager.findFragmentByTag("CB"))
                .show(fragmentManager.findFragmentByTag("CA"))
                .commit();
    }

    public void showNewDialog(DialogFragment dialog){
        dialog.show(fragmentManager, "dialog");
    }

    @Override
    public void startNew() {
        ChangeResults cr = (ChangeResults)fragmentManager.findFragmentByTag("CR");
        cr.setRandomChangeTotal();
        cr.startTimer();
    }


    public void resetCount(){
        ChangeActions ca = (ChangeActions)fragmentManager.findFragmentByTag("CA");
        ca.resetCount();
    }

    @Override
    public void sendWin(int result) {
        if(result == 0){
            ChangeActions ca = (ChangeActions)fragmentManager.findFragmentByTag("CA");
            ca.increaseCount();
            showNewDialog(new YouWinDialog());
        }
        if(result == 1){
            showNewDialog(new YouLoseDialog());
        }
        if(result == 2){
            showNewDialog(new TimesUpDialog());
        }
    }
}
