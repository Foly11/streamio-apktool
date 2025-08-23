package com.streamingapp;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VideoPlayerActivity extends AppCompatActivity {

    private WebView videoWebView;
    private LinearLayout titleOverlay;
    private LinearLayout controlsOverlay;
    private LinearLayout errorLayout;
    private CardView serverMenu;
    private ProgressBar loadingProgress;
    
    private TextView videoTitle;
    private TextView currentTime;
    private TextView totalTime;
    private TextView qualityText;
    private TextView serverText;
    private TextView errorMessage;
    
    private ImageButton btnBack;
    private ImageButton btnFavorite;
    private ImageButton btnPlayPause;
    private ImageButton btnRewind;
    private ImageButton btnForward;
    private ImageButton btnFullscreen;
    private LinearLayout btnSubtitles;
    private LinearLayout btnQuality;
    private LinearLayout btnServer;
    private Button btnRetry;
    private Button btnCloseServerMenu;
    
    private SeekBar seekBar;
    private RecyclerView serversRecycler;
    
    private Movie currentMovie;
    private List<Movie.StreamingServer> streamingServers;
    private int currentServerIndex = 0;
    private boolean isPlaying = false;
    private boolean isFullscreen = false;
    private boolean controlsVisible = true;
    
    private Handler hideControlsHandler;
    private Runnable hideControlsRunnable;
    
    private static final int HIDE_CONTROLS_DELAY = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Enable fullscreen
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        
        setContentView(R.layout.activity_video_player);

        // Get movie data from intent
        currentMovie = getIntent().getParcelableExtra("movie");
        if (currentMovie == null) {
            finish();
            return;
        }

        initializeViews();
        setupWebView();
        setupClickListeners();
        setupAutoHideControls();
        loadVideo();
    }

    private void initializeViews() {
        videoWebView = findViewById(R.id.video_webview);
        titleOverlay = findViewById(R.id.title_overlay);
        controlsOverlay = findViewById(R.id.controls_overlay);
        errorLayout = findViewById(R.id.error_layout);
        serverMenu = findViewById(R.id.server_menu);
        loadingProgress = findViewById(R.id.loading_progress);
        
        videoTitle = findViewById(R.id.video_title);
        currentTime = findViewById(R.id.current_time);
        totalTime = findViewById(R.id.total_time);
        qualityText = findViewById(R.id.quality_text);
        serverText = findViewById(R.id.server_text);
        errorMessage = findViewById(R.id.error_message);
        
        btnBack = findViewById(R.id.btn_back);
        btnFavorite = findViewById(R.id.btn_favorite);
        btnPlayPause = findViewById(R.id.btn_play_pause);
        btnRewind = findViewById(R.id.btn_rewind);
        btnForward = findViewById(R.id.btn_forward);
        btnFullscreen = findViewById(R.id.btn_fullscreen);
        btnSubtitles = findViewById(R.id.btn_subtitles);
        btnQuality = findViewById(R.id.btn_quality);
        btnServer = findViewById(R.id.btn_server);
        btnRetry = findViewById(R.id.btn_retry);
        btnCloseServerMenu = findViewById(R.id.btn_close_server_menu);
        
        seekBar = findViewById(R.id.seek_bar);
        serversRecycler = findViewById(R.id.servers_recycler);
        
        // Set movie title
        videoTitle.setText(currentMovie.getTitle());
        
        // Setup streaming servers
        streamingServers = currentMovie.getStreamingServers();
        if (!streamingServers.isEmpty()) {
            serverText.setText(streamingServers.get(0).getName());
            qualityText.setText(streamingServers.get(0).getQuality());
        }
        
        // Setup servers recycler view
        setupServersRecyclerView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        WebSettings webSettings = videoWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDefaultTextEncodingName("utf-8");
        
        // Enable media playback
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        
        // Set user agent to avoid blocking
        webSettings.setUserAgentString(
            "Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/90.0.4430.66 Mobile Safari/537.36"
        );
        
        videoWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
                
                // Inject Arabic subtitles support
                injectSubtitleSupport();
                
                // Auto-hide controls after page loads
                scheduleHideControls();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                showError("خطأ في تحميل الفيديو: " + description);
            }
        });
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());
        
        btnFavorite.setOnClickListener(v -> {
            currentMovie.setFavorite(!currentMovie.isFavorite());
            updateFavoriteButton();
        });
        
        btnPlayPause.setOnClickListener(v -> togglePlayPause());
        
        btnRewind.setOnClickListener(v -> {
            // Inject JavaScript to rewind 10 seconds
            videoWebView.evaluateJavascript("document.querySelector('video').currentTime -= 10;", null);
            showControls();
        });
        
        btnForward.setOnClickListener(v -> {
            // Inject JavaScript to forward 10 seconds
            videoWebView.evaluateJavascript("document.querySelector('video').currentTime += 10;", null);
            showControls();
        });
        
        btnFullscreen.setOnClickListener(v -> toggleFullscreen());
        
        btnSubtitles.setOnClickListener(v -> {
            // Load Arabic subtitles
            loadArabicSubtitles();
            showControls();
        });
        
        btnQuality.setOnClickListener(v -> {
            // Show quality options (handled by server selection)
            showControls();
        });
        
        btnServer.setOnClickListener(v -> toggleServerMenu());
        
        btnRetry.setOnClickListener(v -> {
            hideError();
            loadVideo();
        });
        
        btnCloseServerMenu.setOnClickListener(v -> hideServerMenu());
        
        // WebView click listener to toggle controls
        videoWebView.setOnClickListener(v -> toggleControls());
        
        // Prevent controls from hiding when interacting with them
        controlsOverlay.setOnClickListener(v -> showControls());
        titleOverlay.setOnClickListener(v -> showControls());
    }

    private void setupServersRecyclerView() {
        serversRecycler.setLayoutManager(new LinearLayoutManager(this));
        ServerAdapter serverAdapter = new ServerAdapter(streamingServers, new ServerAdapter.OnServerClickListener() {
            @Override
            public void onServerClick(int position) {
                currentServerIndex = position;
                Movie.StreamingServer server = streamingServers.get(position);
                serverText.setText(server.getName());
                qualityText.setText(server.getQuality());
                hideServerMenu();
                loadVideo();
            }
        });
        serversRecycler.setAdapter(serverAdapter);
    }

    private void setupAutoHideControls() {
        hideControlsHandler = new Handler();
        hideControlsRunnable = new Runnable() {
            @Override
            public void run() {
                hideControls();
            }
        };
    }

    private void loadVideo() {
        if (streamingServers.isEmpty()) {
            showError("لا توجد سيرفرات متاحة للمشاهدة");
            return;
        }
        
        showLoading();
        hideError();
        
        String streamUrl = streamingServers.get(currentServerIndex).getUrl();
        
        // Load the streaming URL in WebView
        videoWebView.loadUrl(streamUrl);
    }

    private void injectSubtitleSupport() {
        // Inject JavaScript to add Arabic subtitles
        String subtitleScript = 
            "javascript:(function() {" +
            "  var video = document.querySelector('video');" +
            "  if (video && video.textTracks.length === 0) {" +
            "    var track = document.createElement('track');" +
            "    track.kind = 'subtitles';" +
            "    track.label = 'العربية';" +
            "    track.srclang = 'ar';" +
            "    track.src = '" + getArabicSubtitleUrl() + "';" +
            "    track.default = true;" +
            "    video.appendChild(track);" +
            "    video.textTracks[0].mode = 'showing';" +
            "  }" +
            "})()";
        
        videoWebView.evaluateJavascript(subtitleScript, null);
    }

    private String getArabicSubtitleUrl() {
        List<String> subtitleUrls = currentMovie.getSubtitleUrls();
        if (!subtitleUrls.isEmpty()) {
            return subtitleUrls.get(0);
        }
        return "";
    }

    private void loadArabicSubtitles() {
        String subtitleScript = 
            "javascript:(function() {" +
            "  var video = document.querySelector('video');" +
            "  if (video && video.textTracks.length > 0) {" +
            "    for (var i = 0; i < video.textTracks.length; i++) {" +
            "      video.textTracks[i].mode = 'hidden';" +
            "    }" +
            "    video.textTracks[0].mode = 'showing';" +
            "  }" +
            "})()";
        
        videoWebView.evaluateJavascript(subtitleScript, null);
    }

    private void togglePlayPause() {
        String script = 
            "javascript:(function() {" +
            "  var video = document.querySelector('video');" +
            "  if (video) {" +
            "    if (video.paused) {" +
            "      video.play();" +
            "      return 'playing';" +
            "    } else {" +
            "      video.pause();" +
            "      return 'paused';" +
            "    }" +
            "  }" +
            "  return 'none';" +
            "})()";
        
        videoWebView.evaluateJavascript(script, result -> {
            if (result != null) {
                isPlaying = result.contains("playing");
                updatePlayPauseButton();
            }
        });
        
        showControls();
    }

    private void updatePlayPauseButton() {
        if (isPlaying) {
            btnPlayPause.setImageResource(R.drawable.ic_pause);
            btnPlayPause.setContentDescription(getString(R.string.pause));
        } else {
            btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
            btnPlayPause.setContentDescription(getString(R.string.play));
        }
    }

    private void updateFavoriteButton() {
        if (currentMovie.isFavorite()) {
            btnFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    private void toggleFullscreen() {
        if (isFullscreen) {
            // Exit fullscreen
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            titleOverlay.setVisibility(View.VISIBLE);
        } else {
            // Enter fullscreen
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
            titleOverlay.setVisibility(View.GONE);
        }
        
        isFullscreen = !isFullscreen;
        updateFullscreenButton();
        showControls();
    }

    private void updateFullscreenButton() {
        if (isFullscreen) {
            btnFullscreen.setImageResource(R.drawable.ic_fullscreen_exit);
        } else {
            btnFullscreen.setImageResource(R.drawable.ic_fullscreen);
        }
    }

    private void toggleControls() {
        if (controlsVisible) {
            hideControls();
        } else {
            showControls();
        }
    }

    private void showControls() {
        controlsVisible = true;
        controlsOverlay.setVisibility(View.VISIBLE);
        if (!isFullscreen) {
            titleOverlay.setVisibility(View.VISIBLE);
        }
        scheduleHideControls();
    }

    private void hideControls() {
        controlsVisible = false;
        controlsOverlay.setVisibility(View.GONE);
        titleOverlay.setVisibility(View.GONE);
        cancelHideControls();
    }

    private void scheduleHideControls() {
        cancelHideControls();
        hideControlsHandler.postDelayed(hideControlsRunnable, HIDE_CONTROLS_DELAY);
    }

    private void cancelHideControls() {
        hideControlsHandler.removeCallbacks(hideControlsRunnable);
    }

    private void toggleServerMenu() {
        if (serverMenu.getVisibility() == View.VISIBLE) {
            hideServerMenu();
        } else {
            showServerMenu();
        }
    }

    private void showServerMenu() {
        serverMenu.setVisibility(View.VISIBLE);
        showControls();
    }

    private void hideServerMenu() {
        serverMenu.setVisibility(View.GONE);
    }

    private void showLoading() {
        loadingProgress.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
    }

    private void hideLoading() {
        loadingProgress.setVisibility(View.GONE);
    }

    private void showError(String message) {
        hideLoading();
        errorMessage.setText(message);
        errorLayout.setVisibility(View.VISIBLE);
        videoWebView.setVisibility(View.GONE);
    }

    private void hideError() {
        errorLayout.setVisibility(View.GONE);
        videoWebView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (serverMenu.getVisibility() == View.VISIBLE) {
            hideServerMenu();
        } else if (isFullscreen) {
            toggleFullscreen();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoWebView != null) {
            videoWebView.onPause();
        }
        cancelHideControls();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoWebView != null) {
            videoWebView.onResume();
        }
        showControls();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoWebView != null) {
            videoWebView.destroy();
        }
        if (hideControlsHandler != null) {
            hideControlsHandler.removeCallbacks(hideControlsRunnable);
        }
    }
}