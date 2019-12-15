package com.selimkilicaslan.guideme.classes;

public class LanguageOffered {
    private String language;
    private Boolean isOffered;

    public LanguageOffered() {
    }

    public LanguageOffered(String language, Boolean isOffered) {
        this.language = language;
        this.isOffered = isOffered;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getOffered() {
        return isOffered;
    }

    public void setOffered(Boolean offered) {
        isOffered = offered;
    }
}
