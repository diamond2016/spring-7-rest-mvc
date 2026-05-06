package guru.springframework.spring7restmvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerDTO {
    private UUID id;
    private String name;
    private Integer version;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
