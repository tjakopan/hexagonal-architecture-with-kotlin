package domain.vo

data class Network(val ip: IP, val name: String, val cidr: Int) {
  init {
    require(cidr in 1..32) { "Invalid CIDR value." }
  }

  companion object {
    fun protocolPredicate(protocol: Protocol): (Network) -> Boolean = { it.ip.protocol == protocol }

    fun namePredicate(name: String): (Network) -> Boolean = { it.name == name }
  }
}
