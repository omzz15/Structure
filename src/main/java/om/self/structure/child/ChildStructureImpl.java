package om.self.structure.child;

import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of {@link ChildStructure} that uses a {@link HashSet} to store the children.
 * @param <T> the type of the children
 * @see ChildStructure
 */
public class ChildStructureImpl<T> implements ChildStructure<T> {
    /**
     * The children that are attached.
     */
    private final Set<T> children = new HashSet<>();

    /**
     * Default constructor
     */
    public ChildStructureImpl(){}

    /**
     * Constructor that automatically attaches children
     * @param children the children to attach
     */
    public ChildStructureImpl(Iterable<T> children){
        attachChildren(children);
    }

    /**
     * Gets all attached children
     * @return Attached children
     */
    @Override
    public Set<T> getChildren() {
        return children;
    }

    /**
     * Attaches a child and calls {@link ChildStructure#onChildAttach(Object)}
     * @param child The child being attached
     */
    public void attachChild(T child) {
        if (children.add(child))
            onChildAttach(child);
    }

    /**
     * Detaches a child and calls {@link ChildStructure#onChildDetach(Object)}
     * @param child The child being detached
     */
    public void detachChild(T child){
        if (children.remove(child))
            onChildDetach(child);
    }
}