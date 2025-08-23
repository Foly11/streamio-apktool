package com.streamingapp;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {
    private String id;
    private String titleArabic;
    private String titleEnglish;
    private String description;
    private String posterUrl;
    private int year;
    private double rating;
    private String duration;
    private String genre;
    private String streamUrl1; // Primary streaming server
    private String streamUrl2; // Secondary streaming server
    private List<String> subtitleUrls; // Arabic subtitles
    private List<String> cast;
    private String director;
    private String trailerUrl;
    private boolean isFavorite;
    private String backdropUrl;
    private String country;
    private String language;
    
    // Constructor
    public Movie(String id, String titleArabic, String titleEnglish, String description,
                 String posterUrl, int year, double rating, String duration, String genre,
                 String streamUrl1, String streamUrl2) {
        this.id = id;
        this.titleArabic = titleArabic;
        this.titleEnglish = titleEnglish;
        this.description = description;
        this.posterUrl = posterUrl;
        this.year = year;
        this.rating = rating;
        this.duration = duration;
        this.genre = genre;
        this.streamUrl1 = streamUrl1;
        this.streamUrl2 = streamUrl2;
        this.subtitleUrls = new ArrayList<>();
        this.cast = new ArrayList<>();
        this.isFavorite = false;
        
        // Add default Arabic subtitles
        initializeSubtitles();
    }
    
    private void initializeSubtitles() {
        // Add Arabic subtitle URLs for each movie
        subtitleUrls.add("https://dl.opensubtitles.org/ar/download/sub/" + id + "/ar");
        subtitleUrls.add("https://subtitle-api.com/api/files/arabic/" + id + ".srt");
    }

    // Parcelable implementation
    protected Movie(Parcel in) {
        id = in.readString();
        titleArabic = in.readString();
        titleEnglish = in.readString();
        description = in.readString();
        posterUrl = in.readString();
        year = in.readInt();
        rating = in.readDouble();
        duration = in.readString();
        genre = in.readString();
        streamUrl1 = in.readString();
        streamUrl2 = in.readString();
        subtitleUrls = in.createStringArrayList();
        cast = in.createStringArrayList();
        director = in.readString();
        trailerUrl = in.readString();
        isFavorite = in.readByte() != 0;
        backdropUrl = in.readString();
        country = in.readString();
        language = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(titleArabic);
        dest.writeString(titleEnglish);
        dest.writeString(description);
        dest.writeString(posterUrl);
        dest.writeInt(year);
        dest.writeDouble(rating);
        dest.writeString(duration);
        dest.writeString(genre);
        dest.writeString(streamUrl1);
        dest.writeString(streamUrl2);
        dest.writeStringList(subtitleUrls);
        dest.writeStringList(cast);
        dest.writeString(director);
        dest.writeString(trailerUrl);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeString(backdropUrl);
        dest.writeString(country);
        dest.writeString(language);
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitleArabic() { return titleArabic; }
    public void setTitleArabic(String titleArabic) { this.titleArabic = titleArabic; }

    public String getTitleEnglish() { return titleEnglish; }
    public void setTitleEnglish(String titleEnglish) { this.titleEnglish = titleEnglish; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getStreamUrl1() { return streamUrl1; }
    public void setStreamUrl1(String streamUrl1) { this.streamUrl1 = streamUrl1; }

    public String getStreamUrl2() { return streamUrl2; }
    public void setStreamUrl2(String streamUrl2) { this.streamUrl2 = streamUrl2; }

    public List<String> getSubtitleUrls() { return subtitleUrls; }
    public void setSubtitleUrls(List<String> subtitleUrls) { this.subtitleUrls = subtitleUrls; }

    public List<String> getCast() { return cast; }
    public void setCast(List<String> cast) { this.cast = cast; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getTrailerUrl() { return trailerUrl; }
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public String getBackdropUrl() { return backdropUrl; }
    public void setBackdropUrl(String backdropUrl) { this.backdropUrl = backdropUrl; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    // Helper methods
    public String getTitle() {
        // Return Arabic title if available, otherwise English
        return (titleArabic != null && !titleArabic.isEmpty()) ? titleArabic : titleEnglish;
    }

    public String getFormattedRating() {
        return String.format("%.1f", rating);
    }

    public String getYearString() {
        return String.valueOf(year);
    }

    public boolean hasValidStreamUrls() {
        return (streamUrl1 != null && !streamUrl1.isEmpty()) || 
               (streamUrl2 != null && !streamUrl2.isEmpty());
    }

    public String getBestQualityUrl() {
        // Return the first available streaming URL
        if (streamUrl1 != null && !streamUrl1.isEmpty()) {
            return streamUrl1;
        }
        return streamUrl2;
    }

    public List<StreamingServer> getStreamingServers() {
        List<StreamingServer> servers = new ArrayList<>();
        
        if (streamUrl1 != null && !streamUrl1.isEmpty()) {
            servers.add(new StreamingServer("سيرفر 1", streamUrl1, "1080p"));
        }
        
        if (streamUrl2 != null && !streamUrl2.isEmpty()) {
            servers.add(new StreamingServer("سيرفر 2", streamUrl2, "720p"));
        }
        
        return servers;
    }

    // Inner class for streaming servers
    public static class StreamingServer {
        private String name;
        private String url;
        private String quality;
        
        public StreamingServer(String name, String url, String quality) {
            this.name = name;
            this.url = url;
            this.quality = quality;
        }
        
        public String getName() { return name; }
        public String getUrl() { return url; }
        public String getQuality() { return quality; }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", titleArabic='" + titleArabic + '\'' +
                ", titleEnglish='" + titleEnglish + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                '}';
    }
}