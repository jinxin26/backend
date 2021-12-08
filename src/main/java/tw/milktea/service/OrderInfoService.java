package tw.milktea.service;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tw.milktea.model.OrderInfo;
import tw.milktea.model.OrderRequest;
import tw.milktea.repository.OrderInfoRepository;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.stream.Collectors;

@Service
public class OrderInfoService {

    private final OrderInfoRepository orderInfoRepository;

    public OrderInfoService(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    public OrderInfo addOrderInfo(OrderInfo orderInfo) {

        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime zonedDateTime = Instant.now().atZone(zoneId);
        LocalDate localDate = zonedDateTime.toLocalDate();

        DecimalFormat decimalFormat = new DecimalFormat("00");

        String orderId = "" + localDate.getYear() + decimalFormat.format(localDate.getMonthValue())
                + decimalFormat.format(localDate.getDayOfMonth());
        String latestOrderIdForToday = orderInfoRepository.findTopByOrderIdLike(orderId);
        System.out.println(latestOrderIdForToday);
        if(latestOrderIdForToday != null) {
            String orderNumber = latestOrderIdForToday.substring(8, 12);

            orderId += String.format("%04d", Integer.parseInt(orderNumber) + 1);
        } else {
            orderId += "0001";
        }

        orderInfo.setOrderId(orderId);

        String status_id = "Submitted";
        orderInfo.setStatus(status_id);
        return orderInfoRepository.save(orderInfo);
    }

    public OrderInfo getOrderInfoById(String orderId) {
        return orderInfoRepository.findByOrderId(orderId);
    }

    public List<OrderInfo> getAllOrders() {
        return orderInfoRepository.findAll().stream()
                .filter(orderInfo -> !orderInfo.isDeleted())
                .collect(Collectors.toList());
    }

    public OrderInfo updateStatusById(String orderId) {
        OrderInfo order = orderInfoRepository.findByOrderId(orderId);
        if(order == null) {
            throw new Error();
        }
        order.setStatus("Completed");
        return orderInfoRepository.save(order);
    }

    public OrderInfo deleteOrderById(String orderId) {
        OrderInfo orderInfo = orderInfoRepository.findByOrderId(orderId);
        if(orderInfo == null) {
            throw new Error();
        }
        orderInfo.setDeleted(true);
        return orderInfoRepository.save(orderInfo);
    }

    public Page<OrderInfo> getAllOrdersWithPage(OrderRequest orderRequest) {
        Integer pageNumber = orderRequest.getPageNumber();
        Integer pageSize = orderRequest.getPageSize();
        Boolean isDeleted = orderRequest.getDeleted();
        String sortRule = orderRequest.getSortRule();

        isDeleted = isDeleted != null && isDeleted;
        Boolean isDeletedCurrent = isDeleted;

        Specification<OrderInfo> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new LinkedList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), isDeletedCurrent));

            if(orderRequest.getOrderId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("orderId"), orderRequest.getOrderId()));
            }

            if(orderRequest.getOrderStatus() != null) {
                Expression<String> exp = root.<String>get("status");
                predicates.add(exp.in(orderRequest.getOrderStatus()));
            }

            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };

        Sort sort;
        String sortField = "";
        if(orderRequest.getSortField() != null) {
            System.out.println(orderRequest.getSortField());
            sortField = orderRequest.getSortField();
        } else {
            sortField = "orderId";
        }

        if(sortRule.equals("descend")) {
            sort = Sort.by(Sort.Direction.DESC, sortField);
        } else {
            sort = Sort.by(Sort.Direction.ASC, sortField);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        Page<OrderInfo> orders = orderInfoRepository.findAll(specification, pageable);

        List<OrderInfo> collect = new ArrayList<>(orders.getContent());
        return new PageImpl<>(collect, pageable, orders.getTotalElements());
    }
}
