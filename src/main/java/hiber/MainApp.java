package hiber;

import hiber.config.AppConfig;
import hiber.dao.UserDaoImp;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);
      Car car = new Car("BMW",13);
      UserService userService = context.getBean(UserService.class);
      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      //добавление юзера с машиной двумя способами
      User newUser = new User("Bob", "Bobovich","bob@mail.ru");
      newUser.setCar(car);

      userService.add(newUser);
      userService.add(new User("Bob1", "Bobovich1","bob1@mail.ru", new Car("Audi",8)));

      List<User> users = userService.listUsers();
      for (User user : users) {
         Car gotCar = user.getCar();
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         if(gotCar != null){// Вывод юзеров с машина вместе с остальными.
            System.out.println("Car = "+ gotCar.getModel()+ " "+ gotCar.getSeries());
         }
         System.out.println();
      }
      //Метод достающий владельца. Возвращает Лист т.к. в БД могут быть одинаковые машины.
      List<User> userByCar = userService.getUserByCar("BMW",13);
      for (User user : userByCar) {
         Car usersCar = user.getCar();//Показать машину - для наглядности
         System.out.println("Car = "+ usersCar.getModel()+ " "+ usersCar.getSeries());
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = "+user.getEmail());
      }

      context.close();
   }
}
