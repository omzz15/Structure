package om.self.structure.child;

import om.self.structure.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
     * @param children the children to attach
     */
    public KeyedChildStructureWithChecks(Map<K, V> children) {
        super(children);
    }

    public void addCheck(Utils.KeyedCheck<K,V> check){
        checks.add(check);
    }

    public List<Utils.KeyedCheck<K,V>> getChecks(){
        return checks;
    }

    public void removeCheck(Utils.KeyedCheck<K,V> check){
        checks.remove(check);
    }

    public void removeCheck(int index){
        checks.remove(index);
    }

    public void clearChecks(){
        checks.clear();
    }

    /**
     * Attaches a child with a key.
     *
     * @param key   the key associated with the child
     * @param child the child being attached
     * @implNote This method should call {@link KeyedChildStructure#onChildAttach(Object, Object)}
     */
    @Override
    public void attachChild(K key, V child) {
        for (Utils.KeyedCheck<K,V> check : checks)
            if(!check.apply(key, child, Utils.Action.ATTACH)) return;

        super.attachChild(key, child);
    }

    /**
     * Detaches a child based on a key.
     *
     * @param key the key of the child being detached
     * @implNote make sure this method calls {@link KeyedChildStructure#onChildDetach(Object, Object)}
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
