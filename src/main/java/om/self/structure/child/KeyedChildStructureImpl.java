package om.self.structure.child;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An implementation of {@link KeyedChildStructure} that uses a {@link Hashtable} to store the children.
 * @param <K> the type of the key
 * @param <V> the type of the children
 * @see KeyedChildStructure
 */
public class KeyedChildStructureImpl<K, V> implements KeyedChildStructure<K, V> {
    private final ConcurrentHashMap<K, V> children = new ConcurrentHashMap<>();

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
