package schwatzerschnabel.utils;

public class Utils {
    public static void main(String[] args) {
        String s = ",,hello, how are you,,,,,,,,";
        System.out.println(trimCommas(s));
    }

    public static int generateRandom(int min,int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static String trimCommas(String s)  {
        int startIndex = -1;
        int endIndex = s.length()-1;
        for (int i=0; i<s.length(); i++)  {
            String symbol = s.substring(i, i+1);

            if (startIndex==-1 && !symbol.equalsIgnoreCase(","))  {
                startIndex = i;
            }

            if (!symbol.equalsIgnoreCase(","))  {
                endIndex = i;
            }
        }

        return s.substring(startIndex, endIndex+1);
    }
}

