package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.Isbn;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddAssetForm {

    @NotEmpty(message = "addAssetForm.isbn.notEmpty")
    @Isbn(message = "addAssetForm.isbn.invalid")
    private String isbn;

    @NotEmpty(message = "addAssetForm.language.notEmpty")
    @Size(min = 3, max = 100, message = "addAssetForm.language.size")
    private String language;

    @NotEmpty(message = "addAssetForm.author.notEmpty")
    @Size(min = 3, max = 100, message = "addAssetForm.author.size")
    private String author;

    @NotEmpty(message = "addAssetForm.title.notEmpty")
    @Size(min = 3, max = 100, message = "addAssetForm.title.size")
    private String title;

    @NotEmpty(message = "addAssetForm.physicalCondition.notEmpty")
    @Size(min = 3, max = 100, message = "addAssetForm.physicalCondition.size")
    private String physicalCondition;

    @NotEmpty(message = "addAssetForm.zipcode.notEmpty")
    @Pattern(regexp = "^\\d{4}$", message = "addAssetForm.zipcode.pattern")
    private String zipcode;

    @NotEmpty(message = "addAssetForm.locality.notEmpty")
    @Size(min = 3, max = 100, message = "addAssetForm.locality.size")
    private String locality;

    @NotEmpty(message = "addAssetForm.province.notEmpty")
    @Size(min = 3, max = 100, message = "addAssetForm.province.size")
    private String province;

    @NotEmpty(message = "addAssetForm.country.notEmpty")
    @Size(min = 3, max = 100, message = "addAssetForm.country.size")
    private String country;

    @NotEmpty(message = "addAssetForm.email.notEmpty")
    @Email(message = "addAssetForm.email.invalid")
    private String email;

    @NotEmpty(message = "addAssetForm.name.notEmpty")
    @Size(min = 3, max = 100, message = "addAssetForm.name.size")
    private String name;



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


}
