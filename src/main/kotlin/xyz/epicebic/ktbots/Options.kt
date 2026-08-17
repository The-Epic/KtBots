package xyz.epicebic.ktbots

import joptsimple.ArgumentAcceptingOptionSpec
import joptsimple.OptionParser

val OptionParser = OptionParser(true)
val CountOption: ArgumentAcceptingOptionSpec<Int> = OptionParser.acceptsAll(listOf("count", "c")).withOptionalArg()!!.ofType(Int::class.java)!!.defaultsTo(1)
val AddressOption: ArgumentAcceptingOptionSpec<String> = OptionParser.accepts("ip").withRequiredArg()
val DelayOption: ArgumentAcceptingOptionSpec<Long> = OptionParser.acceptsAll(listOf("delay", "d")).withOptionalArg()!!.ofType(Long::class.java).defaultsTo(50)
val PrefixOption: ArgumentAcceptingOptionSpec<String> = OptionParser.accepts("prefix").withOptionalArg()!!.defaultsTo("")
