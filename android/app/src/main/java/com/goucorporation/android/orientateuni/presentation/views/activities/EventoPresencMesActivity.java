package com.goucorporation.android.orientateuni.presentation.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ProgressBar;

import com.goucorporation.android.orientateuni.R;
import com.goucorporation.android.orientateuni.presentation.models.EventoDataMes;
import com.goucorporation.android.orientateuni.presentation.models.EventoMes;
import com.goucorporation.android.orientateuni.presentation.views.fragments.EventoPresencMesFragment;

public class EventoPresencMesActivity extends AppCompatActivity implements
        EventoPresencMesFragment.OnFragmentInteractionListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int currentItem;
    public static final String CURRENT_ITEM = "CURRENT_ITEM";
    public static final String DATA_MES = "DATA_MES";

    private ViewPager mViewPager;
    private EventoDataMes dataMes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_presenc_mes);

        currentItem = getIntent().getIntExtra(CURRENT_ITEM,0);
        dataMes = getIntent().getParcelableExtra(DATA_MES);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mViewPager, true);
        if(dataMes!=null)
            mostrarEventos(dataMes);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evento_presenc_mes, menu);
        return true;
    }

    @Override
    public void onLinkClick(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void mostrarEventos(EventoDataMes dataMes) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), dataMes);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private EventoDataMes mDataMes;

        public SectionsPagerAdapter(FragmentManager fm, EventoDataMes dataMes) {
            super(fm);
            mDataMes = dataMes;
        }

        @Override
        public Fragment getItem(int position) {
            String tituloMes = mDataMes.getTituloMeses().get(position);
            EventoMes eventoMes = mDataMes.getEventosPorMeses().get(position);
            return EventoPresencMesFragment.newInstance(tituloMes,eventoMes);
        }

        @Override
        public int getCount() {
            return mDataMes.getTituloMeses().size();
        }
    }
}
