package om.self.structure.child;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An implementation of {@link ChildStructure} that uses a {@link HashSet} to store the children.
 * @param <T> the type of the children
 * @see ChildStructure
 */
public class ChildStructureImpl<T> implements ChildStructure<T> {
    /**
     * The children that are attached.
     */
    private final Set<T> children = ConcurrentHashMap.newKeySet();

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
    public Collection<T> getChildren() {return children;}
}