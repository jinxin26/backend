package tw.milktea.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import tw.milktea.model.OrderInfo;

import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long>, JpaSpecificationExecutor<OrderInfo> {
    OrderInfo findByOrderId(String orderId);

    @Query(value = "select order_id from order_info where order_id like CONCAT(?, '%') order by order_id desc limit 1", nativeQuery = true)
    String findTopByOrderIdLike(String orderId);

}
