package kg.attractor.java.homework;

import com.google.gson.Gson;

import kg.attractor.java.homework.domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class RestaurantOrders {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private List<Order> orders;

    public RestaurantOrders(String fileName) {
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            orders = List.of(gson.fromJson(Files.readString(filePath), Order[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RestaurantOrders read(String fileName) {
        var ro = new RestaurantOrders(fileName);
        ro.getOrders().forEach(Order::calculateTotal);
        return ro;
    }

    public List<Order> getOrders() {
        return orders;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    // Наполните этот класс решением домашнего задания.
    // Вам необходимо создать все необходимые методы
    // для решения заданий из домашки :)
    // вы можете добавлять все необходимые imports
    //
    public void printOrderSum() {
        for (int i = 0; i < orders.size(); i++) {
            System.out.print("Order №" + (i + 1) + ":");
            orders.get(i).calculateTotal();
            System.out.println(orders.get(i).getTotal());
        }
    }

    public void printList() {
        orders.forEach(System.out::println);
    }

    public void printMaxOrder() {
        var max = orders.stream().mapToDouble(Order::getTotal).max().getAsDouble();
        var list = orders.stream().max(Comparator.comparing(Order::getTotal)).get();
        System.out.printf("Min order:%s %s%n", max, list);
    }
    public void printMinOrder() {
        var min = orders.stream().mapToDouble(Order::getTotal).min().getAsDouble();
        var list = orders.stream().min(Comparator.comparing(Order::getTotal)).
                get();
        System.out.printf("Min order:%s %s%n", min, list);
    }
    public void printHomeMade() {
        var homeDeliveryList = orders.stream().filter(Order::isHomeDelivery).collect(Collectors.toList());
        homeDeliveryList.forEach(System.out::println);
    }
    public void printProfitHome() {
        var homeDeliveryMax = orders.stream().filter(Order::isHomeDelivery).max(Comparator.comparing(Order::getTotal)).
                get();
        var homeDeliveryMin = orders.stream().filter(Order::isHomeDelivery).min(Comparator.comparing(Order::getTotal)).
                get();
        System.out.printf("Наибольший прибыльный заказ на дом:%s%nНаименьший заказ на дом:%s", homeDeliveryMax, homeDeliveryMin);
    }
    public void betweenMinAndMax() {
        var max = orders.stream().mapToDouble(Order::getTotal).max().getAsDouble();
        var min = orders.stream().mapToDouble(Order::getTotal).min().getAsDouble();
        var listBetween = orders.stream().sorted(Comparator.comparingDouble(Order::getTotal)).
                dropWhile(e -> e.getTotal() <= min).takeWhile(e -> e.getTotal() < max).collect(Collectors.toList());
        listBetween.forEach(System.out::println);
        System.out.println(listBetween.size());
    }
    public void printAllSum() {
        var sum = orders.stream().mapToDouble(Order::getTotal).sum();
        System.out.println("Общая сумма всех заказов:" + sum);
    }
    public void printDistinctEmail() {
        var emailList = orders.stream().map(e -> e.getCustomer().getEmail()).collect(toCollection(TreeSet::new));
        emailList.forEach(System.out::println);
    }
}
