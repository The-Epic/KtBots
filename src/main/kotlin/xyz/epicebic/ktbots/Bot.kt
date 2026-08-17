package xyz.epicebic.ktbots

import org.geysermc.mcprotocollib.auth.SessionService
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.session.DisconnectedEvent
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter
import org.geysermc.mcprotocollib.network.factory.ClientNetworkSessionFactory
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.network.session.ClientNetworkSession
import org.geysermc.mcprotocollib.protocol.MinecraftConstants
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol
import org.geysermc.mcprotocollib.protocol.data.game.ClientCommand
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerCombatKillPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerPositionPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.ServerboundClientCommandPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.level.ServerboundAcceptTeleportationPacket
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch

class Bot(@get:JvmName("botName") val name: String, address: InetSocketAddress, val latch: CountDownLatch) : Thread() {
  private val logger = LoggerFactory.getLogger(this::class.java)

  private val client: ClientNetworkSession

  init {
    val protocol = MinecraftProtocol(name.substring(0, 15))

    client = ClientNetworkSessionFactory.factory().setRemoteSocketAddress(address).setProtocol(protocol).create()
  }

  override fun run() {
    val sessionService = SessionService()
    client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService)

    client.addListener(object : SessionAdapter() {

      override fun packetReceived(session: Session, packet: Packet) {
        when (packet) {
          is ClientboundLoginPacket -> {
            logger.info("$name connected.")
          }

          is ClientboundPlayerPositionPacket -> {
            client.send(
              ServerboundAcceptTeleportationPacket(packet.id)
            )
          }

          is ClientboundPlayerCombatKillPacket -> {
            client.send(
              ServerboundClientCommandPacket(
                ClientCommand.RESPAWN
              )
            )
          }
        }
      }

      override fun disconnected(event: DisconnectedEvent) {
        logger.info("Disconnected $name: ${event.reason} ${event.cause}")
        latch.countDown()
      }
    })

    logger.info("Connecting bot $name...")
    client.connect()
  }

  fun disconnect() = client.disconnect("")
}
