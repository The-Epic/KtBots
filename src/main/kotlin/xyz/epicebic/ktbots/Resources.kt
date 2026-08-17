package xyz.epicebic.ktbots

import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import java.nio.file.Path
import javax.imageio.ImageIO
import kotlin.io.path.createParentDirectories
import kotlin.io.path.createTempDirectory
import kotlin.io.path.outputStream

inline fun <reified T : Any> resource(path: String): T {
  val cl = object {}.javaClass.classLoader
  val inputStream = requireNotNull(cl.getResourceAsStream("/$path") ?: cl.getResourceAsStream(path)) { "resource $path does not exist" }

  return inputStream.use { stream ->
    when (T::class) {
      String::class -> stream.readBytes().decodeToString()
      Int::class -> stream.readBytes().decodeToString().toInt()
      ByteArray::class -> stream.readBytes()
      File::class -> readFile(path, stream).toFile()
      Path::class -> readFile(path, stream)
      BufferedImage::class -> ImageIO.read(stream)
      else -> error("invalid resource type: ${T::class}")
    } as T
  }
}

private val tempDir = createTempDirectory(prefix = "ktbots-temp-")

fun readFile(path: String, stream: InputStream): Path {
  val file = tempDir.resolve(path)
  file.createParentDirectories()
  stream.copyTo(file.outputStream())
  return file
}
