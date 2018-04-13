package com.example.adipermana.hidroponik.Beranda;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.adipermana.hidroponik.R;
import com.example.adipermana.hidroponik.User.UserProfile;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class BerandaFragmentControll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_fragment_controll);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        /*bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                //messageView.setText(TabMessage.get(tabId, false));
                //pager.setCurrentItem(tabId);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch(tabId){
                    case R.id.tab_friends:
                        Beranda home= new Beranda();
                        fragmentTransaction.replace(R.id.fragment_container,home);
                        fragmentTransaction.commit();
                        break;
                    case R.id.tab_favorites:
                        UserProfile profile= new UserProfile();
                        fragmentTransaction.replace(R.id.fragment_container,profile);
                        fragmentTransaction.commit();
                        break;
                }
            }
        });*/
    }

}
