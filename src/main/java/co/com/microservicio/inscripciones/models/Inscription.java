package co.com.microservicio.inscripciones.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "inscriptions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private Integer eventId;

    @Column(nullable = false)
    private BigDecimal totalQuotas;

    @Column(nullable = false)
    private BigDecimal actualQuotas;

    @ManyToMany
    @JoinTable(
            name = "assistant_inscription",
            joinColumns = @JoinColumn(name = "inscription_id"),
            inverseJoinColumns = @JoinColumn(name = "assistant_id")
    )
    private List<Assistant> assistants;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    private LocalDateTime updatedAt;

}
