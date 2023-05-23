package com.sju.roomreservationbackend.common.batch;

import com.sju.roomreservationbackend.domain.notification.service.NotificationServ;
import com.sju.roomreservationbackend.domain.reservation.profile.service.NoShowCheckTask;
import com.sju.roomreservationbackend.domain.reservation.profile.service.ReservationCrudServ;
import com.sju.roomreservationbackend.domain.reservation.profile.service.ReservationNotificationSendTask;
import com.sju.roomreservationbackend.domain.room.profile.services.RoomCrudServ;
import com.sju.roomreservationbackend.domain.room.stat.service.RoomLogServ;
import com.sju.roomreservationbackend.domain.room.stat.service.RoomStatCreationTask;
import com.sju.roomreservationbackend.domain.room.stat.service.RoomStatServ;
import com.sju.roomreservationbackend.domain.user.profile.services.UserProfileCrudServ;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchNotificationConfig {
    private final RoomCrudServ roomCrudServ;
    private final ReservationCrudServ reservationCrudServ;
    private final NotificationServ notificationServ;

    @Bean
    public Tasklet sendReservationNotificationTask() {
        return new ReservationNotificationSendTask(roomCrudServ, reservationCrudServ, notificationServ);
    }

    @Bean
    public Step sendReservationNotification(JobRepository jobRepository, Tasklet sendReservationNotificationTask, PlatformTransactionManager transactionManager) {
        return new StepBuilder("sendReservationNotification", jobRepository)
                .tasklet(sendReservationNotificationTask, transactionManager)
                .build();
    }

    @Bean(name = "reservationNotificationBatchJob")
    public Job reservationNotificationBatchJob(JobRepository jobRepository, Step sendReservationNotification) {
        return new JobBuilder("reservationNotificationBatchJob", jobRepository)
                .preventRestart()
                .start(sendReservationNotification)
                .build();
    }
}
