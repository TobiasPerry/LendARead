package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.userContext.interfaces.User;

import javax.persistence.*;

@Entity
@Table(name = "users")
final public class UserImpl implements User {
    @Column(length = 100, nullable = false, unique = true, name = "mail")
    private String email;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String telephone;
    @Column(length = 100, nullable = false)
    private Behaviour behavior;
    @Column(length = 100, nullable = false)
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(sequenceName = "users_id_seq", name = "users_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    public UserImpl(String email, String name, String telephone, Behaviour behavior, String password) {
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.behavior = behavior;
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

    @Override
    public int getId() {
        return Math.toIntExact(id);
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Behaviour getBehavior() {
        return behavior;
    }

    @Override
    public String getTelephone() {
        return telephone;
    }

    @Override
    public String getName() {
        return this.name;
    }

}

