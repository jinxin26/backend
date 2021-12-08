package tw.milktea.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tw.milktea.model.OrderInfo;
import tw.milktea.model.OrderRequest;
import tw.milktea.service.OrderInfoService;

import java.util.List;

@RestController
@RequestMapping("/order_info")
public class OrderInfoController {
    private final OrderInfoService orderInfoService;

    public OrderInfoController(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public OrderInfo addOrderInfo(@RequestBody OrderInfo orderInfo) {
        return orderInfoService.addOrderInfo(orderInfo);
    }

    @GetMapping("/{orderId}")
    public OrderInfo getOrderInfoById(@PathVariable String orderId) {
        return orderInfoService.getOrderInfoById(orderId);
    }

    @GetMapping()
    public List<OrderInfo> getAllOrders() {
        return orderInfoService.getAllOrders();
    }

    @PutMapping("/{orderId}/update")
    public OrderInfo updateStatusById(@PathVariable String orderId) {
        return orderInfoService.updateStatusById(orderId);
    }

    @DeleteMapping("/{orderId}/delete")
    public OrderInfo deleteOrderById(@PathVariable String orderId) {
        return orderInfoService.deleteOrderById(orderId);
    }

    @PostMapping("/page")
    public Page<OrderInfo> getAllOrdersWithPage(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.getAllOrdersWithPage(orderRequest);
    }
}
