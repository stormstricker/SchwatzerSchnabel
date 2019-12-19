package schwatzerschnabel.utils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TextWizard {
    private static Map<String, List<String>> englishPoses = new LinkedHashMap<>();
    private static Map<String, List<String>> germanPoses = new LinkedHashMap<>();

    static {
        englishPoses.put("noun", Arrays.asList("NN", "NNS", "NNP", "NNPS"));
        englishPoses.put("verb", Arrays.asList("VB", "VBD", "VBG", "VBN", "VBP", "VBZ"));
        englishPoses.put("adjective", Arrays.asList("JJ", "JJR", "JJS"));
        englishPoses.put("adverb", Arrays.asList("RB", "RBR", "RBS"));
        englishPoses.put("pronoun", Arrays.asList("PRP", "PRP$", "WP", "WP$"));
        englishPoses.put("cardinal number", Arrays.asList("CD"));
        englishPoses.put("conjunction", Arrays.asList("CC"));
        englishPoses.put("preposition", Arrays.asList("IN"));
        englishPoses.put("particle", Arrays.asList("RP"));
        //TODO: add articles

        germanPoses.put("noun", Arrays.asList("NA", "NE", "NN"));
        germanPoses.put("verb", Arrays.asList("VAFIN", "VMFIN", "VVFIN", "VVIMP", "VVINF", "VVPP"));
        germanPoses.put("adjective", Arrays.asList("ADJA", "ADJD"));
        germanPoses.put("adverb", Arrays.asList("ADV", "PAV", "PROAV", "PAVREL", "PWAV", "PWAVRE"));
        germanPoses.put("pronoun", Arrays.asList("PDS", "PIS", "PPER", "PRF", "PPOSS", "PRELS", "PWS", "PWREL"));
        germanPoses.put("cardinal number", Arrays.asList());
        germanPoses.put("conjunction", Arrays.asList("KON", "KOKOM", "KOUS"));
        germanPoses.put("preposition", Arrays.asList("APPR", "APPRART", "APZR", "KOUI"));
        germanPoses.put("particle", Arrays.asList("PTKA", "PTKANT", "PTKNEG", "PTKREL", "PTKZU"));
        germanPoses.put("determiner", Arrays.asList("PIAT", "ART"));
    }

    public static Map<String, List<String>> getEnglishPoses() {
        return englishPoses;
    }

    public static Map<String, List<String>> getGermanPoses() {
        return germanPoses;
    }


}
