package om.self.structure.child;

import java.util.Hashtable;
import java.util.Map;

//v1 complete

/**
 * An implementation of {@link KeyedChildStructure} that uses a {@link Hashtable} to store the children.
 * @param <K> the type of the key
 * @param <V> the type of the children
 * @see KeyedChildStructure
 */
public class KeyedChildStructureImpl<K, V> implements KeyedChildStructure<K, V> {
    private final Hashtable<K, V> children = new Hashtable<>();

    /**
     * Default constructor
     */
    public KeyedChildStructureImpl(){}

    /**
     * Constructor that automatically attaches children
     * @param children the children to attach
     */
    public KeyedChildStructureImpl(Map<K,V> children){
        attachChildren(children);
    }

    /**
     * Gets the attached children along with their keys.
     * @return The children and their keys
     */
    @Override
    public Map<K, V> getChildrenAndKeys() {
        return children;
    }
}
