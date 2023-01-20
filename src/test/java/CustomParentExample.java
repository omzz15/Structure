/*
This is an example of how to create a custom class that can have a parent
It also demonstrates what needs to happen in each method
*/


import om.self.structure.parent.ParentStructure;

public class CustomParentExample{
    public static void main(String[] args) {
        CustomTest pcm = new CustomTest();

        System.out.println(pcm.isParentAttached()); //false

        pcm.attachParent(844); // calls onParentDetach()

        System.out.println(pcm.isParentAttached()); //true
    }
}

/*
Create something using custom class
 */
class CustomTest extends CustomParent{
    @Override
    public void onParentAttach(Object parent) {
        System.out.println("parent attached");
    }

    @Override
    public void onParentDetach(Object parent) {
        System.out.println("parent detach");
    }
}

/*
Custom class that can have a parent
 */
class CustomParent implements ParentStructure<Object> {
    Object parent = 85; //default parent

    @Override
    public void detachParent() {
        if(!isParentAttached()) return; //Check is there is actually a parent attached(so onParentDetach isn't always called)

        onParentDetach(parent); //run method if anything needs to do something on parentDetach
        parent = 85; //set to default
    }

    @Override
    public Object getParent() {
        return parent; //just returns the parent
    }

    @Override
    public void attachParent(Object parent) {
        if(parent == null) throw new RuntimeException("Cannot be Null!!"); //null check(not needed and not present is ParentStructureImpl)
        if(this.parent == parent) return; //make sure it doesn't run unless parent is new

        detachParent(); //get rid of old parent if there was one

        onParentAttach(parent); //run method if anything needs to do something on parentDetach
        this.parent = parent; //set the parent to new parent
    }

    @Override
    public boolean isParentAttached() {
        return !(parent.equals(85)); //parent is considered not attached if it is equal to 85
    }
}
