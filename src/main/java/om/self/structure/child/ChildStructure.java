package om.self.structure.child;

/**
 * An extention of {@link ChildContainer} that adds the ability to attach and detach children, plus methods that get called on attach and detach.
 * @param <T> the type of the children
 * @see ChildContainer
 */
public interface ChildStructure<T> extends ChildContainer<T> {
    /**
     * Attaches a child.
     * @param child The child being attached
     * @implNote Make sure this method calls {@link ChildStructure#onChildAttach(Object)}
     */
    void attachChild(T child);

    /**
     * Attaches multiple children by calling {@link ChildStructure#attachChild(Object)}.
     * @param children The children being attached
     */
    default void attachChildren(Iterable<T> children){
        for (T child: children)
            attachChild(child);
    }

    /**
     * Detaches a child.
     * @param child The child being detached
     * @implNote Make sure this method calls {@link ChildStructure#onChildDetach(Object)}
     */
    void detachChild(T child);

    /**
     * Detaches multiple children by calling {@link ChildStructure#attachChild(Object)}.
     * @param children The children being attached
     */
    default void detachChildren(Iterable<T> children){
        for (T child: children)
            detachChild(child);
    }

    /**
     * Called when a child is attached.
     * @param child The child that is being attached
     */
    default void onChildAttach(T child) {}

    /**
     * Called when a child is detached.
     * @param child The child that is being detached
     */
    default void onChildDetach(T child) {}
}
