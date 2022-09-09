package om.self.structure.child;

import java.util.Collection;

public interface ChildContainer<T> {
    /**
     * gets the attached children
     * @return the children
     */
    Collection<T> getChildren();

    /**
     * deletes all children attached to object
     */
    void detachChildren();

    /**
     * checks whether the passed in child is attached
     * @param child the child you want to check
     * @return if the child is attached
     */
    default boolean isChildAttached(T child){
        return getChildren().contains(child);
    }
}
