package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.userContext.interfaces.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
final public class UserImpl implements User {
    private String email;
    private String name;
    private String telephone;
    private Behaviour behavior;
    private String password;
    @Id
    private int id;
    public UserImpl(int id,String email, String name, String telephone,String password,Behaviour behaviour) {
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.id = id;
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
        return id;
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

