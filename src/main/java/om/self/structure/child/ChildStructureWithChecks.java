package om.self.structure.child;

import om.self.structure.Utils;

import java.util.LinkedList;
import java.util.List;

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

    public void addCheck(Utils.Check<T> check){
        checks.add(check);
    }

    public List<Utils.Check<T>> getChecks(){
        return checks;
    }

    public void removeCheck(Utils.Check<T> check){
        checks.remove(check);
    }

    public void removeCheck(int index){
        checks.remove(index);
    }

    public void clearChecks(){
        checks.clear();
    }

    /**
     * Attaches a child.
     *
     * @param child The child being attached
     * @implNote This method should call {@link ChildStructure#onChildAttach(Object)}
     */
    @Override
    public void attachChild(T child) {
        for (Utils.Check<T> check : checks)
            if(!check.apply(child, Utils.Action.ATTACH)) return;
        
        super.attachChild(child);
    }

    /**
     * Detaches a child.
     *
     * @param child The child being detached
     * @implNote This method should call {@link ChildStructure#onChildDetach(Object)}
     */
    @Override
    public void detachChild(T child) {
        if(!isChildAttached(child)) return;
        for (Utils.Check<T> check: checks)
            if(!check.apply(child, Utils.Action.DETACH)) return;

        super.detachChild(child);
    }
}
