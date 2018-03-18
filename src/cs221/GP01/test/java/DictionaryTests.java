package cs221.GP01.test.java;

import cs221.GP01.main.java.model.Dictionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTests {

    Dictionary dic = new Dictionary();

    @BeforeEach
    public  void setUp(){
        dic.loadDictionary("test_dictionary");
    }


    @Test
    public void testDictionaryLoad(){
        assertEquals(7, dic.getDictionarySize());

    }

    @Test
    public void testSearchDictionary(){
        assertTrue(dic.searchDictionary("apron"));
        assertFalse(dic.searchDictionary("wordThatIsNotInDictionary"));

    }

}