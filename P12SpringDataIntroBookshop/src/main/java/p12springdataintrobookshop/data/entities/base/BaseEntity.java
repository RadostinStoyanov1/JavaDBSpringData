package p12springdataintrobookshop.data.entities.base;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public BaseEntity() {}

    public int getId() {
        return id;
    }
}
