package software.baller.bulkemployeeprocessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import software.baller.bulkemployeeprocessor.domain.Employee;

import java.util.function.Function;

@Configuration
public class BulkEmployeeProcessorConfig {
    @Bean
    public Function<Flux<String>, Flux<Employee>> process() {
        return inputFlux -> inputFlux
                // Split the input string into lines and skip the header
                .flatMap(message -> Flux.fromArray(message.split("\n")))
                .skip(1) // Skip the first line (e.g., header)
                // Split each row into an array of fields
                .map(row -> row.split(","))
                // Transform each array into an Employee object
                .map(employeeArray -> new Employee(
                        Integer.parseInt(employeeArray[0]), // ID
                        employeeArray[1],                   // Name
                        employeeArray[2],                   // Department
                        Integer.parseInt(employeeArray[3])  // Age
                ));
    }
}
