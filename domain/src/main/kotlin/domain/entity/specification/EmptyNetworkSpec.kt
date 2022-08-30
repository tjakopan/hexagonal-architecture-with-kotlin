package domain.entity.specification

import domain.entity.Switch
import domain.entity.specification.shared.AbstractSpecification
import domain.entity.specification.shared.check

class EmptyNetworkSpec : AbstractSpecification<Switch>() {
  override fun isSatisfiedBy(t: Switch): Boolean = t.networks.isEmpty()

  override fun check(t: Switch) = check(t) { "It's not possible to remove a switch with networks attached to it." }
}