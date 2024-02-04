package ar.edu.itba.paw.models.assetExistanceContext;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        if (!Objects.equals(code, language.code)) return false;
        return Objects.equals(name, language.name);
    }


}
