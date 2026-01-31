package guru.springframework.spring7restmvc.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import guru.springframework.spring7restmvc.model.dto.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Version
    private Integer version;

    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String beerName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private BeerStyle beerStyle;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String upc;

    @DecimalMin("0.01")
    @NotNull
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Positive
    @NotNull
    private Integer quantityOnHand;

    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "beer")
    private Set<BeerOrderLine> beerOrderLines;

    @ManyToMany
    @JoinTable(name = "beer_category",
        joinColumns = @jakarta.persistence.JoinColumn(name = "beer_id"),
        inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "category_id")
    )
    
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    // mthod for many to many starting from this side (beer is owning side, we consider categories somewhat static set)
    public void addCategory(Category category) {
        this.categories.add(category);
        category.getBeers().add(this);
    }
    // this is to remove the relationship from both sides not the category itself
    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getBeers().remove(this);
    }
}

