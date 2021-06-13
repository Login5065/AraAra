package pl.zzpj.spacer.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InitialTagsFromTitleConverterTest {


    private final InitialTagsFromTitleConverter initialTagsFromTitleConverter = new InitialTagsFromTitleConverter();

    @Test
    void convertTitleToTagsShouldCutProperTags() {
        //given
        String title = "Eclipse on the Water";
        //when
        Set<String> set = initialTagsFromTitleConverter.convertTitleToTags(title);
        //then
        assertTrue(set.contains("Eclipse"));
        assertTrue(set.contains("Water"));
        assertFalse(set.contains("on"));
        assertFalse(set.contains("the"));
    }
}