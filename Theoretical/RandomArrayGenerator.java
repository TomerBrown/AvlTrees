import java.util.*;

public class RandomArrayGenerator {
    private static int [] generateArray (int size){
        ArrayList<Integer> list = new ArrayList<>(size);

        for (int i=0; i<size;i++){
            list.add(i);
        }
        Collections.shuffle(list);
        int [] array = new int [size];
        for (int i =0; i<size; i++){
            array [i] = list.get(i);
        }
        return array;
        }

        public static void main (String [] args){
        int size = 6;
        int [] arr;
        for (int i=0; i<5;i++){
            arr = generateArray(size);
            System.out.println(Arrays.toString(arr));

        }
        }
    }



