package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.userContext.interfaces.User;

final public class UserImpl implements User {
    private final String email;
    private final String name;
    private final String telephone;
    private final Behaviour behavior;
    private final String password;
    private final  int id;
    public UserImpl(int id,String email, String name, String telephone,String password,Behaviour behaviour) {
        this.email = email;
        this.name = name;
        this.telephone = telephone;
        this.id = id;
        this.behavior = behaviour;
        this.password = password;
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

