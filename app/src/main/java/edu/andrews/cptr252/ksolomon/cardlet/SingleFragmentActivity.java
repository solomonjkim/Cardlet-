package edu.andrews.cptr252.ksolomon.cardlet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/** This class allows us to reference the fragments in AuthorFactActivity and QuoteActivity
 * @author Solomon Kim
 * @since 03/11/18.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    /** Here we define the fragment */
    protected abstract Fragment createFragment();

    /** This method allows each fragment to continue to run in the background when created
     *
     * @param savedInstanceState Bundle object used to save activity state.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
