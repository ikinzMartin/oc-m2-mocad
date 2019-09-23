import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Neighborhoods {


    public static List<List<Integer>> permutation(List<Integer> solution){
        List<List<Integer>> neighbors = new ArrayList<>();
        for (int i=0; i<solution.size(); i++){
            for (int j=i; j<solution.size(); j++){
                if (i!=j){
                    List<Integer> copy_sol = new ArrayList<>(solution);
                    Collections.swap(copy_sol, i, j);
                    neighbors.add(copy_sol);
                }
            }
        }
        return neighbors;
    }


    public static List<List<Integer>> insertion(List<Integer> solution){
        List<List<Integer>> neighbors = new ArrayList<>();
        for (int i=0; i<solution.size()-1; i++){
            for (int j=i+1; j<solution.size(); j++){
                if(i!=j){
                    List<Integer> copy_sol = new ArrayList<>(solution);
                    List<Integer> n = Stream.concat(
                            copy_sol.subList(0, i).stream(),
                            Stream.concat(
                                    Stream.of(copy_sol.get(j)),
                                    Stream.concat(
                                            copy_sol.subList(i,j).stream(),
                                            copy_sol.subList(j+1,copy_sol.size()).stream())))
                            .collect(Collectors.toList());
                    neighbors.add(n);
                }
            }
        }
        return neighbors;
    }


    public static List<List<Integer>> inversion(List<Integer> solution){
        List<List<Integer>> neighbors = new ArrayList<>();
        for (int i=0; i<solution.size()-1; i++){
            for (int j=i+2; j<=solution.size(); j++){
                List<Integer> copy_sol = new ArrayList<>(solution);
                Collections.reverse(copy_sol.subList(i,j));
                List<Integer> n = Stream.concat(
                        copy_sol.subList(0, i).stream(),
                        Stream.concat(
                                copy_sol.subList(i,j).stream(),
                                copy_sol.subList(j,copy_sol.size()).stream()))
                        .collect(Collectors.toList());
                neighbors.add(n);
            }
        }
        return neighbors;
    }

    public static List<List<Integer>> voisinage(List<Integer> solution, String order){
        List<List<Integer>> result = new ArrayList<>();
        for(String letter : Arrays.asList(order.split(""))){
            switch (letter){
                case "e":
                    result.addAll(permutation(solution));
                    break;
                case "s":
                    result.addAll(inversion(solution));
                    break;
                case "i":
                    result.addAll(insertion(solution));
                    break;
                default:
            }
        }
        return result;
    }


    public static void main(String[] args) {
        List<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(2);
        test.add(3);
        test.add(4);
        test.add(5);
        test.add(6);
        test.add(7);
        test.add(8);
        test.add(9);

        List<List<Integer>> testPermutation = voisinage(test, "es");
        System.out.println(testPermutation.size());
    }

}
