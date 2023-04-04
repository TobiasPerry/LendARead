package models.assetExistanceContext.interfaces;

import models.Interfaces.Asset;

public interface Book extends Asset {

    String getIsbn();

    String getLanguage();

    String author();

}
