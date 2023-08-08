/*
This is an example of how to create a simple class that can have a parent
It also demonstrates automatic parent detaching and how to use the onParent_____() methods
*/


import om.self.structure.parent.ParentStructureImpl;

/**
 * Simple class that can have a parent
 */
public class ParentExample extends ParentStructureImpl<Object> {
    public static void main(String[] args) {
        //create an object that can have a parent
        ParentExample p = new ParentExample();

        //add a parent
        p.attachParent("parent 1");
        //It will output:
        //The parent: parent 1 was attached

        //notice how the onParentAttach method gets called

        //add another parent
        p.attachParent("parent 2");
        //It will output:
        //The parent: parent 1 was detached
        //The parent: parent 2 was attached

        //notice how the first parent is detached before the second one is attached

        //get current parent
        System.out.println("Current Parent: " + p.getParent());
        //It will output:
        //Current Parent: parent 2

        //check if there is a parent
        System.out.println(p.isParentAttached());
        //It will output:
        //true
    }

    @Override
    public void onParentAttach(Object parent) {
        System.out.println("The parent: " + parent + " was attached");
    }

    @Override
    public void onParentDetach(Object parent) {
        System.out.println("The parent: " + parent + " was detached");
    }
}


