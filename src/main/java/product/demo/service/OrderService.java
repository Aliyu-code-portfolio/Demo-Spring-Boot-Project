package product.demo.service;

import org.springframework.stereotype.Service;
import product.demo.entity.Order;
import product.demo.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order){
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: "+e.getMessage());
        }
    }

    public List<Order> fetchAllOrders(){
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all orders: "+e.getMessage());
        }
    }

    public Optional<Order> fetchOrderById(Long id){
        try{
            return orderRepository.findById(id);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to fetch order: "+e.getMessage());
        }
    }

    public Optional<Order> updateOrder(Long id, Order order){
        try{
            Optional<Order> existingOptionalOrder = orderRepository.findById(id);
            if(existingOptionalOrder.isPresent()){
                Order existingOrder = existingOptionalOrder.get();
                existingOrder.setTotalPrice(order.getTotalPrice());
                Order orderUpdated = orderRepository.save(existingOrder);
                return Optional.of(orderUpdated);
            }
            else{
                return Optional.empty();
            }
        }
        catch (Exception e){
            throw new RuntimeException("Failed to update order: "+e.getMessage());
        }
    }

    public boolean deleteOrder(Long id){
        try {
            orderRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete order: "+e.getMessage());
        }
    }
}
