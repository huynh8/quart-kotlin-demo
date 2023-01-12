package com.example.demo

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LogJob : Job {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    override fun execute(context: JobExecutionContext?) {
        logger.info(context?.jobDetail?.jobDataMap?.getString("text") ?: "No text found")
    }

}