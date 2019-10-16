package com.example.assignment4.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment4.R;
import com.example.assignment4.adapter.ViewPagerAdapter;
import com.example.assignment4.fragment.AddUpdateFragment;
import com.example.assignment4.fragment.StudentListFragment;
import com.example.assignment4.model.StudentDetails;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private StudentListFragment studentListFragment;
    private AddUpdateFragment addUpdateFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageButton ibSort, ibGrid;
    private boolean mIsListShowing = true;
    private boolean mIsSortData= true;
    private boolean mUpdateClicked= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        xmlId();
        ViewPagerSetup();
        tabLayout.setupWithViewPager(viewPager);
        pageChangeListner();
        ibGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsListShowing) {
                    mIsListShowing = false;
                    ibGrid.setBackground(getResources().getDrawable(R.drawable.list));

                } else {
                    ibGrid.setBackground(getResources().getDrawable(R.drawable.squares));
                    mIsListShowing = true;
                }
                studentListFragment.toggleView(mIsListShowing);
            }
        });
        ibSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, ibSort);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_sort, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.sort_name:
                                mIsSortData= true;
                                studentListFragment.sortDetailsName(mIsSortData);
                                return true;
                            case R.id.sort_roll:
                                mIsSortData = true;
                                studentListFragment.sortDetailsRoll(mIsSortData);
                                return true;
                            default:
                                mIsSortData=false;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    //getting all xml id's
    private void xmlId() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        ibGrid = findViewById(R.id.ib_grid);
        ibSort = findViewById(R.id.ib_sort);
    }

    //view pager
    private void ViewPagerSetup() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        studentListFragment = new StudentListFragment();
        addUpdateFragment = new AddUpdateFragment();
        viewPagerAdapter.setFragment(studentListFragment, getResources().getString(R.string.label_student_list));
        viewPagerAdapter.setFragment(addUpdateFragment, getResources().getString(R.string.label_add_update));
        viewPager.setAdapter(viewPagerAdapter);

    }

    //click listner on btns on fragments
    public void switchViewPager() {
        if (viewPager.getCurrentItem() == 0) {
            viewPager.setCurrentItem(1, true);
        } else {
            viewPager.setCurrentItem(0, true);
        }
    }

    //getting data from fragment sending to  student list
    public void addData(StudentDetails studentDetails) {
        studentListFragment.setData(studentDetails);
    }

    //dialog on click opens fragment
    public void dialogClick(final int clickedPosition,StudentDetails studentObj,boolean mUpdateClicked){
        viewPager.setCurrentItem(1,true);
        addUpdateFragment.updateStudentDetail(clickedPosition,studentObj,mUpdateClicked);

    }

    public void onDataUpdated(final int clickedPosition,StudentDetails studentObj){
        studentListFragment.updatedStudentDetails(clickedPosition,studentObj);
    }

    //add studnet click
    public void clickedAddStudent(boolean mIsClickedAddStudent){
        addUpdateFragment.clickedAddStudentCheck(mIsClickedAddStudent);
    }

    //btn visibility on fragment switch
    private void pageChangeListner() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    ibGrid.setVisibility(View.INVISIBLE);
                    ibSort.setVisibility(View.INVISIBLE);
                } else {
                    ibGrid.setVisibility(View.VISIBLE);
                    ibSort.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
