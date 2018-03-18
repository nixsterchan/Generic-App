package com.example.frederic.genericapp.Activities;

import android.app.DialogFragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.frederic.genericapp.Data.FoodBatchOrder;
import com.example.frederic.genericapp.Fragments.ConfirmOrderDialogFragment;
import com.example.frederic.genericapp.Fragments.MyPendingOrdersFragment;
import com.example.frederic.genericapp.R;
import com.example.frederic.genericapp.SharedPrefManager;

/**
 * Class with tabs to display pending / sent orders
 * Created by: Frederick Bernkastel
 */
public class MyOrdersActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, MyPendingOrdersFragment.ConfirmOrderListener, ConfirmOrderDialogFragment.ConfirmOrderDialogListener{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FoodBatchOrder pendingOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        // Get reference to widgets
        mTabLayout = findViewById(R.id.my_orders_activity_tabs);
        mViewPager = findViewById(R.id.my_orders_activity_pager);

        // Add tabs to tabLayout
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.pending_orders)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Creating our pager adapter
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));



    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onConfirmSelected(MyPendingOrdersFragment fragment) {
        // Recording orders
        pendingOrders = fragment.pendingOrders;

        // Launch Dialog Fragment
        new ConfirmOrderDialogFragment().show(getFragmentManager(),"ConfirmOrderDialogFragment");
    }

    @Override
    public void onDialogConfirm(android.app.Fragment fragment) {
        // TODO: Post to server

        // Delete all pending orders if POST success
        SharedPrefManager<FoodBatchOrder> prefManager = new SharedPrefManager<>();
        pendingOrders = null;
        prefManager.saveObj(getString(R.string.key_batch_orders),pendingOrders,MyOrdersActivity.this);

        // Go back
        onBackPressed();

        // Inform user of success
        Toast.makeText(MyOrdersActivity.this,getString(R.string.toast_order_sent_success),Toast.LENGTH_SHORT).show();
    }
}


class PagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    PagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new MyPendingOrdersFragment();
            case 1:
                return null;
            default:
                return null;
        }

    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}