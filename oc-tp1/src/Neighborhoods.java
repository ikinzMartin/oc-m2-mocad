import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Neighborhoods {

    /*private val permu = (cities: Seq[Int]) => {
        var res = new ListBuffer[Seq[Int]]
        for (i <- cities.indices){
            for (j <- i until cities.length){
                if (i!=j){
                    var l = copy_list(cities)
                    var r = l.updated(i,l(j)).updated(j,l(i))
                    res += r
                }
            }
        }
        res
    }*/

    public static List<List<Integer>> permutation(List<Integer> solution){
        List<List<Integer>> neighbors = new ArrayList<>();
        for (int i=0; i<solution.size(); i++){
            for (int j=i; j<solution.size(); j++){
                if (i!=j){
                    List<Integer> copy_sol = new ArrayList<>(solution);
                    Collections.swap(copy_sol, copy_sol.get(i), copy_sol.get(j));
                    neighbors.add(copy_sol);
                }
            }
        }
        return neighbors;
    }

    /*private val insertion = (cities: Seq[Int]) => {
        var res = new ListBuffer[Seq[Int]]
        for (i <- 0 until cities.length - 1){
            for (j <- i+1 until cities.length){
                if (i!=j){
                    var tmp = copy_list(cities)
                    var n = tmp.take(i) ++ Seq(tmp(j)) ++ tmp.slice(i,j) ++ tmp.slice(j+1, tmp.length)
                    //print(tmp.length + " "+ n.length + "\n")
                    res += n
                }
            }
        }
        res
    }*/

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


    /*private val inversion = (cities: Seq[Int]) => {
        var res = new ListBuffer[Seq[Int]]
        for (i <- 0 until cities.length - 1) {
            for (j <- i + 1 until cities.length) {
                var tmp = copy_list(cities)
                var rev = cities.slice(i,j).reverse
                res += tmp.take(i) ++ rev ++ tmp.slice(j, tmp.length)
            }
        }
        res
    }*/

    public static List<List<Integer>> inversion(List<Integer> solution){
        List<List<Integer>> neighbors = new ArrayList<>();
        for (int i=0; i<solution.size(); i++){
            for (int j=i+1; j<solution.size(); j++){
                List<Integer> copy_sol = new ArrayList<>(solution);
            }
        }
        return neighbors;
    }

}
