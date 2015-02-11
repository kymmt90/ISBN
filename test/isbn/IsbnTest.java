/*
* Copyright 2015 Kohei Yamamoto
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package isbn;

import isbn.Isbn;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class IsbnTest {
    @Test
    public void when_of_978_4_8443_3667_9_then_get_isbn_object() throws Exception {
        Isbn instance = Isbn.of("978-4-8443-3667-9");
        assertThat(instance.toString(), is("978-4-8443-3667-9"));
    }
    
    @Test
    public void when_of_9784844336679_then_get_isbn_object() throws Exception {
        Isbn instance = Isbn.of("9784844336679");
        assertThat(instance.toString(), is("9784844336679"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void when_9784844336679_then_throw_IllegalArgumentException() throws Exception {
        Isbn.of("9784844336678");
    }
    
    @Test
    public void when_isValid_9784844336679_then_false() throws Exception {
        assertThat(Isbn.isValid("9784844336678"), is(false));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void when_978_4_8443_3667_8_then_throw_IllegalArgumentException() throws Exception {
        Isbn.of("978-4-8443-3667-8");
    }
    
    @Test
    public void when_isValid_978_4_8443_3667_8_then_false() throws Exception {
        assertThat(Isbn.isValid("978-4-8443-3667-8"), is(false));
    }
    
    @Test
    public void when_isValid_978_4_8443_3667_99_then_false() throws Exception {
        assertThat(Isbn.isValid("978-4-8443-3667-99"), is(false));
    }
    
    @Test
    public void when_isValid_978_4_8443_3667_then_false() throws Exception {
        assertThat(Isbn.isValid("978-4-8443-3667"), is(false));
    }
    
    @Test
    public void when_isValid_empty_string_then_false() throws Exception {
        assertThat(Isbn.isValid(""), is(false));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void when_of_invalid_number_then_throw_IllegalArgumentException() throws Exception {
        Isbn.of("978-4-8443-3667-8");
    }
    
    @Test(expected = NullPointerException.class)
    public void when_of_null_then_throw_NullPointerException() throws Exception {
        Isbn.of(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void when_of_alphabet_then_throw_IllegalArgumentException() throws Exception {
        Isbn.of("978-4-8443-3667-a");
    }
    
    @Test
    public void equals_test() throws Exception {
        Isbn sut1 = Isbn.of("978-4-8443-3667-9");
        Isbn sut2 = Isbn.of("9784844336679");
        assertThat(sut1.equals(sut2), is(true));
    }
    
    @Test
    public void equals_identical_objects_test() throws Exception {
        Isbn sut1 = Isbn.of("978-4-8443-3667-9");
        Isbn sut2 = Isbn.of("978-4-8443-3667-9");
        assertThat(sut1.equals(sut2), is(true));
    }
    
    @Test
    public void equals_not_identical_objects_test() throws Exception {
        Isbn sut1 = Isbn.of("978-4-8443-3667-9");
        Isbn sut2 = Isbn.of("978-4-7741-5377-3");
        assertThat(sut1.equals(sut2), is(false));
    }
    
    @Test
    public void equals_not_identical_objects_test2() throws Exception {
        Isbn sut1 = Isbn.of("9784844336679");
        Isbn sut2 = Isbn.of("9784774153773");
        assertThat(sut1.equals(sut2), is(false));
    }
    
    @Test
    public void when_isValidAsIsbn10_4_10_109205_2_is_true() throws Exception {
        assertThat(Isbn.isValidAsIsbn10("4101092052"), is(true));
    }
    
    @Test
    public void when_isValidAsIsbn10_4_10_109205_3_is_false() throws Exception {
        assertThat(Isbn.isValidAsIsbn10("4101092053"), is(false));
    }
    
    @Test
    public void when_toIsbn13_4_10_109205_3() throws Exception {
        assertThat(Isbn.toIsbn13("4-10-109205-2"), is("978-4-10-109205-8"));
    }
    
    @Test
    public void when_toIsbn13_4101092053() throws Exception {
        assertThat(Isbn.toIsbn13("4101092052"), is("9784101092058"));
    }
    
    @Test
    public void when_of_4_10_109205_2() throws Exception {
        Isbn sut = Isbn.of("4-10-109205-2");
        assertThat(sut.getIsbn(), is("978-4-10-109205-8"));
    }
    
    @Test
    public void when_of_4101092052() throws Exception {
        Isbn sut = Isbn.of("4101092052");
        assertThat(sut.getIsbn(), is("9784101092058"));
    }
    
    @Test
    public void when_of_978_4_06_277829_9_linguisticare_is_japanese() throws Exception {
        Isbn sut = Isbn.of("978-4-06-277829-9");
        assertThat(sut.getLinguisticArea(), is(LinguisticArea.JAPANESE));
    }
}
