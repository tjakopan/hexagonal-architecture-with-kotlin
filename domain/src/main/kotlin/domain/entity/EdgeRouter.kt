package domain.entity

import domain.entity.specification.DifferentIpSpec
import domain.entity.specification.EmptyNetworkSpec
import domain.entity.specification.SameCountrySpec
import domain.vo.*

data class EdgeRouter(
  override val id: Id,
  override val vendor: Vendor,
  override val model: Model,
  override val ip: IP,
  override val location: Location,
  val switches: Map<Id, Switch> = mapOf()
) : Router(id, vendor, model, ip, location) {
  fun addSwitch(switch: Switch): EdgeRouter {
    val sameCountrySpec = SameCountrySpec(this)
    val diffIpSpec = DifferentIpSpec(this)
    sameCountrySpec.check(switch)
    diffIpSpec.check(switch)

    val newSwitches = switches + (switch.id to switch)

    return copy(switches = newSwitches)
  }

  fun removeSwitch(switch: Switch): EdgeRouter {
    val emptyNetworkSpec = EmptyNetworkSpec()
    emptyNetworkSpec.check(switch)

    val newSwitches = switches - switch.id

    return copy(switches = newSwitches)
  }
}