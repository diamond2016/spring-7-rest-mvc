package guru.springframework.spring7restmvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spring7restmvc.model.entity.Customer;

import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
