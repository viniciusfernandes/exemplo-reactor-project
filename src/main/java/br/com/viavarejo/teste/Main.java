package br.com.viavarejo.teste;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Main {
    private static final Service SERVICE = new Service();

    public static void main(final String[] args) {
        // printAllEmployeesFromGroupedByDepartment();
        // printAllEmployeesFromOrganization();
        // printAllEmployeesFromDepartments();
        // printAllCurrentEmployeesFromOrganization();

        printAllEmployeesFromDepartmentsUperAndLowerCaseAsList();
    }

    public static void printAllEmployeesFromGroupedByDepartment() {
        System.out.println("\n--- printAllEmployeesFromGRoupedByDepartment ---");
        final Flux<Employee> employees = SERVICE.findEmployees();
        SERVICE.findDepartments()
                        .flatMap(department -> Mono.just(department)
                                        .zipWith(employees.filter(e -> e.getDepartmentId().equals(department.getId())).collectList()))
                        .map(t -> t.getT1().addEmployee(t.getT2()))
                        .subscribe(d -> {
                            System.out.println(d);
                            for (final Employee e : d.getEmployees()) {
                                System.out.println(e);
                            }
                        });
    }

    public static void printAllCurrentEmployeesFromOrganization() {
        System.out.println("\n--- printAllCurrentEmployeesFromOrganization ---");
        SERVICE.findOrganizationByName("Teste1")
                        .zipWhen(organization -> SERVICE.findEmployeesByOrganization(organization.getId()).collectList())
                        .map(t -> t.getT1().addEmployee(t.getT2()))
                        .subscribe(org -> {
                            System.out.println(org);
                            for (final Employee e : org.getEmployees()) {
                                System.out.println(e);
                            }
                        });

    }

    public static void printAllEmployeesFromOrganization() {
        System.out.println("\n--- printAllEmployeesFromOrganization ---");
        final Flux<Employee> old = SERVICE.findOldEmployees();
        final Flux<Employee> current = SERVICE.findEmployees();

        current.mergeWith(old).sort((e1, e2) -> e2.getName().compareTo(e1.getName())).subscribe(System.out::println);

    }

    public static void printAllEmployeesFromDepartments() {
        System.out.println("\n--- printAllEmployeesFromDepartments ---");
        SERVICE.findDepartments().flatMapIterable(d -> d.getEmployees()).collectList().subscribe(e -> e.forEach(System.out::println));
    }

    public static void printAllEmployeesFromDepartmentsUperAndLowerCase() {
        System.out.println("\n--- printAllEmployeesFromDepartmentsUperAndLowerCase ---");
        final Flux<Employee> Employees = SERVICE.findDepartments().flatMap(dep -> {
            System.out.println("Executando...");
            return Flux.fromIterable(dep.getEmployees());
        });

        // Employees.subscribe();
        Employees.subscribe(e -> System.out.println(e.getName().toUpperCase()));
        Employees.subscribe(e -> System.out.println(e.getName().toLowerCase()));
    }

    public static void printAllEmployeesFromDepartmentsUperAndLowerCaseAsList() {
        System.out.println("\n--- printAllEmployeesFromDepartmentsUperAndLowerCaseAsList ---");
        final Mono<List<Employee>> Employees = SERVICE.findDepartments().flatMapIterable(dep -> {
            System.out.println("Executando...");
            return dep.getEmployees();
        }).collectList();

        // Employees.subscribe();
        Employees.subscribe(emps -> emps.forEach(e -> System.out.println(e.getName().toUpperCase())));
        Employees.subscribe(emps -> emps.forEach(e -> System.out.println(e.getName().toLowerCase())));
    }

    public static void testRegex() {
        final String p = Pattern.compile(".*/") + "(.+?)" + Pattern.quote("?");
        Matcher m = Pattern.compile(p).matcher("mongodb://svcviamais/mmm\n\n/catalogo_servicos?XXX");
        m.find();

        int tot = m.groupCount();
        while (tot > 0) {
            System.out.println(m.group(tot));
            tot--;
        }

        final String line = "This order was placed for QT3000! OK?";
        final String pattern = "(.*)(\\d+)(.*)";

        // Create a Pattern object
        final Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        m = r.matcher(line);
        m.find()

        ;
        tot = m.groupCount();
        int init = 0;
        while (init <= tot) {
            System.out.println("Found value: " + m.group(init));

            init++;
        }

        final Pattern datePatt = Pattern.compile("([0-9]{1})/([0-9]{2})/([0-9]{4})");

        m = datePatt.matcher("12/65/9879");
        m.find();
        tot = m.groupCount();
        init = 0;
        while (init <= tot) {
            System.out.println("Date: " + m.group(init));
            init++;
        }

        m = Pattern.compile("(@(.)*@)").matcher("@1234@vczx_(*&¨@xxxxxxxxxxxxxxxx@kkkkkkkkk@");
        m.find();
        tot = m.groupCount();
        init = 1;
        while (init <= tot) {
            System.out.println("Anotation: " + m.group(init));
            init++;
        }

        m = Pattern.compile("/(.)+/").matcher("http:www.abc.com/ABC/ABC-Boots-in-Leather/ProD/product.aspx?iiK=34487");
        m.find();
        tot = m.groupCount();
        init = 0;
        while (init <= tot) {
            System.out.println("Slashs: " + m.group(init));
            init++;
        }

    }
}
