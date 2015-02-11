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

import java.util.regex.Pattern;

/**
 * ISBN class.
 */
public class Isbn {
    /** Number of digits in ISBN. */
    public static final int LENGTH = 13;
    /** Number of digits in old ISBN. */
    public static final int OLD_LENGTH = 10;

    /**
     * Check whether the number sequence is valid as ISBN.
     * Check method is: http://en.wikipedia.org/wiki/International_Standard_Book_Number#Check_digits
     * @param numberSequence the number sequence which you want to check. This sequence is allowed to include hyphens
     * @return true if the number sequence is valid as ISBN, otherwise false
     */
    public static boolean isValid(String numberSequence) {
        if (numberSequence == null) throw new NullPointerException();
        if (!Pattern.matches("^\\d+(-?\\d+)*$", numberSequence)) return false;

        String normalizedSequence = removeHyphen(numberSequence);
        if (normalizedSequence.length() == 13) return isValidAsIsbn13(normalizedSequence);
        else if (normalizedSequence.length() == 10) return isValidAsIsbn10(normalizedSequence);
        else return false;
    }

    /**
     * Check whether the 13-digits number is valid as 13-digits ISBN.
     * @param number 13-digits number which you want to check. This must not include hyphens
     * @return true if the 13-digits number is valid as ISBN, otherwise false
     * @throws IllegalArgumentException number is not 13-digits
     */
    static boolean isValidAsIsbn13(String number) {
        if (number == null) throw new NullPointerException();
        if (!Pattern.matches("^\\d{" + LENGTH + "}$", number)) throw new IllegalArgumentException();

        char[] digits = number.toCharArray();
        final int myDigit = computeIsbn13CheckDigit(digits);
        int checkDigit = digits[LENGTH - 1] - '0';
        return myDigit == 10 && checkDigit == 0 || myDigit == checkDigit;
    }

    /**
     * Compute the check digits of 13-digits ISBN.
     * Both full 13-digits and check-digit-less 12-digits are allowed as the argument.
     * @param digits the array of each digit in ISBN.
     * @return check digit
     * @throws IllegalArgumentException the length of the argument array is neither 12 nor 13 or the element of digits is negative
     */
    static int computeIsbn13CheckDigit(char[] digits) {
        if (digits == null) throw new NullPointerException();
        if (digits.length != LENGTH && digits.length != LENGTH - 1) throw new IllegalArgumentException();
        for (char c : digits) {
            if (c < '0' || '9' < c) throw new IllegalArgumentException();
        }

        int[] weights = {1, 3};
        int sum = 0;
        for (int i = 0; i < LENGTH - 1; ++i) {
            sum += (digits[i] - '0') * weights[i % 2];
        }
        return 10 - sum % 10;
    }

    /**
     * Check whether the 10-digits number is valid as 10-digits ISBN.
     * @param number 10-digits number which you want to check. This must not include hyphens
     * @return true if the 10-digits number is valid as ISBN, otherwise false
     * @throws IllegalArgumentException number is not 10-digits
     */
    static boolean isValidAsIsbn10(String number) {
        if (number == null) throw new NullPointerException();
        if (!Pattern.matches("^\\d{" + OLD_LENGTH + "}$", number)) throw new IllegalArgumentException();

        char[] digits = number.toCharArray();
        final int myDigit = computeIsbn10CheckDigit(digits);
        if (myDigit == 10) return digits[9] == 'X';
        final int checkDigit = digits[9] - '0';
        return myDigit == 11 && checkDigit == 0 || myDigit == checkDigit;
    }

    /**
     * Compute the check digits of 10-digits ISBN.
     * Both full 10-digits and check-digit-less 9-digits are allowed as the argument.
     * @param digits the array of each digit in ISBN.
     * @return check digit
     * @throws IllegalArgumentException the length of the argument array is neither 9 nor 10 / the element in digits is negative
     */
    static int computeIsbn10CheckDigit(char[] digits) {
        if (digits == null) throw new NullPointerException();
        if (digits.length != OLD_LENGTH && digits.length != OLD_LENGTH - 1) throw new IllegalArgumentException();
        for (char c : digits) {
            if (c < '0' || '9' < c) throw new IllegalArgumentException();
        }

        int sum = 0;
        for (int i = 0, weight = 10; i < 9; ++i, --weight) {
            sum += (digits[i] - '0') * weight;
        }
        return 11 - sum % 11;
    }

    /**
     * Convert 10-digits ISBN to 13-digits ISBN. Check digit is re-computed.
     * @param isbn10 10-digits ISBN. It can include hyphens
     * @return 13-digits ISBN
     * @throws IllegalArgumentException the number of digits of the argument is not 10
     */
    public static String toIsbn13(String isbn10) {
        if (isbn10 == null) throw new NullPointerException();
        String normalizedNumber = removeHyphen(isbn10);
        if (normalizedNumber.length() != OLD_LENGTH) throw new IllegalArgumentException();

        // Compute check digit
        String isbn13 = "978" + normalizedNumber.substring(0, OLD_LENGTH - 1);
        final int checkDigit = computeIsbn13CheckDigit(isbn13.toCharArray());

        // Compose 13-digits ISBN from 10-digits ISBN
        if (isbn10.contains("-")) {
            return "978-" + isbn10.substring(0, isbn10.length() - 2) + "-" + String.valueOf(checkDigit);
        } else {
            return "978" + isbn10.substring(0, isbn10.length() - 1) + String.valueOf(checkDigit);
        }
    }

    /**
     * Remove hyphens in the argument string.
     * @param s
     * @return string where hyphens are removed
     */
    static String removeHyphen(String s) {
        if (s == null) throw new NullPointerException();
        return s.replace("-", "");
    }

    /**
     * Static factory.
     * @param number ISBN which you want to instantiate.
     * @return ISBN Object
     * @throws IllegalArgumentException if the argument is invalid as ISBN
     */
    public static Isbn of(String number) throws IllegalArgumentException {
        if (number == null) throw new NullPointerException();
        if (!isValid(number)) throw new IllegalArgumentException();

        if (removeHyphen(number).length() == OLD_LENGTH) {
            return new Isbn(toIsbn13(number));
        } else {
            return new Isbn(number);
        }
    }

    private final String originalIsbn;
    private final String normalizedIsbn;    // hyphens are removed

    private final String prefix;
    private final String group;
    private final String publisher;
    private final String bookName;
    private final String checkDigit;

    private Isbn(String originalIsbn) {
        assert null != originalIsbn;

        this.originalIsbn = originalIsbn;
        this.normalizedIsbn = removeHyphen(this.originalIsbn);

        String[] numbers = this.originalIsbn.split("-");
        if (numbers.length == 5) {
            this.prefix     = numbers[0];
            this.group      = numbers[1];
            this.publisher  = numbers[2];
            this.bookName   = numbers[3];
            this.checkDigit = numbers[4];
        } else {
            this.prefix     = "";
            this.group      = "";
            this.publisher  = "";
            this.bookName   = "";
            this.checkDigit = "";
        }
    }

    public String getIsbn() {
        return originalIsbn;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getGroup() {
        return group;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getBookName() {
        return bookName;
    }

    public String getCheckDigit() {
        return checkDigit;
    }
    
    public LinguisticArea getLinguisticArea() {
        if ("".equals(prefix) || "".equals(group)) return LinguisticArea.UNKNOWN;
        else return LinguisticArea.getValue(Integer.parseInt(prefix), Integer.parseInt(group));
    }

    /**
     * @return original description of ISBN. It can include hyphens like 978-4-***-*****-*.
     */
    @Override
    public String toString() {
        return originalIsbn;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Isbn))
            return false;
        Isbn other = (Isbn)obj;
        return other.normalizedIsbn.equals(normalizedIsbn);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + normalizedIsbn.hashCode();
        return result;
    }
}
