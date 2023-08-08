package examples;

import om.self.structure.bidirectional.BidirectionalStructure;

/**
 * This example gives a basic use of a bidirectional structure using the BidirectionalStructure class
 */
 public class SimpleBidirectional {
    public static void main(String[] args) {
        Bidirectional first = new Bidirectional("first");
        Bidirectional second = new Bidirectional("second");
        Bidirectional third = new Bidirectional("third");
        Bidirectional fourth = new Bidirectional("fourth");

        //these all run without having to detach anything, but it will create an infinite parent and child loop
        first.attachChild(second);
        second.attachChild(third);
        third.attachChild(fourth);
        fourth.attachChild(first);
        System.out.println();

        //this will break the parent child link between third and fourth and replace it with second
        fourth.attachParent(second);
        System.out.println();

        //This will put all of them in one long link with fourth as the head
        first.attachParent(second);
        second.attachParent(third);
        third.attachParent(fourth);
        fourth.detachParent();

        //The final output will be:
        //first attaching child: second
        //third attaching parent: second
        //second attaching child: third
        //fourth attaching parent: third
        //third attaching child: fourth
        //first attaching parent: fourth
        //fourth attaching child: first
        //
        //third detaching child: fourth
        //fourth detaching parent: third
        //second attaching child: fourth
        //fourth attaching parent: second
        //
        //fourth detaching child: first
        //first detaching parent: fourth
        //second attaching child: first
        //first attaching parent: second
        //first detaching child: second
        //second detaching parent: first
        //third attaching child: second
        //second attaching parent: third
        //second detaching child: third
        //third detaching parent: second
        //fourth attaching child: third
        //third attaching parent: fourth
        //second detaching child: fourth
        //fourth detaching parent: second

    }
}

class Bidirectional extends BidirectionalStructure<Bidirectional, Bidirectional>{
    public final String name;

    public Bidirectional(String name) {
        this.name = name;
    }

    @Override
    public void onChildAttach(Bidirectional child) {
        System.out.println(name + " attaching child: " + child.name);
    }

    @Override
    public void onChildDetach(Bidirectional child) {
        System.out.println(name + " detaching child: " + child.name);
    }


    @Override
    public void onParentAttach(Bidirectional parent) {
        System.out.println(name + " attaching parent: " + parent.name);
    }

    @Override
    public void onParentDetach(Bidirectional parent) {
        System.out.println(name + " detaching parent: " + parent.name);
    }
}
