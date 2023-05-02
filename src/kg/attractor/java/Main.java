package kg.attractor.java;

import kg.attractor.java.homework.RestaurantOrders;
import kg.attractor.java.lesson.MovieCollection;

public class Main {

    public static void main(String[] args) {

        // это для занятия
        //   var movieCollection = MovieCollection.readFromJson();

        // это для домашки
        // выберите любое количество заказов, какое вам нравится.

       // var orders = RestaurantOrders.read("orders_100.json").getOrders();
        //var orders = RestaurantOrders.read("orders_1000.json").getOrders();
        //var orders = RestaurantOrders.read("orders_10_000.json").getOrders();
       // var totalOrder = RestaurantOrders.read("orders_100.json").printOrderSum();
        RestaurantOrders ro=new RestaurantOrders("orders_100.json");
        ro.printOrderSum();
        System.out.println();
        ro.printList();
        System.out.println();
        ro.printMaxOrder();
        System.out.println();
        ro.printMinOrder();
        System.out.println();
        ro.printHomeMade();
        System.out.println();
        ro.printProfitHome();
        System.out.println();
        ro.betweenMinAndMax();
        System.out.println();
        ro.printAllSum();
        System.out.println();
        ro.printDistinctEmail();
       



        // протестировать ваши методы вы можете как раз в этом файле (или в любом другом, в котором вам будет удобно)
    }
}
