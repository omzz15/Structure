package om.self.structure.child;

import om.self.structure.child.ChildStructure;

import java.util.HashSet;
import java.util.Set;

/**
 * basic structure class that stores children of type T and has abstract methods for after a child is attached and before a child is detached
 * @param <T> the type of the child object
 */
public class ChildStructureImpl<T> implements ChildStructure<T> {
    /**
     * The children that are associated with this object.
     */
    private final Set<T> children = new HashSet<>();

    /**
     * default constructor
     */
    public ChildStructureImpl(){}

    /**
     * constructor that will automatically attach the passed in children
     */
    public ChildStructureImpl(Iterable<T> children){
        attachChildren(children);
    }

    /**
     * gets the attached children
     * @return the children attached
     */
    public Set<T> getChildren() {
        return children;
    }

    /**
     * attaches a child and calls {@link ChildStructure#onChildAttach(Object)}
     * @param child the child to add to this structure
     */
    public void attachChild(T child) {
        if (children.add(child))
            onChildAttach(child);
    }

    /**
     * detaches a child and calls {@link ChildStructure#onChildDetach(Object)}
     * @param child the child to remove
     */
    public void detachChild(T child){
        if (children.remove(child))
            onChildDetach(child);
    }

    @Override
    public void detachChildren() {
        children.clear();
    }
}