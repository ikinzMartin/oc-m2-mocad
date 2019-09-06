import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
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
            triplet.add(i);
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

    public static Integer score(List<Integer> solution, List<List<Integer>> instance) {
        int time = 0, res = 0;
        for (Integer job : solution) {
            time += instance.get(0).get(job);
            res += Integer.max(time - instance.get(2).get(job), 0) * instance.get(1).get(job);
        }
        return res;
    }

    public static List<Integer> sortInstanceByDueDate(List<List<Integer>> instance) {
        Collections.sort(instance, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                return o1.get(3) > o2.get(3) ? -1 : (o1.get(3) < o2.get(3)) ? 1 : 0;
            }
        });
        return instance.stream().map(job -> job.get(0)).collect(Collectors.toCollection(LinkedList::new));
    }

    public static int mdd(int C, int p, int d) {
        return Integer.max(C+p,d);
    }

    public static List<Integer> constructSolutionByModifiedDueDate(List<List<Integer>> instance) {
        List<Integer> solution = new LinkedList<Integer>();
        List<List<Integer>> unsortedElements = new LinkedList<>();
        for (List<Integer> lst: instance) {
            unsortedElements.add(new ArrayList<Integer>(lst));
        }
        int bestMDDScore = -1, MDDScore;
        List<Integer> bestTask = null;
        int ellapsedTime = 0;
        while (!unsortedElements.isEmpty()) {
            bestTask = unsortedElements.get(0);
            bestMDDScore = SMTWTP.mdd(ellapsedTime, unsortedElements.get(0).get(1), unsortedElements.get(0).get(3));
            for (List<Integer> task: unsortedElements) {
                MDDScore = SMTWTP.mdd(ellapsedTime, task.get(1), task.get(3));
                if (MDDScore < bestMDDScore) {
                    bestTask = task;
                    bestMDDScore = MDDScore;
                }
            }
            solution.add(bestTask.get(0));
            unsortedElements.remove(bestTask);
            ellapsedTime += bestTask.get(1);
        }
        return solution;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<List<List<Integer>>> res = SMTWTP.parseFile("wt100.txt", 100);
        List<List<Integer>> formattedInstance;
        for (List<List<Integer>> instance: res) {
            formattedInstance = SMTWTP.formatInstance(instance, 100);
            System.out.println(SMTWTP.score(SMTWTP.constructSolutionByModifiedDueDate(formattedInstance),instance));
        }
    }

}