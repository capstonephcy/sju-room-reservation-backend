package com.sju.roomreservationbackend.common.batch;

import com.sju.roomreservationbackend.domain.reservation.profile.service.NoShowCheckTask;
import com.sju.roomreservationbackend.domain.reservation.profile.service.ReservationCrudServ;
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
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final DataSource dataSource;

    private final UserProfileCrudServ userProfileCrudServ;
    private final RoomCrudServ roomCrudServ;
    private final ReservationCrudServ reservationCrudServ;
    private final RoomLogServ roomLogServ;
    private final RoomStatServ roomStatServ;

    @Bean
    public Tasklet noShowCheckTask() {
        return new NoShowCheckTask(userProfileCrudServ, roomCrudServ, reservationCrudServ, roomLogServ);
    }

    @Bean
    public Tasklet createRoomStatTask() {
        return new RoomStatCreationTask(roomCrudServ, roomLogServ, roomStatServ);
    }

    @Bean
    public Step checkNoShowRooms(JobRepository jobRepository, Tasklet noShowCheckTask, PlatformTransactionManager transactionManager) {
        return new StepBuilder("checkNoShowRooms", jobRepository)
                .tasklet(noShowCheckTask, transactionManager)
                .build();
    }

    @Bean
    public Step createRoomStats(JobRepository jobRepository, Tasklet createRoomStatTask, PlatformTransactionManager transactionManager) {
        return new StepBuilder("createRoomStat", jobRepository)
                .tasklet(createRoomStatTask, transactionManager)
                .build();
    }

    @Bean(name = "reservationBatchJob")
    public Job job(JobRepository jobRepository, Step checkNoShowRooms, Step createRoomStats) {
        return new JobBuilder("reservationBatchJob", jobRepository)
                .preventRestart()
                .start(checkNoShowRooms)
                .next(createRoomStats)
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager() {
        return new JpaTransactionManager();
    }

    @Bean(name = "jobRepository")
    public JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(getTransactionManager());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "jobLauncher")
    public JobLauncher getJobLauncher() throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
}
