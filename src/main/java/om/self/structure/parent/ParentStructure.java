package om.self.structure.parent;

/**
 * An extension of {@link ParentContainer} that adds the ability to attach a parent, plus methods that get called on attach and detach.
 * @param <T> the type of the parent
 * @see ParentContainer
 */
public interface ParentStructure<T> extends ParentContainer<T> {
    /**
     * Attaches a parent <br>
     * Implementation Note: This method should call {@link ParentStructure#onParentAttach(Object)}
     * @param parent the parent being attached
     */
    void attachParent(T parent);

    /**
     * Called when a parent is attached or changed.
     * @param parent the parent that is being attached
     */
    default void onParentAttach(T parent){}

    /**
     * Called when a parent is detached.
     * @param parent the parent that is being detached
     */
    default void onParentDetach(T parent){}
}
