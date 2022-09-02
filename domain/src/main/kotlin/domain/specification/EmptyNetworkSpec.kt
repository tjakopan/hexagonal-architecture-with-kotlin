package domain.specification

import domain.entity.Switch
import domain.specification.shared.AbstractSpecification
import domain.specification.shared.check

class EmptyNetworkSpec : AbstractSpecification<Switch>() {
  override fun isSatisfiedBy(t: Switch): Boolean = t.networks.isEmpty()

  override fun check(t: Switch) = check(t) { "It's not possible to remove a switch with networks attached to it." }
}