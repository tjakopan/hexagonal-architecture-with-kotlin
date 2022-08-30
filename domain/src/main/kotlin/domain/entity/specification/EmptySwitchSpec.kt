package domain.entity.specification

import domain.entity.EdgeRouter
import domain.entity.specification.shared.AbstractSpecification
import domain.entity.specification.shared.check

class EmptySwitchSpec : AbstractSpecification<EdgeRouter>() {
  override fun isSatisfiedBy(t: EdgeRouter): Boolean = t.switches.isEmpty()

  override fun check(t: EdgeRouter) =
    check(t) { "It isn't allowed to remove an edge router with a switch attached to it." }
}