package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import javax.persistence.Column;
import javax.persistence.Id;

public class LanguageImpl {

    @Id
    @Column(length = 3, nullable = false)
    private final String code;

    @Column
    private final String name;

    public LanguageImpl(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Code: " + this.code + " - Name:" + this.name;
    }
}
