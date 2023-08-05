package om.self.structure.parent;

import om.self.structure.Utils;
import om.self.structure.bidirectional.KeyedBidirectionalStructure;

import java.util.LinkedList;
import java.util.List;

/**
 * An extension of {@link ParentStructureImpl} that adds the ability to run checks before attaching or detaching anything
 * @param <T> the type of the parent
 */
public class ParentStructureWithChecks<T> extends ParentStructureImpl<T>{
    private final List<Utils.Check<T>> checks = new LinkedList<>();

    /**
     * Default constructor
     */
    public ParentStructureWithChecks() {
        super();
    }

    /**
     * Constructor that automatically attaches a parent
     *
     * @param parent the parent being attached
     */
    public ParentStructureWithChecks(T parent) {
        super(parent);
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
     * Detaches the current parent if one is attached then attaches a parent.
     *
     * @param parent the parent being attached
     */
    @Override
    public void attachParent(T parent) {
        for(Utils.Check<T> check : checks)
            if(!check.apply(parent, Utils.Action.ATTACH)) return;
        super.attachParent(parent);
    }

    /**
     * Detached the parent if one is attached.
     */
    @Override
    public void detachParent() {
        if(!isParentAttached()) return;

        T p = getParent();
        for(Utils.Check<T> check : checks)
            if(!check.apply(p, Utils.Action.DETACH)) return;
        super.detachParent();
    }
}
