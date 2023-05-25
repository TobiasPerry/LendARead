package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Language;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class LanguageImpl implements Language {

    @Id
    @Column(length = 3, nullable = false)
    private final String code;

    @Column
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
