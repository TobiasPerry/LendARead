package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.Isbn;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddAssetForm {

    @NotEmpty
    @Isbn
    private String isbn;
    @NotEmpty
    @Size(min = 3,max = 100)
    private String language;
    @NotEmpty
    @Size(min = 3,max = 100)
    private String author;

    @NotEmpty
    @Size(min = 3,max = 100)
    private String title;

    @NotEmpty
    @Size(min = 3,max = 100)
    private String physicalCondition;

    @NotEmpty
    @Pattern(regexp = "^\\d{4}$")
    private String zipcode;

    @NotEmpty
    @Size(min = 3,max = 100)
    private String locality;

    @NotEmpty
    @Size(min = 3,max = 100)
    private String province;

    @NotEmpty
    @Size(min = 3,max = 100)
    private String country;





    public String getIsbn() {
        return isbn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPhysicalCondition() {
        return physicalCondition;
    }

    public void setPhysicalCondition(String physicalCondition) {
        this.physicalCondition = physicalCondition;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
