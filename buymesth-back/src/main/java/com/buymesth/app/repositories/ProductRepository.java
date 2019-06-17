package com.buymesth.app.repositories;

import com.buymesth.app.models.Product;
import com.buymesth.app.utils.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoryInAndPriceIsGreaterThanEqualAndPriceIsLessThanEqual(List<Category> categories, Double priceGreater, Double priceLess);

    @Query(value = "select category, count(*) as Total from operation o join operation_type ot on o.op_type_id=ot.id_op_type join product p on o.product_id=p.id_product where ot.name='BUY' group by category", nativeQuery = true)
    List<Object[]> countProductsByCategory();
}
