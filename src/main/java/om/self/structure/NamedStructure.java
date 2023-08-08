package om.self.structure;

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
     * Sets the name of whatever implements this. <br>
     * Implementation Note:
     * This method could not do anything if, for example, this was implemented by something with a final name
     * @param name the new name to set
     */
    void setName(K name);
}
