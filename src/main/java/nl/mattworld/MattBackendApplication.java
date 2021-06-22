package nl.mattworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class MattBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MattBackendApplication.class, args);
    }

    @RestController
    public static class BlaEndpoint{

        @GetMapping("/blas")
        public List<Bla> getBlas(){
            return List.of(new Bla("1","very"));
        }
    }

    public static class Bla{
        final String id;
        final String blaness;

        public Bla(String id, String blaness) {
            this.id = id;
            this.blaness = blaness;
        }

        public String getId() {
            return id;
        }

        public String getBlaness() {
            return blaness;
        }

    }

}