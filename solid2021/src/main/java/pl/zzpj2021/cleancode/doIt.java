package pl.zzpj2021.cleancode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class doIt {

    Map<Integer, Integer> MapCount = new HashMap<>();
    private int minValue = Integer.MIN_VALUE;
    private int maxValue = Integer.MAX_VALUE;

    public doIt(List<Integer> integerList) {
        set(integerList);
    }

    public doIt() {
    }

    public void set(List<Integer> integerList) {
        for (Integer integer : integerList) {
            set(integer);
        }
    }

    public void set(Integer number) {
        MapCount.merge(number, 1, Integer::sum);

        if (number > minValue) {
            minValue = number;
        }

        if (number < maxValue) {
            maxValue = number;
        }
    }

    public int getDefault(int integer) {
        return MapCount.getOrDefault(integer, 0);
    }

    public double isOk() {
        double sum = 0;
        double count = 0;
        for (Entry<Integer, Integer> entry : MapCount.entrySet()) {
            count += entry.getValue();
            sum += entry.getKey() * entry.getValue();
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

}