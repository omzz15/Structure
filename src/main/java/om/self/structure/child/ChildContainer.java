package om.self.structure.child;

import java.util.Collection;

/**
 * Low level interface that indicates something contains children. The functionality provided by this interface is limted to getting all children, detaching all children, and checking if a child is attached. This is ment to be used as a base for further implementations such as {@link ChildStructure} or {@link KeyedChildStructure}
 * @param <T> The type of the children
 * @see ChildStructure
 * @see KeyedChildStructure
 */
public interface ChildContainer<T> {
    /**
     * Gets all attached children
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
     * Checks whether the child is attached
     * @param child The child you want to check
     * @return If the child is attached
     */
    default boolean isChildAttached(T child){
        return getChildren().contains(child);
    }
}
