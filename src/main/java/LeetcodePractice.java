import java.util.HashMap;
import java.util.Map;

public class LeetcodePractice {
    public static void main(String[] args) {
        mySqrt(8);
    }

    public static boolean isValid(String s) {
        if (s.length()%2 != 0) return false;
        Map<Character, Integer> br = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
System.out.println("1. for loop " + i);

            int counter = 1;
            char c = s.charAt(i);
            switch (c) {
                case '(':
                case '{':
                case '[':
                    if (br.containsKey(c)) {
                        int n = br.get(c);
                        br.put(c,++n);
                    } else br.put(c,counter);
                    break;

                case ')':
                    if (!br.containsKey('(')) return false;
                    else {
                        int n = br.get('(');
                        br.put('(',--n);
System.out.println("2. " + br.toString());
                    } if (br.get('(') == 0) br.remove('(');
System.out.println("3. " + br.toString());

                case '}':
System.out.println("9. " + br.toString());
                    if (!br.containsKey('{')) return false;
                    else {
                        int n = br.get('{');
                        br.put('{',--n);
                    } if (br.get('{') == 0) br.remove('{');

                case ']':
                    if (!br.containsKey('[')) return false;
                    else {
                        int n = br.get('[');
                        br.put('[',--n);
                    } if (br.get('[') == 0) br.remove('[');
            }
System.out.println("4. yes");
        }
System.out.println("5. lol");
        if (br.isEmpty()) return true;
        else
            return false;
    }

    public static int mySqrt(int x) {
        int res = -1;
        for (double i = 0; i <= x/2; i++) {
            int multRes = (int) Math.round(i*i);
System.out.println(i);
            if (multRes == x) {
                res = (int) Math.round(i);
                break;
            }
        } return res;
    }
}
