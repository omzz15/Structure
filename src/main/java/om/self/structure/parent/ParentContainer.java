package om.self.structure.parent;

public interface ParentContainer<T> {
    /**
     * method used to detach current parent
     * @implNote make sure this method calls {@link ParentStructure#onParentDetach(Object)}
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
