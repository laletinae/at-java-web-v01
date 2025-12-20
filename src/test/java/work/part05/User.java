package work.part05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

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
public class User {
    private String username;
    private String password;
    private Boolean isBlocked;

    public User() {

    }

    /**
     *
     * @param username - имя пользователя
     * @param password - пароль
     * @param isBlocked - заблокирован или нет
     */
    public User (String username, String password, Boolean isBlocked) {
        this.username = username;
        this.password = password;
        this.isBlocked = isBlocked;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    /**
     * метод checkAuth, который принимает два строковых аргумента: имя пользователя и пароль.
     * Если аргументы не совпадают с соответствующими полями объекта, то метод возвращает "FailedAuth",
     * иначе: если пользователь блокирован, то метод возвращает "Blocked", иначе: метод возвращает "OK".
     * @param username - имя пользователя
     * @param password - пароль
     * @return String OK|Blocked|FailedAuth
     */
    public String checkAuth (String username, String password) {
        System.out.println("Сравниваем логин: " + this.username + " " + username
                + "; Сравниваем пароль: " + this.password + " " + password
                + "; Блокировка " + this.isBlocked);

        if (isBlocked == true)
            return "Blocked";
        if (Objects.equals(this.username, username) && Objects.equals(this.password, password)) {
            return "OK";
        } else return "FailedAuth";
    }

    /**
     * Проверка выполнения домашнего задания по созданию класса Registry
     * @param args
     */
    public static void main(String[] args) {
        //Создать пользователей (способ 1)
        User evgenia = new User("Evgenia", "Pa$$word", false);
        User elena = new User("Elena", "Pa$$word", false);
        User ksenia = new User("Ksenia", "Pa$$word", true);

        //Создать пользователя (способ 2)
        User valeria = new User();
        valeria.setUsername("Valeria");
        valeria.setPassword("TruthPa$$");
        valeria.setBlocked(false);

        //Проверить авторизацию созданных пользователей
        System.out.println("Evgenia " + evgenia.checkAuth("Evgenia", "WrongPass"));
        System.out.println("Elena " + elena.checkAuth("Elena", "Pa$$word"));
        System.out.println("Ksenia " + ksenia.checkAuth("Ksenia", "Pa$$word"));
        System.out.println("Valeria " + valeria.checkAuth("Valeria34", "Pa$$word"));
    }

}
