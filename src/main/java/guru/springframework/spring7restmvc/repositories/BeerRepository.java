package guru.springframework.spring7restmvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spring7restmvc.model.entity.Beer;

import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
