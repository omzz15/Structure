package om.self.structure.parent;

import om.self.structure.Utils;
import om.self.structure.bidirectional.KeyedBidirectionalStructure;

import java.util.LinkedList;
import java.util.List;

/**
 * An extension of {@link KeyedParentStructureImpl} that adds the ability to run checks before attaching or detaching anything
 * @param <V> the type of the parent
 * @param <K>  the type of the key used to identify parents
 */
public class KeyedParentStructureWithChecks<K,V> extends KeyedParentStructureImpl<K,V> {
    private final List<Utils.KeyedCheck<K,V>> checks = new LinkedList<>();

    /**
     * Default constructor
     */
    public KeyedParentStructureWithChecks() {
        super();
    }

    /**
     * Constructor that automatically attaches a parent
     *
     * @param parent the parent being attached
     * @param key the key for the parent
    */
    public KeyedParentStructureWithChecks(K key, V parent) {
        super(key, parent);
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
     * Detaches the current parent if one is attached then attaches a parent.
     *
     * @param key    the key of the parent
     * @param parent the parent being attached
     */
    @Override
    public void attachParent(K key, V parent) {
        for (Utils.KeyedCheck<K,V> check : checks)
            if(!check.apply(key, parent, Utils.Action.ATTACH)) return;
        super.attachParent(key, parent);
    }

    /**
     * Detached the parent if one is attached.
     */
    @Override
    public void detachParent() {
        if(!isParentAttached()) return;

        K k = getParentKey();
        V p = getParent();
        for (Utils.KeyedCheck<K,V> check: checks)
            if(!check.apply(k, p, Utils.Action.DETACH)) return;
        super.detachParent();
    }
}
