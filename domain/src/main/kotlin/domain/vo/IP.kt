package domain.vo

@Suppress("DataClassPrivateConstructor")
data class IP private constructor(val address: String, val protocol: Protocol) {
  constructor(address: String) : this(address, if (address.length <= 15) Protocol.IPV4 else Protocol.IPV6)
}
