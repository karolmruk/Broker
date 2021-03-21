package kmr.kuajiri.broker.domain

import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId


class TestClock extends Clock {
    Clock clock

    TestClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    ZoneId getZone() {
        return clock.getZone()
    }

    @Override
    Clock withZone(ZoneId zoneId) {
        return clock.withZone(zoneId)
    }

    @Override
    Instant instant() {
        return clock.instant()
    }

    void tick(Duration duration) {
        this.clock = offset(this.clock, duration)
    }
}
