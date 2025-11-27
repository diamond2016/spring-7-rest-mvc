package guru.springframework.spring7restmvc.mapper;

import org.mapstruct.Mapper;

import guru.springframework.spring7restmvc.model.dto.CustomerDTO;
import guru.springframework.spring7restmvc.model.entity.Customer;

/**
 * Created by jt, Spring Framework Guru.
 */
@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);

}
