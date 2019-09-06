import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;

public class SMTWTP {

    /**
     * Builds a list containing the set of instances of the provided file
     * @param path of the instance file
     * @param instanceSize size of an instance
     * @return the list of instance which is a list of three lists
     * @throws FileNotFoundException
     */
    public static List<List<List<Integer>>> parseFile(String path, int instanceSize) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        List<List<List<Integer>>> res = new ArrayList<>();
        while (sc.hasNext()){
            List<List<Integer>> instance = new ArrayList<>();
            for (int i=0; i<3; i++){
                List<Integer> inf = new ArrayList<>();
                for (int j=0; j<instanceSize; j++){
                    inf.add(sc.nextInt());
                }
                instance.add(inf);
            }
            res.add(instance);
        }
        return res;
    }

    public static List<List<Integer>> formatInstance(List<List<Integer>> rawInstance, int instanceSize){
        List<List<Integer>> processedInstance = new LinkedList<>();
        for (int i=0; i<instanceSize; i++){
            List<Integer> triplet = new LinkedList<>();
            triplet.add(rawInstance.get(0).get(i));
            triplet.add(rawInstance.get(1).get(i));
            triplet.add(rawInstance.get(2).get(i));
            processedInstance.add(triplet);
        }
        return processedInstance;
    }

    public static List<Integer> randomSolution(int range){
        List<Integer> l = new LinkedList<>();
        for (int i=0; i<range; i++){
            l.add(i);
        }
        Collections.shuffle(l);
        return l;
    }

    /*public static List<List<Integer>> sortInstancePerDueDate(List<List<Integer>> instance){
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> dueDates = instance.get(2);
        dueDates.stream().
        return res;
    }*/

    public static void main(String[] args) throws FileNotFoundException {
        List<List<List<Integer>>> res = SMTWTP.parseFile("wt100.txt", 100);
        System.out.println(res.size());
        System.out.println(res.get(1).size());
        System.out.println(res.get(1).get(1).size());

        System.out.println(SMTWTP.randomSolution(100).size());
        System.out.println(SMTWTP.formatInstance(res.get(1), 100));
    }

}