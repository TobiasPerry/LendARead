package ar.edu.itba.paw.webapp.form;

//import org.hibernate.validator.constraints.ISBN;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddAssetForm {

    //@NotEmpty
//    @ISBN(type = ISBN.Type.ANY)
    private String isbn;
    //@NotEmpty
    private String language;
    //@NotEmpty
    private String author;

    //@NotEmpty
    private String title;

    //@NotEmpty
    private String physicalCondition;

    //@NotEmpty
    @Pattern(regexp = "^\\d{4}$")
    private String zipcode;

    //@NotEmpty
    private String locality;

    //@NotEmpty
    private String province;

    //@NotEmpty
    private String country;

    //@NotEmpty
//    @Email
    private String email;

    //@NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    //@NotEmpty
    @Size(min = 10, max = 200)
    private String message;

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
