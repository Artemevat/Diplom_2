package constants;

import org.apache.commons.lang3.RandomStringUtils;

public class UserRandomDate {
    //случайные значений имя, пароль, почта
    public static String EMAIL = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
    public static String PASSWORD = RandomStringUtils.randomNumeric(8);
    public static String NAME = RandomStringUtils.randomAlphabetic(10);

}
