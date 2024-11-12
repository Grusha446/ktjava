package ru.ithub.spring.kt1java;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderMain {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Orders> createOrded(@Valid @RequestBody Orders orders)
    {
        Orders createOrder = orderService.createOrder(orders);
        return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Orders> getAllOrders()
    {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        return orderService.getOrderByID(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @Valid @RequestBody Orders ordersDetails)
    {
        Orders updateOrder = orderService.updateOrder(id, ordersDetails);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id)
    {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
