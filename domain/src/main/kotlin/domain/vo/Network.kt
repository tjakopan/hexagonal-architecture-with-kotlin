package domain.vo

data class Network(val address: IP, val name: String, val cidr: Int) {
  init {
    require(cidr in 1..32) { "Invalid CIDR value." }
  }
}
