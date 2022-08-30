package domain.entity

import domain.entity.specification.CidrSpec
import domain.entity.specification.NetworkAmountSpec
import domain.entity.specification.NetworkAvailabilitySpec
import domain.vo.*

class Switch(
  id: Id,
  vendor: Vendor,
  model: Model,
  ip: IP,
  location: Location,
  val type: SwitchType,
  networks: List<Network> = listOf()
) : Equipment(id, vendor, model, ip, location) {
  private val _networks: MutableList<Network> = networks.toMutableList()
  val networks: List<Network>
    get() = _networks

  fun addNetworkToSwitch(network: Network): Boolean {
    val availabilitySpec = NetworkAvailabilitySpec(network)
    val cidrSpec = CidrSpec()
    val amountSpec = NetworkAmountSpec()
    availabilitySpec.check(this)
    cidrSpec.check(network.cidr)
    amountSpec.check(this)

    return _networks.add(network)
  }

  fun removeNetworkFromSwitch(network: Network): Boolean = _networks.remove(network)

  companion object {
    fun networkProtocolPredicate(protocol: Protocol): (Network) -> Boolean = { it.address.protocol == protocol }

    fun typePredicate(type: SwitchType): (Switch) -> Boolean = { it.type == type }
  }
}