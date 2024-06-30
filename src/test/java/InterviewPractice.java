import io.cucumber.java.sl.In;

import java.util.HashMap;
import java.util.Map;

public class InterviewPractice {
    public static void main(String[] args) {
        String str = "hello";
        System.out.println();

//        String result = reverseString(str);
//        System.out.println(result);

        int[] cinemaSeatsArr = {1, 1, 1,1,1,1,1 , 0, 1, 1, 1, 1};
        int emptyCinemaSeat = selectSeatInCinema(cinemaSeatsArr);
        System.out.println(emptyCinemaSeat);
    }

    //method to reverse a string
    //Elena & Baiaman questions:
    //1. what if the string is empty?
    //2. are there any leading or tracing spaces?
    //3. are there  any more than 1 spaces between the words?
    // if the answers to 2 and 3 are no, then the below works.
    public static String reverseString(String str) {
        StringBuilder reverseStr = null;
        if (!str.isEmpty()) {
            String[] arr = str.trim().split(" ");
            System.out.println(arr.length);

            for (int i = arr.length - 1; i >= 0; i--) {
                String word = arr[i];
                if (i == 0) {
                    reverseStr.append(word);
                } else reverseStr.append(word + " ");
            }
        }
         return reverseStr.toString();
    }

//    There is a row in a cinema of length n which is represented as an array.
//    1 (true) means that the place is occupied, 0 (false) means it's free.
//    You need to seat a person as far away as possible from the closest neighbors.
//    Return an index of the array where to sit a person.
//      Constraints: there is always at least one empty and at least one occupied place in the row.

    public static int selectSeatInCinema(int[] arr) {
        int index = -1;
        int max = 0;
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                counter++;
            } else {
                if (counter > max) {
                    max = counter;
                    index = i - 1;
                } counter = 0;
            }
        } return index - max/2;
    }
}
