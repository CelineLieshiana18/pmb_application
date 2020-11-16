package com.example.pmb_application;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmb_application.Adapter.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class FragmentKelolaKegiatanPengumumanForum extends Fragment {
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

        myFragment =  inflater.inflate(R.layout.fragment_kelola_kegiatan_pengumuman_forum, container, false);
        viewPager = myFragment.findViewById(R.id.viewPagerKelolaKegiatanPengumumanForum);
        tabLayout = myFragment.findViewById(R.id.tabLayoutKelolaKegiatanPengumumanForum);
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

        sectionPagerAdapter.addFragment(new FragmentKelolaKegiatan(),"Data Kegiatan");
        sectionPagerAdapter.addFragment(new FragmentKelolaPengumumanDosenPanitia(),"Data Pengumuman");
        sectionPagerAdapter.addFragment(new FragmentKelolaForumDosenPanitia(),"Data Forum");

        viewPager.setAdapter(sectionPagerAdapter);
    }
}