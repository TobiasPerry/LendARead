package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Language;

public class LanguageImpl implements Language {
    private final String code;
    private final String name;

    public LanguageImpl(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Code: " + this.code + " - Name:" + this.name;
    }
}
