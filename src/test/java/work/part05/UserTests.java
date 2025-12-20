package work.part05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Задание на 21.12.25
 * =============================================================================
 * Создать класс User.
 * В классе User создать:
 * приватные поля Username, Password, IsBlocked
 * конструктор
 * методы для присвоения значений каждому из полей
 * методы для получения значений каждого из полей
 * метод checkAuth, который принимает два строковых аргумента: имя пользователя и пароль. Если аргументы не совпадают с соответствующими полями объекта, то метод возвращает "FailedAuth", иначе: если пользователь блокирован, то метод возвращает "Blocked", иначе: метод возвращает "OK".
 * Написать программу, которая создаст два объекта класса User, у каждого вызовет метод checkAuth так, чтобы у первого он вернул "OK", а у второго любую ошибку.
 * Java-3 (*):  С использованием библиотеки JUnit написать unit-тесты для unit-тестирования класса User
 */

public class UserTests {
    User user;


    /**
     * Тест для метода checkAuth при успешной авторизации
     */
    @Test
    void test01checkAuthOk() {
        user = new User("Evgenia", "Pa$$word", false);
        String result = user.checkAuth("Evgenia", "Pa$$word");
        // Проверка (Assert) - ожидаем, что результат будет ОК
        assertEquals("OK", result, "Авторизация ОК");
    }

    /**
     * Тест для метода checkAuth при заблокированном пользователе
     */
    @Test
    void test01checkAuthBlocked() {
        user = new User("Evgenia", "Pa$$word", true);
        String result = user.checkAuth("Evgenia", "Pa$$word");
        // Проверка (Assert) - ожидаем, что результат будет Blocked
        assertEquals("Blocked", result, "Авторизация ОК");
    }

    /**
     * Тест для метода checkAuth при неверном логине или пароле
     */
    @Test
    void test01checkAuthFailedAuth() {
        user = new User("Evgenia", "Pa$$word", false);
        String result = user.checkAuth("Evgenia", "");
        // Проверка (Assert) - ожидаем, что результат будет Blocked
        assertEquals("FailedAuth", result, "Авторизация ОК");
    }
}
