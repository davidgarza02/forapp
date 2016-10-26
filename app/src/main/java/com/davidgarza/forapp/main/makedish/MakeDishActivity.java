package com.davidgarza.forapp.main.makedish;

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
import android.widget.Button;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.main.exploredishes.AddNewRecipeFragment;
import com.davidgarza.forapp.main.exploredishes.MainDishesFragment;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidgarza on 16/10/16.
 */
public class MakeDishActivity extends AppCompatActivity {
    @BindString(R.string.menu_dishes) String strExploreDishes;
    @BindString(R.string.possible_dishes) String strPossibleDishes;
    @BindString(R.string.menu_make_a_dish) String strMakeDish;
    @BindColor(R.color.white) int clrWhite;
    @BindView(R.id.dishes_toolbar) Toolbar dishesToolbar;
    @BindView(R.id.button_next) Button buttonNext;

    public ArrayList<String> checkedChecks = new ArrayList<>();
    private int currentFragment = 0;
    private boolean firstAppeareance = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_dish);
        ButterKnife.bind(this);

        setupToolbar();
        showFragment(0);
    }

    @OnClick(R.id.button_next)
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
                fragment = new MainMakeDishFragment();
                setToolbarTitle(strMakeDish);
                if (!firstAppeareance)
                    fragmentTransaction.setCustomAnimations(R.animator.enter_from_left,R.animator.exit_to_right,
                            R.animator.enter_from_right,R.animator.exit_to_left);
                firstAppeareance = false;
                break;
            case 1:
                fragment = new ShowPosibleDishesFragment();
                setToolbarTitle(strPossibleDishes);
                fragmentTransaction.setCustomAnimations(R.animator.enter_from_right,R.animator.exit_to_left,
                        R.animator.enter_from_left,R.animator.exit_to_right);
                break;
            default:
                fragment = new MainMakeDishFragment();
                break;
        }

        fragmentTransaction
                .replace(R.id.dishes_fragment_container,fragment)
                .commit();
        currentFragment = index;
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
