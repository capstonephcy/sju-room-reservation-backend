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
public class ScheduledMetricsJobBean {
    private final JobLauncher jobLauncher;
    private final Job reservationMetricsBatchJob;

    public ScheduledMetricsJobBean(JobLauncher jobLauncher, @Qualifier("reservationMetricsBatchJob") Job reservationMetricsBatchJob) {
        this.jobLauncher = jobLauncher;
        this.reservationMetricsBatchJob = reservationMetricsBatchJob;
    }

    @Scheduled(cron = "0 0 0 * * *") // spring batch cron = seconds minutes hours day-of-month month day-of-week
    public void performReservationMetricsBatchJob() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("ReservationMetricsBatchJobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        jobLauncher.run(reservationMetricsBatchJob, params);
    }
}
