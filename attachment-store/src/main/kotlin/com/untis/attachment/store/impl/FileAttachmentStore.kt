@file:Suppress("LeakingThis")

package com.untis.attachment.store.impl

import com.untis.attachment.store.AttachmentStore
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Component
internal class FileAttachmentStore @Autowired constructor(

    @Value("\${untis.io.attachments}")
    val path: String

) : AttachmentStore {

    private val logger = LoggerFactory.getLogger(FileAttachmentStore::class.java)

    @Suppress("JoinDeclarationAndAssignment")
    private lateinit var root: File

    init {
        root = File(path)
        logger.info("Path {} resulted in the absolute path {}", path, root.absolutePath)
        if (!root.exists()) {
            root.mkdirs()
        }
    }

    override fun save(file: MultipartFile, id: Long) = save(file.bytes, id)

    override fun save(bytes: ByteArray, id: Long) {
        if(exists(id)) throw IllegalArgumentException("An attachment file with id '$id' already exists.")

        val newFile = File(root, "/$id")
        newFile.createNewFile()

        newFile.outputStream().use { stream ->
            stream.write(bytes)
        }
    }

    override fun delete(id: Long) {
        if (!exists(id)) throw IllegalArgumentException("An attachment file with id '$id' does not exist.")

        val file = File(root, "/$id")
        file.delete()
    }

    override fun exists(id: Long): Boolean {
        val all = root.listFiles()
        return all?.any { file -> file.name == id.toString() } == true
    }

    override fun load(id: Long): Resource {
        val file = root.listFiles()?.find { file -> file.name == id.toString() }
            ?: throw IllegalArgumentException("An attachment file with id '$id' does not exist.")

        return FileSystemResource(file)
    }

}