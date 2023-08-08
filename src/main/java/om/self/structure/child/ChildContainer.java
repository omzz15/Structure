package om.self.structure.child;

import java.util.Collection;

/**
 * Low level interface that indicates something contains children. The functionality provided by this interface is limited to getting all children, detaching all children, and checking if a child is attached. This is meant to be used as a base for further implementations such as {@link ChildStructure} or {@link KeyedChildStructure}
 * @param <T> The type of the children
 * @see ChildStructure
 * @see KeyedChildStructure
 */
public interface ChildContainer<T> {
    /**
     * Gets all attached children <br>
     * Note: In order for the default implementations of methods such as {@link ChildContainer#detachChildren()} to work, this must return a reference(pointer) to the child collection(not a copy).
     * @return Attached children
     */
    Collection<T> getChildren();

    /**
     * Detaches all attached children
     */
    default void detachChildren(){
        getChildren().clear();
    }

    /**
     * Checks whether the child is attached (uses the Collections.contains method)
     * @param child The child you want to check
     * @return If the child is attached
     */
    default boolean isChildAttached(T child){
        return getChildren().contains(child);
    }
}
