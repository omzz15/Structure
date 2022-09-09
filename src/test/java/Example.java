import om.self.structure.base.BidirectionalStructure;

public class Example {
    public static void main(String[] args) {
        Test t1 = new Test();
        Test t2 = new Test();
        Test t3 = new Test();

        System.out.println("1 to 2");
        t2.attachChild(t1);
        System.out.println("2 to 3");
        t2.attachParent(t3);
        t2.detachChild(t1);
        t2.detachParent();
    }
}

class Test extends BidirectionalStructure<Test, Test>{
    @Override
    public void onChildAttach(Test child) {
        System.out.println(child + " attached");
    }

    @Override
    public void onChildDetach(Test child) {
        System.out.println(child + " detached");
    }

    @Override
    public void onParentAttach(Test parent) {
        System.out.println(parent + " attached");
    }

    @Override
    public void onParentDetach(Test parent) {
        System.out.println(parent + " detached");
    }
}
