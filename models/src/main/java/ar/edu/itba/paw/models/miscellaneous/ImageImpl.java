package ar.edu.itba.paw.models.miscellaneous;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class ImageImpl implements Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photos_id_seq")
    @SequenceGenerator(name = "photos_id_seq", sequenceName = "photos_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    public ImageImpl() {
        // Default constructor required by JPA
    }

    public ImageImpl(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public byte[] getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
