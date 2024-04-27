package moim_today.implement.schedule;

import moim_today.dto.schedule.ScheduleUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.schedule.ScheduleJpaEntity;
import moim_today.persistence.repository.schedule.ScheduleRepository;
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
        scheduleJpaEntity.updateSchedule(scheduleUpdateRequest);
        validateAlreadyExist(scheduleJpaEntity);
    }

    private void validateAlreadyExist(final ScheduleJpaEntity scheduleJpaEntity) {
        if (scheduleRepository.exists(scheduleJpaEntity)) {
            throw new BadRequestException(SCHEDULE_ALREADY_EXIST.message());
        }
    }
}
