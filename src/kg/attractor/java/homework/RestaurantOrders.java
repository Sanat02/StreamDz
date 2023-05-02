package kg.attractor.java.homework;

import com.google.gson.Gson;

import kg.attractor.java.homework.domain.Item;
import kg.attractor.java.homework.domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.mapping;

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
    //Первое задание можно реализовать с помощью 2х методов
    public void printOrderSum() {
        for (int i = 0; i < orders.size(); i++) {
            System.out.print("Order №" + (i + 1) + ":");
            orders.get(i).calculateTotal();             //1
            System.out.println(orders.get(i).getTotal());
        }
    }
    public void printOrderSum2() {
        var hj=getOrders().stream().map(Order::getTotal).collect(toList());     //2
        hj.forEach(System.out::println);
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
    public void uniqueOrderers() {
        var listOfUniqueOrderers = orders.stream().collect(groupingBy(e -> e.getCustomer().getFullName(),
                mapping(Order::getItems, toList())));
        listOfUniqueOrderers.forEach((k, v) -> System.out.printf("%s - %s%n", k, v));
        System.out.println(listOfUniqueOrderers.size());
    }

    public void totalSumOfUniqueOrderers() {

        var listOfUniqueOrderers = orders.stream().collect(groupingBy(e -> e.getCustomer().getFullName(),
                mapping(Order::getItems, toList())));
        Map<String, Double> mp = new HashMap<>();
        for (Map.Entry<String, List<List<Item>>> entry : listOfUniqueOrderers.entrySet()) {
            var value = entry.getValue().stream().flatMap(Collection::stream).mapToDouble(Item::getPrice).sum();
            mp.put(entry.getKey(), value);
        }
        mp.forEach((k, v) -> System.out.printf("%s - %s%n", k, v));
    }
    public void totalSumOfUniqueOrderersMax() {
        var listOfUniqueOrderers = orders.stream().collect(groupingBy(e -> e.getCustomer().getFullName(),
                mapping(Order::getItems, toList())));
        Map<String, Double> mp = new HashMap<>();
        for (Map.Entry<String, List<List<Item>>> entry : listOfUniqueOrderers.entrySet()) {
            var value = entry.getValue().stream().flatMap(Collection::stream).mapToDouble(Item::getPrice).sum();
            mp.put(entry.getKey(), value);
        }
        double max = mp.values().stream().mapToDouble(e -> e).max().getAsDouble();
        var maxOrder = mp.entrySet().stream().filter(e -> e.getValue() == max).map(Map.Entry::getKey).collect(toList());
        maxOrder.forEach(System.out::println);
        System.out.println("Max:" + max);
    }

    public void totalSumOfUniqueOrdererMin() {
        var listOfUniqueOrderers = orders.stream().collect(groupingBy(e -> e.getCustomer().getFullName(),
                mapping(Order::getItems, toList())));
        Map<String, Double> mp = new HashMap<>();
        for (Map.Entry<String, List<List<Item>>> entry : listOfUniqueOrderers.entrySet()) {
            var value = entry.getValue().stream().flatMap(Collection::stream).mapToDouble(Item::getPrice).sum();
            mp.put(entry.getKey(), value);
        }
        double min = mp.values().stream().mapToDouble(e -> e).min().getAsDouble();
        var minOrder = mp.entrySet().stream().filter(e -> e.getValue() == min).map(Map.Entry::getKey).collect(toList());
        minOrder.forEach(System.out::println);
        System.out.println("Min:" + min);
    }
    public void productQuantity() {
        var counting = orders.stream().flatMap(o -> o.getItems().stream()).collect(groupingBy(Item::getName, counting()));
        counting.forEach((k, v) -> System.out.printf("%s - %s%n", k, v));
        System.out.println();

    }
    public void listEmail() {
        var emailList = orders.stream().flatMap(p -> p.getItems().stream().map(g -> new AbstractMap.SimpleEntry(g.getName(), p.getCustomer().getEmail()))).
                collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
        emailList.forEach((k, v) -> System.out.printf("%s - %s%n", k, v));
        System.out.println();
    }

}
