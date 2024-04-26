package moim_today.fake_class.schedule;

import moim_today.application.schedule.ScheduleService;
import moim_today.dto.schedule.TimeTableRequest;
import moim_today.global.error.BadRequestException;

import static moim_today.global.constant.exception.EveryTimeExceptionConstant.TIME_INPUT_ERROR;
import static moim_today.util.TestConstant.*;

public class FakeScheduleService implements ScheduleService {

    @Override
    public void fetchTimeTable(final long memberId, final TimeTableRequest timeTableRequest) {
        if (!timeTableRequest.everytimeId().equals(EVERY_TIME_ID.value())) {
            throw new BadRequestException(TIME_INPUT_ERROR.value());
        }
    }
}
