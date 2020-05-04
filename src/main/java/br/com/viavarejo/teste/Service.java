package br.com.viavarejo.teste;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Service {
    public Flux<Department> findDepartments() {

        System.out.println("findDepartments ===>");

        final Department d1 = new Department(1000, "Department1", 1).addEmployee(new Employee(1000, 10, "OLD Employee10", 1))
                        .addEmployee(new Employee(2000, 20, "OLD Employee20", 2))
                        .addEmployee(new Employee(2000, 30, "OLD Employee30", 1))
                        .addEmployee(new Employee(1000, 40, "OLD Employee40", 1));

        final Department d2 = new Department(2000, "Department2", 1).addEmployee(new Employee(1000, 1, "Employee1", 1))
                        .addEmployee(new Employee(2000, 2, "Employee2", 2))
                        .addEmployee(new Employee(2000, 3, "Employee3", 1))
                        .addEmployee(new Employee(1000, 4, "Employee4", 1));

        return Flux.just(d1, d2);
    }

    public Flux<Employee> findEmployees() {
        return Flux.just(new Employee(1000, 1, "Employee1", 1), new Employee(2000, 2, "Employee2", 2),
                        new Employee(2000, 3, "Employee3", 1), new Employee(1000, 4, "Employee4", 1));
    }

    public Flux<Employee> findOldEmployees() {
        return Flux.just(new Employee(1000, 10, "OLD Employee10", 1), new Employee(2000, 20, "OLD Employee20", 2),
                        new Employee(2000, 30, "OLD Employee30", 1), new Employee(1000, 40, "OLD Employee40", 1));

    }

    public Flux<Employee> findEmployeesByOrganization(final Integer id) {
        return Flux.just(new Employee(1000, 1, "Employee1", id), new Employee(2000, 2, "Employee2", id));
    }

    public Mono<Organization> findOrganizationByName(final String name) {
        return Mono.just(new Organization(1, name));
    }

}
