package org.example.contest.domain.map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MapEntity {

    @Id
    @GeneratedValue
    private Long id;
}
