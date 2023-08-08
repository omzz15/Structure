package om.self.structure.child;

import om.self.structure.Utils;
import om.self.structure.bidirectional.KeyedBidirectionalStructure;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * An extension of {@link KeyedChildStructureImpl} that adds the ability to run checks before attaching or detaching anything
 * @param <V> the type of the child
 * @param <K>  the type of the key used to identify children
 */
public class KeyedChildStructureWithChecks<K, V> extends KeyedChildStructureImpl<K, V> {
    private final List<Utils.KeyedCheck<K,V>> checks = new LinkedList<>();

    /**
     * Default constructor
     */
    public KeyedChildStructureWithChecks() {
    }

    /**
     * Constructor that automatically attaches children
     *
     * @param children the children to attach(this has to be in the format of a map between child keys and the child)
     */
    public KeyedChildStructureWithChecks(Map<K, V> children) {
        super(children);
    }

    /**
     * Adds a check to the list of checks.
     * @param check The check being added
     */
    public void addCheck(Utils.KeyedCheck<K,V> check){
        checks.add(check);
    }

    /**
     * Gets all checks.
     * @return All checks
     */
    public List<Utils.KeyedCheck<K,V>> getChecks(){
        return checks;
    }

    /**
     * Removes a check.
     * @param check The check being removed
     */
    public void removeCheck(Utils.KeyedCheck<K,V> check){
        checks.remove(check);
    }

    /**
     * Removes a check.
     * @param index The index of the check being removed
     */
    public void removeCheck(int index){
        checks.remove(index);
    }

    /**
     * Removes all checks.
     */
    public void clearChecks(){
        checks.clear();
    }

    /**
     * Attaches a child with a key. <br>
     * Implementation note: This method should call {@link KeyedChildStructure#onChildAttach(Object, Object)}
     *
     * @param key   the key associated with the child
     * @param child the child being attached
     */
    @Override
    public void attachChild(K key, V child) {
        for (Utils.KeyedCheck<K,V> check : checks)
            if(!check.apply(key, child, Utils.Action.ATTACH)) return;

        super.attachChild(key, child);
    }

    /**
     * Detaches a child based on a key. <br>
     * Implementation note: This method should call {@link KeyedChildStructure#onChildDetach(Object, Object)}
     *
     * @param key the key of the child being detached
     */
    @Override
    public void detachChild(K key) {
        if(!isChildKeyAttached(key)) return;
        V c = getChild(key);
        for (Utils.KeyedCheck<K,V> check: checks)
            if(!check.apply(key, c, Utils.Action.DETACH)) return;

        super.detachChild(key);
    }
}
