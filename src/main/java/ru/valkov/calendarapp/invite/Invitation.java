package ru.valkov.calendarapp.invite;

import lombok.*;
import ru.valkov.calendarapp.meeting.Meeting;
import ru.valkov.calendarapp.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "invitation")
public class Invitation {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "invitation_id_sequence"
    )
    @SequenceGenerator(
            name = "invitation_id_sequence",
            sequenceName = "invitation_id_sequence",
            allocationSize = 1
    )
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "invitation_meeting",
            joinColumns = {
                    @JoinColumn(name = "invitation_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
            }
    )
    private Set<Meeting> meetings;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User invitedUser;
    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;
}
