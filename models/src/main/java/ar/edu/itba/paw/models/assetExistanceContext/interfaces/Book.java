package ar.edu.itba.paw.models.assetExistanceContext.interfaces;


public interface Book extends Asset {

    String getIsbn();

    String getLanguage();

    String getAuthor();

    int getId();

    void setAuthor(String author);

    void setLanguage(String language);

}
