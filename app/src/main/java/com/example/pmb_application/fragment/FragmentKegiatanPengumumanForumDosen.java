package com.example.pmb_application.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmb_application.R;
import com.example.pmb_application.adapter.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FragmentKegiatanPengumumanForumDosen extends Fragment {
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myFragment =  inflater.inflate(R.layout.fragment_kegiatan_pengumuman_forum_dosen, container, false);
        viewPager = myFragment.findViewById(R.id.viewPagerKelolaKegiatanPengumumanForumDosen);
        tabLayout = myFragment.findViewById(R.id.tabLayoutKelolaKegiatanPengumumanForumDosen);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getChildFragmentManager());

        System.out.println("masuk fragment dosen");
        sectionPagerAdapter.addFragment(new FragmentKegiatanDosen(),"Data Kegiatan");
        sectionPagerAdapter.addFragment(new FragmentPengumumanMhs(),"Data Pengumuman");
        sectionPagerAdapter.addFragment(new FragmentForumDosen(),"Data Forum");

        viewPager.setAdapter(sectionPagerAdapter);
    }
}