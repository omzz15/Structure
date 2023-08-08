package om.self.structure.child;

import om.self.structure.Utils;
import om.self.structure.bidirectional.KeyedBidirectionalStructure;

import java.util.LinkedList;
import java.util.List;

/**
 * An extension of {@link ChildStructureImpl} that adds the ability to run checks before attaching or detaching anything
 * @param <T> the type of the child
 */
public class ChildStructureWithChecks<T> extends ChildStructureImpl<T> {
    private final List<Utils.Check<T>> checks = new LinkedList<>();

    /**
     * Default constructor
     */
    public ChildStructureWithChecks() {
    }

    /**
     * Constructor that automatically attaches children
     *
     * @param children the children to attach
     */
    public ChildStructureWithChecks(Iterable<T> children) {
        super(children);
    }

    /**
     * Adds a check to the list of checks.
     * @param check The check being added
     */
    public void addCheck(Utils.Check<T> check){
        checks.add(check);
    }

    /**
     * Gets all checks.
     * @return All checks
     */
    public List<Utils.Check<T>> getChecks(){
        return checks;
    }

    /**
     * Removes a check.
     * @param check The check being removed
     */
    public void removeCheck(Utils.Check<T> check){
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
     * Attaches a child. <br>
     * Implementation Note: This method should call {@link ChildStructure#onChildAttach(Object)}
     * @param child The child being attached
     */
    @Override
    public void attachChild(T child) {
        for (Utils.Check<T> check : checks)
            if(!check.apply(child, Utils.Action.ATTACH)) return;
        
        super.attachChild(child);
    }

    /**
     * Detaches a child. <br>
     * Implementation Note: This method should call {@link ChildStructure#onChildDetach(Object)}
     *
     * @param child The child being detached
     */
    @Override
    public void detachChild(T child) {
        if(!isChildAttached(child)) return;
        for (Utils.Check<T> check: checks)
            if(!check.apply(child, Utils.Action.DETACH)) return;

        super.detachChild(child);
    }
}
