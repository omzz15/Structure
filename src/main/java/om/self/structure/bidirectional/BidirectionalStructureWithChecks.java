package om.self.structure.bidirectional;

import om.self.structure.Utils;
import om.self.structure.child.ChildStructure;
import om.self.structure.parent.ParentStructure;

import java.util.LinkedList;
import java.util.List;

/**
 * An extension of {@link BidirectionalStructure} that adds the ability to run checks before attaching or detaching anything
 * @param <PARENT> the type of the parent
 * @param <CHILD> the type of the child
 */
public class BidirectionalStructureWithChecks<PARENT, CHILD> extends BidirectionalStructure<PARENT, CHILD> {
    private final List<Utils.Check<PARENT>> parentChecks = new LinkedList<>();
    private final List<Utils.Check<CHILD>> childChecks = new LinkedList<>();

    /**
     * Default constructor that creates a bidirectional node with checks without children or a parent
     */
    public BidirectionalStructureWithChecks() {
    }

    /**
     * Creates a bidirectional node with checks with the specified parent attached
     *
     * @param parent the parent of this node
     */
    public BidirectionalStructureWithChecks(PARENT parent) {
        super(parent);
    }

    /**
     * Creates a bidirectional node with checks with the specified children attached
     *
     * @param children the children to attach to this node
     */
    public BidirectionalStructureWithChecks(Iterable<CHILD> children) {
        super(children);
    }

    /**
     * add a check to preform when the parent is edited
     * @param check the check to preform
     */
    public void addParentCheck(Utils.Check<PARENT> check){
        parentChecks.add(check);
    }

    /**
     * get all parent checks
     * @return all parent checks
     */
    public List<Utils.Check<PARENT>> getParentChecks(){
        return parentChecks;
    }

    /**
     * remove a parent check
     * @param check the check to remove
     */
    public void removeParentCheck(Utils.Check<PARENT> check){
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
    public void addChildCheck(Utils.Check<CHILD> check){
        childChecks.add(check);
    }

    /**
     * get all child checks
     * @return all child checks
     */
    public List<Utils.Check<CHILD>> getChildChecks(){
        return childChecks;
    }

    /**
     * remove a child check
     * @param check the check to remove
     */
    public void removeChildCheck(Utils.Check<CHILD> check){
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
     * If the child is not already attached, it attaches the child then attaches itself as a parent to the child if it's the right type.
     *
     * @param child The child being attached
     */
    @Override
    public void attachChild(CHILD child) {
        for(Utils.Check<CHILD> check : childChecks)
            if(!check.apply(child, Utils.Action.ATTACH)) return;

        super.attachChild(child);
    }

    /**
     * If the child is already attached, it detached the child then detaches itself as a parent from the child if it's the right type.
     *
     * @param child The child being detached
     */
    @Override
    public void detachChild(CHILD child) {
        if(!isChildAttached(child)) return;

        for(Utils.Check<CHILD> check : childChecks)
            if(!check.apply(child, Utils.Action.DETACH)) return;

        super.detachChild(child);
    }

    /**
     * Detaches the previous parent, then attaches the new parent and itself as a child to the parent if it is the right type.
     *
     * @param parent the parent being attached
     */
    @Override
    public void attachParent(PARENT parent) {
        for(Utils.Check<PARENT> check : parentChecks)
            if(!check.apply(parent, Utils.Action.ATTACH)) return;

        super.attachParent(parent);
    }

    /**
     * Detaches the parent then detaches itself from the parents children if it is the right type.
     */
    @Override
    public void detachParent() {
        if(!isParentAttached()) return;

        for(Utils.Check<PARENT> check : parentChecks)
            if(!check.apply(getParent(), Utils.Action.DETACH)) return;

        super.detachParent();
    }
}
