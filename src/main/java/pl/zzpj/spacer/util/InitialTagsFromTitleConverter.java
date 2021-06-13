package pl.zzpj.spacer.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InitialTagsFromTitleConverter {

    private static final Set<String> REDUNDANT_WORDS = new HashSet<>(Arrays.asList("the", "The", "a", "A", "an", "An",
            "of", "Of", "is", "Is", "are", "Are", "in", "In", "at", "At","on", "On", "and", "And", ":", ",", "."));


    public Set<String> convertTitleToTags(String title) {
        Set<String> splits = Arrays.stream(title.split(" ")).collect(Collectors.toSet());
        splits.removeAll(REDUNDANT_WORDS);
        return splits;
    }


}
