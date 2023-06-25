package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.miscellaneous.ImageImpl;

import javax.persistence.*;

@Entity
@Table(name = "users")
final public class UserImpl{
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
    private ImageImpl profilePhoto;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(sequenceName = "users_id_seq", name = "users_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    public UserImpl(String email, String name, String telephone, String password, Behaviour behaviour) {
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.behavior = behaviour;
        this.password = password;
        this.profilePhoto = null;
    }

    public UserImpl(int id, String email, String name, String telephone, String password, Behaviour behaviour) {
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.id = Long.valueOf(id);
        this.behavior = behaviour;
        this.password = password;
        this.profilePhoto = null;
    }

    public UserImpl(String email, String name, String telephone, String password, Behaviour behaviour, ImageImpl profilePhoto) {
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.behavior = behaviour;
        this.password = password;
        this.profilePhoto = profilePhoto;
    }

    public UserImpl() {

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

    public ImageImpl getProfilePhoto() {
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

        UserImpl otherUser = (UserImpl) obj;
        return id != null && id.equals(otherUser.id);
    }



    public void setProfilePhoto(ImageImpl img) {
        this.profilePhoto = img;
    }

}

