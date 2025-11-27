package guru.springframework.spring7restmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.springframework.spring7restmvc.mapper.CustomerMapper;
import guru.springframework.spring7restmvc.model.dto.CustomerDTO;
import guru.springframework.spring7restmvc.model.entity.Customer;
import guru.springframework.spring7restmvc.repository.CustomerRepository;
import guru.springframework.spring7restmvc.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Created by jt, Spring Framework Guru.
 */
@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return Optional.ofNullable(customerMapper
                .customerToCustomerDto(customerRepository.findById(uuid).orElse(null)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        Customer customerEntity = customerMapper.customerDtoToCustomer(customer);
        if (customerEntity == null) {
            throw new IllegalArgumentException("Failed to convert CustomerDTO to Customer entity");
        }
        // persist the entity so an id is generated, then map back to DTO
        Customer saved = customerRepository.save(customerEntity);
        return customerMapper.customerToCustomerDto(saved);
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        if (customerId == null) {
            throw new IllegalArgumentException("Error, no ID provided to get CustomerDTO");
        }
        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setName(customer.getName());
            atomicReference.set(Optional.of(customerMapper
                    .customerToCustomerDto(customerRepository.save(foundCustomer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Error, no ID provided to delete CustomerDTO");
        }
        if(customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
        if (customerId == null) {
            throw new IllegalArgumentException("Eror, no ID provided to convert CustomerDTO to Customer entity");
        }
        var foundCustomer = customerRepository.findById(customerId).get();
        if (foundCustomer == null) {
            return Optional.empty();
        }
        else {
            if (StringUtils.hasText(customer.getName())) {
                foundCustomer.setName(customer.getName());
            }
        }
        
        return Optional.ofNullable(customerMapper
                .customerToCustomerDto(customerRepository.save(foundCustomer)));
    }
}
