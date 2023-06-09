package com.sju.roomreservationbackend.common.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledNotificationJobBean {
    private final JobLauncher jobLauncher;
    private final Job reservationNotificationBatchJob;

    public ScheduledNotificationJobBean(JobLauncher jobLauncher, @Qualifier("reservationNotificationBatchJob") Job reservationNotificationBatchJob) {
        this.jobLauncher = jobLauncher;
        this.reservationNotificationBatchJob = reservationNotificationBatchJob;
    }

    @Scheduled(cron = "0 * * * * *") // spring batch cron = seconds minutes hours day-of-month month day-of-week
    public void performReservationNotificationBatchJob() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("ReservationNotificationBatchJobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        jobLauncher.run(reservationNotificationBatchJob, params);
    }
}
