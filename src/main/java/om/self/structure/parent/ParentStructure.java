package om.self.structure.parent;

/**
 * An extension of {@link ParentContainer} that adds the ability to attach and detach parents, plus methods that get called on attach and detach.
 * @param <T> the type of the parent
 * @see ParentContainer
 */

public interface ParentStructure<T> extends ParentContainer<T> {
    /**
     * method used to attach a parent
     * @param parent the parent being attached
     * @implNote make sure this method calls {@link ParentStructure#onParentAttach(Object)}
     */
    void attachParent(T parent);

    /**
     * method that is called when a parent is attached or changed.
     * @param parent the parent that is being attached
     */
    default void onParentAttach(T parent){}

    /**
     * method that is called when a parent is detached.
     * @param parent the parent that is being detached
     */
    default void onParentDetach(T parent){}
}
