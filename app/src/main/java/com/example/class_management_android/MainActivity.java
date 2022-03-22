package com.example.class_management_android;

import android.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {

    // Initialize variable
    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // hide The Default Actionbar
        getSupportActionBar().hide();
        // Assign variable
        bottomNavigation = findViewById(R.id.bottom_navigation);
        // Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_calendar_today_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_bottom_bar_classroom));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_about));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // Initialize fragment
                Fragment fragment = null;
                // Check condition
                switch (item.getId()){
                    case 1:
                        // When id is 1, initialize notification fragment
                        fragment = new NotificationFragment();
                        break;
                    case 2:
                        // When id is 2, initialize home fragment
                        fragment = new HomeFragment();
                        break;
                    case 3:
                        // When id is 3, initialize home fragment
                        fragment = new ClassroomsListFragment();
                        break;
                    case 4:
                        // When id is 4, initialize about fragment
                        fragment = new AboutFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });

        // Set notification count
//        bottomNavigation.setCount(1, "3");
        // Set home fragment initially selected
        bottomNavigation.show(3, true);

//        Các thao tác xử lý khi click vào botton navigation
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item)
            {
                // Display toast
//                Toast.makeText(getApplicationContext(),"You clicked " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item)
            {
                // Display toast
//                Toast.makeText(getApplicationContext(),"You reselected " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment)
    {
        // Replace fragment
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}