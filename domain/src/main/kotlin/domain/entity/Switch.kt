package domain.entity

import domain.entity.specification.CidrSpec
import domain.entity.specification.NetworkAmountSpec
import domain.entity.specification.NetworkAvailabilitySpec
import domain.vo.*

data class Switch(
  override val id: Id,
  override val vendor: Vendor,
  override val model: Model,
  override val ip: IP,
  override val location: Location,
  val type: SwitchType,
  val networks: List<Network> = listOf()
) : Equipment(id, vendor, model, ip, location) {
  fun addNetworkToSwitch(network: Network): Switch {
    val availabilitySpec = NetworkAvailabilitySpec(network)
    val cidrSpec = CidrSpec()
    val amountSpec = NetworkAmountSpec()
    availabilitySpec.check(this)
    cidrSpec.check(network.cidr)
    amountSpec.check(this)

    val newNetworks = networks + network
    return this.copy(networks = newNetworks)
  }

  fun removeNetworkFromSwitch(network: Network): Switch {
    val newNetworks = networks - network
    return this.copy(networks = newNetworks)
  }

  companion object {
    fun networkProtocolPredicate(protocol: Protocol): (Network) -> Boolean = { it.ip.protocol == protocol }

    fun typePredicate(type: SwitchType): (Switch) -> Boolean = { it.type == type }
  }
}