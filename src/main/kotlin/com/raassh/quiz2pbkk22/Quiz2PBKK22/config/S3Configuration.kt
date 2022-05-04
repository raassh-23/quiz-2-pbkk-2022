package com.raassh.quiz2pbkk22.Quiz2PBKK22.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class S3Configuration {
    @Value("\${access.key.id}")
    private val accessKeyId: String? = null

    @Value("\${access.key.secret}")
    private val accessKeySecret: String? = null

    @Value("\${s3.region.name}")
    private val s3RegionName: String? = null

    @Bean
    fun getAmazonS3Client(): AmazonS3? {
        val basicAWSCredentials = BasicAWSCredentials(accessKeyId, accessKeySecret)
        // Get Amazon S3 client and return the S3 client object
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(basicAWSCredentials))
            .withRegion(s3RegionName)
            .build()
    }
}