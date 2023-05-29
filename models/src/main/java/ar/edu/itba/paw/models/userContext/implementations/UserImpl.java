package ar.edu.itba.paw.models.userContext.implementations;

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
    }

    public UserImpl(int id, String email, String name, String telephone, String password, Behaviour behaviour) {
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.id = Long.valueOf(id);
        this.behavior = behaviour;
        this.password = password;
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
}

