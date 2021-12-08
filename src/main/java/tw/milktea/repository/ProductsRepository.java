package tw.milktea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.milktea.model.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
