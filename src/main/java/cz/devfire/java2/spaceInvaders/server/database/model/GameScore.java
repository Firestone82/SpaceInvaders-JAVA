package cz.devfire.java2.spaceInvaders.server.database.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String playerName;

    @Column( columnDefinition = "INT")
    private int score;

    @Column(columnDefinition = "BIGINT")
    private long time;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created = LocalDateTime.now();

}
