package domain.entity.specification

import domain.entity.Switch
import domain.entity.specification.shared.AbstractSpecification
import domain.entity.specification.shared.check
import domain.vo.IP
import domain.vo.Network

class NetworkAvailabilitySpec private constructor(
  private val address: IP,
  private val name: String,
  private val cidr: Int
) : AbstractSpecification<Switch>() {
  constructor(network: Network) : this(network.ip, network.name, network.cidr)

  override fun isSatisfiedBy(t: Switch): Boolean = isNetworkAvailable(t)

  private fun isNetworkAvailable(switch: Switch): Boolean =
    switch.networks.none { it.ip == address && it.name == name && it.cidr == cidr }

  override fun check(t: Switch) = check(t) { "This network already exists." }
}