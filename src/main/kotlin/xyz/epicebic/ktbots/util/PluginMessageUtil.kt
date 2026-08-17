package xyz.epicebic.ktbots.util

import java.nio.ByteBuffer

fun byteBufferArray(source: ByteArray): ByteArray {
  return ByteBuffer.allocate(source.size + getVarIntLength(source.size)).put(writeVarInt(source.size)).put(source).array()
}

fun writeVarInt(value: Int): ByteArray {
  var value = value
  val data = ByteArray(getVarIntLength(value))
  var index = 0

  do {
    var temp = (value and 0b01111111).toByte()
    value = value ushr 7
    if (value != 0) {
      temp = (temp.toInt() or 0b10000000).toByte()
    }

    data[index] = temp
    index++
  } while (value != 0)

  return data
}

fun getVarIntLength(number: Int): Int {
  if ((number and -0x80) == 0) {
    return 1
  } else if ((number and -0x4000) == 0) {
    return 2
  } else if ((number and -0x200000) == 0) {
    return 3
  } else if ((number and -0x10000000) == 0) {
    return 4
  }
  return 5
}
