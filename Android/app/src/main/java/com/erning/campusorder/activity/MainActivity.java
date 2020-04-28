package com.erning.campusorder.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.erning.campusorder.R;
import com.erning.campusorder.fragment.HomeFragment;
import com.erning.campusorder.fragment.MyFragment;
import com.erning.campusorder.fragment.OrderFragment;
import com.erning.common.adapter.BaseFragmentPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.nav_view) BottomNavigationView navigationView;
    @BindView(R.id.pager_main) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        HomeFragment homeFragment = new HomeFragment();
        OrderFragment orderFragment = new OrderFragment();
        MyFragment myFragment = new MyFragment();

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), homeFragment, orderFragment, myFragment));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationView.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        navigationView.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                    case 2:
                        navigationView.setSelectedItemId(R.id.navigation_notifications);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });
    }
}
