package models.implementations;

import models.Interfaces.Asset;

public class Book implements Asset {

    
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public byte[] getPhoto() {
        return new byte[0];
    }

    @Override
    public String display() {
        return null;
    }

    @Override
    public String type() {
        return null;
    }
}
