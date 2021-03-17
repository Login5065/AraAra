package pl.zzpj2021.cleancode;


import java.util.ArrayList;
import java.util.List;


import junit.framework.TestCase;

public class doItTest extends TestCase {

    public void testSetHashMap() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(3);
        list.add(3);
        list.add(3);
        System.out.println("ArrayList : " + list.toString());

        doIt map = new doIt(list);
        System.out.println("MaxValue : " + map.getMaxValue());
        System.out.println("MinValue : " + map.getMinValue());
        System.out.println("Average : " + map.getAverage());
        System.out.println("Default for '2': " + map.getDefault(2));
        System.out.println("Default for '5': " + map.getDefault(5));


    }


}