package com.sakamalab.yutauenishi.chordeditor;



import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;


public class FragmentPA extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();

    public FragmentPA(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {//ここで何を表示するか決める
        return mFragments.get(position);
    }
    @Override
    public int getCount(){
        return mFragments.size();
    }

    public void addFragment(Fragment ft) {
        mFragments.add(ft);
    }
    @Override
    public CharSequence getPageTitle(int position) {//ここでタブの文字を決める
        String text="";
        switch (position) {
            case 0:
                text="歌詞+コード";
                break;
            case 1:
                text="歌詞";
                break;
            case 2:
                text="コード";
                break;
        }

        return text;
    }
}