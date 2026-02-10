package com.xyz.moviebooking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
public class Show {
    @Id
    private Long id;

    @Column(nullable = false)
    private Long theatreId;

    @Column(nullable = false)
    private Long screenId;

    @Column(nullable = false)
    private String movieTitle;

    @Column(nullable = false)
    private LocalDateTime showTime;

    public Long getId() { return id; }
    public Long getTheatreId() { return theatreId; }
    public Long getScreenId() { return screenId; }
    public String getMovieTitle() { return movieTitle; }
    public LocalDateTime getShowTime() { return showTime; }

    public void setId(Long id) { this.id = id; }
    public void setTheatreId(Long theatreId) { this.theatreId = theatreId; }
    public void setScreenId(Long screenId) { this.screenId = screenId; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public void setShowTime(LocalDateTime showTime) { this.showTime = showTime; }
}
