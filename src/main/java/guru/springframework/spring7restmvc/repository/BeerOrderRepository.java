package guru.springframework.spring7restmvc.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spring7restmvc.model.entity.BeerOrder;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
