package ru.valkov.calendarapp.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query(
            value = """
                    SELECT * FROM meeting
                    LEFT JOIN invitation_meeting im on meeting.id = im.meeting_id
                    LEFT JOIN invitation i on im.invitation_id = i.id
                    WHERE ((i.user_id = :userId AND i.invitation_status != 'REJECTED') OR (meeting.owner_id = :userId)) AND
                        meeting.begin_date_time >= :beginDateTime AND meeting.end_date_time <= :endDateTime
                    """, nativeQuery = true)
    List<Meeting> findAllByStartTimeAndEndTime(Long userId, LocalDateTime beginDateTime, LocalDateTime endDateTime);

    @Query(
            value = """
                        SELECT * FROM meeting
                        LEFT JOIN invitation_meeting im on meeting.id = im.meeting_id
                        LEFT JOIN invitation i on im.invitation_id = i.id
                        WHERE owner_id = :userId OR (i.user_id = :userId AND i.invitation_status != 'REJECTED')
                    """, nativeQuery = true)
    List<Meeting> findAllByUserId(@Param("userId") Long userId);

    List<Meeting> findAllByOwnerIdAndGroupId(Long ownerId, String groupId);

    @Query("SELECT m FROM Meeting m WHERE m.owner.id = :ownerId AND m.groupId = :groupId")
    List<Meeting> findAllByIdAndGroupId(Long ownerId, String groupId);

    void deleteByOwnerIdAndGroupId(Long ownerId, String groupId);

    @Query("SELECT m.id FROM Meeting m WHERE m.owner.id = :ownerId AND m.groupId = :groupId ORDER BY m.beginDateTime ASC")
    List<Long> getAllIdsByOwnerIdAndGroupIdOrderByBeginDateTime(Long ownerId, String groupId);
}
