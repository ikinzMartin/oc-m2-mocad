import java.io.*;
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

    public static List<Integer> parseSolution(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        List<Integer> result = new ArrayList<>();
        while (sc.hasNext()) {
            result.add(sc.nextInt());
        }
        return result;
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
            time += instance.get(job).get(1);
            res += Integer.max(time - instance.get(job).get(3), 0) * instance.get(job).get(2);
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

    public static List<Integer> localSearch(List<List<Integer>> instance, Boolean firstImprovement, Heuristic initialSolution, Neighborhood neighboor) {
        List<Integer> finalSolution;
        if (initialSolution.equals(Heuristic.MDD)) {
            finalSolution = constructSolutionByModifiedDueDate(instance);
        } else if (initialSolution.equals(Heuristic.EDD)) {
            finalSolution = sortInstanceByDueDate(instance);
        } else {
            finalSolution = randomSolution(instance.size());
        }
        boolean improved = true;
        List<List<Integer>> N_s;
        while (improved) { // stop condition
            if (Neighborhood.insertion.equals(neighboor)) {
                N_s = Neighborhoods.insertion(finalSolution);
            } else if (Neighborhood.permutation.equals(neighboor)) {
                N_s = Neighborhoods.permutation(finalSolution);
            } else {
                N_s = Neighborhoods.inversion(finalSolution);
            }
            improved = false;
            for (List<Integer> solution : N_s) {
                if (score(solution, instance) < score(finalSolution, instance)) {
                    finalSolution = solution;
                    improved = true;
                    if (firstImprovement) {
                        break;
                    }
                }
            }
        }
        return finalSolution;
    }

    public static void main(String[] args) throws IOException {
        List<List<List<Integer>>> res = SMTWTP.parseFile("wt100.txt", 100);
        List<Integer> bestSolution = SMTWTP.parseSolution("wtbest100b.txt");
        List<List<Integer>> formattedInstance;
        long startTime, executionTime;
        int score, instanceId = -1;
        PrintWriter file = new PrintWriter(new FileWriter("res.csv"));
        file.println("instanceId,initialHeuristic,neighborhood,firstImprovementFlag,optimalScoreDeviation,executionTime");
        for (List<List<Integer>> instance: res) {
            instanceId += 1;
            formattedInstance = SMTWTP.formatInstance(instance, 100);
            for (Heuristic heuristic: Heuristic.values()) {
                for (Neighborhood neighborhood: Neighborhood.values()) {
                    for (boolean firstImprovement: new boolean[] {true, false}) {
                        //System.out.println("Running..."+heuristic+","+neighborhood+","+firstImprovement);
                        startTime = System.currentTimeMillis();
                        score = SMTWTP.score(SMTWTP.localSearch(formattedInstance, firstImprovement, heuristic, neighborhood), formattedInstance);
                        executionTime = System.currentTimeMillis() - startTime;
                        file.println(instanceId+","+heuristic+","+neighborhood+","+firstImprovement+","+(score-bestSolution.get(instanceId))+","+executionTime);
                    }
                }
            }
        }
        file.close();
    }

}