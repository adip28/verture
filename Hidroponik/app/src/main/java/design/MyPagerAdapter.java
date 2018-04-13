package design;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.adipermana.hidroponik.LoginRegister.ContentLogin;
import com.example.adipermana.hidroponik.LoginRegister.ContentRegis;


/**
 * Created by one on 31/3/16.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public MyPagerAdapter(FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0 :

                ContentLogin signinFragment = new ContentLogin();
                return signinFragment;

            case 1 :

                ContentRegis signupFragment = new ContentRegis();
                return signupFragment;


        }

    return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
