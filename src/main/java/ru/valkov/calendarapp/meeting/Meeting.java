package ru.valkov.calendarapp.meeting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.valkov.calendarapp.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "meeting")
public class Meeting implements Cloneable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meeting_id_sequence"
    )
    @SequenceGenerator(
            name = "meeting_id_sequence",
            sequenceName = "meeting_id_sequence",
            allocationSize = 1
    )
    @Column(unique = true, nullable = false)
    private Long id;
    private String groupId;
    private String name;
    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;
    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;
    private LocalDate until;

    @Override
    public Meeting clone() throws CloneNotSupportedException {
        return (Meeting) super.clone();
    }
}
