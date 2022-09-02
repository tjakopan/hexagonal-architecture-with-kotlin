package domain.specification

import domain.entity.EdgeRouter
import domain.specification.shared.AbstractSpecification
import domain.specification.shared.check

class EmptySwitchSpec : AbstractSpecification<EdgeRouter>() {
  override fun isSatisfiedBy(t: EdgeRouter): Boolean = t.switches.isEmpty()

  override fun check(t: EdgeRouter) =
    check(t) { "It isn't allowed to remove an edge router with a switch attached to it." }
}