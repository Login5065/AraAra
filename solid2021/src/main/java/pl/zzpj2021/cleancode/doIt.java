package pl.zzpj2021.cleancode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//Class counts number of occurences of number in a list and puts them in numberMap
public class doIt {

    Map<Integer, Integer> numberMap = new HashMap<>();
    private int minValue = Integer.MAX_VALUE;
    private int maxValue = Integer.MIN_VALUE;

    public doIt(List<Integer> integerList) {
        setHashMap(integerList);
    }

    public doIt() {
    }

    public void setHashMap(List<Integer> integerList) {
        for (Integer number : integerList) {
            setHashMap(number);
        }
    }

    public void setHashMap(Integer number) {
        numberMap.merge(number, 1, Integer::sum);

        if (number < minValue) {
            minValue = number;
        }

        if (number > maxValue) {
            maxValue = number;
        }
    }

    public int getDefault(int number) {
        return numberMap.getOrDefault(number, 0);
    }

    public double getAverage() {
        double sum = 0;
        double count = 0;
        for (Entry<Integer, Integer> entry : numberMap.entrySet()) {
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