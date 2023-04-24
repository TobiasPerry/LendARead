package ar.edu.itba.paw.models.userContext.interfaces;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;

public interface User {
    String getEmail();

    String getPassword();

    Behaviour getBehavior();

    int getId();

    String getTelephone();

    String getName();
}
