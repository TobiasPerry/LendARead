package ar.edu.itba.paw.models.userContext;

import ar.edu.itba.paw.models.miscellaneous.Image;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name = "users")
final public class User {
    @Column(length = 100, nullable = false, unique = true, name = "mail")
    private String email;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String telephone;
    @Column(length = 100, nullable = false)
    @Enumerated(EnumType.STRING)
    private Behaviour behavior;
    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String locale;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", referencedColumnName = "id", nullable = true)
    private Image profilePhoto;

    @Formula("(SELECT COALESCE(AVG(COALESCE(ur.rating, 0)),0) FROM users AS us left outer join userreview AS ur on us.id = ur.recipient  WHERE us.id = id)")
    private float rating;

    @Formula("(SELECT COALESCE(AVG(COALESCE(ur.rating, 0)),0) FROM users AS us left outer join userreview AS ur on us.id = ur.recipient  JOIN lendings AS l ON ur.lendId = l.id JOIN AssetInstance AS ai ON l.assetInstanceId = ai.id WHERE ur.recipient = id AND ai.owner = id AND us.id = id)")
    private float ratingAsLender;

    @Formula("(SELECT COALESCE(AVG(COALESCE(ur.rating, 0)),0) FROM users AS us left outer join userreview AS ur on us.id = ur.recipient  JOIN lendings AS l ON ur.lendId = l.id WHERE ur.recipient = id AND l.borrowerid = id )")
    private float ratingAsBorrower;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(sequenceName = "users_id_seq", name = "users_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    public User(String email, String name, String telephone, String password, Behaviour behaviour,String locale) {
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.behavior = behaviour;
        this.password = password;
        this.locale = locale;
        this.profilePhoto = null;
    }

    public User(int id, String email, String name, String telephone, String password, Behaviour behaviour,String locale) {
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.id = (long) id;
        this.behavior = behaviour;
        this.password = password;
        this.locale = locale;
        this.profilePhoto = null;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", message='" + telephone + '\'' +
                '}';
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getId() {
        return Math.toIntExact(id);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Behaviour getBehavior() {
        return behavior;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getName() {
        return this.name;
    }

    public Image getProfilePhoto() {
        return this.profilePhoto;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setBehaviour(Behaviour behaviour) {
        this.behavior = behaviour;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setBehavior(Behaviour behavior) {
        this.behavior = behavior;
    }

    public float getRating() {
        return rating;
    }

    public float getRatingAsLender() {
        return ratingAsLender;
    }

    public float getRatingAsBorrower() {
        return ratingAsBorrower;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User otherUser = (User) obj;
        return id != null && id.equals(otherUser.id);
    }


    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (behavior != null ? behavior.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (profilePhoto != null ? profilePhoto.hashCode() : 0);
        result = 31 * result + (this.getRating() != +0.0f ? Float.floatToIntBits(this.rating) : 0);
        result = 31 * result + (this.getRatingAsLender() != +0.0f ? Float.floatToIntBits(this.getRatingAsLender()) : 0);
        result = 31 * result + (this.getRatingAsBorrower() != +0.0f ? Float.floatToIntBits(this.getRatingAsBorrower()) : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    public void setProfilePhoto(Image img) {
        this.profilePhoto = img;
    }

}

