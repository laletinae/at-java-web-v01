package work.part05;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Задание на 21.12.25
 * =============================================================================
 * Создать класс Registry.
 * В классе Registry создать:
 * поле Users - коллекция объектов класса User
 * конструктор
 * метод addUser для добавления объекта в коллекцию Users, при добавлении требуется убедиться, то в коллекции ещё нет объекта, у которого поле Username совпадает с добавляемым
 * метод deleteUser для удаления объекта из коллекции Users, поиск объекта для удаления по полю Username
 * метод getUser, который принимает один строковый аргумент - имя пользователя, а возвращает null, если пользователя с таким именем нет в коллекции. Иначе - объект класса User из коллекции Users.
 * метод checkAuth, который принимает два строковых аргумента: имя пользователя и пароль.
 * Если с этими аргументами хотя бы для одного объекта в коллекции Users метод checkAuth (относящийся к классу User) вернёт "OK" - вернуть "OK", если хотя бы для одного объекта в коллекции Users метод checkAuth (относящийся к классу User) вернёт "Blocked" - вернуть "Blocked", иначе - вернуть "FailedAuth".
 * Java-5 (***): С использованием библиотеки JUnit написать unit-тесты для модульного тестирования класса Registry.
 */
public class Registry {
    Set<User> users;

    public Registry () {
        users = new HashSet<>();
    }

    /**
     * метод addUser для добавления объекта в коллекцию Users, при добавлении требуется убедиться,
     * то в коллекции ещё нет объекта, у которого поле Username совпадает с добавляемым
     * @param username - имя пользователя
     * @param password - пароль
     * @return true/false - добавили или нет
     */
    public boolean addUser (String username, String password) {

        boolean usernameExists = false;

        for (User user : users) {
            String name = user.getUsername();
            if (Objects.equals(name, username)) {
                System.out.println("Элемент " + username + " уже есть");
                usernameExists = true;
            }
        }

        if (usernameExists)
            return false;
        else {
            System.out.println("Элемент " + username + " добавлен");
            User user = new User(username, password, false);
            users.add(user);
            return true;
        }
    }

    /**
     * метод deleteUser для удаления объекта из коллекции Users, поиск объекта для удаления по полю Username
     * @param username - имя пользователя
     * @return true/false - удалили или нет
     */
    public boolean deleteUser (String username) {
        for (User user : users) {
            String name = user.getUsername();
            if (Objects.equals(name, username)) {
                users.remove(user);
                System.out.println("Элемент " + username + " удалён");
                return true;
            }
        }
        System.out.println("Элемент " + username + " отсутствует, не удалён");
        return false;
    }

    /**
     * метод getUser, который принимает один строковый аргумент - имя пользователя, а возвращает null,
     * если пользователя с таким именем нет в коллекции. Иначе - объект класса User из коллекции Users.
     * @param username - имя пользователя
     * @return User или null, если не найден
     */
    public User getUser (String username) {
        for (User user : users) {
            String name = user.getUsername();
            if (Objects.equals(name, username)) {
                return user;
            }
        }
        System.out.println("Элемент " + username + " не найден");
        return null;
    }

    /**
     * метод checkAuth, который принимает два строковых аргумента: имя пользователя и пароль.
     * Если с этими аргументами хотя бы для одного объекта в коллекции Users метод checkAuth
     * (относящийся к классу User) вернёт "OK" - вернуть "OK", если хотя бы для одного объекта
     * в коллекции Users метод checkAuth (относящийся к классу User) вернёт "Blocked" -
     * вернуть "Blocked", иначе - вернуть "FailedAuth".
     * @param username - имя пользователя
     * @param password - пароль
     * @return String OK/Blocked/FailedAuth
     */
    public String checkAuth (String username, String password) {

        /* Пример фильтрации по полю класса (работает)
        long usersOkAuth = users.stream()
                .filter(p -> p.getUsername().equals(username))
                .filter(p -> p.getPassword().equals(password))
                .count();*/

        long usersOkAuth = users.stream()
                .filter(p -> p.checkAuth(username, password).equals("OK"))
                .count();
        System.out.println("Найдено ОК пользователей " + username + ": " + usersOkAuth);

        long usersBlocked = users.stream()
                .filter(p -> p.checkAuth(username, password).equals("Blocked"))
                .count();
        System.out.println("Найдено Blocked пользователей " + username + ": " + usersBlocked);

        long usersFailedAuth = users.stream()
                .filter(p -> p.checkAuth(username, password).equals("FailedAuth"))
                .count();
        System.out.println("Найдено FailedAuth пользователей " + username + ": " + usersFailedAuth);

        if (usersOkAuth >= 1) return "OK";
        if (usersBlocked >= 1) return "Blocked";
        return "FailedAuth";
    }

    /**
     * Проверка выполнения домашнего задания по созданию класса Registry
     * @param args
     */
    public static void main(String[] args) {

        Registry reg = new Registry();

        //Создаём пользователей (User3 не добавится, т.к. уже есть)
        reg.addUser("User1", "Pa$$word");
        reg.addUser("User2", "Pa$$word");
        reg.addUser("User3", "Pa$$word");
        reg.addUser("User3", "Pa$$word");
        reg.addUser("User3", "Pa$$word");


        //Удаляем пользователей (User0 не удалится, т.к.не существует)
        reg.deleteUser("User2");
        reg.deleteUser("User0");


        //Берём пользователя (User33 не существует, вернёт null)
        reg.getUser("User3");
        reg.getUser("User33");

        //Проверка авторизации User1 - OK, User11111 - FailedAuth
        System.out.println(reg.checkAuth("User1", "Pa$$word"));
        System.out.println(reg.checkAuth("User11111", "Pa$$word"));

    }
}
