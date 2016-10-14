package com.davidgarza.forapp.cupboard;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.davidgarza.forapp.R;
import com.davidgarza.forapp.cupboard.fragment.AddNewItemFragment;
import com.davidgarza.forapp.cupboard.fragment.MainCupboardFragment;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidgarza on 08/10/16.
 */
public class MyCupBoardActivity extends AppCompatActivity{
    @BindString(R.string.menu_my_cupboard) String strMyCupboard;
    @BindString(R.string.adding_new_item) String strAddingNewItem;
    @BindColor(R.color.white) int clrWhite;
    @BindView(R.id.cupboard_toolbar) Toolbar cupboardToolbar;
    @BindView(R.id.fab_additem) FloatingActionButton fabAddItem;

    private int currentFragment = 0;
    private boolean firstAppeareance = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupboard);
        ButterKnife.bind(this);

        setupToolbar();
        showFragment(currentFragment);
    }

    @OnClick(R.id.fab_additem)
    void addItem(){
        if (currentFragment == 0){
            showFragment(1);
        }else if (currentFragment == 1){
            showFragment(0);
        }
    }

    private void hideFab() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.scale_down);
        fabAddItem.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fabAddItem.setVisibility(View.GONE);
                fabAddItem.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void showFab() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        fabAddItem.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fabAddItem.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void showFragment(int index){
        Fragment fragment;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        switch(index){
            case 0:
                fragment = new MainCupboardFragment();
                setToolbarTitle(strMyCupboard);
                if (!firstAppeareance)
                    fragmentTransaction.setCustomAnimations(R.animator.enter_from_left,R.animator.exit_to_right,
                            R.animator.enter_from_right,R.animator.exit_to_left);
                showFab();
                firstAppeareance = false;
                break;
            case 1:
                fragment = new AddNewItemFragment();
                setToolbarTitle(strAddingNewItem);
                fragmentTransaction.setCustomAnimations(R.animator.enter_from_right,R.animator.exit_to_left,
                        R.animator.enter_from_left,R.animator.exit_to_right);
                hideFab();
                break;
            default:
                fragment = new MainCupboardFragment();
                break;
        }

        fragmentTransaction
                .replace(R.id.cupboard_fragment_container,fragment)
                .commit();
        currentFragment = index;
    }

    private void setupToolbar() {
        cupboardToolbar.setTitleTextColor(clrWhite);
        cupboardToolbar.setTitle(strMyCupboard);
        setSupportActionBar(cupboardToolbar);
    }

    private void setToolbarTitle(String title){
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }
}
