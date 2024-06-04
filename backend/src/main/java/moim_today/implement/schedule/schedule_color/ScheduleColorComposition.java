package moim_today.implement.schedule.schedule_color;

import moim_today.global.annotation.Implement;


@Implement
public class ScheduleColorComposition {

    private final ScheduleColorAppender scheduleColorAppender;
    private final ScheduleColorFinder scheduleColorFinder;
    private final ScheduleColorUpdater scheduleColorUpdater;

    public ScheduleColorComposition(final ScheduleColorAppender scheduleColorAppender,
                                    final ScheduleColorFinder scheduleColorFinder,
                                    final ScheduleColorUpdater scheduleColorUpdater) {
        this.scheduleColorAppender = scheduleColorAppender;
        this.scheduleColorFinder = scheduleColorFinder;
        this.scheduleColorUpdater = scheduleColorUpdater;
    }

    public void save(final long memberId, final int colorCount) {
        scheduleColorAppender.save(memberId, colorCount);
    }

    public String getColorHex(final long memberId) {
        return scheduleColorFinder.getColorHex(memberId);
    }

    public int getColorCount(final long memberId) {
        return scheduleColorFinder.getColorCount(memberId);
    }

    public void updateColorCount(final long memberId, final int count) {
        scheduleColorUpdater.updateColorCount(memberId, count);
    }
}
