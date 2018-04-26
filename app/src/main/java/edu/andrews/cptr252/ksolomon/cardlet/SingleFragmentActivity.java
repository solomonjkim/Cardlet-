package edu.andrews.cptr252.ksolomon.cardlet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/** This class allows us to reference the other fragments
 * Created by solomonjkim on 4/12/18.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    /** Making the fragment */
    protected abstract Fragment createFragment();

    protected int getLayoutResId() {return R.layout.activity_fragment;}
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
