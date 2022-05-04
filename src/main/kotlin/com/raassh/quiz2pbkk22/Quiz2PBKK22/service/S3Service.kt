package com.raassh.quiz2pbkk22.Quiz2PBKK22.service

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3ObjectInputStream
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.time.LocalDateTime


@Service
class S3Service {
    private val LOG: Logger = LoggerFactory.getLogger(S3Service::class.java)

    @Autowired
    private val amazonS3: AmazonS3? = null

    @Value("\${s3.bucket.name}")
    private val s3BucketName: String? = null

    private fun convertMultiPartFileToFile(multipartFile: MultipartFile): File {
        val file = File(multipartFile.originalFilename as String)
        try {
            FileOutputStream(file).use { outputStream -> outputStream.write(multipartFile.bytes) }
        } catch (e: IOException) {
            LOG.error("Error {} occurred while converting the multipart file", e.localizedMessage)
        }
        return file
    }

    // @Async annotation ensures that the method is executed in a different thread
    @Async
    fun findByName(fileName: String?): S3ObjectInputStream? {
        LOG.info("Downloading file with name {}", fileName)
        return amazonS3!!.getObject(s3BucketName, fileName).objectContent
    }

    @Async
    fun save(multipartFile: MultipartFile): String? {
        try {
            val file: File = convertMultiPartFileToFile(multipartFile)
            val fileName: String = LocalDateTime.now().toString() + "_" + file.name
            LOG.info("Uploading file with name {}", fileName)
            val putObjectRequest = PutObjectRequest(s3BucketName, fileName, file)
            amazonS3!!.putObject(putObjectRequest)
            Files.delete(file.toPath()) // Remove the file locally created in the project folder
            return amazonS3.getUrl(s3BucketName, fileName).toString()
        } catch (e: AmazonServiceException) {
            LOG.error("Error {} occurred while uploading file", e.localizedMessage)
        } catch (ex: IOException) {
            LOG.error("Error {} occurred while deleting temporary file", ex.localizedMessage)
        }

        return null
    }
}