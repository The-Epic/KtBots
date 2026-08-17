package xyz.epicebic.ktbots

import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch

private val Logger = LoggerFactory.getLogger("KtBots")
private val Bots = mutableListOf<Bot>()

fun main(args: Array<String>) {
  val options = OptionParser.parse(*args)
  val nicks = resource<String>("nicks.txt")
    .lineSequence()
    .map(String::trim)
    .filter(String::isNotEmpty)
    .shuffled()
    .toMutableList()

  var ip = options.valueOf(AddressOption)
  var port = 25565
  if (ip.contains(":")) {
    val (address, prt) = ip.split(":")
    ip = address
    port = prt.toInt()
  }

  val address = InetSocketAddress(ip, port)
  val count = options.valueOf(CountOption)
  val delay = options.valueOf(DelayOption)
  val prefix = options.valueOf(PrefixOption)

  Logger.info("Starting $count bots on $ip:$port")

  val latch = CountDownLatch(count)

  for (idx in 0 until count) {
    val bot = Bot(subStringName(prefix, nicks.removeFirst()), address, latch)
    bot.isDaemon = true
    Bots += bot
    bot.start()
    Thread.sleep(delay.toLong())
  }

  Logger.info("Waiting for $count bots to disconnect...")
  latch.await()
  Logger.info("All bots disconnected.")
}

fun subStringName(prefix: String, name: String): String {
  var total = prefix + name
  if (total.length > 16) {
    total = total.dropLast(prefix.length)
  }

  return total
}
