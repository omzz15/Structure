package om.self.structure.parent;

/**
 * Low level interface that indicates something contains a parent. The functionality provided by this interface is limited to getting the parent, detaching the parent, and checking if a parent is attached. This is meant to be used as a base for further implementations such as {@link ParentStructure} or {@link KeyedParentStructure}
 * @param <T> The type of the parent
 * @see ParentStructure
 * @see KeyedParentStructure
 */
public interface ParentContainer<T> {
    /**
     * Detaches the parent <br>
     * Implementation Note: call onParentDetach if your implementation has it
     * (ex: {@link ParentStructure#onParentDetach(Object)})
     */
    void detachParent();

    /**
     * method used to get the current parent
     * @return the current parent
     */
    T getParent();

    /**
     * checks whether there is a parent attached(if {@link ParentContainer#getParent()} is not null)
     * @return if a parent is attached
     */
    default boolean isParentAttached(){
        return getParent() != null;
    }
}
