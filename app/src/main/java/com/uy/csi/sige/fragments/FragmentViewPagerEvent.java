package com.uy.csi.sige.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by dtrinidad on 04/08/2016.
 */
public class FragmentViewPagerEvent extends FragmentStatePagerAdapter {

    private Bundle args;
    private Context context;

    private List<Fragment> fragmentList;

    public FragmentViewPagerEvent(FragmentManager fm,Context context, List<Fragment> fragmentList ,int screenSize,int idusuario) {
        super(fm);
        args = new Bundle();
        args.putInt("screen_size", screenSize);

        args.putInt("id_usuario", idusuario);
        this.context=context;
        this.fragmentList=fragmentList;

    }




    @Override
    public Fragment getItem(int position) {
        System.out.println("*******************POSCITION FragmentViewPagerFile =>" + position);
        fragmentList.get(position).setArguments(args);
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
