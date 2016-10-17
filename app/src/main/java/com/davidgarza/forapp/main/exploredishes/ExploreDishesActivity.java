package com.davidgarza.forapp.main.exploredishes;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.cupboard.fragment.AddNewItemFragment;
import com.davidgarza.forapp.cupboard.fragment.MainFoodFragment;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidgarza on 16/10/16.
 */
public class ExploreDishesActivity extends AppCompatActivity{
    @BindString(R.string.menu_dishes) String strExploreDishes;
    @BindString(R.string.new_dish) String strNewDish;
    @BindColor(R.color.white) int clrWhite;
    @BindView(R.id.dishes_toolbar) Toolbar dishesToolbar;
    @BindView(R.id.fab_adddish) FloatingActionButton fabAddDish;

    private int currentFragment = 0;
    private boolean firstAppeareance = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_dishes);
        ButterKnife.bind(this);

        setupToolbar();
        showFragment(0);
    }

    @OnClick(R.id.fab_adddish)
    void addItem(){
        if (currentFragment == 0){
            showFragment(1);
        }else if (currentFragment == 1){
            showFragment(0);
        }
    }

    public void showFragment(int index){
        Fragment fragment;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        switch(index){
            case 0:
                fragment = new MainDishesFragment();
                setToolbarTitle(strExploreDishes);
                if (!firstAppeareance)
                    fragmentTransaction.setCustomAnimations(R.animator.enter_from_left,R.animator.exit_to_right,
                            R.animator.enter_from_right,R.animator.exit_to_left);
                showFab();
                firstAppeareance = false;
                break;
            case 1:
                fragment = new AddNewRecipeFragment();
                setToolbarTitle(strNewDish);
                fragmentTransaction.setCustomAnimations(R.animator.enter_from_right,R.animator.exit_to_left,
                        R.animator.enter_from_left,R.animator.exit_to_right);
                hideFab();
                break;
            default:
                fragment = new MainDishesFragment();
                break;
        }

        fragmentTransaction
                .replace(R.id.dishes_fragment_container,fragment)
                .commit();
        currentFragment = index;
    }

    private void hideFab() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.scale_down);
        fabAddDish.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fabAddDish.setVisibility(View.GONE);
                fabAddDish.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void showFab() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        fabAddDish.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fabAddDish.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void setupToolbar() {
        dishesToolbar.setTitleTextColor(clrWhite);
        dishesToolbar.setTitle(strExploreDishes);
        setSupportActionBar(dishesToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (currentFragment > 0){
            showFragment(0);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (currentFragment > 0){
                showFragment(0);
            }else{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbarTitle(String title){
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }
}
