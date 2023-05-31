package ar.edu.itba.paw.models.miscellaneous;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "photos")
public class ImageImpl {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photos_id_seq")
    @SequenceGenerator(name = "photos_id_seq", sequenceName = "photos_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "photo")
    private byte[] photo;

    public ImageImpl() {
        // Default constructor required by JPA
    }

    public ImageImpl(byte[] photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageImpl image = (ImageImpl) o;
        return Arrays.equals(photo, image.photo);
    }
}
