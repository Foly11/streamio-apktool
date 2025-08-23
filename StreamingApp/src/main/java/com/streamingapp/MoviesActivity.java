package com.streamingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private RecyclerView popularMoviesRecycler;
    private RecyclerView recentMoviesRecycler;
    private RecyclerView topRatedMoviesRecycler;
    private RecyclerView allMoviesRecycler;
    private CardView featuredMovieCard;
    private ProgressBar loadingProgress;
    private FloatingActionButton fabSearch;
    
    private MovieAdapter popularMoviesAdapter;
    private MovieAdapter recentMoviesAdapter;
    private MovieAdapter topRatedMoviesAdapter;
    private MovieAdapter allMoviesAdapter;
    
    private List<Movie> featuredMovies;
    private List<Movie> popularMovies;
    private List<Movie> recentMovies;
    private List<Movie> topRatedMovies;
    private List<Movie> allMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        initializeViews();
        setupToolbar();
        setupTabLayout();
        setupRecyclerViews();
        loadMoviesData();
        setupClickListeners();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        popularMoviesRecycler = findViewById(R.id.popular_movies_recycler);
        recentMoviesRecycler = findViewById(R.id.recent_movies_recycler);
        topRatedMoviesRecycler = findViewById(R.id.top_rated_movies_recycler);
        allMoviesRecycler = findViewById(R.id.all_movies_recycler);
        featuredMovieCard = findViewById(R.id.featured_movie_card);
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
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.action)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.comedy)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.drama)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.horror)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.romance)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.sci_fi)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.thriller)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.animation)));
        
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterMoviesByCategory(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupRecyclerViews() {
        // Popular movies horizontal layout
        LinearLayoutManager popularLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularMoviesRecycler.setLayoutManager(popularLayoutManager);
        
        // Recent movies horizontal layout
        LinearLayoutManager recentLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentMoviesRecycler.setLayoutManager(recentLayoutManager);
        
        // Top rated movies horizontal layout
        LinearLayoutManager topRatedLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topRatedMoviesRecycler.setLayoutManager(topRatedLayoutManager);
        
        // All movies grid layout
        GridLayoutManager allMoviesLayoutManager = new GridLayoutManager(this, 2);
        allMoviesRecycler.setLayoutManager(allMoviesLayoutManager);
    }

    private void loadMoviesData() {
        loadingProgress.setVisibility(View.VISIBLE);
        
        // Initialize movie lists with real movie data
        initializeMovieLists();
        
        // Setup adapters
        popularMoviesAdapter = new MovieAdapter(this, popularMovies, true);
        recentMoviesAdapter = new MovieAdapter(this, recentMovies, true);
        topRatedMoviesAdapter = new MovieAdapter(this, topRatedMovies, true);
        allMoviesAdapter = new MovieAdapter(this, allMovies, false);
        
        // Set adapters
        popularMoviesRecycler.setAdapter(popularMoviesAdapter);
        recentMoviesRecycler.setAdapter(recentMoviesAdapter);
        topRatedMoviesRecycler.setAdapter(topRatedMoviesAdapter);
        allMoviesRecycler.setAdapter(allMoviesAdapter);
        
        loadingProgress.setVisibility(View.GONE);
    }

    private void initializeMovieLists() {
        // Initialize all lists
        featuredMovies = new ArrayList<>();
        popularMovies = new ArrayList<>();
        recentMovies = new ArrayList<>();
        topRatedMovies = new ArrayList<>();
        allMovies = new ArrayList<>();
        
        // Add real movie data with streaming servers
        addRealMovies();
    }

    private void addRealMovies() {
        // Popular Action Movies
        popularMovies.add(new Movie(
            "1", "أفاتار: طريق الماء", "Avatar: The Way of Water",
            "انطلاقة ملحمية جديدة من أفاتار تأخذنا في رحلة مذهلة تحت الماء",
            "https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
            2022, 8.1, "192 دقيقة", "Action/Adventure",
            "https://vidsrc.to/embed/movie/76600",
            "https://embedme.top/embed/movie/76600"
        ));

        popularMovies.add(new Movie(
            "2", "الرجل العنكبوت: لا عودة للوطن", "Spider-Man: No Way Home",
            "بيتر باركر يواجه أعداءه من عوالم متعددة في مغامرة استثنائية",
            "https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            2021, 8.4, "148 دقيقة", "Action/Adventure",
            "https://vidsrc.to/embed/movie/634649",
            "https://embedme.top/embed/movie/634649"
        ));

        popularMovies.add(new Movie(
            "3", "توب غان: مافريك", "Top Gun: Maverick",
            "بيت ميتشل يعود بعد أكثر من ثلاثين عاماً كطيار متميز",
            "https://image.tmdb.org/t/p/w500/62HCnUTziyWcpDaBO2i1DX17ljH.jpg",
            2022, 8.3, "130 دقيقة", "Action/Drama",
            "https://vidsrc.to/embed/movie/361743",
            "https://embedme.top/embed/movie/361743"
        ));

        // Recent Movies
        recentMovies.add(new Movie(
            "4", "باربي", "Barbie",
            "باربي تنطلق في مغامرة ملونة من عالم الألعاب إلى العالم الحقيقي",
            "https://image.tmdb.org/t/p/w500/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg",
            2023, 7.2, "114 دقيقة", "Comedy/Fantasy",
            "https://vidsrc.to/embed/movie/346698",
            "https://embedme.top/embed/movie/346698"
        ));

        recentMovies.add(new Movie(
            "5", "الصوت", "Sound of Freedom",
            "قصة حقيقية عن رجل يحارب تجارة الأطفال",
            "https://image.tmdb.org/t/p/w500/kSf9svfL2WrKeuK8W08xeR5lTn8.jpg",
            2023, 7.7, "131 دقيقة", "Drama/Thriller",
            "https://vidsrc.to/embed/movie/678512",
            "https://embedme.top/embed/movie/678512"
        ));

        recentMovies.add(new Movie(
            "6", "المحولات: صعود الوحوش", "Transformers: Rise of the Beasts",
            "مغامرة جديدة مع المحولات في التسعينيات",
            "https://image.tmdb.org/t/p/w500/gPbM0MK8CP8A174rmUwGsADNYKD.jpg",
            2023, 6.0, "127 دقيقة", "Action/Sci-Fi",
            "https://vidsrc.to/embed/movie/667538",
            "https://embedme.top/embed/movie/667538"
        ));

        // Top Rated Movies
        topRatedMovies.add(new Movie(
            "7", "العراب", "The Godfather",
            "ملحمة العائلة الإيطالية في عالم الجريمة المنظمة",
            "https://image.tmdb.org/t/p/w500/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
            1972, 9.2, "175 دقيقة", "Crime/Drama",
            "https://vidsrc.to/embed/movie/238",
            "https://embedme.top/embed/movie/238"
        ));

        topRatedMovies.add(new Movie(
            "8", "الفارس الأسود", "The Dark Knight",
            "باتمان يواجه الجوكر في معركة ملحمية للخير والشر",
            "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            2008, 9.0, "152 دقيقة", "Action/Crime",
            "https://vidsrc.to/embed/movie/155",
            "https://embedme.top/embed/movie/155"
        ));

        topRatedMovies.add(new Movie(
            "9", "قائمة شندلر", "Schindler's List",
            "قصة حقيقية عن رجل أعمال ألماني ينقذ أرواح اليهود",
            "https://image.tmdb.org/t/p/w500/sF1U4EUQS8YHUYjNl3pMGNIQyr0.jpg",
            1993, 8.9, "195 دقيقة", "Biography/Drama",
            "https://vidsrc.to/embed/movie/424",
            "https://embedme.top/embed/movie/424"
        ));

        // Add more movies for different categories
        addActionMovies();
        addComedyMovies();
        addDramaMovies();
        addHorrorMovies();
        
        // Combine all movies for "All Movies" section
        allMovies.addAll(popularMovies);
        allMovies.addAll(recentMovies);
        allMovies.addAll(topRatedMovies);
    }

    private void addActionMovies() {
        allMovies.add(new Movie(
            "10", "جون ويك", "John Wick",
            "قاتل محترف يخرج من اعتزاله للانتقام",
            "https://image.tmdb.org/t/p/w500/fZUDiWH3hg6VJBmKfIexMECtdI0.jpg",
            2014, 7.4, "101 دقيقة", "Action/Thriller",
            "https://vidsrc.to/embed/movie/245891",
            "https://embedme.top/embed/movie/245891"
        ));

        allMovies.add(new Movie(
            "11", "ماد ماكس: طريق الغضب", "Mad Max: Fury Road",
            "في عالم ما بعد الكارثة، ماكس ينضم لفوريوسا في رحلة هروب",
            "https://image.tmdb.org/t/p/w500/hA2ple9q4qnwxp3hKVNhroipsir.jpg",
            2015, 8.1, "120 دقيقة", "Action/Adventure",
            "https://vidsrc.to/embed/movie/76341",
            "https://embedme.top/embed/movie/76341"
        ));

        allMovies.add(new Movie(
            "12", "المهمة المستحيلة", "Mission: Impossible",
            "إيثان هانت في مهام سرية مستحيلة",
            "https://image.tmdb.org/t/p/w500/VuukZLgaCrho2Ar8Scl9HtV3yD.jpg",
            1996, 7.1, "110 دقيقة", "Action/Adventure",
            "https://vidsrc.to/embed/movie/954",
            "https://embedme.top/embed/movie/954"
        ));
    }

    private void addComedyMovies() {
        allMovies.add(new Movie(
            "13", "الدب الأزرق", "Ted",
            "رجل وصديقه دب الطفولة المتحرك",
            "https://image.tmdb.org/t/p/w500/r6lWGMzJPO62EAnWzLdyVxmJHHb.jpg",
            2012, 6.9, "106 دقائق", "Comedy",
            "https://vidsrc.to/embed/movie/72105",
            "https://embedme.top/embed/movie/72105"
        ));

        allMovies.add(new Movie(
            "14", "في الصميم", "Inside Out",
            "رحلة داخل عقل فتاة صغيرة ومشاعرها",
            "https://image.tmdb.org/t/p/w500/2H1TmgdfNtsKlU9jKdeNyYL5y8T.jpg",
            2015, 8.1, "95 دقيقة", "Animation/Comedy",
            "https://vidsrc.to/embed/movie/150540",
            "https://embedme.top/embed/movie/150540"
        ));
    }

    private void addDramaMovies() {
        allMovies.add(new Movie(
            "15", "فورست غامب", "Forrest Gump",
            "رجل بسيط يعيش أحداثاً استثنائية في التاريخ الأمريكي",
            "https://image.tmdb.org/t/p/w500/arw2vcBveWOVZr6pxd9XTd1TdQa.jpg",
            1994, 8.8, "142 دقيقة", "Drama/Romance",
            "https://vidsrc.to/embed/movie/13",
            "https://embedme.top/embed/movie/13"
        ));

        allMovies.add(new Movie(
            "16", "الحياة جميلة", "Life Is Beautiful",
            "أب يحمي ابنه من أهوال الحرب بالخيال والحب",
            "https://image.tmdb.org/t/p/w500/f7DImXDebOs148U4uPjI61iDvaK.jpg",
            1997, 8.6, "116 دقيقة", "Comedy/Drama",
            "https://vidsrc.to/embed/movie/637",
            "https://embedme.top/embed/movie/637"
        ));
    }

    private void addHorrorMovies() {
        allMovies.add(new Movie(
            "17", "الراهبة", "The Nun",
            "راهبة شابة تواجه قوة شريرة في الدير",
            "https://image.tmdb.org/t/p/w500/sFC1ElvoKGdHJIWRpNB3xWJ9lJA.jpg",
            2018, 5.3, "96 دقيقة", "Horror/Mystery",
            "https://vidsrc.to/embed/movie/438808",
            "https://embedme.top/embed/movie/438808"
        ));

        allMovies.add(new Movie(
            "18", "إنه", "It",
            "مجموعة من الأطفال تواجه مهرجاً شريراً",
            "https://image.tmdb.org/t/p/w500/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg",
            2017, 7.3, "135 دقيقة", "Horror/Thriller",
            "https://vidsrc.to/embed/movie/346364",
            "https://embedme.top/embed/movie/346364"
        ));
    }

    private void setupClickListeners() {
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoviesActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        featuredMovieCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!popularMovies.isEmpty()) {
                    openMovieDetails(popularMovies.get(0));
                }
            }
        });
    }

    private void filterMoviesByCategory(int categoryIndex) {
        // Filter movies based on selected category
        List<Movie> filteredMovies = new ArrayList<>();
        
        String category = "";
        switch (categoryIndex) {
            case 0: category = "Action"; break;
            case 1: category = "Comedy"; break;
            case 2: category = "Drama"; break;
            case 3: category = "Horror"; break;
            case 4: category = "Romance"; break;
            case 5: category = "Sci-Fi"; break;
            case 6: category = "Thriller"; break;
            case 7: category = "Animation"; break;
        }
        
        for (Movie movie : allMovies) {
            if (movie.getGenre().toLowerCase().contains(category.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }
        
        allMoviesAdapter.updateMovies(filteredMovies);
    }

    private void openMovieDetails(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}