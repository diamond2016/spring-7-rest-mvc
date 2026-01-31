package guru.springframework.spring7restmvc.model.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor

public class BeerOrder {

    // removbe @AllArgsConstructor to manage both sides of the relationship in setCustomer
    public BeerOrder(UUID id, Integer version, String customerRef, LocalDateTime createdDate, LocalDateTime lastModifiedDate, Customer customer, Set<BeerOrderLine> beerOrderLines) {
        this.id = id;
        this.version = version;
        this.customerRef = customerRef;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.setCustomer(customer);
        this.beerOrderLines = beerOrderLines;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "char(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Version
    private Integer version;

    @Column(length = 255, columnDefinition = "varchar(255)")
    @Size(max = 255)
    private String customerRef;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    @ManyToOne
    private Customer customer;

    // override default by Lombok in order to manage both sides of the relationship. Must drop @AllArgsConstructor
    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getBeerOrders().add(this);
    }

    @OneToMany(mappedBy = "beerOrder")
    private Set<BeerOrderLine> beerOrderLines;
    
    public boolean isNew() {
        return this.id == null;
    }


}