package com.example.crm.models;

import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;

/**
 * Abstract base class for all entities in the application.
 * Provides common attributes and methods for entities.
 */
@MappedSuperclass
public abstract class AbstractEntity {

    /**
     * Unique identifier for the entity.
     * Generated using a sequence generator.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    @SequenceGenerator(name = "idgenerator", initialValue = 1000)
    private Long id;

    /**
     * Version number for optimistic locking.
     * Used to detect concurrent modifications.
     */
    @Version
    private Integer version;

    /**
     * Returns the unique identifier for the entity.
     * 
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the entity.
     * 
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the version number for optimistic locking.
     * 
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    @Override
    /**
     * Returns a hash code value for the object.
     * Used for storing and retrieving objects from a hash-based data structure.
     * 
     * @return a hash code value for this object
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        return result;
    }

    @Override
    /**
     * Indicates whether some other object is "equal to" this one.
     * Used for comparing objects.
     * 
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractEntity other = (AbstractEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }
}