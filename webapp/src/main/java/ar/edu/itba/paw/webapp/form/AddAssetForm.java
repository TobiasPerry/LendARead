package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddAssetForm {

    @NotEmpty
    @Size(min = 2, max = 100)
    private String title;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String author;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String language;

    @NotEmpty
    private String isbn;

    @NotEmpty
    private String physicalCondition;

    @NotEmpty
    @Pattern(regexp = "^\\d{5}$") // 5-digit postal code
    private String zipcode;

    @NotEmpty
    private String locality;

    @NotEmpty
    private String province;

    @NotEmpty
    private String country;

    @NotEmpty
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") // Email format
    private String email;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @NotEmpty
    @Size(min = 10, max = 200)
    private String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsbn() {
        return isbn;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
