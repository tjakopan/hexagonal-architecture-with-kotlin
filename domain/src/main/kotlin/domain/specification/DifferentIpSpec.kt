package domain.specification

import domain.entity.Equipment
import domain.specification.shared.AbstractSpecification
import domain.specification.shared.check

class DifferentIpSpec(private val equipment: Equipment) : AbstractSpecification<Equipment>() {
  override fun isSatisfiedBy(t: Equipment): Boolean = equipment.ip != t.ip

  override fun check(t: Equipment) = check(t) { "It's not possible to attach routers with same IP." }
}