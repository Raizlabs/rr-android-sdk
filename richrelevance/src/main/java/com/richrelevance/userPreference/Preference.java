package com.richrelevance.userPreference;

import java.util.List;

public class Preference {
    private TargetType targetType;
    
    private List<String> likes;
    private List<String> dislikes;
    private List<String> neutrals;
    private List<String> notForRecs;

    public Preference(TargetType targetType) {
        this.targetType = targetType;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public List<String> getLikes() {
        return likes;
    }

    void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<String> getDislikes() {
        return dislikes;
    }

    void setDislikes(List<String> dislikes) {
        this.dislikes = dislikes;
    }

    public List<String> getNeutrals() {
        return neutrals;
    }

    void setNeutrals(List<String> neutrals) {
        this.neutrals = neutrals;
    }

    public List<String> getNotForRecommendations() {
        return notForRecs;
    }

    void setNotForRecommendations(List<String> notForRecs) {
        this.notForRecs = notForRecs;
    }
}
