package moim_today.implement.schedule.schedule;

import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.schedule.schedule.ScheduleJpaEntity;
import moim_today.persistence.repository.schedule.schedule.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;


import static moim_today.global.constant.exception.ScheduleExceptionConstant.SCHEDULE_ALREADY_EXIST;

@Implement
public class ScheduleUpdater {

    private final ScheduleRepository scheduleRepository;

    public ScheduleUpdater(final ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public void updateSchedule(final long memberId, final ScheduleUpdateRequest scheduleUpdateRequest) {
        ScheduleJpaEntity scheduleJpaEntity = scheduleRepository.getById(scheduleUpdateRequest.scheduleId());
        scheduleJpaEntity.validateMember(memberId);
        validateAlreadyExist(scheduleJpaEntity.getId(), memberId, scheduleUpdateRequest);

        scheduleJpaEntity.updateSchedule(scheduleUpdateRequest);
    }

    private void validateAlreadyExist(final long scheduleId, final long memberId, final ScheduleUpdateRequest scheduleUpdateRequest) {
        if (scheduleRepository.existsExcludeEntity(scheduleId, memberId, scheduleUpdateRequest)) {
            throw new BadRequestException(SCHEDULE_ALREADY_EXIST.message());
        }
    }
}
