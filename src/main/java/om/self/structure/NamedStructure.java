package om.self.structure;

//v1 complete

/**
 * Signals that something has a unique name that can be used as a key in keyed structures.
 * @param <K> The type of the key
 */
public interface NamedStructure<K>{
    /**
     * Gets the name of whatever implements this.
     * @return the name
     */
    K getName();

    /**
     * Sets the name of whatever implements this.
     * @param name the new name to set
     * @implNote This method could not do anything if, for example, this was implemented by something with a final name
     */
    void setName(K name);
}
