package com.anosh.christmasgiftmanager.Tabs;


import com.anosh.christmasgiftmanager.R;

public class TabListener implements android.support.v7.app.ActionBar.TabListener {

    android.support.v4.app.Fragment fragment;

    public TabListener(android.support.v4.app.Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        ft.replace(R.id.tabContainer, fragment);
    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        ft.remove(fragment);
    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }
}
