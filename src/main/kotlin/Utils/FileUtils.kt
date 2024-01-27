package Utils

import java.io.*

object FileUtils {
    val KEY_FOLDER = "keys/"
    val ENCRYPT_FOLDER = "encrypts/"
    val DECRYPT_FOLDER = "decrypts/"
    val INPUT = "input/"
    val OUTPUT = "output/"
    fun saveObjectAsBinary(fileName: String, obj: Serializable) {
        try {
            ObjectOutputStream(FileOutputStream(fileName)).use { oos ->
                oos.writeObject(obj)
            }
            println("Object saved as binary: $fileName")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadObjectFromBinary(fileName: String): Any? {
        return try {
            ObjectInputStream(FileInputStream(fileName)).use { ois ->
                val obj = ois.readObject()
                println("Object loaded from binary: $fileName")
                obj
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    fun listFile(folderPath: String): Array<File>? {
        val folder = File(folderPath)

        if (folder.exists() && folder.isDirectory) {
            val files = folder.listFiles()

            if (files != null) {
                return files
            }
        }
        return null
    }

    fun deleteFiles(folderPath: String) {
        val folder = File(folderPath)

        if (folder.exists() && folder.isDirectory) {
            val files = folder.listFiles()

            if (files != null) {
                files.forEach {
                    it.delete()
                }
            }
        }
    }


}