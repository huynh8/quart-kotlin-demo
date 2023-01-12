package com.example.demo

import org.quartz.JobBuilder
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.TriggerBuilder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JobHandler(val scheduler: Scheduler) {

    fun schedule(jobParams: JobParams): Flux<Map<String, Any>> {
        val jobKey = JobKey.jobKey(UUID.randomUUID().toString())
        val job = JobBuilder.newJob(LogJob::class.java)
                .storeDurably()
                .usingJobData("text", jobParams.text)
                .withIdentity(jobKey)
                .withDescription("Invoking my job")
                .build()
        val runOnceTrigger = TriggerBuilder.newTrigger()
                .startAt(Date.from(LocalDateTime.now().plusSeconds(jobParams.delay).atZone(ZoneId.systemDefault()).toInstant()))
                .build()
        val jobDate = scheduler.scheduleJob(job, runOnceTrigger)
        val map = mapOf("status" to "ok", "schedule" to jobDate)
        return Flux.just(map)
    }
}
