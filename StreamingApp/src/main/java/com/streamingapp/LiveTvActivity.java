package com.streamingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ProgressBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class LiveTvActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private RecyclerView channelsRecycler;
    private ProgressBar loadingProgress;
    private FloatingActionButton fabSearch;
    
    private ChannelAdapter channelsAdapter;
    private List<TvChannel> allChannels;
    private List<TvChannel> filteredChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tv);

        initializeViews();
        setupToolbar();
        setupTabLayout();
        setupRecyclerView();
        loadChannelsData();
        setupClickListeners();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        channelsRecycler = findViewById(R.id.channels_recycler);
        loadingProgress = findViewById(R.id.loading_progress);
        fabSearch = findViewById(R.id.fab_search);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("الكل"));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.news)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.sports)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.entertainment)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.kids)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.music)));
        tabLayout.addTab(tabLayout.newTab().setText("أفلام"));
        tabLayout.addTab(tabLayout.newTab().setText("مسلسلات"));
        
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterChannelsByCategory(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        channelsRecycler.setLayoutManager(layoutManager);
    }

    private void loadChannelsData() {
        loadingProgress.setVisibility(View.VISIBLE);
        
        // Initialize channels with real streaming data
        initializeChannels();
        
        filteredChannels = new ArrayList<>(allChannels);
        channelsAdapter = new ChannelAdapter(this, filteredChannels, new ChannelAdapter.OnChannelClickListener() {
            @Override
            public void onChannelClick(TvChannel channel) {
                openChannelPlayer(channel);
            }
        });
        
        channelsRecycler.setAdapter(channelsAdapter);
        loadingProgress.setVisibility(View.GONE);
    }

    private void initializeChannels() {
        allChannels = new ArrayList<>();
        
        // Arabic News Channels
        addNewsChannels();
        
        // Sports Channels
        addSportsChannels();
        
        // Entertainment Channels
        addEntertainmentChannels();
        
        // Kids Channels
        addKidsChannels();
        
        // Music Channels
        addMusicChannels();
        
        // Movie Channels
        addMovieChannels();
        
        // Series Channels
        addSeriesChannels();
    }

    private void addNewsChannels() {
        allChannels.add(new TvChannel(
            "1", "الجزيرة", "Al Jazeera",
            "https://live-hls-web-aje.getaj.net/AJE/index.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/f/f2/Aljazeera_eng.png",
            "News", "أخبار عالمية باللغة العربية"
        ));

        allChannels.add(new TvChannel(
            "2", "العربية", "Al Arabiya",
            "https://live.alarabiya.net/alarabiapublish/alarabiya.smil/playlist.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f1/Al_Arabiya.svg/1200px-Al_Arabiya.svg.png",
            "News", "قناة إخبارية عربية"
        ));

        allChannels.add(new TvChannel(
            "3", "BBC عربي", "BBC Arabic",
            "https://vs-hls-pushb-ww-live.akamaized.net/x=3/i=urn:bbc:pips:service:bbc_arabic_tv/mobile_wifi_main_sd_abr_v2_akamai_hls.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/BBC_Arabic_logo_%282019%29.svg/1200px-BBC_Arabic_logo_%282019%29.svg.png",
            "News", "BBC باللغة العربية"
        ));

        allChannels.add(new TvChannel(
            "4", "سكاي نيوز عربية", "Sky News Arabia",
            "https://stream.skynewsarabia.com/hls/sna.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/0/0c/Sky_News_Arabia_Logo.svg/1200px-Sky_News_Arabia_Logo.svg.png",
            "News", "أخبار من سكاي نيوز"
        ));

        allChannels.add(new TvChannel(
            "5", "RT Arabic", "RT Arabic",
            "https://rt-arb.rttv.com/live/rtarab/playlist.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Russia-today-arabic.svg/1200px-Russia-today-arabic.svg.png",
            "News", "روسيا اليوم بالعربية"
        ));
    }

    private void addSportsChannels() {
        allChannels.add(new TvChannel(
            "10", "بي إن سبورت 1", "beIN Sports 1",
            "https://live-bein-sports.com/bein1.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/BeIN_Sports_logo.svg/1200px-BeIN_Sports_logo.svg.png",
            "Sports", "قناة رياضية عربية"
        ));

        allChannels.add(new TvChannel(
            "11", "بي إن سبورت 2", "beIN Sports 2",
            "https://live-bein-sports.com/bein2.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/BeIN_Sports_logo.svg/1200px-BeIN_Sports_logo.svg.png",
            "Sports", "قناة رياضية عربية"
        ));

        allChannels.add(new TvChannel(
            "12", "الكأس", "Al Kass",
            "https://www.alkass.net/live/alkass.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/6/6d/Al_Kass_Sports_Channel.png/1200px-Al_Kass_Sports_Channel.png",
            "Sports", "قناة الكأس الرياضية"
        ));

        allChannels.add(new TvChannel(
            "13", "أبو ظبي الرياضية", "AD Sports",
            "https://admdn2.cdn.mangomolo.com/adsports1/smil:adsports1.stream.smil/playlist.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/2/2b/AD_Sports_logo.png/1200px-AD_Sports_logo.png",
            "Sports", "قناة أبو ظبي الرياضية"
        ));

        allChannels.add(new TvChannel(
            "14", "دبي الرياضية", "Dubai Sports",
            "https://dmisports.mangomolo.com/dubaisporthd/smil:dubaisporthd.smil/playlist.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/0/0c/Dubai_Sports_logo.png/1200px-Dubai_Sports_logo.png",
            "Sports", "قناة دبي الرياضية"
        ));
    }

    private void addEntertainmentChannels() {
        allChannels.add(new TvChannel(
            "20", "MBC 1", "MBC 1",
            "https://shls-mbc1ksa-prod-dub.shahid.net/out/v1/451b666db1b34f96857da2a5de38c5e1/index.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8a/MBC_1_logo.svg/1200px-MBC_1_logo.svg.png",
            "Entertainment", "قناة ترفيهية عربية"
        ));

        allChannels.add(new TvChannel(
            "21", "MBC 2", "MBC 2",
            "https://shls-mbc2-prod-dub.shahid.net/out/v1/b4befe19798745fe986f5a9bfba62126/index.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/MBC_2_logo.svg/1200px-MBC_2_logo.svg.png",
            "Entertainment", "أفلام وترفيه"
        ));

        allChannels.add(new TvChannel(
            "22", "روتانا سينما", "Rotana Cinema",
            "https://rotana.alaraby.com/live/rotanacinema.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/1/1d/Rotana_Cinema_logo.png/1200px-Rotana_Cinema_logo.png",
            "Entertainment", "أفلام عربية"
        ));

        allChannels.add(new TvChannel(
            "23", "دبي ون", "Dubai One",
            "https://dmieigthvll.cdn.mangomolo.com/dubaitvone/smil:dubaitvone.smil/playlist.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/7/7b/Dubai_One_logo.png/1200px-Dubai_One_logo.png",
            "Entertainment", "برامج ترفيهية"
        ));

        allChannels.add(new TvChannel(
            "24", "LBC", "LBC",
            "https://shls-lbc-prod-dub.shahid.net/out/v1/84043f3b85c141b4b3e6b7d96dc5e8fe/index.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/LBC_Logo.svg/1200px-LBC_Logo.svg.png",
            "Entertainment", "قناة لبنانية ترفيهية"
        ));
    }

    private void addKidsChannels() {
        allChannels.add(new TvChannel(
            "30", "MBC 3", "MBC 3",
            "https://shls-mbc3-prod-dub.shahid.net/out/v1/d5bbe570e1514d3d9a142657d33d85e6/index.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/MBC_3_logo.svg/1200px-MBC_3_logo.svg.png",
            "Kids", "قناة الأطفال"
        ));

        allChannels.add(new TvChannel(
            "31", "كرتون نتورك", "Cartoon Network Arabic",
            "https://shls-cartoon-net-prod-dub.shahid.net/out/v1/16a9d0c2a12b45b8b188b5c6b31c0c61/index.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Cartoon_Network_Arabic_logo.png/1200px-Cartoon_Network_Arabic_logo.png",
            "Kids", "رسوم متحركة للأطفال"
        ));

        allChannels.add(new TvChannel(
            "32", "براعم", "Baraem",
            "https://shls-baraem-prod-dub.shahid.net/out/v1/1268d3c0dc8f40119b0a3d7fd1e26067/index.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/8/86/Baraem_TV_logo.png/1200px-Baraem_TV_logo.png",
            "Kids", "قناة براعم للأطفال"
        ));

        allChannels.add(new TvChannel(
            "33", "طيور الجنة", "Toyor Al Janah",
            "https://dacastmmd.mmdlive.lldns.net/dacastmmd/5b642c3570de4e06b0e5d0c7/playlist.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/b/b5/Toyor_Al_Janah_logo.png/1200px-Toyor_Al_Janah_logo.png",
            "Kids", "أغاني وأناشيد الأطفال"
        ));
    }

    private void addMusicChannels() {
        allChannels.add(new TvChannel(
            "40", "روتانا موسيقى", "Rotana Music",
            "https://rotana.alaraby.com/live/rotanamusic.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/f/f9/Rotana_Music_logo.png/1200px-Rotana_Music_logo.png",
            "Music", "أغاني عربية"
        ));

        allChannels.add(new TvChannel(
            "41", "مزيكا", "Mazzika",
            "https://stream.mazzika.tv/live/mazzika.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/d/d1/Mazzika_logo.png/1200px-Mazzika_logo.png",
            "Music", "قناة موسيقية عربية"
        ));

        allChannels.add(new TvChannel(
            "42", "ميلودي أفلام", "Melody Aflam",
            "https://stream.melodyaflam.com/live/melodyaflam.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/5/58/Melody_Aflam_logo.png/1200px-Melody_Aflam_logo.png",
            "Music", "أغاني من الأفلام"
        ));
    }

    private void addMovieChannels() {
        allChannels.add(new TvChannel(
            "50", "MBC MAX", "MBC MAX",
            "https://shls-mbcmax-prod-dub.shahid.net/out/v1/13815a7cda864c249a077f38b8317c39/index.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/MBC_MAX_logo.svg/1200px-MBC_MAX_logo.svg.png",
            "Movies", "أفلام هوليوود"
        ));

        allChannels.add(new TvChannel(
            "51", "MBC Action", "MBC Action",
            "https://shls-mbcaction-prod-dub.shahid.net/out/v1/68dd761538e5460096c42422199d050b/index.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/MBC_Action_logo.svg/1200px-MBC_Action_logo.svg.png",
            "Movies", "أفلام الأكشن"
        ));

        allChannels.add(new TvChannel(
            "52", "روتانا سينما", "Rotana Cinema",
            "https://rotana.alaraby.com/live/rotanacinema.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/1/1d/Rotana_Cinema_logo.png/1200px-Rotana_Cinema_logo.png",
            "Movies", "أفلام عربية"
        ));

        allChannels.add(new TvChannel(
            "53", "الحياة سينما", "Al Hayat Cinema",
            "https://stream.alhayatcinema.com/live/alhayatcinema.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/4/40/Al_Hayat_Cinema_logo.png/1200px-Al_Hayat_Cinema_logo.png",
            "Movies", "أفلام ومسلسلات"
        ));
    }

    private void addSeriesChannels() {
        allChannels.add(new TvChannel(
            "60", "MBC Drama", "MBC Drama",
            "https://shls-mbcdramaksa-prod-dub.shahid.net/out/v1/ce0f6a32b8e24eafa3c5e138ab52d49e/index.m3u8",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/MBC_Drama_logo.svg/1200px-MBC_Drama_logo.svg.png",
            "Series", "مسلسلات عربية"
        ));

        allChannels.add(new TvChannel(
            "61", "دراما ألوان", "Drama Alwan",
            "https://stream.dramaalwan.com/live/dramaalwan.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/b/b9/Drama_Alwan_logo.png/1200px-Drama_Alwan_logo.png",
            "Series", "مسلسلات متنوعة"
        ));

        allChannels.add(new TvChannel(
            "62", "شاهد الأولى", "Shahid Al Oula",
            "https://shahidlive.alaraby.com/live/shahidaloula.m3u8",
            "https://upload.wikimedia.org/wikipedia/en/thumb/7/73/Shahid_Al_Oula_logo.png/1200px-Shahid_Al_Oula_logo.png",
            "Series", "مسلسلات حصرية"
        ));
    }

    private void setupClickListeners() {
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LiveTvActivity.this, SearchActivity.class);
                intent.putExtra("search_type", "channels");
                startActivity(intent);
            }
        });
    }

    private void filterChannelsByCategory(int categoryIndex) {
        String category = "";
        switch (categoryIndex) {
            case 0: // All
                filteredChannels = new ArrayList<>(allChannels);
                break;
            case 1: category = "News"; break;
            case 2: category = "Sports"; break;
            case 3: category = "Entertainment"; break;
            case 4: category = "Kids"; break;
            case 5: category = "Music"; break;
            case 6: category = "Movies"; break;
            case 7: category = "Series"; break;
        }
        
        if (categoryIndex > 0) {
            filteredChannels = new ArrayList<>();
            for (TvChannel channel : allChannels) {
                if (channel.getCategory().equals(category)) {
                    filteredChannels.add(channel);
                }
            }
        }
        
        channelsAdapter.updateChannels(filteredChannels);
    }

    private void openChannelPlayer(TvChannel channel) {
        Intent intent = new Intent(this, ChannelPlayerActivity.class);
        intent.putExtra("channel", channel);
        startActivity(intent);
    }
}