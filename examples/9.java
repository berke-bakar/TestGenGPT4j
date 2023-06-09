import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, World!");
    }

    @PostMapping("/calculate")
    public ResponseEntity<Integer> calculateSum(@RequestParam int num1, @RequestParam int num2) {
        return ResponseEntity.ok(num1 + num2);
    }
}