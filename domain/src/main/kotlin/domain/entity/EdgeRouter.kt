package domain.entity

import domain.entity.specification.DifferentIpSpec
import domain.entity.specification.EmptyNetworkSpec
import domain.entity.specification.SameCountrySpec
import domain.vo.*

class EdgeRouter(
  id: Id,
  vendor: Vendor,
  model: Model,
  ip: IP,
  location: Location,
  switches: Map<Id, Switch> = mapOf()
) : Router(id, vendor, model, ip, location) {
  private val _switches: MutableMap<Id, Switch> = switches.toMutableMap()
  val switches: Map<Id, Switch>
    get() = _switches

  fun addSwitch(switch: Switch) {
    val sameCountrySpec = SameCountrySpec(this)
    val diffIpSpec = DifferentIpSpec(this)
    sameCountrySpec.check(switch)
    diffIpSpec.check(switch)

    _switches[switch.id] = switch
  }

  fun removeSwitch(switch: Switch): Switch? {
    val emptyNetworkSpec = EmptyNetworkSpec()
    emptyNetworkSpec.check(switch)

    return _switches.remove(switch.id)
  }

  override fun toString(): String {
    return "EdgeRouter(switches=$switches)"
  }
}