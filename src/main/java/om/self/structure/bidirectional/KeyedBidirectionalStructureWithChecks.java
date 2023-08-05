package om.self.structure.bidirectional;

import om.self.structure.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * An extension of {@link KeyedBidirectionalStructure} that adds the ability to run checks before attaching or detaching anything
 * @param <PARENT> the type of the parent
 * @param <CHILD> the type of the child
 * @param <K>  the type of the key used to identify children/parents
 */
public class KeyedBidirectionalStructureWithChecks<K, PARENT, CHILD> extends KeyedBidirectionalStructure<K, PARENT, CHILD> {
    //A list of all the checks that must be passed before a parent can be attached
    private final List<Utils.KeyedCheck<K,PARENT>> parentChecks = new LinkedList<>();
    //A list of all the checks that must be passed before a child can be attached
    private final List<Utils.KeyedCheck<K,CHILD>> childChecks = new LinkedList<>();

    /**
     * Creates a keyed bidirectional node without a patent, children, or checks
     */
    public KeyedBidirectionalStructureWithChecks() {
    }

    /**
     * add a check to preform when the parent is edited
     * @param check the check to preform
     */
    public void addParentCheck(Utils.KeyedCheck<K,PARENT> check){
        parentChecks.add(check);
    }

    /**
     * get all parent checks
     * @return all parent checks
     */
    public List<Utils.KeyedCheck<K,PARENT>> getParentChecks(){
        return parentChecks;
    }

    /**
     * remove a parent check
     * @param check the check to remove
     */
    public void removeParentCheck(Utils.KeyedCheck<K,PARENT> check){
        parentChecks.remove(check);
    }

    /**
     * remove a parent check
     * @param index the index of the check to remove
     */
    public void removeParentCheck(int index){
        parentChecks.remove(index);
    }

    /**
     * remove all parent checks
     */
    public void clearParentChecks(){
        parentChecks.clear();
    }

    /**
     * add a check to preform when the children are edited
     * @param check the check to preform
     */
    public void addChildCheck(Utils.KeyedCheck<K,CHILD> check){
        childChecks.add(check);
    }

    /**
     * get all child checks
     * @return all child checks
     */
    public List<Utils.KeyedCheck<K,CHILD>> getChildChecks(){
        return childChecks;
    }

    /**
     * remove a child check
     * @param check the check to remove
     */
    public void removeChildCheck(Utils.KeyedCheck<K,CHILD> check){
        childChecks.remove(check);
    }

    /**
     * remove a child check
     * @param index the index of the check to remove
     */
    public void removeChildCheck(int index){
        childChecks.remove(index);
    }

    /**
     * remove all child checks
     */
    public void clearChildChecks(){
        childChecks.clear();
    }

    /**
     * Makes sure all {@link KeyedBidirectionalStructureWithChecks#childChecks} pass before attaching the child using {@link KeyedBidirectionalStructure#attachChild(Object, Object)}.
     * @param child The child being attached
     * @param customParentKey if not null, it will try to use this parameter as the key for attaching this object as the parent to the child else it will just use the key parameter
     */
    @Override
    public void attachChild(K key, CHILD child, K customParentKey){
        for(Utils.KeyedCheck<K,CHILD> check : childChecks)
            if(!check.apply(key, child, Utils.Action.ATTACH)) return;

        super.attachChild(key, child, customParentKey);
    }

    /**
     * If the child is already attached, it detached the child then detaches itself as a parent from the child if it's the right type.
     *
     * @param key The key of the child being detached
     */
    @Override
    public void detachChild(K key) {
        if(!isChildKeyAttached(key)) return;

        CHILD c = getChild(key);
        for(Utils.KeyedCheck<K,CHILD> check : childChecks)
            if(!check.apply(key, c, Utils.Action.DETACH)) return;

        super.detachChild(key);
    }

    /**
     * Makes sure all {@link KeyedBidirectionalStructureWithChecks#parentChecks} pass before attaching the parent using {@link KeyedBidirectionalStructure#attachParent(Object, Object, Object)}
     * @param key the key associated with the parent
     * @param parent the parent being attached
     * @param customChildKey if not null, it will try to use this parameter as the key for attaching this object as the child to the parent else it will just use the key parameter
     */
    @Override
    public void attachParent(K key, PARENT parent, K customChildKey) {
        for(Utils.KeyedCheck<K,PARENT> check : parentChecks)
            if(!check.apply(key, parent, Utils.Action.ATTACH)) return;

        super.attachParent(key, parent, customChildKey);
    }

    /**
     * Check to make sure all parent checks are good them detaches the parent then detaches itself from the parent if it is the right type.
     * @param customChildName if not null, it will try to use this parameter to detach this object as a child from the parent else it will just use the parentKey variable
     */
    @Override
    public void detachParent(K customChildName) {
        if(!isParentAttached()) return;

        K k = getParentKey();
        PARENT p = getParent();
        for(Utils.KeyedCheck<K,PARENT> check : parentChecks)
            if(!check.apply(k, p, Utils.Action.DETACH)) return;

        super.detachParent(customChildName);
    }
}
