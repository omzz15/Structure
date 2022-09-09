package om.self.structure.base.child;

import java.util.Collection;

public interface ChildStructure<T> extends ChildContainer<T> {
    /**
     * method used to attach a child
     * @param child the child being attached
     * @implNote make sure this method calls {@link ChildStructure#onChildAttach(Object)}
     */
    void attachChild(T child);

    /**
     * method used to attach multiple children
     * @param children the children being attached
     */
    default void attachChildren(Iterable<T> children){
        for (T child: children)
            attachChild(child);
    }

    /**
     * method used to detach a child
     * @param child the child being detached
     * @implNote make sure this method calls {@link ChildStructure#onChildDetach(Object)}
     */
    void detachChild(T child);

    /**
     * method that is called when a child is attached.
     * @param child the child that is being attached
     */
    default void onChildAttach(T child) {}


    /**
     * method that is called before a child is detached.
     * @param child the child that is being detached
     */
    default void onChildDetach(T child) {}
}
