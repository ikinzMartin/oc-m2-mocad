import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SMTWTP {

    public static List<List<Integer>> parseFile(String path, int instanceSize, int lines) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        /*while (sc.hasNextLine()){
            String line = sc.nextLine();
            parsedLine = Arrays
                    .stream(line.split(" "))
                    .map(Integer::parseInt)
                    .toArray(Integer[] :: new);
            break;
        }*/
        List<List<Integer>> res = new ArrayList<>();
        while (sc.hasNextLine()){
            for (int x=0; x<3; x++){
                List<Integer> instancePart = new ArrayList<Integer>();
                for (int y=0; y<lines; y++){
                    String line = sc.nextLine();
                    String[] parsedLine = line.split(" ");
                    for(int i=0; i<parsedLine.length; i++){
                        System.out.println(parsedLine[i]);
                        instancePart.add(Integer.parseInt(parsedLine[i]));
                    }
                }
                res.add(instancePart);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        try {
            SMTWTP.parseFile("wt100.txt", 100, 5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}