package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService  = new UserServiceImpl();
        // Создание таблицы User(ов)
        userService.createUsersTable();
        // Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        userService.saveUser("Name1", "LastName1", (byte)27);
        userService.saveUser("Name2", "LastName2", (byte) 29);
        userService.saveUser("Name3", "LastName3", (byte) 45);
        userService.saveUser("Name4", "LastName4", (byte) 60);
        // Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        userService.getAllUsers();
        //Очистка таблицы User(ов)
        userService.cleanUsersTable();
        // Удаление таблицы
        userService.dropUsersTable();
    }
}
