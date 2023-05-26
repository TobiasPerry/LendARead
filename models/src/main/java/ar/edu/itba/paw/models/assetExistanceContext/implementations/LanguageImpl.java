package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "languages")
public class LanguageImpl {

    @Id
    @Column(length = 3, nullable = false, name = "id")
    private String code;

    @Column
    private String name;

    public LanguageImpl(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    public LanguageImpl() {
        // For Hibernate
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
