package om.self.structure.parent;

import om.self.structure.Utils;

import java.util.LinkedList;
import java.util.List;

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
    */
    public KeyedParentStructureWithChecks(K key, V parent) {
        super(key, parent);
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
