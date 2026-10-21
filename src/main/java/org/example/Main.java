package org.example;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//TIP Для <b>запуска</b> кода нажмите <shortcut actionId="Run"/> или
// щелкните значок <icon src="AllIcons.Actions.Execute"/> в боковой области.
public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
            for(int i = 0; i < 1_000_000; i++){
                persons.add(new Person(
                        names.get(new Random().nextInt(names.size())),
                        families.get(new Random().nextInt(families.size())),
                        new Random().nextInt(100),
                        SEX.values()[new Random().nextInt(SEX.values().length)],
                        Education.values()[new Random().nextInt(Education.values().length)]
                ));
            }
        Supplier<Collection<Person>> supplier = () -> persons; //TODO: <>

        // 1. Количество несовершеннолетних людей
        int yang = (int)supplier.get().stream().filter(x -> x.getAge() < 18).count();
        System.out.println("Количество несовершеннолетних людей: " + yang + " чел.");

        // 2. Список фамилий призывников
        List<String> listOfFamily = supplier.get().stream()
                .filter(x -> x.getSex() == SEX.MAN)
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27)
                .map(x -> x.getFamily())
                .collect(Collectors.toList());
        System.out.println("Список фамилий призывников: " + listOfFamily);

        // 3. Список потенциальных работоспособных людей с высшим образованием
        List<Person> workers = supplier.get().stream()
                .filter(x -> x.getEducation() == Education.HIGHER)
                .filter(x -> ((x.getSex() == SEX.MAN && (x.getAge() >= 18 && x.getAge() <= 65)) || (x.getSex() == SEX.WOMAN && (x.getAge() >= 18 && x.getAge() <= 60))))
                .sorted((x,y) -> x.getFamily().compareTo(y.getFamily()))
                .collect(Collectors.toList());
        System.out.println("Список потенциальных работоспособных людей с высшим образованием: " + workers);
    }
}