package lab5;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HeapTest {

    public static void main(String[] args) {
        testPeak();
        testPoll();
        testSize();
    }

    public static void testPeak() {
        output("Test Peak");
        Heap<Integer> integerHeap = new Heap<>();
        integerHeap.addAll(Arrays.asList(30, 10, 20));
//        integerHeap.printHeap();
        if(Integer.valueOf(10).equals(integerHeap.peek())) {
            output("Success");
        } else {
            output("Fail");
        }
        
    }

    public static void testPoll() {
        output("Test Poll");
        Heap<Integer> integerHeap = new Heap<>();
        List<Integer> values = Arrays.asList(2, 53, 213, 5, 1, 5, 4, 210, 14, 26, 44, 35, 31, 33, 19, 52, 27);
        integerHeap.addAll(values);

        Collections.sort(values);
//        System.out.println("List: "+ values);
//        integerHeap.printHeap();
        for (int x : values) {
            int pool = integerHeap.poll();
//            System.out.print("Heap: ");
//            integerHeap.printHeap();
//            System.out.println("Pooled integer: " + pool + " Value: " + x);
            if(!Integer.valueOf(x).equals(pool)) {
                output("Fail");
                return;
            }
        }
        output("Success");
    }

    public static void testSize() {
        output("Test Size");
        Heap<Integer> integerHeap = new Heap<>();
        List<Integer> values = Arrays.asList(10, 14, 26, 44, 35, 31, 33, 19, 52, 27);
        integerHeap.addAll(values);
        if(values.size() == integerHeap.size()) {
            output("Success");
        } else {
            output("Fail");
        }
    }

    public static void output(String s) {
        System.out.println(s);
    }





}