package guru.springframework.spring7restmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.entity.Category;
/**
 * Created by jt, Spring Framework Guru.
 */
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
