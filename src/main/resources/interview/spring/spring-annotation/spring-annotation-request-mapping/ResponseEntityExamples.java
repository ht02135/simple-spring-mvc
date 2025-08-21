/*
2. Can ResponseEntity carry List, Map, Set, or any object?
✅ Absolutely. ResponseEntity<T> can carry any Java object, including:

Single DTO (like User)
List<User>
Map<String, Object>
Set<String>
Even ApiResponse<List<User>>
Spring will automatically serialize these into JSON (or XML) 
depending on produces.
*/

@RestController
@RequestMapping("/api/demo")
public class ResponseEntityExamples {

    // return single object
    @GetMapping("/user")
    public ResponseEntity<User> getUser() {
        User user = new User("Alice", 25);
        return ResponseEntity.ok(user); // 200 OK
    }

    // return list of objects
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = List.of(
            new User("Alice", 25),
            new User("Bob", 30)
        );
        return ResponseEntity.ok(users);
    }

    // return map
    @GetMapping("/map")
    public ResponseEntity<Map<String, Object>> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Hello");
        map.put("count", 2);
        map.put("activeUsers", List.of("Alice", "Bob"));

        return ResponseEntity.ok(map);
    }

    // return set
    @GetMapping("/set")
    public ResponseEntity<Set<String>> getSet() {
        Set<String> roles = Set.of("ADMIN", "USER");
        return ResponseEntity.ok(roles);
    }

    // return with status + headers
    @GetMapping("/with-headers")
    public ResponseEntity<String> withHeaders() {
        return ResponseEntity.status(HttpStatus.ACCEPTED) // 202
                .header("X-Custom-Header", "MyValue")
                .body("Accepted request");
    }
}
