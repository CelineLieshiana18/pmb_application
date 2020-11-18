package com.example.pmb_application;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmb_application.adapter.SectionPagerAdapter;
import com.example.pmb_application.databinding.FragmentHomeMhsBinding;
import com.google.android.material.tabs.TabLayout;

public class FragmentHomeMhs extends Fragment {
    private FragmentHomeMhsBinding binding;
    private FragmentPengumumanMhs FragmentPengumumanMhs;
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;

    public FragmentPengumumanMhs getFragmentPengumumanMhs() {
        if (FragmentPengumumanMhs == null){
            FragmentPengumumanMhs = new FragmentPengumumanMhs();
        }
        return FragmentPengumumanMhs;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        binding = FragmentHomeMhsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment =  inflater.inflate(R.layout.fragment_home_mhs, container, false);
        viewPager = myFragment.findViewById(R.id.viewPagerHomeMhs);
        tabLayout = myFragment.findViewById(R.id.tabLayoutHomeMhs);
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

        sectionPagerAdapter.addFragment(new FragmentKegiatanMhs(),"Data Kegiatan");
        sectionPagerAdapter.addFragment(new FragmentPengumumanMhs(),"Data Pengumuman");

        viewPager.setAdapter(sectionPagerAdapter);
    }
}