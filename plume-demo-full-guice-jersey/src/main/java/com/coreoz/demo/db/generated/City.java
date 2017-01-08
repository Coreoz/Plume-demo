package com.coreoz.demo.db.generated;

import javax.annotation.Generated;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.querydsl.sql.Column;

/**
 * City is a Querydsl bean type
 */
@Generated("com.coreoz.plume.db.querydsl.generation.IdBeanSerializer")
public class City extends com.coreoz.plume.db.querydsl.crud.CrudEntityQuerydsl {

    @Column("active")
    private Boolean active;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("id")
    private Long id;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("id_file_image")
    private Long idFileImage;

    @Column("last_modified")
    private java.time.LocalDateTime lastModified;

    @Column("name")
    private String name;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFileImage() {
        return idFileImage;
    }

    public void setIdFileImage(Long idFileImage) {
        this.idFileImage = idFileImage;
    }

    public java.time.LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(java.time.LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (id == null) {
            return super.equals(o);
        }
        if (!(o instanceof City)) {
            return false;
        }
        City obj = (City) o;
        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        }
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "City#" + id;
    }

}

