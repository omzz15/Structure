package om.self.structure.child;

/**
 * An extension of {@link ChildContainer} that adds the ability to attach and detach children, plus methods that get called on attach and detach.
 * @param <T> the type of the children
 * @see ChildContainer
 */
public interface ChildStructure<T> extends ChildContainer<T> {
    /**
     * Attaches a child. <br>
     * Implementation Note: This method should call {@link ChildStructure#onChildAttach(Object)}
     * @param child The child being attached
     */
    default void attachChild(T child){
        if(getChildren().add(child))
            onChildAttach(child);
    }

    /**
     * Attaches multiple children by calling {@link ChildStructure#attachChild(Object)}.
     * @param children The children being attached
     */
    default void attachChildren(Iterable<T> children){
        for (T child: children)
            attachChild(child);
    }

    /**
     * Detaches a child. <br>
     * Implementation Note: This method should call {@link ChildStructure#onChildDetach(Object)}
     * @param child The child being detached
     */
    default void detachChild(T child){
        if(getChildren().remove(child))
            onChildDetach(child);
    }

    /**
     * Detaches multiple children by calling {@link ChildStructure#detachChild(Object)}.
     * @param children The children being detached
     */
    default void detachChildren(Iterable<T> children){
        for (T child: children)
            detachChild(child);
    }

    /**
     * Detaches all children by calling {@link ChildStructure#detachChild(Object)} <br>
     * Implementation Note: To use the default implementation of this method, the set used to store the children must be concurrent
     */
    @Override
    default void detachChildren() {
        for (T child: getChildren())
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
