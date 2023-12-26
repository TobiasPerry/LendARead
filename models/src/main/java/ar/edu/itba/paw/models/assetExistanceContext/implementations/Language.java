package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "languages")
public class Language {

    @Id
    @Column(length = 3, nullable = false, name = "id")
    private String code;

    @Column
    private String name;

    public Language(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    public Language() {
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
