package xyz.epicebic.ktbots.util

import java.net.InetSocketAddress

fun parseIp(content: String): InetSocketAddress {
  val parts = content.split(":", limit = 2)

  val host = parts[0]
  val port = parts.getOrNull(1)?.toIntOrNull() ?: 25565

  return InetSocketAddress(host, port)
}
