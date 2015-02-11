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

public enum LinguisticArea {
    ENGLISH,
    FRENCH,
    GERMAN,
    JAPANESE,
    RUSSIAN,
    CHINESE,
    CZECHOSLOVAKIAN,
    INDIAN,
    NORWEGIAN,
    POLISH,
    SPANISH,
    BRAZILIAN,
    SERBIAN,
    DENISH,
    ITALIAN,
    KOREAN,
    NETHERLANDISH,
    SWEDISH,
    NGO_EU,
    OTHERS,
    UNKNOWN;
    
    public static LinguisticArea getValue(final int prefix, final int group) {
        if (prefix == 978) {
            if ((600 <= group && group <= 621) || (950 <= group && group <= 99972)) {
                return OTHERS;
            } else if (group == 0 || group == 1) {
                return ENGLISH;
            } else if (group == 2) {
                return FRENCH;
            } else if (group == 3) {
                return GERMAN;
            } else if (group == 4) {
                return JAPANESE;
            } else if (group == 5) {
                return RUSSIAN;
            } else if (group == 7) {
                return CHINESE;
            } else if (group == 80) {
                return CZECHOSLOVAKIAN;
            } else if (group == 81 || group == 93) {
                return INDIAN;
            } else if (group == 82) {
                return NORWEGIAN;
            } else if (group == 83) {
                return POLISH;
            } else if (group == 84) {
                return SPANISH;
            } else if (group == 85) {
                return BRAZILIAN;
            } else if (group == 86) {
                return SERBIAN;
            } else if (group == 87) {
                return DENISH;
            } else if (group == 88) {
                return ITALIAN;
            } else if (group == 89) {
                return KOREAN;
            } else if (group == 90 || group == 94) {
                return NETHERLANDISH;
            } else if (group == 91) {
                return SWEDISH;
            } else if (group == 92) {
                return NGO_EU;
            } else {
                return UNKNOWN;
            }
        } else if (prefix == 979) {
            if (group == 10) {
                return FRENCH;
            } else if (group == 11) {
                return KOREAN;
            } else {
                return UNKNOWN;
            }
        } else {
            return UNKNOWN;
        }
    }
}
